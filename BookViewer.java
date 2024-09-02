import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class BookViewer extends JFrame {

    private DefaultTableModel tableModel;

    public BookViewer() {
        super("Book Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create table model
        String[] columnNames = {"Name", "AuthorName", "Publishing Date", "ISBN", "IsIssued", "BorrowerName"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Read data from CSV and populate the table
        readDataFromCSV("books.csv");

        // Add components to the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create "Add Book" button
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddBookDialog();
            }
        });

        // Create "Issue Book" button
        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIssueBookDialog();
            }
        });
        
        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReturnBookDialog();
            }
        });

        JButton exportButton = new JButton("Export CSV");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            exportCSV();
            }
        });



        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(exportButton);

        // Add button panel to the frame
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showAddBookDialog() {
        AddBookDialog addBookDialog = new AddBookDialog(this);
        addBookDialog.setVisible(true);
    }

    private void showIssueBookDialog() {
        IssueBookDialog issueBookDialog = new IssueBookDialog(this);
        issueBookDialog.setVisible(true);
    }

    private void showReturnBookDialog() {
        ReturnBookDialog returnBookDialog = new ReturnBookDialog(this);
        returnBookDialog.setVisible(true);
    }

    private void exportCSV() {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";
            String exportFileName = downloadsPath + "books_export.csv";

            BufferedWriter writer = new BufferedWriter(new FileWriter(exportFileName));
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                writer.write(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "CSV file exported to Downloads folder", "Export Success", JOptionPane.INFORMATION_MESSAGE);

        

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting CSV file", "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void addBook(Vector<Object> data) {
        // Add book to table
        tableModel.addRow(data);

        // Save data to CSV file
        saveDataToCSV("books.csv", data);
    }

    void issueBook(String bookName, String borrowerName) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String name = (String) tableModel.getValueAt(i, 0);
            if (name.equalsIgnoreCase(bookName)) {
                Vector<Object> newRow = new Vector<>();
                newRow.add(name);
                newRow.add(tableModel.getValueAt(i, 1)); // Author
                newRow.add(tableModel.getValueAt(i, 2)); // Publishing Date
                newRow.add(tableModel.getValueAt(i, 3)); // ISBN
                newRow.add("true");  // Set IsIssued to true
                newRow.add(borrowerName);  // Set BorrowerName
                tableModel.removeRow(i);  // Remove the cleared row}
                tableModel.addRow(newRow);  // Add the new row

                // Save updated data to CSV file
                saveDataToCSV("books.csv", newRow);
                return;
            }
        }

        // If book not found, display an error message
        JOptionPane.showMessageDialog(this, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    void returnBook(String bookName, String borrowerName) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String book = (String) tableModel.getValueAt(i, 0);
            if (book.equalsIgnoreCase(bookName)) {
                Vector<Object> newRow = new Vector<>();
                newRow.add(book);
                newRow.add(tableModel.getValueAt(i, 1)); // Author
                newRow.add(tableModel.getValueAt(i, 2)); // Publishing Date
                newRow.add(tableModel.getValueAt(i, 3)); // ISBN
                newRow.add("false");  // Set IsIssued to false
                newRow.add("");  // Set BorrowerName
                tableModel.removeRow(i);  // Remove the cleared row
                tableModel.addRow(newRow);  // Add the new row

                // Save updated data to CSV file
                saveDataToCSV("books.csv", newRow);
                return;
            }
        }

        // If book not found, display an error message
        JOptionPane.showMessageDialog(this, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new BookViewer());
        SwingUtilities.invokeLater(() -> new MainDirectoryFrame());     
    }
}

class AddBookDialog extends JDialog {

    private JTextField nameField, authorField, dateField, isbnField;

    public AddBookDialog(JFrame parent) {
        super(parent, "Add Book", true);

        setLayout(new GridLayout(5, 2));

        nameField = new JTextField(20);
        authorField = new JTextField(20);
        dateField = new JTextField(10);
        isbnField = new JTextField(15);

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Author:"));
        add(authorField);
        add(new JLabel("Publishing Date:"));
        add(dateField);
        add(new JLabel("ISBN:"));
        add(isbnField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
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
        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    private void addBook() {
        // Get user input
        String name = nameField.getText();
        String author = authorField.getText();
        String date = dateField.getText();
        String isbn = isbnField.getText();

        // Validate input
        if (name.isEmpty() || author.isEmpty() || date.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add book to parent frame
        Vector<Object> row = new Vector<>();
        row.add(name);
        row.add(author);
        row.add(date);
        row.add(isbn);
        row.add("false");  // Empty fields for IsIssued and BorrowerName
        row.add("");
        ((BookViewer) getParent()).addBook(row);

        // Close the dialog
        dispose();
    }
}

class IssueBookDialog extends JDialog {

    private JTextField bookNameField, borrowerNameField;

    public IssueBookDialog(JFrame parent) {
        super(parent, "Issue Book", true);

        setLayout(new GridLayout(3, 2));

        bookNameField = new JTextField(20);
        borrowerNameField = new JTextField(20);

        add(new JLabel("Book Name:"));
        add(bookNameField);
        add(new JLabel("Borrower Name:"));
        add(borrowerNameField);

        JButton issueButton = new JButton("Issue");
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });
        add(issueButton);

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

    private void issueBook() {
        // Get user input
        String bookName = bookNameField.getText();
        String borrowerName = borrowerNameField.getText();

        // Validate input
        if (bookName.isEmpty() || borrowerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Issue book in parent frame
        ((BookViewer) getParent()).issueBook(bookName, borrowerName);

        // Close the dialog
        dispose();
    }
}

    class ReturnBookDialog extends JDialog {

        private JTextField bookNameField, borrowerNameField;
    
        public ReturnBookDialog(JFrame parent) {
            super(parent, "Return Book", true);
    
            setLayout(new GridLayout(3, 2));
    
            bookNameField = new JTextField(20);
            borrowerNameField = new JTextField(20);
    
            add(new JLabel("Book Name:"));
            add(bookNameField);
            add(new JLabel("Borrower Name:"));
            add(borrowerNameField);
    
            JButton returnButton = new JButton("Return");
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    returnBook();
                }
            });
            add(returnButton);
    
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
    
        private void returnBook() {
            // Get user input
            String bookName = bookNameField.getText();
            String borrowerName = borrowerNameField.getText();
    
            // Validate input
            if (bookName.isEmpty() || borrowerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Return book in parent frame
            ((BookViewer) getParent()).returnBook(bookName, borrowerName);
    
            // Close the dialog
            dispose();
        }
    }
