package Myproject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ImagePanelExample extends JPanel implements ActionListener {
    private ArrayList<BufferedImage> images;
    private ArrayList<String> imageTitles;
    private JButton openButton;
    private JLabel homeLabel;
    private File saveFile;

    public ImagePanelExample() {
        setLayout(new FlowLayout(FlowLayout.LEADING));

        // Add a label for "HOME" at the top of the panel
        homeLabel = new JLabel("<html><b>HOME</b></html>");
        add(homeLabel);

        openButton = new JButton("New+");
        openButton.addActionListener(this);
        add(openButton);

        images = new ArrayList<>();
        imageTitles = new ArrayList<>();
        saveFile = new File("images.txt"); // File to save image data

        // Load saved images upon starting the program
        loadImages();
    }

    // Load images from the saved file
    private void loadImages() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            images.clear(); // Clear existing images
            imageTitles.clear(); // Clear existing titles

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String imagePath = parts[0];
                String title = parts[1];

                // Read image from file
                BufferedImage image = ImageIO.read(new File(imagePath));
                images.add(image);
                imageTitles.add(title);
            }
            repaint(); // Repaint the panel to display the loaded images
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save images and titles to the file
    private void saveImages() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveFile))) {
            for (int i = 0; i < images.size(); i++) {
                String imagePath = "image_" + i + ".png"; // Generate unique file name for each image
                ImageIO.write(images.get(i), "PNG", new File(imagePath)); // Save image to file

                writer.println(imagePath + "," + imageTitles.get(i)); // Write image path and title to file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int imageSize = 150; // Size of each image
        int x = 10;
        int y = 40;

        // Draw each image on the panel
        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            String title = imageTitles.get(i);

            // Draw image button
            JButton imageButton = new JButton(new ImageIcon(image));
            imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
            imageButton.setText("<html><center>" + title + "</center></html>");

            // Create a delete button for each image button
            JButton deleteButton = new JButton("Delete");
            int index = i; // Need final or effectively final variable for lambda
            deleteButton.addActionListener(e -> {
                // Remove the image and its title
                images.remove(index);
                imageTitles.remove(index);

                // Save the updated images and titles to file
                saveImages();

                // Remove all components from the panel
                removeAll();
                revalidate();
                repaint();
            });

            // Create a panel to hold the image and delete buttons
            JPanel imagePanel = new JPanel(new BorderLayout());
            imagePanel.add(imageButton, BorderLayout.CENTER);
            imagePanel.add(deleteButton, BorderLayout.SOUTH);

            // Add the image panel to the main panel
            add(imagePanel);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600); // Set preferred size of the panel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            // Show a file chooser dialog to select an image file
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Load the selected image file
                    BufferedImage image = ImageIO.read(selectedFile);
                    // Resize the image to 150x150 pixels
                    image = resizeImage(image, 150, 150);
                    // Add the resized image to the list
                    images.add(image);

                    // Prompt the user to enter the title for the image
                    String title = JOptionPane.showInputDialog(this, "Enter title for the image:");
                    if (title != null && !title.isEmpty()) {
                        imageTitles.add(title);
                    } else {
                        imageTitles.add("Untitled");
                    }

                    // Save the updated images and titles to file
                    saveImages();

                    removeAll(); // Remove all components from the panel
                    revalidate();
                    repaint(); // Repaint the panel to display the new image
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a frame to display the image panel
            JFrame frame = new JFrame("Image Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false); // Make the frame non-resizable

            // Create an instance of the image panel
            ImagePanelExample imagePanel = new ImagePanelExample();

            // Add the image panel to the frame
            frame.getContentPane().add(imagePanel);

            // Set preferred size of the frame
            frame.setPreferredSize(new Dimension(800, 600));

            // Pack the components in the frame
            frame.pack();

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
