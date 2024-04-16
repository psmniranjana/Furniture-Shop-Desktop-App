package Myproject;

import javax.swing.*;
import java.awt.*;

public class ImagePanelExampleFrame extends JFrame {
    public ImagePanelExampleFrame() {
        setTitle("Image Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create an instance of the image panel
        ImagePanelExample imagePanel = new ImagePanelExample();

        // Add the image panel to the frame
        getContentPane().add(imagePanel);

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Go back to the main menu
            dispose();
            new MainMenu();
        });
        getContentPane().add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImagePanelExampleFrame::new);
    }
}
