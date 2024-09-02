
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Library System Login");
        setLayout(new BorderLayout());
        setSize(500, 300);  // Set a larger size

        // Create a title label
        JLabel titleLabel = new JLabel("Library System Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a panel for the login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        // Create password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Add components to the panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty cell
        loginPanel.add(loginButton);

        // Add components to the frame
        add(titleLabel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);

        // Set frame properties
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);
        setVisible(true);
    }

    private void performLogin() {
        // Replace this with your login logic
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Example: Display a message based on the entered credentials
        if (username.equals("admin") && String.valueOf(password).equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new MainDirectoryFrame();
        } else {
            JOptionPane.showMessageDialog(
                    this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

}
