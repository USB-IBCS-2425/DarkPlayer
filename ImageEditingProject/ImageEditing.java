import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

class ImageEditing {
    private JFrame startFrame;
    private JLabel welcomeText;
    private ImageIcon icon;
    private JLabel pixelCol;
    private BufferedImage im;
    private JButton pengButton;
    private JButton getPixel;
    private JTextField xCoord;
    private JTextField yCoord;
    private JButton change;
    private JFrame f;
    private JPanel p;
    private JLabel lab;

    public ImageEditing() {
        startFrame = new JFrame("Image Example");
        startFrame.setSize(400, 400);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLayout(new FlowLayout());

        welcomeText = new JLabel("Welcome to the Image Example", JLabel.CENTER);
        startFrame.add(welcomeText);

        pengButton = new JButton("Penguins!");
        pengButton.setActionCommand("Penguins");
        pengButton.addActionListener(new ButtonClickListener());
        startFrame.add(pengButton);

        getPixel = new JButton("Get Pixel");
        getPixel.setActionCommand("PIX");
        getPixel.addActionListener(new ButtonClickListener());

        pixelCol = new JLabel("");
        startFrame.add(pixelCol);

        xCoord = new JTextField(3);
        yCoord = new JTextField(3);
        startFrame.add(new JLabel("X:"));
        startFrame.add(xCoord);
        startFrame.add(new JLabel("Y:"));
        startFrame.add(yCoord);

        change = new JButton("go GREEN");
        change.setActionCommand("CHANGE");
        change.addActionListener(new ButtonClickListener());
        startFrame.add(change);

        try {
            im = ImageIO.read(new File("AntarcticaCS.png"));
            icon = new ImageIcon(im);
        } catch (IOException e) {
            System.out.println("Error reading image: " + e.getMessage());
        }

        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        p = new JPanel();
        lab = new JLabel(icon);
        p.add(lab);
        f.add(p);
        f.pack();

        startFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ImageEditing();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Penguins")) {
                f.setVisible(true);
                if (getPixel.getParent() == null) {
                    startFrame.add(getPixel);
                    startFrame.revalidate();
                    startFrame.repaint();
                }
                f.setLocationRelativeTo(null);
            }

            if (command.equals("PIX")) {
                try {
                    int x = Integer.parseInt(xCoord.getText());
                    int y = Integer.parseInt(yCoord.getText());
                    int rgb = im.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;

                    int red = r - 50;
                    int green = g + 50;
                    int blue = b- 50;
                    if (r < 0) {r = 0;}
                    if (g > 255) {g = 255;}
                    if (b < 0) {b = 0;}

                    pixelCol.setText("Color: [" + r + ", " + g + ", " + b + "]");
                } catch (Exception ex) {
                    pixelCol.setText("Invalid coordinates");
                }
            }

            if (command.equals("CHANGE")) {

                

                for (int i = 0; i < im.getWidth(); i++) {
                    for (int j = 0; j < im.getHeight(); j++) {
                    	int rgb = im.getRGB(i, j);
	                    int r = (rgb >> 16) & 0xFF;
	                    int g = (rgb >> 8) & 0xFF;
	                    int b = rgb & 0xFF;

	                    r = r + 50;
	                    g = g -50;
	                    b = b+ 50;
	                    if (r > 255) {r = 255;}
	                    if (g < 0) {g = 0;}
	                    if (b > 255) {b = 255;}

	                    int col = (0xFF << 24) | (r << 16) | (g << 8) | b;
                        im.setRGB(i, j, col);
                    }
                }

                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }
        }
    }
}
