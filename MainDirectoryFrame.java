
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDirectoryFrame extends JFrame {

    private JButton bookListButton;
    private JButton userListButton;

    public MainDirectoryFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Library Management System");
        setLayout(new GridLayout(2, 1, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1200);

        // Create buttons
        bookListButton = new JButton("Book List");
        userListButton = new JButton("User List");

        // Add action listeners to the buttons
        bookListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookViewer();
            }
        });

        userListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserViewer();
            }
        });

        // Add buttons to the frame
        add(bookListButton);
        add(userListButton);

        // Set frame properties
        setLocationRelativeTo(null); // Center the frame
        setResizable(true);
        setPreferredSize(new Dimension(300, 150));
        setVisible(true);
    }
}