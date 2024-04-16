package Myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JLabel infoLabel;
    private Map<String, String> temporaryCredentials; // Temporary storage for signed up users

    public LoginFrame() {
        setTitle("Website Style Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new BorderLayout());

        temporaryCredentials = new HashMap<>(); // Initialize the temporary credentials map

        // Panel for top (info)
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("<html><b>Login</b><br>Welcome back! Please sign in to your account.</html>");
        infoLabel = new JLabel(" ");
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(infoLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel for left side (image)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        ImageIcon icon = new ImageIcon(System.getProperty("user.home") + "/Documents/sofa.png"); // Load image from Documents directory
        JLabel imageLabel = new JLabel(resizeImage(icon, 150, 150)); // Resize image to 150x150 pixels
        leftPanel.add(imageLabel);
        add(leftPanel, BorderLayout.WEST);

        // Panel for right side (login form)
        JPanel rightPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // Using GridLayout for form
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        JLabel usernameLabel = new JLabel("Username:");
        rightPanel.add(usernameLabel);
        usernameField = new JTextField();
        rightPanel.add(usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        rightPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        rightPanel.add(passwordField);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        rightPanel.add(loginButton);
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        rightPanel.add(signUpButton);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login button click
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check if the entered credentials match the temporary credentials
            if (temporaryCredentials.containsKey(username) && temporaryCredentials.get(username).equals(password)) {
                dispose(); // Close the login frame
                new MainMenu(); // Open the MainMenu
            } else {
                infoLabel.setText("Invalid username or password. Please try again.");
            }
        } else if (e.getSource() == signUpButton) {
            // Handle sign up button click
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check if username already exists
            if (temporaryCredentials.containsKey(username)) {
                infoLabel.setText("Username already exists. Please choose a different one.");
            } else {
                // Add the new user to the temporary credentials
                temporaryCredentials.put(username, password);
                infoLabel.setText("Sign up successful! You can now log in.");
            }
        }
    }

    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
