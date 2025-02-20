import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class FileReading {
	public JFrame mainframe;
	public JPanel output;
	public JTextField toRead;
	public JButton readB;
	public JLabel readingT;
	public JTextArea resultT;
	public ArrayList<String> words;

	public FileReading() {

		words = new ArrayList<String>();

		mainframe = new JFrame("File Reading example");
		mainframe.setSize(800, 600);

		mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });

		toRead = new JTextField("sample.txt");
		toRead.setBounds(300, 50, 200, 40);
        mainframe.setLayout(null);
        mainframe.add(toRead);

        output = new JPanel();
        output.setBounds(200, 250, 100, 40);
        mainframe.add(output);

        readB = new JButton("Read the file");
        readB.setActionCommand("READ");
        readB.addActionListener(new ButtonClickListener());
        readB.setBounds(320, 150, 100, 100);
        mainframe.add(readB);

        readingT = new JLabel("");
        output.add(readingT);

        resultT = new JTextArea("");
        resultT.setBounds(200, 300, 400, 240);
        mainframe.add(resultT);

        mainframe.setVisible(true);
	}

	public static void main(String[] args) {
		FileReading fo = new FileReading();
	}

	private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("READ")) {
            	String fname = toRead.getText();

            	try {
            		File f = new File(fname);
            		Scanner s = new Scanner(f);
            		while (s.hasNextLine()) {
            			String data = s.nextLine();
            			words.add(data);
            		}
            		s.close();
            	}
            	catch (FileNotFoundException err) {
            		System.out.println("An error occurred.");
            		err.printStackTrace();
            	}
            	String toDisplay = "";
            	for (int i = 0; i < words.size(); i++) {
            		toDisplay = toDisplay + "\n" + words.get(i);
            	}
            	readingT.setText("Reading...");
            	resultT.setText(toDisplay);
            }
        }
    }
}