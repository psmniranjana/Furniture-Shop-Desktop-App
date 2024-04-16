package Myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    public MainMenu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton floorButton = createMenuButton("Floor Panel Example", FloorPanelExampleFrame.class);
        JButton imageButton = createMenuButton("Image Panel Example", ImagePanelExampleFrame.class);
        JButton roomButton = createMenuButton("Room Image Panel Example", RoomImagePanelExampleFrame.class);
        JButton floorFrameButton = createMenuButton("Floor Frame", FloorFrame.class); // New button for FloorFrame
        JButton backButton = createBackButton();

        panel.add(floorButton);
        panel.add(imageButton);
        panel.add(roomButton);
        panel.add(floorFrameButton);
        panel.add(backButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private JButton createMenuButton(String buttonText, Class<?> frameClass) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Set button font
        button.setPreferredSize(new Dimension(200, 30)); // Set button size
        button.addActionListener(e -> {
            try {
                dispose(); // Close the main menu frame
                frameClass.getDeclaredConstructor().newInstance(); // Open the selected frame
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Set button font
        backButton.setPreferredSize(new Dimension(100, 30)); // Set button size
        backButton.addActionListener(e -> {
            // You can define the back action here, e.g., go back to the previous frame
            // For now, I'll just close the main menu frame
            dispose();
        });
        return backButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implement action if needed
    }
}
