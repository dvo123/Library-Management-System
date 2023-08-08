package gui;

import api.*;
import domain.*;

import javax.swing.*;

public class StudentGUI
{
	private JFrame frame;
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;
	
	JTextField libraryIdField;
	JTextField broncoIdField;
    JTextField nameField;
    JTextField courseField;
	
	public StudentGUI()
	{
		frame = new JFrame("Student Management");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Create components and add them to the JFrame
		JPanel panel = new JPanel();
		panel.setLayout(null);
        
        // Create labels
        JLabel libraryIdLabel = new JLabel("Library ID");
		JLabel broncoIdLabel = new JLabel("Bronco ID");
        JLabel nameLabel = new JLabel("Name");
        JLabel courseLabel = new JLabel("Course");
        
        // Create text fields
        libraryIdField = new JTextField(20);
        broncoIdField = new JTextField(20);
        nameField = new JTextField(20);
        courseField = new JTextField(20);
        
        // Create buttons
        JButton createButton = new JButton("Create");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        
        // Add library id components
        libraryIdLabel.setBounds(25, 25, 100, 25);
        libraryIdField.setBounds(25, 50, 300 ,25);
        panel.add(libraryIdLabel);
        panel.add(libraryIdField);
        libraryIdField.setEnabled(false);
        
        // Add name components
        nameLabel.setBounds(25, 100, 100, 25);
        nameField.setBounds(25, 125, 925, 25);
        panel.add(nameLabel);
        panel.add(nameField);
        
        // Add broncoId components
        broncoIdLabel.setBounds(25, 175, 100, 25);
        broncoIdField.setBounds(25, 200, 300, 25);
        panel.add(broncoIdLabel);
        panel.add(broncoIdField);
        
        // Add course components
        courseLabel.setBounds(350, 175, 100, 25);
        courseField.setBounds(350, 200, 600, 25);
        panel.add(courseLabel);
        panel.add(courseField);
           
        // Add buttons to panel
        createButton.setBounds(575, 500, 75, 30);
        readButton.setBounds(675, 500, 75, 30);
        updateButton.setBounds(775, 500, 75, 30);
        deleteButton.setBounds(875, 500, 75, 30);
        panel.add(createButton);
        panel.add(readButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        
        frame.getContentPane().add(panel);

        
        // Create student
        createButton.addActionListener(e -> {
        	String broncoId = broncoIdField.getText();
            String name = nameField.getText();
            String course = courseField.getText();
            
            StudentDAO.createStudent(broncoId, name, course);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Student: [" + name + "] successfully added.");
        });
        
        // Read student
        readButton.addActionListener(e -> {
        	String name = nameField.getText();
        	
        	Student tempStudent = StudentDAO.readStudent(name);
        	
        	if (tempStudent == null)
        	{
        		JOptionPane.showMessageDialog(null, "No student found!");
        		return;
        	}
        	
        	libraryIdField.setText(String.valueOf(tempStudent.getLibraryId())); 
        	broncoIdField.setText(tempStudent.getBroncoId());
        	nameField.setText(tempStudent.getName());
        	courseField.setText(tempStudent.getCourse());    	
        });
        
        // Update student
        updateButton.addActionListener(e -> {
        	Student tempStudent = new Student();
            
        	tempStudent.setLibraryId(Integer.parseInt(libraryIdField.getText())); 
        	tempStudent.setBroncoId(broncoIdField.getText());
        	tempStudent.setName(nameField.getText());
        	tempStudent.setCourse(courseField.getText());
            
            StudentDAO.updateStudent(tempStudent);
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Student: [" + tempStudent.getName() + "] successfully updated.");
        });
        
        // Delete student
        deleteButton.addActionListener(e -> {
        	Student tempStudent = new Student();
            
            tempStudent.setLibraryId(Integer.parseInt(libraryIdField.getText())); 
        	tempStudent.setBroncoId(broncoIdField.getText());
            tempStudent.setName(nameField.getText());
            tempStudent.setCourse(courseField.getText());
     
            boolean success = StudentDAO.deleteStudent(tempStudent);
            
            if (!success)
            {
            	JOptionPane.showMessageDialog(null, "Student: [" + tempStudent.getName() + "] can't be deleted as they have an open loan.");
            	return;
            }
            
            clearFields();
            
            JOptionPane.showMessageDialog(null, "Student: [" + tempStudent.getName() + "] successfully deleted.");
        });
    }
	
	public void showWindow()
	{
		frame.setVisible(true);
	}
	
	public void clearFields()
	{
		libraryIdField.setText("");
		broncoIdField.setText("");
        nameField.setText("");
        courseField.setText("");
	}

	
	public static void main(String[] args)
	{
		StudentGUI studentGUI = new StudentGUI();
		studentGUI.showWindow();
	}
}
