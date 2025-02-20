import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
/*Important*/
// Below are the 5 book files
// GreatGatsby.txt, MobyDick.txt, LittleWomen.txt, Frankenstein.txt, PridePrejudice.txt

class EBookReader {
    public JFrame mainframe;
    public JPanel output;
    public JTextField toRead;
    public JButton readB;
    public JButton writeB;
    public JLabel readingT;
    public JTextArea resultT;
    public ArrayList<String> words;
    public ArrayList<String> allwords;
    public JButton analysB;
    public JButton leastB;
    public JButton mostB;
    public HashMap<String, Integer> wordFrequency;

    public EBookReader() {
        words = new ArrayList<String>();
        allwords = new ArrayList<String>();
        wordFrequency = new HashMap<String, Integer>();

        mainframe = new JFrame("File Reading example");
        mainframe.setSize(800, 600);

        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });

        toRead = new JTextField("GreatGatsby.txt");
        toRead.setBounds(300, 50, 200, 40);
        mainframe.setLayout(null);
        mainframe.add(toRead);

        output = new JPanel();
        output.setBounds(200, 250, 100, 40);
        mainframe.add(output);

        readB = new JButton("Read the file");
        readB.setActionCommand("READ");
        readB.addActionListener(new ButtonClickListener());
        readB.setBounds(100, 150, 100, 100);
        mainframe.add(readB);

        analysB = new JButton("STATS");
        analysB.setActionCommand("ANALYZE");
        analysB.addActionListener(new ButtonClickListener());
        analysB.setBounds(240, 150, 100, 100);
        mainframe.add(analysB);

        writeB = new JButton("Write to the file");
        writeB.setActionCommand("WRITE");
        writeB.addActionListener(new ButtonClickListener());
        writeB.setBounds(380, 150, 100, 100);
        mainframe.add(writeB);

        leastB = new JButton("Least Common Word");
        leastB.setActionCommand("LEAST");
        leastB.addActionListener(new ButtonClickListener());
        leastB.setBounds(520, 150, 100, 100);
        mainframe.add(leastB);

        mostB = new JButton("Most Common Word");
        mostB.setActionCommand("MOST");
        mostB.addActionListener(new ButtonClickListener());
        mostB.setBounds(660, 150, 100, 100);
        mainframe.add(mostB);

        readingT = new JLabel("");
        output.add(readingT);

        resultT = new JTextArea("");
        resultT.setBounds(200, 300, 400, 240);
        mainframe.add(resultT);

        JScrollPane scrollPane = new JScrollPane(resultT);
        scrollPane.setBounds(200, 300, 400, 240);
        mainframe.add(scrollPane);

        mainframe.setVisible(true);
    }

    public static void main(String[] args) {
        EBookReader fo = new EBookReader();
    }

    public static double round(double x, int places) {
        int mult = (int)Math.pow(10, places);
        int y = (int)(x*mult);
        return y / (double) mult;
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("WRITE")) {
                String fname = toRead.getText();
                String toWrite = resultT.getText();

                try {
                    FileWriter w = new FileWriter(fname);
                    w.write(toWrite);
                    w.close();
                }
                catch (IOException er) {
                    System.out.println("Error message:");
                    er.printStackTrace();
                }
            }

            if (command.equals("READ")) {
                String fname = toRead.getText();

                words.clear();
                allwords.clear();
                wordFrequency.clear();

                try {
                    File f = new File(fname);
                    Scanner s = new Scanner(f);
                    while (s.hasNext()) {
                        String data = s.next();
                        words.add(data);
                    }
                    s.close();
                }
                catch (FileNotFoundException err) {
                    System.out.println("An error occurred.");
                    err.printStackTrace();
                }

                for (String word : words) {
                    String[] tempWords = word.split("[^a-zA-Z0-9]");
                    for (String s : tempWords) {
                        if (!s.isEmpty()) {
                            allwords.add(s.toUpperCase());
                        }
                    }
                }

                for (String word : allwords) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }

                String toDisplay = "";
                for (int i = 0; i < words.size(); i++) {
                    toDisplay = toDisplay + "\n" + words.get(i);
                }
                resultT.setText("File size is " + words.size() + " lines");
                readingT.setText("Reading...");
                resultT.setText(resultT.getText() + "\n" + toDisplay);
            }

            if (command.equals("LEAST")) {
                ArrayList<String> leastCommon = new ArrayList<String>();
                int minCount = Integer.MAX_VALUE;
                for (String word : wordFrequency.keySet()) {
                    if (wordFrequency.get(word) < minCount) {
                        minCount = wordFrequency.get(word);
                        leastCommon.clear(); leastCommon.add(word);
                    } 
                    else if (wordFrequency.get(word) == minCount) {
                        leastCommon.add(word);
                    }
                }
                String res = "The least common word(s):\n";
                for (String word : leastCommon) {
                    res += word + "\n";
                }
                resultT.setText(res);
            }

            if (command.equals("MOST")) {
                ArrayList<String> mostCommon = new ArrayList<String>();
                int maxCount = 0;
                for (String word : wordFrequency.keySet()) {
                    if (wordFrequency.get(word) > maxCount) {
                        maxCount = wordFrequency.get(word);
                        mostCommon.clear(); mostCommon.add(word);
                    } 
                    else if (wordFrequency.get(word) == maxCount) {
                        mostCommon.add(word);
                    }
                }
                String res = "The most common word(s):\n";
                for (String word : mostCommon) {
                    res += word + "\n";
                }
                resultT.setText(res);
            }

            if (command.equals("ANALYZE")) {
                double totLen = 0;
                for (String w : allwords) {
                    totLen = totLen + w.length();
                }
                double avgLen = totLen / allwords.size();
                avgLen = round(avgLen, 2);
                String res = "The average word length is:\n";
                res = res + avgLen + " characters";
                resultT.setText(res);
            }
        }
    }
}