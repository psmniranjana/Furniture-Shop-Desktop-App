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

public class RoomImagePanelExample extends JPanel implements ActionListener {
    private ArrayList<BufferedImage> images;
    private ArrayList<String> imageTitles;
    private JButton addButton;
    private File saveDir;

    public RoomImagePanelExample() {
        setLayout(new FlowLayout(FlowLayout.LEADING));

        images = new ArrayList<>();
        imageTitles = new ArrayList<>();

        addButton = new JButton("Add Room Image");
        addButton.addActionListener(this);
        add(addButton);

        // Set up the directory to save images
        saveDir = new File(System.getProperty("user.home") + File.separator + "room_images");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        } else {
            loadImages(); // Load images if directory exists
        }
    }

    // Load images from the directory
    private void loadImages() {
        File[] files = saveDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".png");
            }
        });

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    images.add(image);
                    imageTitles.add("Untitled");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
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

                    // Resize the image to fit the panel
                    int maxSize = Math.max(image.getWidth(), image.getHeight());
                    double scale = 150.0 / maxSize;
                    int scaledWidth = (int) (image.getWidth() * scale);
                    int scaledHeight = (int) (image.getHeight() * scale);
                    BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledImage.createGraphics();
                    g2d.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
                    g2d.dispose();

                    // Generate a unique filename
                    String fileName = "room_image_" + System.currentTimeMillis() + ".png";
                    File saveFile = new File(saveDir, fileName);

                    // Save the image to the file
                    ImageIO.write(scaledImage, "PNG", saveFile);

                    // Add the title and the image path to the lists
                    imageTitles.add("Untitled");
                    images.add(scaledImage);

                    // Remove all components from the panel
                    removeAll();
                    revalidate();
                    repaint(); // Repaint the panel to display the new image
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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

                // Remove the image file from the directory
                File imageFile = new File(saveDir, "room_image_" + index + ".png");
                if (imageFile.exists()) {
                    imageFile.delete();
                }

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
}
