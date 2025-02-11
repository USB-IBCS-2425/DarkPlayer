import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

class ImageEditingProject {
    private JFrame startFrame;
    private JLabel welcomeText;
    private ImageIcon icon;
    private BufferedImage original;
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

    private JButton isolate_Yellow;

    private JButton reset;

    private JButton blur;

    private JButton zoom;
    private JButton edge;
    private JButton saturate;


    public ImageEditingProject() {
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

        change = new JButton("Make Contrast");
        change.setActionCommand("CONTRAST");
        change.addActionListener(new ButtonClickListener());
        startFrame.add(change);

        isolate_Yellow = new JButton("Highlight Yellow");
        isolate_Yellow.setActionCommand("HIGHLIGHT");
        isolate_Yellow.addActionListener(new ButtonClickListener());
        startFrame.add(isolate_Yellow);

        reset = new JButton("Reset");
        reset.setActionCommand("RESET");
        reset.addActionListener(new ButtonClickListener());
        startFrame.add(reset);

        blur = new JButton("Rotate");
        blur.setActionCommand("ROTATE");
        blur.addActionListener(new ButtonClickListener());
        startFrame.add(blur);

        zoom = new JButton("Zoom");
        zoom.setActionCommand("ZOOM");
        zoom.addActionListener(new ButtonClickListener());
        startFrame.add(zoom);

        edge = new JButton("Outline");
        edge.setActionCommand("EDGE_DETECTION");
        edge.addActionListener(new ButtonClickListener());
        startFrame.add(edge);

        saturate = new JButton("Saturate");
        saturate.setActionCommand("SATURATE");
        saturate.addActionListener(new ButtonClickListener());
        startFrame.add(saturate);




        try {
            im = ImageIO.read(new File("AntarcticaCS.png"));
            original = ImageIO.read(new File("AntarcticaCS.png"));
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
        new ImageEditingProject();
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

            else if (command.equals("CONTRAST")) {

                for (int i = 0; i < im.getWidth(); i++) {
                    for (int j = 0; j < im.getHeight(); j++) {
                    	int rgb = im.getRGB(i, j);
	                    int r = (rgb >> 16) & 0xFF;
	                    int g = (rgb >> 8) & 0xFF;
	                    int b = rgb & 0xFF;

	                    if (r > 128) r = Math.min(255, r + 50);
                        else r = Math.max(0, r - 50);

                        if (g > 128) g = Math.min(255, g + 50);
                        else g = Math.max(0, g - 50);

                        if (b > 128) b = Math.min(255, b + 50);
                        else b = Math.max(0, b - 50);

                        int col = (0xFF << 24) | (r << 16) | (g << 8) | b;
                        im.setRGB(i, j, col);
                    }
                }

                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }

            else if (command.equals("HIGHLIGHT")) {
                for (int i = 0; i < im.getWidth(); i++) {
                    for (int j = 0; j < im.getHeight(); j++) {
                        int rgb = im.getRGB(i, j);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;

                        if (r > 128 && g > 128 && b < 128) {
                            int col = (0xFF << 24) | (r << 16) | (g << 8) | b;
                            im.setRGB(i, j, col);
                        } else {
                            int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                            int col = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
                            im.setRGB(i, j, col);
                        }
                    }
                }
                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }
            

            else if (command.equals("RESET")) {

                for (int i = 0; i < im.getWidth(); i++) {
                    for (int j = 0; j < im.getHeight(); j++) {
                        int rgb = original.getRGB(i, j);
                        im.setRGB(i, j, rgb);
                    }
                }
                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }

            else if (command.equals("ROTATE")) {
                int width = im.getWidth();
                int height = im.getHeight();

                for (int y = 0; y < height / 2; y++) {
                    for (int x = 0; x < width; x++) {
                        int currentPixel = im.getRGB(x, y);
                        int rotPixel = im.getRGB(width - 1 - x, height - 1 - y);

                        // Swap the pixels
                        im.setRGB(x, y, rotPixel); 
                        im.setRGB(width - 1 - x, height - 1 - y, currentPixel); 
                    }
                }

                // Update the image display
                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }
            else if (command.equals("ZOOM")) {
                int width = im.getWidth();
                int height = im.getHeight();

                int cropX = width / 4;
                int cropY = height / 4; 
                int cropWidth = width / 2;
                int cropHeight = height / 2; 

               
                BufferedImage zoomedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

             
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        
                        int xCoord = cropX + (x * cropWidth) / width;
                        int yCoord = cropY + (y * cropHeight) / height;

                  
                        int pixel = im.getRGB(xCoord, yCoord);
                        zoomedImage.setRGB(x, y, pixel);
                    }
                }

                lab.setIcon(new ImageIcon(zoomedImage));
                lab.repaint();
            }

            else if (command.equals("EDGE_DETECTION")) {
                int width = im.getWidth();
                int height = im.getHeight();

                
                BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb = im.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;
                        int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                        grayscaleImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
                    }
                }

            
                BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                for (int y = 1; y < height - 1; y++) {
                    for (int x = 1; x < width - 1; x++) {
                        int pixel = (grayscaleImage.getRGB(x, y) >> 16) & 0xFF;
                        int pixelRight = (grayscaleImage.getRGB(x + 1, y) >> 16) & 0xFF;
                        int pixelBottom = (grayscaleImage.getRGB(x, y + 1) >> 16) & 0xFF;

                        int diffX = Math.abs(pixel - pixelRight); 
                        int diffY = Math.abs(pixel - pixelBottom); 
                        int magnitude = diffX + diffY; 

                       
                        if (magnitude > 128) {
                            edgeImage.setRGB(x, y, 0x000000); 
                        } else {
                            edgeImage.setRGB(x, y, 0xFFFFFF); 
                        }
                    }
                }
                lab.setIcon(new ImageIcon(edgeImage));
                lab.repaint();
            }

            else if (command.equals("SATURATE")) {
                int width = im.getWidth();
                int height = im.getHeight();
                for (int y = 1; y < height - 1; y++) {
                    for (int x = 1; x < width - 1; x++) {
                        int rgb = im.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;

                        int maximum = Math.max(r,Math.max(g,b));
                        int minimum = Math.min(r, Math.min(g,b));
                        int inc_dec = Math.min(Math.min(255-maximum, minimum-0), 10);
                     
                        if (r == maximum) {r+=inc_dec;}
                        if (g == maximum) {g+=inc_dec;}
                        if (b == maximum) {b+=inc_dec;}
                        if (r == minimum) {r-=inc_dec;}
                        if (g == minimum) {g-=inc_dec;}
                        if (b == minimum) {b-=inc_dec;}

                        int col = (0xFF << 24) | (r << 16) | (g << 8) | b;
                        im.setRGB(x, y, col);

                    }
                }
                lab.setIcon(new ImageIcon(im));
                lab.repaint();
            }
        }

        
    }
}


