package gui;
import javax.swing.*;

import api.DirectorDAO;
import domain.Director;

import java.awt.*;

@SuppressWarnings("serial")
public class DirectorGUI extends JFrame {
    private JLabel directorIdLabel, nameLabel, nationalityLabel, styleLabel;
    private JTextField directorIdField, nameField, nationalityField, styleField;
    private JButton searchButton, addButton, updateButton, deleteButton;

    public DirectorGUI() {
        super("Director Management");
        
        setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        directorIdLabel = new JLabel("Director ID:");
		nameLabel = new JLabel("Name:");
        nationalityLabel = new JLabel("Nationality:");
        styleLabel = new JLabel("Style:");

        directorIdField = new JTextField(20);
        nameField = new JTextField(20);
        nationalityField = new JTextField(20);
        styleField = new JTextField(20);

        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        directorIdField.setEnabled(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(directorIdLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        panel.add(directorIdField, c);
        
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
        panel.add(styleLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(styleField, c);

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
        
        // Create director
        addButton.addActionListener(e -> {
        	String name = nameField.getText();
            String nationality = nationalityField.getText();
            String style = styleField.getText();
            
            DirectorDAO.createDirector(name, nationality, style);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Director: [" + name + "] successfully added.");
        });
        
        // Read director
        searchButton.addActionListener(e -> {
        	String name = nameField.getText();
        	
        	Director tempDirector = DirectorDAO.readDirector(name);
        	
        	if (tempDirector == null)
        	{
        		JOptionPane.showMessageDialog(null, "No director found!");
        		return;
        	}
        	
        	directorIdField.setText(String.valueOf(tempDirector.getId())); 
        	nameField.setText(tempDirector.getName());
        	nationalityField.setText(tempDirector.getNationality());
        	styleField.setText(tempDirector.getStyle());    	
        });
        
        // Update director
        updateButton.addActionListener(e -> {
        	Director tempDirector = new Director();
            
        	tempDirector.setId(Integer.parseInt(directorIdField.getText()));
        	tempDirector.setName(nameField.getText());
        	tempDirector.setNationality(nationalityField.getText());
        	tempDirector.setStyle(styleField.getText());
            
            DirectorDAO.updateDirector(tempDirector);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Director: [" + tempDirector.getName() + "] successfully updated.");
        });
        
        // Delete director
        deleteButton.addActionListener(e -> {
        	Director tempDirector = new Director();
            
        	tempDirector.setId(Integer.parseInt(directorIdField.getText()));
        	tempDirector.setName(nameField.getText());
        	tempDirector.setNationality(nationalityField.getText());
        	tempDirector.setStyle(styleField.getText());
     
        	boolean success = DirectorDAO.deleteDirector(tempDirector);
        	
        	if (!success)
        	{
        		JOptionPane.showMessageDialog(null, "Director: [" + tempDirector.getName() + "] can't be deleted as they are required for an item.");
        		return;
        	}
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Director: [" + tempDirector.getName() + "] successfully updated.");
        });
    }
    
    public void clearFields()
	{
		directorIdField.setText("");
		nameField.setText("");
        nationalityField.setText("");
        styleField.setText("");
	}
     
    public void showWindow()
	{
		setVisible(true);
	}

    public static void main(String[] args) {
        new DirectorGUI();
    }
}