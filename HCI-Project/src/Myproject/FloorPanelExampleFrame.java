package Myproject;

import javax.swing.*;
import java.awt.*;

public class FloorPanelExampleFrame extends JFrame {
    public FloorPanelExampleFrame() {
        setTitle("Floor Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create an instance of the floor panel
        FloorPanelExample floorPanel = new FloorPanelExample();

        // Add the floor panel to the frame
        getContentPane().add(floorPanel);

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
        SwingUtilities.invokeLater(FloorPanelExampleFrame::new);
    }
}
