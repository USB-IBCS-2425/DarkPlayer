import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

class LitAnalysisLab {
    public JFrame mainframe;
    public JPanel output;
    public JButton readB;
    public JButton avgB;
    public JButton writeB;
    public JButton mostB;
    public JButton leastB;
    public JButton longestB;
    public JButton shortestB;
    public JButton mostVB;
    public JButton leastVB;
    public JButton avgSB;
    public JButton uniqueB;

    public static JTextField toRead;
    public static JTextArea resultT;
    public static ArrayList<String> textTokens;
    public static ArrayList<String> allwords;
    public static ArrayList<String> punct_allwords;
    public static HashMap<String, Integer> wordFrequency;

    public LitAnalysisLab() {
        textTokens = new ArrayList<>();
        allwords = new ArrayList<>();
        punct_allwords = new ArrayList<>();
        wordFrequency = new HashMap<>();

        mainframe = new JFrame("Literature Analysis");
        mainframe.setSize(1700, 600);

        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        toRead = new JTextField("MobyDick2.txt");
        toRead.setBounds(300, 50, 200, 40);
        mainframe.setLayout(null);
        mainframe.add(toRead);

        output = new JPanel();
        output.setBounds(200, 250, 100, 40);
        mainframe.add(output);

        readB = new JButton("Read File");
        readB.setActionCommand("READ");
        readB.addActionListener(new ButtonClickListener());
        readB.setBounds(200, 150, 100, 100);
        mainframe.add(readB);

        avgB = new JButton("Average Word");
        avgB.setActionCommand("AVG");
        avgB.addActionListener(new ButtonClickListener());
        avgB.setBounds(340, 150, 100, 100);
        mainframe.add(avgB);

        writeB = new JButton("Write File");
        writeB.setActionCommand("WRITE");
        writeB.addActionListener(new ButtonClickListener());
        writeB.setBounds(480, 150, 100, 100);
        mainframe.add(writeB);

        mostB = new JButton("Most Common");
        mostB.setActionCommand("MOST");
        mostB.addActionListener(new ButtonClickListener());
        mostB.setBounds(620, 150, 100, 100);
        mainframe.add(mostB);

        leastB = new JButton("Least Common");
        leastB.setActionCommand("LEAST");
        leastB.addActionListener(new ButtonClickListener());
        leastB.setBounds(740, 150, 100, 100);
        mainframe.add(leastB);

        longestB = new JButton("Longest Word");
        longestB.setActionCommand("LONG");
        longestB.addActionListener(new ButtonClickListener());
        longestB.setBounds(880, 150, 100, 100);
        mainframe.add(longestB);

        shortestB = new JButton("Shortest Word");
        shortestB.setActionCommand("SHORT");
        shortestB.addActionListener(new ButtonClickListener());
        shortestB.setBounds(1020, 150, 100, 100);
        mainframe.add(shortestB);

        mostVB = new JButton("Most Vowels");
        mostVB.setActionCommand("MOSTV");
        mostVB.addActionListener(new ButtonClickListener());
        mostVB.setBounds(1140, 150, 100, 100);
        mainframe.add(mostVB);

        leastVB = new JButton("Least Vowels");
        leastVB.setActionCommand("LEASTV");
        leastVB.addActionListener(new ButtonClickListener());
        leastVB.setBounds(1260, 150, 100, 100);
        mainframe.add(leastVB);

        avgSB = new JButton("Average Sentence");
        avgSB.setActionCommand("AVGSENT");
        avgSB.addActionListener(new ButtonClickListener());
        avgSB.setBounds(1380, 150, 100, 100);
        mainframe.add(avgSB);

        uniqueB = new JButton("Unique Sentence");
        uniqueB.setActionCommand("UNIQUE");
        uniqueB.addActionListener(new ButtonClickListener());
        uniqueB.setBounds(620, 250, 100, 100);
        mainframe.add(uniqueB);



        resultT = new JTextArea("");
        resultT.setBounds(200, 300, 400, 240);

        JScrollPane scrollPane = new JScrollPane(resultT);
        scrollPane.setBounds(200, 300, 400, 240);
        mainframe.add(scrollPane);

        mainframe.setVisible(true);

        
    }

    public static void main(String[] args) {
        new LitAnalysisLab();
    }

    public static double round(double x, int places) {
        int mult = (int) Math.pow(10, places);
        int y = (int) (x * mult);
        return y / (double) mult;
    }

    public static void readFile() {
        String fname = toRead.getText();
        textTokens.clear();

        try {
            File f = new File(fname);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                textTokens.add(s.next());
            }
            s.close();
        } catch (FileNotFoundException err) {
            System.out.println("An error occurred.");
            err.printStackTrace();
        }
    }

    public static void parseWords() {
        allwords.clear();

        int asteriskCount = 0;
        for (String token : textTokens) {
            String[] tempWords = token.split("\\s+|--|[-–—]");  // Splits by spaces, --
            
            for (String s : tempWords) {
                if (!s.isEmpty()) {  // Ensure it's not an empty string
                    if (s.equals("***")) {
                        asteriskCount++;
                        continue;
                    }
                    
                    // Efficient punctuation removal using a character loop
                    StringBuilder cleanedWord = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            cleanedWord.append(Character.toLowerCase(c));
                        }
                    }

                    if (asteriskCount == 2 && cleanedWord.length() > 0) {
                        allwords.add(cleanedWord.toString());
                    }
                }
            }
        }

        wordFrequency.clear();
        for (String word : allwords) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }


        
    }

    public static void printBook() {
        StringBuilder toDisplay = new StringBuilder();
        for (String word : allwords) {
            toDisplay.append("\n").append(word);
        }
        resultT.setText(toDisplay.toString());
    }

    public static void showAvg() {
        double totLen = 0;
        for (String w : allwords) {
            totLen += w.length();
        }
        double avgLen = round(totLen / allwords.size(), 2);
        resultT.setText("The average word length is:\n" + avgLen + " characters");
    }

    public static void writeFile() {
        String fname = toRead.getText();
        String toWrite = resultT.getText();

        try {
            FileWriter w = new FileWriter(fname);
            w.write(toWrite);
            w.close();
        } catch (IOException er) {
            System.out.println("Error message:");
            er.printStackTrace();
        }
    }

    public static void mostCommon() {
        
        ArrayList<String> mostCommon = new ArrayList<String>();
        int maxCount = 0;
        for (String word : wordFrequency.keySet()) {
            if (wordFrequency.get(word) > maxCount) {
                maxCount = wordFrequency.get(word);
                mostCommon.clear(); mostCommon.add(word);
            } 
            else if (wordFrequency.get(word) == maxCount && mostCommon.size() < 5) {
                mostCommon.add(word);
            }
        }
        String res = "The most common word(s):\n";
        for (String word : mostCommon) {
            res += word + "\n";
        }
        resultT.setText(res);
    }

    public static void leastCommon() {
        
        ArrayList<String> leastCommon = new ArrayList<String>();
        int minCount = Integer.MAX_VALUE;
        for (String word : wordFrequency.keySet()) {
            if (wordFrequency.get(word) < minCount) {
                minCount = wordFrequency.get(word);
                leastCommon.clear(); leastCommon.add(word);
            } 
            else if (wordFrequency.get(word) == minCount && leastCommon.size() < 5) {
                leastCommon.add(word);
            }
        }
        String res = "The least common word(s):\n";
        for (String word : leastCommon) {
            res += word + "\n";
        }
        resultT.setText(res);
    }

    public static void longestWord() {
        
        int maxLength = 0;
        ArrayList<String> longest = new ArrayList<>();

        for (String word : wordFrequency.keySet()) {
            if (word.length() > maxLength) {
                maxLength = word.length();
                longest.clear();
                longest.add(word);
            }
            else if (word.length() == maxLength) {
                longest.add(word);
            }
        }
        String res = "The longest word(s): \n";
        for (int i = 0; i < longest.size(); i++) {
            res += longest.get(i) + "\n";
        }

        resultT.setText(res);
    }

    public static void shortestWord() {
        
        int minLength = 2147483647;
        ArrayList<String> shortest = new ArrayList<>();

        for (String word : wordFrequency.keySet()) {
            if (word.length() < minLength && word.length() > 0) {
                minLength = word.length();
                shortest.clear();
                shortest.add(word);
            }
            else if (word.length() == minLength) {
                shortest.add(word);
            }
        }
        String res = "The shortest word(s): \n";
        for (int i = 0; i < shortest.size(); i++) {
            res += shortest.get(i) + "\n";
        }

        resultT.setText(res);
    }

    public static void mostVowels() {
        ArrayList<String> most = new ArrayList<>();
        int maxVowels = 0;
        for (String word : wordFrequency.keySet()) {
            int vowels = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || 
                    word.charAt(i) == 'o' || word.charAt(i) == 'u') {
                    vowels++;
                }
            }
            if (vowels > maxVowels) {
                maxVowels = vowels;
                most.clear();
                most.add(word);
            }
            else if (vowels == maxVowels) {
                most.add(word);
            }
        }
        String res = "The word(s) with the most vowels: \n";
        for (int i = 0; i < most.size(); i++) {
            res += most.get(i) + "\n";
        }
        resultT.setText(res);
    }

    public static void leastVowels() {
        ArrayList<String> least = new ArrayList<>();
        int minVowels = 2147483647;
        for (String word : wordFrequency.keySet()) {
            int vowels = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || 
                    word.charAt(i) == 'o' || word.charAt(i) == 'u') {
                    vowels++;
                }
            }
            if (vowels < minVowels) {
                minVowels = vowels;
                least.clear();
                least.add(word);
            }
            else if (vowels == minVowels) {
                least.add(word);
            }
        }
        String res = "The word(s) with the least vowels: \n";
        for (int i = 0; i < least.size(); i++) {
            res += least.get(i) + "\n";
        }
        resultT.setText(res);
    }

    public static void avgSentence() {
        int astrikCount = 0;
        double runningCount = 0;
        int numSent = 0;
        int curSent = 0;
        for (String token : textTokens) {
            String[] tempWords = token.split("\\s+|--|[-–—]");  // Splits by spaces, --
            for (String s : tempWords) {
                if (s.equals("***")) {astrikCount++; continue;}
                if (astrikCount != 2) {continue;}
                if (s.length() > 1) { // Ensure it's not an empty string
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == '?' || s.charAt(i) == '.' || s.charAt(i) == '!') {
                            if (curSent < 3) {numSent--; runningCount-=curSent;}
                            curSent = 0;
                            numSent++;
                            break;
                        }
                    }
                    curSent++;
                    runningCount++;
                }
                
            }
        }
        String res = "The average sentence length is: " + String.valueOf(runningCount/numSent);

        resultT.setText(res);
    }

    public static void mostUnique() {
        double min_uniqueInd = 2147483647;
        double cur_uniqueInd = 0;

        ArrayList<String> uniqueSent = new ArrayList<>();
        ArrayList<String> curSent = new ArrayList<>();

        // getting rid of the header and footer
        int astrikCount = 0;

        for (String token : textTokens) {
            String[] tempWords = token.split("\\s+|--|[-–—]");  // Splits by spaces, --
            for (String s : tempWords) {
                if (!s.isEmpty()) { // Ensure it's not an empty string
                    if (s.equals("***")) {astrikCount++; continue;}
                    if (astrikCount != 2) {continue;}
                    boolean end = false;
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == '?' || s.charAt(i) == '.' || s.charAt(i) == '!') {
                            end = true;
                            break;
                        }
                    }
                    s = s.replaceAll("[\\p{P}_]", "");
                    s = s.toLowerCase();
                    curSent.add(s);

                    cur_uniqueInd += wordFrequency.getOrDefault(s, 0);
                    if (end){
                        if (curSent.size() >= 8) {
                            System.out.println(cur_uniqueInd);
                            cur_uniqueInd /= curSent.size();
                            if (cur_uniqueInd < min_uniqueInd) {
                                min_uniqueInd = cur_uniqueInd;
                                uniqueSent = new ArrayList<>(curSent);
                            }
                        }
                        
                        cur_uniqueInd = 0;
                        curSent.clear();
                    }
                    
                }
                
            }
        }

        String res = "The most unique sentence is: ";
        for (int i = 0; i < uniqueSent.size(); i++) {
            res += uniqueSent.get(i) + " ";
        }
        resultT.setText(res);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("WRITE")) {
                writeFile();
            }

            if (command.equals("READ")) {
                readFile();
                parseWords();
                printBook();
                
            }
            if (command.equals("LONG")) {
                longestWord();
            }
            if (command.equals("SHORT")) {
                shortestWord();
            }

            if (command.equals("AVG")) {
                parseWords();
                showAvg();
            }

            if (command.equals("MOST")) {
                mostCommon();
            }
            if (command.equals("LEAST")) {
                leastCommon();
            }
            if (command.equals("MOSTV")) {
                mostVowels();
            }
            if (command.equals("LEASTV")) {
                leastVowels();
            }
            if (command.equals("AVGSENT")) {
                avgSentence();
            }
            if (command.equals("UNIQUE")) {
                mostUnique();
            }
        }
    }
}