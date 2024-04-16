package Myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloorFrame extends JFrame implements ActionListener {
    private JTextField sizeField;
    private JTextField shapeField;
    private JTextField colorField;
    private JButton createButton;
    private JButton visualize2DButton;
    private JButton visualize3DButton;
    private JButton scaleButton;
    private JButton addShadeButton;
    private JButton changeColorButton;
    private JButton saveButton;
    private JButton editDeleteButton;
    private JButton backButton; // Added back button
    private JLabel messageLabel;

    public FloorFrame() {
        setTitle("Floor Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create an instance of the floor panel
        JPanel floorPanel = new JPanel();
        floorPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        floorPanel.setPreferredSize(new Dimension(800, 600)); // Set preferred size of the panel

        // Add a label for "Choose Room Shape" at the top of the panel
        JLabel chooseRoomLabel = new JLabel("<html><b>Choose Room Shape</b></html>");
        floorPanel.add(chooseRoomLabel);

        JLabel sizeLabel = new JLabel("Size:");
        floorPanel.add(sizeLabel);

        sizeField = new JTextField(10);
        floorPanel.add(sizeField);

        JLabel shapeLabel = new JLabel("Shape:");
        floorPanel.add(shapeLabel);

        shapeField = new JTextField(10);
        floorPanel.add(shapeField);

        JLabel colorLabel = new JLabel("Color:");
        floorPanel.add(colorLabel);

        colorField = new JTextField(10);
        floorPanel.add(colorField);

        createButton = new JButton("Create Design");
        createButton.addActionListener(this);
        floorPanel.add(createButton);

        visualize2DButton = new JButton("Visualize in 2D");
        visualize2DButton.addActionListener(this);
        floorPanel.add(visualize2DButton);

        visualize3DButton = new JButton("Visualize in 3D");
        visualize3DButton.addActionListener(this);
        floorPanel.add(visualize3DButton);

        scaleButton = new JButton("Scale Design");
        scaleButton.addActionListener(this);
        floorPanel.add(scaleButton);

        addShadeButton = new JButton("Add Shade");
        addShadeButton.addActionListener(this);
        floorPanel.add(addShadeButton);

        changeColorButton = new JButton("Change Color");
        changeColorButton.addActionListener(this);
        floorPanel.add(changeColorButton);

        saveButton = new JButton("Save Design");
        saveButton.addActionListener(this);
        floorPanel.add(saveButton);

        editDeleteButton = new JButton("Edit/Delete Design");
        editDeleteButton.addActionListener(this);
        floorPanel.add(editDeleteButton);

        // Add back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        floorPanel.add(backButton);

        // Add message label to show success/failure messages
        messageLabel = new JLabel("");
        floorPanel.add(messageLabel);

        // Add the floor panel to the frame
        getContentPane().add(floorPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            // Action when "Create Design" button is clicked
            String size = sizeField.getText();
            String shape = shapeField.getText();
            String color = colorField.getText();
            // Your logic to create a new design based on the input fields
            messageLabel.setText("Design created successfully!"); // Set success message
        } else if (e.getSource() == visualize2DButton) {
            // Action when "Visualize in 2D" button is clicked
            // Your logic to visualize the design in 2D
        } else if (e.getSource() == visualize3DButton) {
            // Action when "Visualize in 3D" button is clicked
            // Your logic to visualize the design in 3D
        } else if (e.getSource() == scaleButton) {
            // Action when "Scale Design" button is clicked
            // Your logic to scale the design to fit the room
        } else if (e.getSource() == addShadeButton) {
            // Action when "Add Shade" button is clicked
            // Your logic to add shade to the design
        } else if (e.getSource() == changeColorButton) {
            // Action when "Change Color" button is clicked
            // Your logic to change the color of the design
        } else if (e.getSource() == saveButton) {
            // Action when "Save Design" button is clicked
            // Your logic to save the design
        } else if (e.getSource() == editDeleteButton) {
            // Action when "Edit/Delete Design" button is clicked
            // Your logic to edit or delete the design
            // For example, you can prompt the user to confirm deletion
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the design?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Perform deletion logic here
                messageLabel.setText("Design deleted successfully!"); // Set deletion success message
            }
        } else if (e.getSource() == backButton) {
            // Go back to the main menu
            dispose();
            new MainMenu();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FloorFrame::new);
    }
}
