package gui;

import javax.swing.*;

import api.AuthorDAO;
import domain.Author;

import java.awt.*;

@SuppressWarnings("serial")
public class AuthorGUI extends JFrame {
    private JLabel authorIdLabel, nameLabel, nationalityLabel, subjectLabel;
    private JTextField authorIdField, nameField, nationalityField, subjectField;
    private JButton searchButton, addButton, updateButton, deleteButton;

    public AuthorGUI() {
        super("Author Management");

        authorIdLabel = new JLabel("Author ID: ");
        nameLabel = new JLabel("Name:");
        nationalityLabel = new JLabel("Nationality:");
        subjectLabel = new JLabel("Subject:");

        authorIdField = new JTextField(20);
        nameField = new JTextField(20);
        nationalityField = new JTextField(20);
        subjectField = new JTextField(20);

        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        
        authorIdField.setEnabled(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(authorIdLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        panel.add(authorIdField, c);
        
        c.gridx = 0;
        c.gridy = 1;
        panel.add(nameLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(nameField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(nationalityLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(nationalityField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(subjectLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(subjectField, c);

        c.gridx = 2;
        c.gridy = 4;
        panel.add(searchButton, c);

        c.gridx = 3;
        c.gridy = 4;
        panel.add(addButton, c);

        c.gridx = 4;
        c.gridy = 4;
        panel.add(updateButton, c);

        c.gridx = 5;
        c.gridy = 4;
        panel.add(deleteButton, c);

        add(panel);

        setSize(800, 250);
        setLocationRelativeTo(null);
        setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        // Create author
        addButton.addActionListener(e -> {
        	String name = nameField.getText();
            String nationality = nationalityField.getText();
            String subject = subjectField.getText();
            
            AuthorDAO.createAuthor(name, nationality, subject);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Author: [" + name + "] successfully added.");
        });
        
        // Read author
        searchButton.addActionListener(e -> {
        	String name = nameField.getText();
        	
        	Author tempAuthor = AuthorDAO.readAuthor(name);
        	
        	if (tempAuthor == null)
        	{
        		JOptionPane.showMessageDialog(null, "No author found!");
        		return;
        	}
        	
        	authorIdField.setText(String.valueOf(tempAuthor.getId())); 
        	nameField.setText(tempAuthor.getName());
        	nationalityField.setText(tempAuthor.getNationality());
        	subjectField.setText(tempAuthor.getSubject());    	
        });
        
        // Update author
        updateButton.addActionListener(e -> {
        	Author tempAuthor = new Author();
            
        	tempAuthor.setId(Integer.parseInt(authorIdField.getText()));
        	tempAuthor.setName(nameField.getText());
        	tempAuthor.setNationality(nationalityField.getText());
        	tempAuthor.setSubject(subjectField.getText());
            
            AuthorDAO.updateAuthor(tempAuthor);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Author: [" + tempAuthor.getName() + "] successfully updated.");
        });
        
        // Delete author
        deleteButton.addActionListener(e -> {
        	Author tempAuthor = new Author();
            
        	tempAuthor.setId(Integer.parseInt(authorIdField.getText()));
        	tempAuthor.setName(nameField.getText());
        	tempAuthor.setNationality(nationalityField.getText());
        	tempAuthor.setSubject(subjectField.getText());
     
        	boolean success = AuthorDAO.deleteAuthor(tempAuthor);
        	
        	if (!success)
        	{
        		JOptionPane.showMessageDialog(null, "Author: [" + tempAuthor.getName() + "] can't be deleted as they are required for an item.");
        		return;
        	}
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Author: [" + tempAuthor.getName() + "] successfully updated.");
        });
    }
    
    public void clearFields()
	{
		authorIdField.setText("");
		nameField.setText("");
        nationalityField.setText("");
        subjectField.setText("");
	}
    
    public void showWindow()
	{
		setVisible(true);
	}

    public static void main(String[] args) {
        new AuthorGUI();
    }
}




