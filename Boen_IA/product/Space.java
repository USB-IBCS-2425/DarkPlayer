import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Space extends JFrame {

    private int population = 10;
    private long energy = 0; 
    private int food = 100; 
    private int air = 100;  
    private int water = 100; 

    private JLabel populationLabel;
    private JLabel energyLabel;
    private JLabel foodLabel;
    private JLabel airLabel;
    private JLabel waterLabel;
    private JLabel statusLabel;

    // Resource costs
    private static final int FOOD_COST_PER_UNIT_RAW = 10;
    private static final int AIR_COST_PER_UNIT_RAW = 8;
    private static final int WATER_COST_PER_UNIT_RAW = 9;

    private Timer consumptionTimer;
    private Timer growthTimer;
    private boolean gameOver = false;

    private final int CONSUMPTION_INTERVAL = 5000; // 5 seconds
    private final int GROWTH_INTERVAL = 10000;     // 10 seconds

    private int foodAtLastGrowthCheck;
    private int airAtLastGrowthCheck;
    private int waterAtLastGrowthCheck;

    private JButton clickButton;

    // Resource plants
    private ResourcePlant foodPlant;
    private ResourcePlant airPlant;
    private ResourcePlant waterPlant;
    private ResourcePlant energyPlant;

    private JButton buyFoodPlantButton, upgradeFoodPlantButton;
    private JButton buyAirPlantButton, upgradeAirPlantButton;
    private JButton buyWaterPlantButton, upgradeWaterPlantButton;
    private JButton buyEnergyPlantButton, upgradeEnergyPlantButton;
    private JLabel foodPlantLabel, airPlantLabel, waterPlantLabel, energyPlantLabel;

    private static final double[] PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS = {
            5.5, 4.5, 3.5, 2.5, 1.5, 0.75, 0.5
    };
    private static final double[] ENERGY_PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS = {
            1.5, 1.0, 0.75, 0.45, 0.3, 0.15, 0.075
    };
    private int[] plantProductionIntervalsMillis;
    private int[] energyPlantProductionIntervalsMillis;

    // Initial costs are for plants
    private static final long ENERGY_PLANT_INITIAL_COST = 300;

    
    private static final long[] FOOD_PLANT_UPGRADE_COSTS = {50, 75, 88, 100, 150, 250, 500}; 
    private static final long[] AIR_PLANT_UPGRADE_COSTS = {40, 50, 70, 90, 140, 225, 450};  
    private static final long[] WATER_PLANT_UPGRADE_COSTS = {45, 55, 80, 95, 145, 240, 475}; 
    private static final long[] ENERGY_PLANT_UPGRADE_COSTS = {300, 400, 500, 1000, 2000, 4000, 8000}; 

    // map containing all the resource animators (ex. +1 -1)
    private Map<String, ResourceAnimator> resourceAnimators = new HashMap<>();

    private int currentMonth = 1;
    private int currentYear = 1;
    private JLabel dateLabel;
    private Timer gameMonthTimer;

    private PopulationGraphPanel populationGraphPanel;
    private List<Integer> populationHistory = new ArrayList<>();
    private static final int MAX_HISTORY_POINTS = 100;

    private JLabel populationAnimLabel;


    public Space() {
        setTitle("Space Colonization Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 850);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        this.foodAtLastGrowthCheck = this.food;
        this.airAtLastGrowthCheck = this.air;
        this.waterAtLastGrowthCheck = this.water;

        // converting to ms
        plantProductionIntervalsMillis = new int[PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS.length];
        for (int i = 0; i < PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS.length; i++) {
            plantProductionIntervalsMillis[i] = (int) (PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS[i] * 1000);
        }
        energyPlantProductionIntervalsMillis = new int[ENERGY_PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS.length];
        for (int i = 0; i < ENERGY_PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS.length; i++) {
            energyPlantProductionIntervalsMillis[i] = (int) (ENERGY_PLANT_UPGRADE_INTERVAL_LEVELS_SECONDS[i] * 1000);
        }

        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        energyLabel = new JLabel("Energy: " + energy);
        energyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        energyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel energyPanelWithAnimation = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        energyPanelWithAnimation.add(energyLabel);
        JLabel energyAnimLabel = new JLabel("");
        energyPanelWithAnimation.add(energyAnimLabel);
        resourceAnimators.put("Energy", new ResourceAnimator(energyAnimLabel));

        clickButton = new JButton("Click for Energy!");
        clickButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clickButton.setPreferredSize(new Dimension(200, 60)); // width x height
        clickButton.addActionListener(e -> {
            if (gameOver) return;
            int energyPerClick = Math.max(1, population / 2); 
            energy += energyPerClick;
            resourceAnimators.get("Energy").showAnimation("+" + energyPerClick, new Color(0,150,0));
            updateLabels();
            updatePlantUIsActive();
        });

        topPanel.add(energyPanelWithAnimation, BorderLayout.NORTH);
        topPanel.add(clickButton, BorderLayout.CENTER);

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(dateLabel, BorderLayout.SOUTH);


        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setLayout(new GridLayout(0, 1, 10, 5)); 
        resourcesPanel.setBorder(BorderFactory.createTitledBorder("Colony Resources"));

        populationLabel = new JLabel("Population: " + population);
        populationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel populationDisplayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        populationDisplayPanel.add(populationLabel);
        populationAnimLabel = new JLabel("");
        populationDisplayPanel.add(populationAnimLabel);
        resourceAnimators.put("Population", new ResourceAnimator(populationAnimLabel));

        Font resourceFont = new Font("Arial", Font.PLAIN, 14);

        JPanel foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        foodLabel = new JLabel("Food: " + food);
        foodLabel.setFont(resourceFont);
        JLabel foodAnimLabel = new JLabel("");
        foodPanel.add(foodLabel);
        foodPanel.add(foodAnimLabel);
        resourceAnimators.put("Food", new ResourceAnimator(foodAnimLabel));

        JPanel airPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        airLabel = new JLabel("Air: " + air);
        airLabel.setFont(resourceFont);
        JLabel airAnimLabel = new JLabel("");
        airPanel.add(airLabel);
        airPanel.add(airAnimLabel);
        resourceAnimators.put("Air", new ResourceAnimator(airAnimLabel));

        JPanel waterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        waterLabel = new JLabel("Water: " + water);
        waterLabel.setFont(resourceFont);
        JLabel waterAnimLabel = new JLabel("");
        waterPanel.add(waterLabel);
        waterPanel.add(waterAnimLabel);
        resourceAnimators.put("Water", new ResourceAnimator(waterAnimLabel));

        resourcesPanel.add(populationDisplayPanel);
        resourcesPanel.add(foodPanel);
        resourcesPanel.add(airPanel);
        resourcesPanel.add(waterPanel);


        JPanel plantsPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Label, Buy(Hidden or active), Upgrade
        plantsPanel.setBorder(BorderFactory.createTitledBorder("Resource Plants"));
        Font plantFont = new Font("Arial", Font.PLAIN, 12);

        foodPlantLabel = new JLabel(); foodPlantLabel.setFont(plantFont);
        buyFoodPlantButton = new JButton(); buyFoodPlantButton.setFont(plantFont); // Will be hidden
        upgradeFoodPlantButton = new JButton(); upgradeFoodPlantButton.setFont(plantFont);
        plantsPanel.add(foodPlantLabel);
        plantsPanel.add(buyFoodPlantButton);
        plantsPanel.add(upgradeFoodPlantButton);

        airPlantLabel = new JLabel(); airPlantLabel.setFont(plantFont);
        buyAirPlantButton = new JButton(); buyAirPlantButton.setFont(plantFont); // Will be hidden
        upgradeAirPlantButton = new JButton(); upgradeAirPlantButton.setFont(plantFont);
        plantsPanel.add(airPlantLabel);
        plantsPanel.add(buyAirPlantButton);
        plantsPanel.add(upgradeAirPlantButton);

        waterPlantLabel = new JLabel(); waterPlantLabel.setFont(plantFont);
        buyWaterPlantButton = new JButton(); buyWaterPlantButton.setFont(plantFont); // Will be hidden
        upgradeWaterPlantButton = new JButton(); upgradeWaterPlantButton.setFont(plantFont);
        plantsPanel.add(waterPlantLabel);
        plantsPanel.add(buyWaterPlantButton);
        plantsPanel.add(upgradeWaterPlantButton);

        energyPlantLabel = new JLabel(); energyPlantLabel.setFont(plantFont);
        buyEnergyPlantButton = new JButton(); buyEnergyPlantButton.setFont(plantFont);
        upgradeEnergyPlantButton = new JButton(); upgradeEnergyPlantButton.setFont(plantFont);
        plantsPanel.add(energyPlantLabel);
        plantsPanel.add(buyEnergyPlantButton);
        plantsPanel.add(upgradeEnergyPlantButton);

        // Initialize plants: Food, Air, Water are pre-established
        foodPlant = new ResourcePlant("Food", this, 0, FOOD_PLANT_UPGRADE_COSTS, plantProductionIntervalsMillis, foodPlantLabel, buyFoodPlantButton, upgradeFoodPlantButton, true);
        airPlant = new ResourcePlant("Air", this, 0, AIR_PLANT_UPGRADE_COSTS, plantProductionIntervalsMillis, airPlantLabel, buyAirPlantButton, upgradeAirPlantButton, true);
        waterPlant = new ResourcePlant("Water", this, 0, WATER_PLANT_UPGRADE_COSTS, plantProductionIntervalsMillis, waterPlantLabel, buyWaterPlantButton, upgradeWaterPlantButton, true);
        energyPlant = new ResourcePlant("Energy", this, ENERGY_PLANT_INITIAL_COST, ENERGY_PLANT_UPGRADE_COSTS, energyPlantProductionIntervalsMillis, energyPlantLabel, buyEnergyPlantButton, upgradeEnergyPlantButton, false);

        // Action Listeners for plant buttons
        upgradeFoodPlantButton.addActionListener(e -> { if (!gameOver) foodPlant.upgrade(); updatePlantUIsActive();});
        upgradeAirPlantButton.addActionListener(e -> { if (!gameOver) airPlant.upgrade(); updatePlantUIsActive();});
        upgradeWaterPlantButton.addActionListener(e -> { if (!gameOver) waterPlant.upgrade(); updatePlantUIsActive();});

        buyEnergyPlantButton.addActionListener(e -> { if (!gameOver) energyPlant.buy(); updatePlantUIsActive();});
        upgradeEnergyPlantButton.addActionListener(e -> { if (!gameOver) energyPlant.upgrade(); updatePlantUIsActive();});


        JPanel centerSubPanel = new JPanel(new BorderLayout(0, 10));
        centerSubPanel.add(resourcesPanel, BorderLayout.NORTH);
        centerSubPanel.add(plantsPanel, BorderLayout.CENTER);

        populationGraphPanel = new PopulationGraphPanel();
        populationGraphPanel.setData(populationHistory);

        JPanel mainGameAreaPanel = new JPanel(new BorderLayout(0, 10));
        mainGameAreaPanel.add(centerSubPanel, BorderLayout.CENTER);
        mainGameAreaPanel.add(populationGraphPanel, BorderLayout.SOUTH);


        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusLabel = new JLabel("Welcome to your Space Colony!");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.BLUE);
        statusPanel.add(statusLabel);

        add(topPanel, BorderLayout.NORTH);
        add(mainGameAreaPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        setupTimers();
        updateGameDateAndGraph();
        updateLabels(); // Initial label update
        updatePlantUIsActive(); // Initial plant UI update
        setVisible(true);
    }

    private void setupTimers() {
        consumptionTimer = new Timer(CONSUMPTION_INTERVAL, e -> consumeResources());
        growthTimer = new Timer(GROWTH_INTERVAL, e -> growPopulation());
        gameMonthTimer = new Timer(1000, e -> updateGameDateAndGraph());

        if (!gameOver) {
            consumptionTimer.start();
            growthTimer.start();
            gameMonthTimer.start();
        }
    }

    private void updatePlantUIsActive() {
        if (foodPlant != null) foodPlant.updateUI();
        if (airPlant != null) airPlant.updateUI();
        if (waterPlant != null) waterPlant.updateUI();
        if (energyPlant != null) energyPlant.updateUI();
    }


    private void updateGameDateAndGraph() {
        if (gameOver) {
            if (gameMonthTimer != null) gameMonthTimer.stop();
            return;
        }

        currentMonth++;
        if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
        }

        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        String monthName = monthNames[currentMonth - 1];
        dateLabel.setText(String.format("Date: %s Year %04d", monthName, currentYear));

        populationHistory.add(population);
        if (populationHistory.size() > MAX_HISTORY_POINTS) {
            populationHistory.remove(0);
        }
        if (populationGraphPanel != null) {
            populationGraphPanel.repaint();
        }
    }


    private void consumeResources() {
        if (gameOver || population <= 0) { 
             if (population <= 1 && !gameOver) {
                handleGameOver("Colony population collapse!");
            }
            return;
        }


        int consumedAmount = population; 

        food -= consumedAmount;
        resourceAnimators.get("Food").showAnimation("-" + consumedAmount, Color.ORANGE.darker());
        air -= consumedAmount;
        resourceAnimators.get("Air").showAnimation("-" + consumedAmount, Color.ORANGE.darker());
        water -= consumedAmount;
        resourceAnimators.get("Water").showAnimation("-" + consumedAmount, Color.ORANGE.darker());


        statusLabel.setText("Population consumed resources.");
        statusLabel.setForeground(Color.DARK_GRAY);

        boolean resourcesDepleted = false;
        if (food < 0) { food = 0; resourcesDepleted = true;} 
        if (air < 0) { air = 0; resourcesDepleted = true;}
        if (water < 0) { water = 0; resourcesDepleted = true;}

        if (resourcesDepleted && population > 1) { 
            statusLabel.setText("Resources critical! Population declining.");
            statusLabel.setForeground(Color.RED.darker());

            int oldPopulation = population;
            population = (int) Math.floor(population * 0.6);
            if (population < 0) population = 0;


            int populationChange = population - oldPopulation;
            if (populationChange < 0) {
                resourceAnimators.get("Population").showAnimation(String.valueOf(populationChange), Color.RED.darker());
            }

            if (population <= 1) {
                population = 0; 
                handleGameOver("Your colony ran out of vital resources and the population perished!");
            }
        }
        updateLabels();
        updatePlantUIsActive(); // Production rate might depend on population for UI display
    }

    private void growPopulation() {
        if (gameOver || population <= 0) return; // No growth if no one is left or game over

        if (population < 2) {
             foodAtLastGrowthCheck = food;
             airAtLastGrowthCheck = air;
             waterAtLastGrowthCheck = water;
             updateLabels();
             return;
        }

        boolean foodIncreasedSufficiently = food > foodAtLastGrowthCheck*0.8 && food >= population * 2;
        boolean airIncreasedSufficiently = air > airAtLastGrowthCheck*0.8 && air >= population * 2;
        boolean waterIncreasedSufficiently = water > waterAtLastGrowthCheck*0.8 && water >= population * 2;

        if (food > 0 && air > 0 && water > 0 &&
                foodIncreasedSufficiently && airIncreasedSufficiently && waterIncreasedSufficiently) {
            population += Math.max(population/2, 1);
            resourceAnimators.get("Population").showAnimation("+" + Math.max(population/2, 1), new Color(0, 128, 0));
            statusLabel.setText("Population grew by " + Math.max(population/2, 1) + "!");
            statusLabel.setForeground(new Color(0, 100, 0));
            
        } else {
            String reason = "Poor conditions leading to famine!";
            if (!(food > 0 && air > 0 && water > 0)) reason = "Essential resources are zero.";
            else if (!foodIncreasedSufficiently) reason += " Food levels not sufficiently increased or too low.";
            else if (!airIncreasedSufficiently) reason += " Air levels not sufficiently increased or too low.";
            else if (!waterIncreasedSufficiently) reason += " Water levels not sufficiently increased or too low.";
            else reason += " Resource levels may not be increasing enough or are too low relative to population.";
            statusLabel.setText(reason);
            statusLabel.setForeground(Color.ORANGE.darker());
        }

        foodAtLastGrowthCheck = food;
        airAtLastGrowthCheck = air;
        waterAtLastGrowthCheck = water;
        updateLabels();
        updatePlantUIsActive(); // UI for plant production might change with population
    }

    private void handleGameOver(String message) {
        if (gameOver) return;
        gameOver = true;

        statusLabel.setText("GAME OVER! " + message);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        if (consumptionTimer != null) consumptionTimer.stop();
        if (growthTimer != null) growthTimer.stop();
        if (gameMonthTimer != null) gameMonthTimer.stop();

        clickButton.setEnabled(false);

        if (foodPlant != null) foodPlant.setInactive();
        if (airPlant != null) airPlant.setInactive();
        if (waterPlant != null) waterPlant.setInactive();
        if (energyPlant != null) energyPlant.setInactive();

        updateLabels(); 
        updatePlantUIsActive(); 
    }

    public void updateLabels() {
        energyLabel.setText("Energy: " + energy);
        populationLabel.setText("Population: " + population);
        foodLabel.setText("Food: " + food);
        airLabel.setText("Air: " + air);
        waterLabel.setText("Water: " + water);
    }

    class ResourceAnimator {
        private JLabel animLabel;
        private Timer animTimer;
        private final int ANIM_DURATION = 1500;

        public ResourceAnimator(JLabel label) {
            this.animLabel = label;
        }

        public void showAnimation(String text, Color color) {
            if (animTimer != null && animTimer.isRunning()) {
                animTimer.stop();
            }
            animLabel.setText(text);
            animLabel.setForeground(color);
            animLabel.setFont(new Font("Arial", Font.BOLD, 14));

            animTimer = new Timer(ANIM_DURATION, e -> {
                animLabel.setText("");
                ((Timer)e.getSource()).stop();
            });
            animTimer.setRepeats(false);
            animTimer.start();
        }
        public void showAnimation(String text) {
            showAnimation(text, new Color(0, 128, 0));
        }
    }

    class ResourcePlant {
        private String resourceType;
        private Space game;
        private int level; 
        private long initialCost; 
        private long[] upgradeCosts;
        private int[] productionIntervalsMillis;
        private Timer generationTimer;
        private JLabel plantInfoLabel;
        private JButton buyButtonRef;
        private JButton upgradeButtonRef;
        private int productionAmountCurrentCycle; 

        private boolean isPreEstablished;
        public ResourcePlant(String resourceType, Space game, long initialCost, long[] upgradeCosts,
                             int[] productionIntervalsMillis, JLabel plantInfoLabel,
                             JButton buyButtonRef, JButton upgradeButtonRef, boolean isPreEstablished) {
            this.resourceType = resourceType;
            this.game = game;
            this.initialCost = initialCost;
            this.upgradeCosts = upgradeCosts;
            this.productionIntervalsMillis = productionIntervalsMillis;
            this.plantInfoLabel = plantInfoLabel;
            this.buyButtonRef = buyButtonRef;
            this.upgradeButtonRef = upgradeButtonRef;
            this.isPreEstablished = isPreEstablished;

            if (this.isPreEstablished) {
                this.level = 1; 
                if (this.buyButtonRef != null) {
                    this.buyButtonRef.setVisible(false); // Hide buy button
                }
                startGeneration();
            } else {
                this.level = 0; // Not owned
                if (this.buyButtonRef != null) {
                    this.buyButtonRef.setVisible(true);
                }
            }
        }

        public void buy() {
            if (game.energy >= initialCost) {
                game.energy -= initialCost;
                level = 1; 
                startGeneration();
                game.statusLabel.setText(resourceType + " Plant purchased!");
                game.statusLabel.setForeground(new Color(0, 128, 0));
                game.updateLabels();
            } else {
                game.statusLabel.setText("Not enough energy for " + resourceType + " Plant (Cost: " + initialCost + ")!");
                game.statusLabel.setForeground(Color.RED);
            }
        }

        public void upgrade() {
            if (level == 0) { 
                game.statusLabel.setText("Buy the " + resourceType + " Plant first!");
                game.statusLabel.setForeground(Color.RED);
                return;
            }

            int currentActualConfigIndex = level - 1;
            
            // Check if max level based on defined upgrade costs
            if (currentActualConfigIndex >= upgradeCosts.length) {
                 game.statusLabel.setText(resourceType + " Plant is at max level (no more upgrade costs defined)!");
                 game.statusLabel.setForeground(Color.BLUE);
                 return;
            }
            // Check if max level based on defined production intervals
            if (currentActualConfigIndex + 1 >= productionIntervalsMillis.length) {
                 game.statusLabel.setText(resourceType + " Plant is at max level (no faster interval defined)!");
                 game.statusLabel.setForeground(Color.BLUE);
                 return;
            }

            long currentUpgradeCost = upgradeCosts[currentActualConfigIndex];

            if (game.energy >= currentUpgradeCost) {
                game.energy -= currentUpgradeCost;
                level++; 
                if (generationTimer != null) generationTimer.stop(); 
                startGeneration();
                game.statusLabel.setText(resourceType + " Plant upgraded to Level " + level + "!");
                game.statusLabel.setForeground(new Color(0, 128, 0));
                game.updateLabels();
            } else {
                game.statusLabel.setText("Not enough energy to upgrade (Cost: " + currentUpgradeCost + ")!");
                game.statusLabel.setForeground(Color.RED);
            }
        }

        private void startGeneration() {
            if (level > 0 && !game.gameOver) {
                int actualConfigIndex = level - 1;
                int interval = productionIntervalsMillis[actualConfigIndex];
                if (generationTimer == null) {
                    generationTimer = new Timer(interval, e -> generateResource());
                    generationTimer.setInitialDelay(interval); // Start producing after first interval
                } else {
                    generationTimer.setDelay(interval);
                    generationTimer.setInitialDelay(interval);
                }
                if (!generationTimer.isRunning()) {
                    generationTimer.start();
                }
            } else if (generationTimer != null && generationTimer.isRunning()) {
                generationTimer.stop();
            }
        }

        public void stopGeneration() {
            if (generationTimer != null) generationTimer.stop();
        }

        private void generateResource() {
            if (game.gameOver) {
                stopGeneration();
                return;
            }

            productionAmountCurrentCycle = game.population;
            if (resourceType.equals("Energy")) { 
                 productionAmountCurrentCycle = Math.max(game.population / 5, 1); // Energy plant scaling
            }


            String animText = "+" + productionAmountCurrentCycle;
            Color animColor = new Color(0, 150, 0);
            long energyCostForProduction = 0;
            boolean canProduce = true;

            switch (resourceType) {
                case "Food":
                    energyCostForProduction = (long) (Space.FOOD_COST_PER_UNIT_RAW * productionAmountCurrentCycle * 0.5);
                    if (game.energy >= energyCostForProduction) {
                        game.energy -= energyCostForProduction;
                        game.food += productionAmountCurrentCycle;
                        game.resourceAnimators.get("Food").showAnimation(animText, animColor);
                    } else { canProduce = false; }
                    break;
                case "Air":
                    energyCostForProduction = (long) (Space.AIR_COST_PER_UNIT_RAW * productionAmountCurrentCycle * 0.5);
                    if (game.energy >= energyCostForProduction) {
                        game.energy -= energyCostForProduction;
                        game.air += productionAmountCurrentCycle;
                        game.resourceAnimators.get("Air").showAnimation(animText, animColor);
                    } else { canProduce = false; }
                    break;
                case "Water":
                    energyCostForProduction = (long) (Space.WATER_COST_PER_UNIT_RAW * productionAmountCurrentCycle * 0.5);
                    if (game.energy >= energyCostForProduction) {
                        game.energy -= energyCostForProduction;
                        game.water += productionAmountCurrentCycle;
                        game.resourceAnimators.get("Water").showAnimation(animText, animColor);
                    } else { canProduce = false; }
                    break;
                case "Energy": // Energy plant produces energy, doesn't consume it for production here.
                    game.energy += productionAmountCurrentCycle;
                    game.resourceAnimators.get("Energy").showAnimation(animText, animColor);
                    break;
            }

            if (!canProduce && !resourceType.equals("Energy")) {
                 game.statusLabel.setText(resourceType + " Plant needs more energy to operate!");
                 game.statusLabel.setForeground(Color.ORANGE.darker());
            }

            game.updateLabels(); // Update main resource labels
        }

        public void updateUI() {
            if (buyButtonRef != null) { 
                buyButtonRef.setText("Buy " + resourceType + " Plant");
                if (isPreEstablished || level > 0) {
                    buyButtonRef.setVisible(false); 
                    buyButtonRef.setEnabled(false);
                } else {
                    buyButtonRef.setVisible(true); // Show if Buyable 
                    buyButtonRef.setEnabled(!game.gameOver && game.energy >= initialCost);
                }
            }

            if (level == 0 && !isPreEstablished) { 
                plantInfoLabel.setText(String.format("%s Plant: Not Owned (Cost: %d E)", resourceType, initialCost));
                upgradeButtonRef.setEnabled(false);
                upgradeButtonRef.setText("Upgrade " + resourceType);
            } else { 
                int actualConfigIndex = level - 1;
                double intervalSeconds = productionIntervalsMillis[actualConfigIndex] / 1000.0;
                
                int displayProductionAmount = Math.max(game.population / 3, 1);
                 if (resourceType.equals("Energy")) { // Use energy plant specific scaling for display
                    displayProductionAmount = Math.max(game.population / 10, 1);
                    if (level > 4) displayProductionAmount = Math.max(game.population / 5, 2);
                    if (level > 6) displayProductionAmount = Math.max(game.population / 3, 3);
                }

                if (!"Energy".equals(resourceType) && game.population <= 1) {
                    displayProductionAmount = 0; // Show 0 production if idle
                    plantInfoLabel.setText(String.format("<html>%s Plant Lvl: %d<br>(Idle - Low Pop)</html>", resourceType, level));
                } else {
                     plantInfoLabel.setText(String.format("<html>%s Plant Lvl: %d<br>Prod: %d u / %.2fs</html>",
                                         resourceType, level, displayProductionAmount, intervalSeconds));
                }


                // Max level checks for upgrade button
                boolean canUpgradeCost = actualConfigIndex < upgradeCosts.length;
                boolean canUpgradeInterval = actualConfigIndex + 1 < productionIntervalsMillis.length;

                if (canUpgradeCost && canUpgradeInterval) {
                    long nextUpgradeCost = upgradeCosts[actualConfigIndex];
                    upgradeButtonRef.setText(String.format("Upgrade %s (%d E)", resourceType, nextUpgradeCost));
                    upgradeButtonRef.setEnabled(!game.gameOver && game.energy >= nextUpgradeCost);
                } else {
                    upgradeButtonRef.setText(resourceType + " Max Lvl");
                    upgradeButtonRef.setEnabled(false);
                }
            }
        }

        public void setInactive() { // Called for Game Over
            stopGeneration();
            if (buyButtonRef != null) buyButtonRef.setEnabled(false);
            upgradeButtonRef.setEnabled(false);
            if (level == 0 && !isPreEstablished) {
                 plantInfoLabel.setText(String.format("%s Plant: Not Owned", resourceType));
            } else {
                 int actualConfigIndex = Math.min(Math.max(0, level - 1), productionIntervalsMillis.length -1);
                 double intervalSeconds = productionIntervalsMillis[actualConfigIndex] / 1000.0;
                 plantInfoLabel.setText(String.format("<html>%s Plant Lvl: %d<br>(INACTIVE)</html>", resourceType, level));
            }
        }
         public int getPlayerLevel() { return level; }
    }

    class PopulationGraphPanel extends JPanel {
        private List<Integer> data;
        private static final int PADDING = 25;
        private static final int POINT_RADIUS = 3;

        public PopulationGraphPanel() {
            setPreferredSize(new Dimension(600, 150));
            setBackground(Color.WHITE); 
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) {
                g.setColor(Color.WHITE);
                g.drawString("No population data yet.", getWidth()/2 - 50, getHeight()/2);
                return;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int maxPop = 0;
            for (int pop : data) {
                if (pop > maxPop) {
                    maxPop = pop;
                }
            }
            if (maxPop == 0) maxPop = 10; // Avoid division by zero if pop is always 0

            int graphWidth = getWidth() - 2 * PADDING;
            int graphHeight = getHeight() - 2 * PADDING;

            // Draw axes
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawLine(PADDING, getHeight() - PADDING, PADDING, PADDING); // Y-axis
            g2d.drawLine(PADDING, getHeight() - PADDING, getWidth() - PADDING, getHeight() - PADDING); // X-axis

            // Draw axis labels (simple)
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawString("Time", getWidth() - PADDING - 30, getHeight() - PADDING + 15);
            g2d.drawString("Pop", PADDING - 20, PADDING - 5);
             g2d.drawString(String.valueOf(maxPop), PADDING - 20, PADDING +10 );


            // Plot data
            g2d.setColor(new Color(100, 200, 255)); // Light blue for graph line
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(2f));

            if (data.size() < 2) { 
                 for (int i = 0; i < data.size(); i++) {
                    int x = PADDING + (i * graphWidth / Math.max(1, (MAX_HISTORY_POINTS -1) )) ; // Use MAX_HISTORY_POINTS for scaling X
                    int yVal = data.get(i);
                    int y = getHeight() - PADDING - (yVal * graphHeight / maxPop);
                    g2d.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);
                }
            } else {
                for (int i = 0; i < data.size() - 1; i++) {
                    int x1 = PADDING + (i * graphWidth / Math.max(1, (data.size() - 1)));
                    int y1Val = data.get(i);
                    int y1 = getHeight() - PADDING - (y1Val * graphHeight / maxPop);

                    int x2 = PADDING + ((i + 1) * graphWidth / Math.max(1, (data.size() - 1)));
                    int y2Val = data.get(i + 1);
                    int y2 = getHeight() - PADDING - (y2Val * graphHeight / maxPop);

                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.fillOval(x1 - POINT_RADIUS, y1 - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);
                    if (i == data.size() -2) { // Draw last point
                         g2d.fillOval(x2 - POINT_RADIUS, y2 - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);
                    }
                }
            }
            g2d.setStroke(oldStroke);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Space::new);
    }
}