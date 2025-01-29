import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Museum {
	private JFrame startFrame;
	private JLabel welcomeText;



	public Museum() {
		startFrame = new JFrame("Boen's Vacations Museum");
        startFrame.setSize(800, 800);
        welcomeText = new JLabel("Welcome to Boen's Vacation Museum", JLabel.CENTER);
        welcomeText.setBounds(250, 50, 300, 40);

        startFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        startFrame.add(welcomeText);
        startFrame.setLayout(null);
        startFrame.setVisible(true);
        startFrame.setLocationRelativeTo(null);

        createButton("Antarctica", 300, 150, "AntarcticaCS.png");
        createButton("France", 300, 250, "ParisCS.png");
        createButton("Netherlands", 300, 350, "NetherlandsCS.png");
        createButton("Death Valley", 300, 450, "DeathValleyCS.png");
        createButton("Iceland", 300, 550, "IcelandCS.png");
	}
	private void createButton(String text, int x, int y, String imgP) {
        JButton button = new JButton(text);
        button.setActionCommand("Start");
        button.addActionListener(new ButtonClickListener(imgP));
        button.setBounds(x, y, 200, 40);
        startFrame.add(button);
    }

	private class ButtonClickListener implements ActionListener{
		private String imagePath;
		public ButtonClickListener(String imagePath) {
			this.imagePath = imagePath;
		}
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();  
         
            if(command.equals("Start"))  {
            	JFrame f = new JFrame();
				JPanel p = new JPanel();
				ImageIcon icon = new ImageIcon(imagePath);
				JLabel lab = new JLabel(icon);
				f.add(p);
				p.add(lab);
				f.pack(); 
				f.setLocationRelativeTo(null); 
				f.setVisible(true);
            }     
      }     
   }

	public static void main(String[] args) {
		Museum mWin = new Museum();
		
	}
}