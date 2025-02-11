import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class MultiWindow {
    private JFrame startFrame;
    private JLabel welcomeText;
    private Timer timer;
    private Random random;

    public MultiWindow() {
        startFrame = new JFrame("Image Example");
        startFrame.setSize(400, 400);
        welcomeText = new JLabel("Welcome to the Image Example", JLabel.CENTER);
        welcomeText.setBounds(100, 50, 200, 40);

        random = new Random(); // Random object for generating random positions

        startFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        startFrame.add(welcomeText);
        startFrame.setLayout(null);
        startFrame.setVisible(true);

        JButton sigmaButton = new JButton("Start Generating Images");
        sigmaButton.setActionCommand("Start");
        sigmaButton.addActionListener(new ButtonClickListener());
        sigmaButton.setBounds(100, 150, 200, 40);
        startFrame.add(sigmaButton);
    }

    public static void main(String[] args) {
        MultiWindow mWin = new MultiWindow();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Start")) {
                // Start a timer to create windows 10 times per second
                timer = new Timer(100, new ActionListener() { // Executes every 100 ms
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

    // Method to create a new window at a random position
    private void createRandomWindow() {
        JFrame imageFrame = new JFrame("Generated Image");
        JPanel imagePanel = new JPanel();
        ImageIcon icon = new ImageIcon("psycho.gif"); // Replace with your image file
        JLabel imageLabel = new JLabel(icon);

        // Add components to the frame
        imageFrame.add(imagePanel);
        imagePanel.add(imageLabel);

        // Set random position on the screen
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int x = random.nextInt(screenWidth - 200); // Random x position (leave some space for the frame size)
        int y = random.nextInt(screenHeight - 200); // Random y position (leave some space for the frame size)

        imageFrame.setLocation(x, y);
        imageFrame.pack();
        imageFrame.setVisible(true);
    }
}
