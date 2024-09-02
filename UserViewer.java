import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class UserViewer extends JFrame {

    private DefaultTableModel tableModel;

    public UserViewer() {
        super("User Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create table model
        String[] columnNames = {"Name", "Title", "Username", "Password"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Read data from CSV and populate the table
        readDataFromCSV("users.csv");

        // Add components to the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create "Add User" button
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddUserDialog();
            }
        });
        

        JButton addAdminButton = new JButton("Add Administrator");
        addAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddAdminDialog();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(addUserButton);
        buttonPanel.add(addAdminButton);

        // Add button panel to the frame
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showAddUserDialog() {
        AddUserDialog addUserDialog = new AddUserDialog(this);
        addUserDialog.setVisible(true);
    }

    private void showAddAdminDialog() {
        AddAdminDialog addAdminDialog = new AddAdminDialog(this);
        addAdminDialog.setVisible(true);
    }

    void addUser(Vector<Object> data) {
        // Add user to table
        tableModel.addRow(data);

        // Save data to CSV file
        saveDataToCSV("users.csv", data);
    }

    void addAdministrator(Vector<Object> data) {
        // Add administrator to table
        tableModel.addRow(data);

        // Save data to CSV file
        saveDataToCSV("users.csv", data);
    }

    private void readDataFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Vector<Object> row = new Vector<>();
                for (String value : values) {
                    row.add(value.trim());
                }
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToCSV(String fileName, Vector<Object> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            StringBuilder line = new StringBuilder();
            for (Object value : data) {
                line.append(value).append(",");
            }
            line.deleteCharAt(line.length() - 1);  // Remove the last comma
            writer.write(line.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserViewer::new);
    }

class AddUserDialog extends JDialog {

    private JTextField nameField;

    public AddUserDialog(JFrame parent) {
        super(parent, "Add User", true);

        setLayout(new GridLayout(2, 2));

        nameField = new JTextField(20);

        add(new JLabel("Name:"));
        add(nameField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    private void addUser() {
        // Get user input
        String name = nameField.getText();

        // Validate input
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the name field", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add user to parent frame
        Vector<Object> row = new Vector<>();
        row.add(name);
        row.add("Borrower"); // Set title as "Borrower"
        row.add("");  // Empty fields for Username and Password
        row.add("");
        ((UserViewer) getParent()).addUser(row);

        // Close the dialog
        dispose();
    }
}
class AddAdminDialog extends JDialog {

    private JTextField nameField;

    public AddAdminDialog(JFrame parent) {
        super(parent, "Add Administrator", true);

        setLayout(new GridLayout(2, 2));

        nameField = new JTextField(20);

        add(new JLabel("Name:"));
        add(nameField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdmin();
            }
        });
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    private void addAdmin() {
        // Get admin input
        String name = nameField.getText();

        // Validate input
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the name field", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add admin to parent frame
        Vector<Object> row = new Vector<>();
        row.add(name);
        row.add("Administrator"); // Set title as "Administrator"
        row.add("admin");  // Set username as "admin"
        row.add("admin123");  // Set password as "admin123"
        ((UserViewer) getParent()).addAdministrator(row);

        // Close the dialog
        dispose();
    }
}
}

