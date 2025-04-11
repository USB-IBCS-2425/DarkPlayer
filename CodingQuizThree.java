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

	public CodingQuizGUI() {
		mainF = new JFrame("Coding Quiz");
		mainF.setSize(800, 600);
		mainF.setLayout(null);
		

		mainF.setVisible(true);
	}

	public static void colorMap() {
	}

	public static void printImage() {
	}

	public static void getWords() {
	}

	public static void editStory() {
	}

	public static void main(String[] args) {
		CodingQuizGUI cqg = new CodingQuizGUI();
	}

	private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

        	if (e.getActionCommand().equals("")) {
        		colorMap();
        	}
        	if (e.getActionCommand().equals("")) {
        		printImage();
        	}
        	if (e.getActionCommand().equals("")) {
        		getWords();
        	}
        	if (e.getActionCommand().equals("")) {
        		editStory();
        	}
        }
    }
}