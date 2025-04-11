import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.util.*;

class CodingQuizGUI {

	public JFrame mainF;
	public static ArrayList<String> textTokens;
	public JButton getWordsButton;
	public JButton editStoryButton;
	public static JLabel outputText1;
	public static JLabel outputText2;

	public CodingQuizGUI() {
		mainF = new JFrame("Coding Quiz");
		mainF.setSize(800, 600);
		mainF.setLayout(null);

		outputText1 = new JLabel("Hello There");
		outputText1.setBounds(50,50,300,50);
		mainF.add(outputText1);

		outputText2 = new JLabel("Hello There");
		outputText2.setBounds(50,150,300,50);
		mainF.add(outputText2);

		getWordsButton = new JButton("GET WORDS");
		getWordsButton.setBounds(50,250,300,50);
		mainF.add(getWordsButton);

		editStoryButton = new JButton("EDIT WORDS");
		editStoryButton.setBounds(50,350,300,50);
		mainF.add(editStoryButton);




		textTokens = new ArrayList<String>();
		

		mainF.setVisible(true);
	}

	public static void colorMap() {
	}

	public static void printImage() {
	}

	public static void getWords(ArrayList<Integer> list) {
		textTokens.clear();
		//read file
		try {
			File f = new File("sampleOne.txt");
			Scanner s = new Scanner(f);
			while (s.hasNext()) {
				String data = s.next();
				textTokens.add(data);
			}
			s.close();
		} catch (FileNotFoundException err) {
			System.out.println("An error occured");
			err.printStackTrace();
		}
		
		String outputString = "getWords(): ";
		for (int i = 0; i < list.size(); i++) {
			outputString += textTokens.get(list.get(i)) + " ";
		}
		outputText1.setText(outputString);




	}

	public static void editStory(String s, String t) {
		textTokens.clear();
		//read file
		try {
			File f = new File("sampleOne.txt");
			Scanner scan = new Scanner(f);
			while (scan.hasNext()) {
				String data = scan.next();
				textTokens.add(data);
			}
			scan.close();
		} catch (FileNotFoundException err) {
			System.out.println("An error occured");
			err.printStackTrace();
		}
		String newString = "";
		for (int i = 0; i < textTokens.size(); i++) {
			String theString = textTokens.get(i);
			if (theString.equals(s)) {
				theString = t;
			}

			newString += theString + " ";
		}
		outputText2.setText(newString);
	}

	public static void main(String[] args) {
		CodingQuizGUI cqg = new CodingQuizGUI();
		ArrayList<Integer> lister = new ArrayList<>();
        	lister.add(1); lister.add(2); lister.add(4);
        	cqg.getWords(lister);
        cqg.editStory("Challenges", "ha");;

	}

	private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

        	if (e.getActionCommand().equals("")) {
        		colorMap();
        	}
        	if (e.getActionCommand().equals("")) {
        		printImage();
        	}
        	if (e.getActionCommand().equals("HI")) {
        		ArrayList<Integer> lister = new ArrayList<>();
        		lister.add(1);
        		getWords(lister);
        	}
        	if (e.getActionCommand().equals("")) {
        		editStory("Challenges", "ha");
        	}
        }
    }
}