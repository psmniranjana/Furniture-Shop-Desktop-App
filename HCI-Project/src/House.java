import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class House extends JFrame {
    private HashMap<String, BufferedImage> imagesMap;
    private JPanel mainPanel;
    private JTextField titleField;
    private JPanel imagePanel;
    private JLabel titleLabel;
    private BufferedImage currentImage;
    private Properties properties;
    private File configFile;

    public House() {
        imagesMap = new HashMap<>();
        setTitle("Compact Image Processor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleField = new JTextField(15);
        JButton importButton = new JButton("Import Image");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importImage();
            }
        });

        controlPanel.add(titleField);
        controlPanel.add(importButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        imagePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton showSavedImagesButton = new JButton("Show Saved Images");
        showSavedImagesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSavedImages();
            }
        });
        mainPanel.add(showSavedImagesButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        properties = new Properties();
        configFile = new File("config.properties");
        loadSavedImages();

        refreshDisplay();
    }

    private void importImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(House.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String title = titleField.getText().trim();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                imagesMap.put(title, image);
                saveImage(title, selectedFile.getAbsolutePath());
                refreshDisplay();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error importing image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteImage(String title) {
        imagesMap.remove(title);
        removeImageFromConfig(title);
        refreshDisplay();
    }

    private void updateImage(String title) {
        BufferedImage newImage = currentImage; // Modify the image as needed
        imagesMap.put(title, newImage);
        refreshDisplay();
    }

    private void saveImage(String title, String filePath) {
        properties.setProperty(title, filePath);
        try {
            properties.store(new FileOutputStream(configFile), null);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeImageFromConfig(String title) {
        properties.remove(title);
        try {
            properties.store(new FileOutputStream(configFile), null);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSavedImages() {
        if (configFile.exists()) {
            try {
                properties.load(new FileInputStream(configFile));
                for (String title : properties.stringPropertyNames()) {
                    String filePath = properties.getProperty(title);
                    File imageFile = new File(filePath);
                    BufferedImage image = ImageIO.read(imageFile);
                    imagesMap.put(title, image);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading saved images", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSavedImages() {
        refreshDisplay();
    }

    private void refreshDisplay() {
        imagePanel.removeAll();
        for (String title : imagesMap.keySet()) {
            BufferedImage image = imagesMap.get(title);
            ImageIcon icon = new ImageIcon(getScaledImage(image, 100, 100));
            JButton imageButton = new JButton(icon);
            imageButton.setToolTipText(title);
            imageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    deleteImage(title);
                }
            });
            imagePanel.add(imageButton);
            JLabel titleLabel = new JLabel(title, JLabel.CENTER);
            imagePanel.add(titleLabel);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private Image getScaledImage(Image srcImg, int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new House().setVisible(true);
            }
        });


    }
}
