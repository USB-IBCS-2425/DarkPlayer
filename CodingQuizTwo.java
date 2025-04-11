import java.awt.*;
import java.awt.image.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;

class CodingQuizTwo {
    private JFrame startFrame;
    private JLabel welcomeText;
    public static ImageIcon icon;
    public static BufferedImage im;
    public JButton daisyButton;
    public static JTextField fileName;
    public static JTextField theWord;
    public JButton reader;
    public JLabel outputText;
    public static ArrayList<String> textTokens;
    public static String wordRepeat;

    public CodingQuizTwo() {
        startFrame = new JFrame("Image Example");
        startFrame.setSize(600, 600);
        welcomeText = new JLabel("Welcome to the Image Example", JLabel.CENTER);
        welcomeText.setBounds(100, 50, 200, 40);

        startFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });

        startFrame.add(welcomeText);
        startFrame.setLayout(null);
       

        daisyButton = new JButton("daisy!");
        daisyButton.setActionCommand("DAISY");
        daisyButton.addActionListener(new ButtonClickListener());
        daisyButton.setBounds(50, 150, 100, 40);
        startFrame.add(daisyButton);

        reader = new JButton("read!");
        reader.setActionCommand("READ");
        reader.addActionListener(new ButtonClickListener());
        reader.setBounds(50, 250, 100, 40);
        startFrame.add(reader);

        fileName = new JTextField("");
        fileName.setBounds(50, 350, 100, 40);
        startFrame.add(fileName);

        theWord = new JTextField("");
        theWord.setBounds(50, 450, 100, 40);
        startFrame.add(theWord);


        textTokens = new ArrayList<String>();
        wordRepeat = "";



        


        startFrame.setVisible(true);
    }

    public static void main(String[] args) {
        CodingQuizTwo mWin = new CodingQuizTwo();
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();  


         
            if (command.equals("DAISY")) {
                String haha = theWord.getText();
                Random rand = new Random();
        int randomNum = rand.nextInt(10);
                for (int i = 0; i < randomNum; i++) {
                    wordRepeat += haha + ", ";
                }
                wordRepeat += haha;
                String fname = fileName.getText(); // Ensure this is a valid filename
        

        try (FileWriter w = new FileWriter(fname)) { // Try-with-resources ensures closure
            w.write(wordRepeat);
            System.out.println("File written successfully.");
        } catch (IOException er) {
            System.out.println("Error writing file:");
            er.printStackTrace();
        }
}


           
          
        
    }

           
    }
}