
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

    

    public class ButtonDemo {
    private JFrame mainFrame;
    private JLabel headerLabel1, headerLabel2, headerLabel3, receipt;
    private JPanel controlPanel;
    String size = "Small";
        String topping = "Cheese";
        String drink = "";

    public ButtonDemo() {
        mainFrame = new JFrame("Button Examples");
        mainFrame.setSize(1000, 1000);
        mainFrame.setLayout(new GridLayout(4, 1));

        headerLabel1 = new JLabel("Pick your toppings, size, drinks", JLabel.LEFT);
        headerLabel2 = new JLabel("", JLabel.CENTER);
        headerLabel3 = new JLabel("", JLabel.RIGHT);
        receipt = new JLabel("", JLabel.CENTER);
        receipt.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Set up controlPanel (main panel to hold everything)
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Add the header labels and the control panel (which will contain buttons) to the main frame
       
        mainFrame.add(receipt);
        mainFrame.add(controlPanel);
        mainFrame.add(headerLabel1);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        ButtonDemo b = new ButtonDemo();
        b.showEvent();
    }

    private void showEvent() {

        JButton Pep = new JButton("Pepperoni");
        JButton Cheese = new JButton("Cheese");
        JButton Veg = new JButton("Vegetable");

        JButton Small = new JButton("Small");
        JButton Medium = new JButton("Medium");
        JButton Large = new JButton("Large");

        JButton Coke = new JButton("Coke");
        JButton Pepsi = new JButton("Pepsi");
        JButton DrPepper = new JButton("DrPepper");

        Pep.setActionCommand("aPepperoni");
        Cheese.setActionCommand("aCheese");
        Veg.setActionCommand("aVegetable");

        Small.setActionCommand("bSmall");
        Medium.setActionCommand("bMedium");
        Large.setActionCommand("bLarge");

        Coke.setActionCommand("cCoke");
        Pepsi.setActionCommand("cPepsi");
        DrPepper.setActionCommand("cDr Pepper");

        Pep.addActionListener(new ButtonClickListener()); 
        Cheese.addActionListener(new ButtonClickListener()); 
        Veg.addActionListener(new ButtonClickListener()); 

        Small.addActionListener(new ButtonClickListener()); 
        Medium.addActionListener(new ButtonClickListener()); 
        Large.addActionListener(new ButtonClickListener()); 

        Coke.addActionListener(new ButtonClickListener()); 
        Pepsi.addActionListener(new ButtonClickListener()); 
        DrPepper.addActionListener(new ButtonClickListener()); 

        controlPanel.add(Pep);
        controlPanel.add(Cheese);
        controlPanel.add(Veg);  
        controlPanel.add(Small);
        controlPanel.add(Medium);
        controlPanel.add(Large); 
        controlPanel.add(Coke);
        controlPanel.add(Pepsi);
        controlPanel.add(DrPepper);      

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();  
            switch(command.charAt(0)){
                case 'a':
                    topping = command.substring(1, command.length());
                    break;
                case 'b':
                    size = command.substring(1, command.length());
                    break;
                case 'c':
                    drink = command.substring(1, command.length());
                    break;
            }
            receipt.setText(topping + "\n" + size + "\n" + drink);     
      }     
   }
}
