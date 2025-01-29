import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class FunPics {
    private JFrame startFrame;
    private JLabel welcomeText;
    private Timer timer;
    private Random random;

    public FunPics() {
        startFrame = new JFrame("Image Example");
        startFrame.setSize(400, 400);
        welcomeText = new JLabel("Welcome to the Image Example", JLabel.CENTER);
        welcomeText.setBounds(100, 50, 200, 40);

        random = new Random();

        startFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        startFrame.add(welcomeText);
        startFrame.setLayout(null);
        startFrame.setVisible(true);
        startFrame.setLocationRelativeTo(null);

        JButton sigmaButton = new JButton("Click to see some fun");
        sigmaButton.setActionCommand("Sigma");
        sigmaButton.addActionListener(new ButtonClickListener());
        sigmaButton.setBounds(100, 150, 200, 40);
        startFrame.add(sigmaButton);
    }

    public static void main(String[] args) {
        FunPics mWin = new FunPics();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Sigma")) {
                timer = new Timer(100, new ActionListener() { 
                    public void actionPerformed(ActionEvent evt) {
                        for (int i = 0; i < 10; i++) {
                            createRandomWindow();
                        }
                    }
                });
                timer.start();
            }
        }
    }

    
    private void createRandomWindow() {
        JFrame imageFrame = new JFrame("Generated Image");
        JPanel imagePanel = new JPanel();
        ImageIcon icon = new ImageIcon("psycho.gif"); 
        JLabel imageLabel = new JLabel(icon);

       
        imageFrame.add(imagePanel);
        imagePanel.add(imageLabel);

  
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int x = random.nextInt(screenWidth - 200); 
        int y = random.nextInt(screenHeight - 200); 

        imageFrame.setLocation(x, y);
        imageFrame.pack();
        imageFrame.setVisible(true);
    }
}
