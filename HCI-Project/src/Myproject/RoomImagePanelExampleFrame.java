package Myproject;

import javax.swing.*;
import java.awt.*;

public class RoomImagePanelExampleFrame extends JFrame {
    public RoomImagePanelExampleFrame() {
        setTitle("Room Image Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create an instance of the room image panel
        RoomImagePanelExample roomImagePanel = new RoomImagePanelExample();

        // Add the room image panel to the frame
        getContentPane().add(roomImagePanel);

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
        SwingUtilities.invokeLater(RoomImagePanelExampleFrame::new);
    }
}
