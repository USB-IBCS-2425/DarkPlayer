import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class ResourcePlant {
    private String resourceType;
    private Space game;
    private int level; // Player-facing level (1-indexed for owned plants)
    private long initialCost; // Used for buyable plants
    private long[] upgradeCosts;
    private int[] productionIntervalsMillis;
    private Timer generationTimer;
    private JLabel plantInfoLabel;
    private JButton buyButtonRef;
    private JButton upgradeButtonRef;
    private int productionAmountCurrentCycle; // Store amount for this cycle for UI/energy calc

    private boolean isPreEstablished;
    private int internalLevelOffset; // For pre-established plants to map player level to config level

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
            this.level = 1; // Start at player-facing level 1
            this.internalLevelOffset = Space.PRE_ESTABLISHED_PLANT_INTERNAL_LEVEL_OFFSET;
            if (this.buyButtonRef != null) {
                this.buyButtonRef.setVisible(false); // Hide buy button
            }
            startGeneration();
        } else {
            this.level = 0; // Not owned
            this.internalLevelOffset = 0; // No offset for regularly bought plants
            if (this.buyButtonRef != null) {
                this.buyButtonRef.setVisible(true);
            }
        }
        // updateUI(); // Called by Space constructor after all plants are created
    }

    public void buy() {
        if (isPreEstablished) {
            game.statusLabel.setText(resourceType + " Plant is pre-established and active.");
            game.statusLabel.setForeground(Color.ORANGE);
            return;
        }
        if (level > 0) {
            game.statusLabel.setText(resourceType + " Plant already owned.");
            game.statusLabel.setForeground(Color.ORANGE);
            return;
        }
        if (game.energy >= initialCost) {
            game.energy -= initialCost;
            level = 1; // Set to level 1 upon purchase
            startGeneration();
            game.statusLabel.setText(resourceType + " Plant purchased!");
            game.statusLabel.setForeground(new Color(0, 128, 0));
            // updateUI(); // Will be called by game.updatePlantUIsActive()
            game.updateLabels();
        } else {
            game.statusLabel.setText("Not enough energy for " + resourceType + " Plant (Cost: " + initialCost + ")!");
            game.statusLabel.setForeground(Color.RED);
        }
    }

    public void upgrade() {
        if (level == 0) { // This implies !isPreEstablished
            game.statusLabel.setText("Buy the " + resourceType + " Plant first!");
            game.statusLabel.setForeground(Color.RED);
            return;
        }

        int currentActualConfigIndex = level - 1 + internalLevelOffset;
        
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
            level++; // Increment player-facing level
            if (generationTimer != null) generationTimer.stop(); // Stop and restart to apply new interval
            startGeneration();
            game.statusLabel.setText(resourceType + " Plant upgraded to Level " + level + "!");
            game.statusLabel.setForeground(new Color(0, 128, 0));
            // updateUI(); // Will be called by game.updatePlantUIsActive()
            game.updateLabels();
        } else {
            game.statusLabel.setText("Not enough energy to upgrade (Cost: " + currentUpgradeCost + ")!");
            game.statusLabel.setForeground(Color.RED);
        }
    }

    private void startGeneration() {
        if (level > 0 && !game.gameOver) {
            int actualConfigIndex = level - 1 + internalLevelOffset;
            if (actualConfigIndex < 0 || actualConfigIndex >= productionIntervalsMillis.length) {
                // Should not happen with proper max level checks, but as a safeguard:
                if (generationTimer != null) generationTimer.stop();
                plantInfoLabel.setText(resourceType + " Plant Error: Invalid Level Config");
                return;
            }
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
         // If population is 1 or less, non-energy plants do not produce and do not consume energy.
        if (!"Energy".equals(resourceType) && game.population <= 1) {
            // Plant is idle, no production, no energy cost. UI should reflect this.
            // To ensure UI updates correctly if population changes, game.updatePlantUIsActive() should be called then.
            return; 
        }

        productionAmountCurrentCycle = Math.max(game.population / 3, 1);
        if (resourceType.equals("Energy")) { // Energy plant has simpler production logic
             productionAmountCurrentCycle = Math.max(game.population / 10, 1); // Energy plant scaling
             if (level > 4) productionAmountCurrentCycle = Math.max(game.population / 5, 2); // Bonus for higher levels
             if (level > 6) productionAmountCurrentCycle = Math.max(game.population / 3, 3);
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

        if (!canProduce && !"Energy".equals(resourceType)) {
            // Optional: status message that plant couldn't produce due to lack of energy
             game.statusLabel.setText(resourceType + " Plant needs more energy to operate!");
             game.statusLabel.setForeground(Color.ORANGE.darker());
        }

        game.updateLabels(); // Update main resource labels
        // game.updatePlantUIsActive(); // This might be too frequent, update from game loop or events.
    }

    public void updateUI() {
        if (buyButtonRef != null) { // General handling for buy button if it exists
            buyButtonRef.setText("Buy " + resourceType + " Plant"); // Default text
            if (isPreEstablished || level > 0) {
                buyButtonRef.setVisible(false); // Hide if pre-established or already bought
                buyButtonRef.setEnabled(false);
            } else {
                buyButtonRef.setVisible(true); // Show if buyable and not yet bought
                buyButtonRef.setEnabled(!game.gameOver && game.energy >= initialCost);
            }
        }

        if (level == 0 && !isPreEstablished) { // Not owned (only for buyable plants like Energy)
            plantInfoLabel.setText(String.format("%s Plant: Not Owned (Cost: %d E)", resourceType, initialCost));
            upgradeButtonRef.setEnabled(false);
            upgradeButtonRef.setText("Upgrade " + resourceType);
        } else { // Owned (either pre-established or bought)
            int actualConfigIndex = level - 1 + internalLevelOffset;
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

    public void setInactive() { // Called on Game Over
        stopGeneration();
        if (buyButtonRef != null) buyButtonRef.setEnabled(false);
        upgradeButtonRef.setEnabled(false);
        if (level == 0 && !isPreEstablished) {
             plantInfoLabel.setText(String.format("%s Plant: Not Owned", resourceType));
        } else {
             int actualConfigIndex = Math.min(Math.max(0, level - 1 + internalLevelOffset), productionIntervalsMillis.length -1);
             double intervalSeconds = productionIntervalsMillis[actualConfigIndex] / 1000.0;
             plantInfoLabel.setText(String.format("<html>%s Plant Lvl: %d<br>(INACTIVE)</html>", resourceType, level));
        }
    }
     public int getPlayerLevel() { return level; }
}