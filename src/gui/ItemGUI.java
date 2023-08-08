package gui;

import api.*;
import domain.*;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

public class ItemGUI
{
	private JFrame frame;
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;
	
	// Generic item fields
	JTextField codeField;
	JTextField titleField;
    JTextField descriptionField;
    JTextField locationField;
    JTextField dailyPriceField;
    JTextField creatorField;
    JTextField availabilityField;
    JTextField dueDateField;
    
    // Book fields
    JRadioButton bookButton;
    JTextField pagesField;
    JTextField publisherField;
    JTextField publicationDateField;
    
    // Documentary fields
    JRadioButton documentaryButton;
    JTextField lengthField;
    JTextField releaseDateField;
    
    // Choice field
    ButtonGroup itemGroup;
    
	public ItemGUI()
	{
		frame = new JFrame("Item Management");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Create components and add them to the JFrame
		JPanel panel = new JPanel();
		panel.setLayout(null);
        
        // Create labels
		JLabel codeLabel = new JLabel("Item Code");
		JLabel titleLabel = new JLabel("Title");
        JLabel descriptionLabel = new JLabel("Description");
        JLabel locationLabel = new JLabel("Location");
        JLabel dailyPriceLabel = new JLabel("Daily Price ($)");
        JLabel creatorLabel = new JLabel("Creator Name");
        JLabel availabilityLabel = new JLabel("Has Current Loan");
        JLabel dueDateLabel = new JLabel("Due Date");
        
        // Book related labels
        JLabel bookLabel = new JLabel("Book");
        JLabel pagesLabel = new JLabel("Pages");
        JLabel publisherLabel = new JLabel("Publisher");
        JLabel publicationDateLabel = new JLabel("Publication Date (yyyy-[m]m-[d]d)");
        
        // Documentary related labels
        JLabel documentaryLabel = new JLabel("Documentary");
        JLabel lengthLabel = new JLabel("Length (minutes)");
        JLabel releaseDateLabel = new JLabel("Release Date (yyyy-[m]m-[d]d)");

        
        // Create text fields
        codeField = new JTextField(20);
        titleField = new JTextField(20);
        descriptionField = new JTextField(30);
        locationField = new JTextField(20);
        dailyPriceField = new JTextField(20);
        creatorField = new JTextField(20);
        availabilityField = new JTextField(20);
        dueDateField = new JTextField(20);
        
        // Book related text fields
        pagesField = new JTextField(20);
        publisherField = new JTextField(20);
        
        // Documentary related text fields
        publicationDateField = new JTextField(20);
        lengthField = new JTextField(20);
        releaseDateField = new JTextField(20);
        
        // Create radio buttons
        bookButton = new JRadioButton("Book");
        documentaryButton = new JRadioButton("Documentary");
        
        // Create item radio button group and add radio buttons
        itemGroup = new ButtonGroup();
        itemGroup.add(bookButton);
        itemGroup.add(documentaryButton);
        
        // Create buttons
        JButton createButton = new JButton("Create");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Add code components
        codeLabel.setBounds(25, 25, 100, 25);
        codeField.setBounds(25, 50, 250, 25);
        codeField.setEnabled(false);
        panel.add(codeLabel);
        panel.add(codeField);
        
        // Add title components
        titleLabel.setBounds(350, 25, 100, 25); 
        titleField.setBounds(350, 50, 425, 25); 
        panel.add(titleLabel);
        panel.add(titleField);
        
        // Add description components
        descriptionLabel.setBounds(25, 100, 100, 25);
        descriptionField.setBounds(25, 125, 925, 25);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        
        // Add location components
        locationLabel.setBounds(25, 175, 100, 25);
        locationField.setBounds(25, 200, 300, 25);
        panel.add(locationLabel);
        panel.add(locationField);
        
        // Add creator components
        creatorLabel.setBounds(350, 175, 100, 25);
        creatorField.setBounds(350, 200, 300, 25);
        panel.add(creatorLabel);
        panel.add(creatorField);
        
        // Add daily price components
        dailyPriceLabel.setBounds(675, 175, 100, 25);
        dailyPriceField.setBounds(675, 200, 100, 25);
        panel.add(dailyPriceLabel);
        panel.add(dailyPriceField);
        
        // Add availability components
        availabilityLabel.setBounds(800, 175, 100, 25);
        availabilityField.setBounds(800, 200, 100, 25);
        availabilityField.setEnabled(false);
        panel.add(availabilityLabel);
        panel.add(availabilityField);
        
        // Add item due date components
        dueDateLabel.setBounds(675, 225, 100, 25);
        dueDateField.setBounds(675, 250, 225, 25);
        dueDateField.setEnabled(false);
        panel.add(dueDateLabel);
        panel.add(dueDateField);
        
        // Add book components
        bookButton.setBounds(25, 277, 20, 20);
        bookLabel.setBounds(45, 275, 100, 25);
        panel.add(bookButton);
        panel.add(bookLabel);
        
        // Add page components
        pagesLabel.setBounds(50, 300, 100, 25);
        pagesField.setBounds(50, 325, 250, 25);
        panel.add(pagesLabel);
        panel.add(pagesField);
        
        // Add publisher components
        publisherLabel.setBounds(50, 375, 100, 25);
        publisherField.setBounds(50, 400, 250, 25);
        panel.add(publisherLabel);
        panel.add(publisherField);
        
        // Add publication components
        publicationDateLabel.setBounds(50, 450, 250, 25);
        publicationDateField.setBounds(50, 475, 250, 25);
        panel.add(publicationDateField);
        panel.add(publicationDateLabel);
          
        // Add documentary components
        documentaryButton.setBounds(495, 277, 20, 20);
        documentaryLabel.setBounds(515, 275, 100, 25);
        panel.add(documentaryButton);
        panel.add(documentaryLabel);
        
        // Add length components
        lengthLabel.setBounds(525, 300, 100, 25);
        lengthField.setBounds(525, 325, 250, 25);
        panel.add(lengthLabel);
        panel.add(lengthField);
        
        // Add release date components
        releaseDateLabel.setBounds(525, 375, 250, 25);
        releaseDateField.setBounds(525, 400, 250, 25);
        panel.add(releaseDateLabel);
        panel.add(releaseDateField);
        
        
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

        
        // Create item
        createButton.addActionListener(e -> {
        	String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            double dailyPrice = Double.parseDouble(dailyPriceField.getText());
            String creator = creatorField.getText();;
            
            // Book parameters
            int pages;
            String publisher;
            Date publicationDate;
            
            // Documentary parameters
            int length;
            Date releaseDate;
            
            
            
            
            if (bookButton.isSelected())
            {
            	 Author author = AuthorDAO.readAuthor(creator);
            	 
            	 if (author == null)
                 {
                 	JOptionPane.showMessageDialog(null, "You must create the author first.");
                 	return;
                 }
            	
            	 pages = Integer.parseInt(pagesField.getText());
            	 publisher = publisherField.getText();
            	 publicationDate = Date.valueOf(publicationDateField.getText()); 
            	 BookDAO.createBook(title, description, location, dailyPrice, pages, publisher, publicationDate, author);
            	 JOptionPane.showMessageDialog(null, "Book: [" + title + "] successfully added.");
            	 clearFields();
            }
                      
            else if (documentaryButton.isSelected())
            {
            	Director director = DirectorDAO.readDirector(creator);
            	
            	if (director == null)
                {
                	JOptionPane.showMessageDialog(null, "You must create the director first.");
                	return;
                }
            	
            	length = Integer.parseInt(lengthField.getText());
            	releaseDate = Date.valueOf(releaseDateField.getText());
            	DocumentaryDAO.createDocumentary(title, description, location, dailyPrice, length, releaseDate, director);
            	
            	JOptionPane.showMessageDialog(null, "Documentary: [" + title + "] successfully added.");
            	clearFields();
            }
            
            else 
            {
            	JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
            }  
        });
        
        // read item
        readButton.addActionListener(e -> {
        	String title = titleField.getText();
        	
        	clearFields();
        	
        	if (bookButton.isSelected())
        	{
        		Book tempBook = BookDAO.readBook(title);
            	
            	if (tempBook == null)
            	{
            		JOptionPane.showMessageDialog(null, "No book found!");
            		return;
            	}
            	
            	// code of books are - 1 because their corresponding item ID 
            	// is 1 less than the book code
            	codeField.setText(String.valueOf(tempBook.getCode() - 1));
            	titleField.setText(tempBook.getTitle());
            	descriptionField.setText(tempBook.getDescription());
            	locationField.setText(tempBook.getLocation());
            	creatorField.setText(tempBook.getAuthor().getName());
            	dailyPriceField.setText(String.valueOf(tempBook.getDailyPrice())); 
            	availabilityField.setText(String.valueOf(tempBook.getIsOnLoan()));
            	dueDateField.setText(getLoanDueDateForItem(tempBook));
            	pagesField.setText(String.valueOf(tempBook.getPages()));
            	publisherField.setText(tempBook.getPublisher());
            	publicationDateField.setText(tempBook.getPublicationDate().toString());
        	}
        	
        	else if (documentaryButton.isSelected())
        	{
        		Documentary tempDocumentary = DocumentaryDAO.readDocumentary(title);
            	
            	if (tempDocumentary == null)
            	{
            		JOptionPane.showMessageDialog(null, "No documentary found!");
            		return;
            	}
            	
            	// code of documentaries are - 1 because their corresponding item ID 
            	// is 1 less than the documentary code
            	codeField.setText(String.valueOf(tempDocumentary.getCode() - 1));
            	titleField.setText(tempDocumentary.getTitle());
            	descriptionField.setText(tempDocumentary.getDescription());
            	locationField.setText(tempDocumentary.getLocation());
            	creatorField.setText(tempDocumentary.getDirector().getName());
            	dailyPriceField.setText(String.valueOf(tempDocumentary.getDailyPrice()));
            	availabilityField.setText(String.valueOf(tempDocumentary.getIsOnLoan()));
            	dueDateField.setText(getLoanDueDateForItem(tempDocumentary));
            	lengthField.setText(String.valueOf(tempDocumentary.getLength()));
            	releaseDateField.setText(tempDocumentary.getReleaseDate().toString());
        	}
        	
        	else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}
        });
        
        // Update item
        updateButton.addActionListener(e -> {
        	
        	if (bookButton.isSelected())
        	{
        		Book tempBook = new Book();
        		
        		tempBook.setCode(Integer.parseInt(codeField.getText()) + 1);
        		tempBook.setTitle(titleField.getText());
        		tempBook.setDescription(descriptionField.getText());
        		tempBook.setLocation(locationField.getText());
        		tempBook.setDailyPrice(Double.parseDouble(dailyPriceField.getText()));
        		tempBook.setIsOnLoan(BookDAO.readBook(titleField.getText()).getIsOnLoan());
        		tempBook.setPages(Integer.parseInt(pagesField.getText())); 
        		tempBook.setPublisher(publisherField.getText());
        		tempBook.setPublicationDate(Date.valueOf(publicationDateField.getText()));
        		tempBook.setAuthor(AuthorDAO.readAuthor(creatorField.getText()));
        		
        		BookDAO.updateBook(tempBook);
        		
        		clearFields();
        		
        		JOptionPane.showMessageDialog(null, "Book: [" + tempBook.getTitle() + "] successfully updated.");
        	}
        	
        	else if (documentaryButton.isSelected())
        	{
        		Documentary tempDocumentary = new Documentary();
        		
        		tempDocumentary.setCode(Integer.parseInt(codeField.getText()) + 1);
        		tempDocumentary.setTitle(titleField.getText());
        		tempDocumentary.setDescription(descriptionField.getText());
        		tempDocumentary.setLocation(locationField.getText());
        		tempDocumentary.setDailyPrice(Double.parseDouble(dailyPriceField.getText()));
        		tempDocumentary.setIsOnLoan(DocumentaryDAO.readDocumentary(titleField.getText()).getIsOnLoan());
        		tempDocumentary.setLength(Integer.parseInt(lengthField.getText())); 
        		tempDocumentary.setReleaseDate(Date.valueOf(releaseDateField.getText())); 
        		tempDocumentary.setDirector(DirectorDAO.readDirector(creatorField.getText()));
        		
        		DocumentaryDAO.updateDocumentary(tempDocumentary);
        		
        		clearFields();
        		
        		JOptionPane.showMessageDialog(null, "Documentary: [" + tempDocumentary.getTitle() + "] successfully updated.");
        	}
        	
        	else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}         
        });
        
        // Delete item
        deleteButton.addActionListener(e -> {
        	
            if (bookButton.isSelected())
            {
            	Book tempBook = BookDAO.readBook(titleField.getText());
            	
            	boolean success = BookDAO.deleteBook(tempBook);
            	
            	if (!success)
            	{
            		JOptionPane.showMessageDialog(null, "Book: [" + tempBook.getTitle() + "] can't be deleted as it is required for a loan.");
            		return;
            	}
            	
            	clearFields();
            	
            	JOptionPane.showMessageDialog(null, "Book: [" + tempBook.getTitle() + "] successfully deleted.");
            }
            
            else if (documentaryButton.isSelected())
            {
            	Documentary tempDocumentary = DocumentaryDAO.readDocumentary(titleField.getText());
            	
            	boolean success = DocumentaryDAO.deleteDocumentary(tempDocumentary);
            	
            	if (!success)
            	{
            		JOptionPane.showMessageDialog(null, "Documentary: [" + tempDocumentary.getTitle() + "] can't be deleted as it is required for a loan.");
            		return;
            	}
            	
            	clearFields();
            	
            	JOptionPane.showMessageDialog(null, "Documentary: [" + tempDocumentary.getTitle() + "] successfully deleted.");
            }
            
            else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}    
        });
    }
	
	private String getLoanDueDateForItem(Item item)
	{
		List<Loan> openLoans = LoanDAO.getOpenLoans();
		
		for (int i = 0; i < openLoans.size(); i++)
		{
			Loan currentLoan = openLoans.get(i);
			
			if (currentLoan.getItem().getTitle().equals(item.getTitle()))
			{
				return currentLoan.getDueDate().toString(); 
			}
		}
		
		return "";
	}
	
	public void showWindow()
	{
		frame.setVisible(true);
	}
	
	public void clearFields()
	{
		codeField.setText("");
		titleField.setText("");
	    descriptionField.setText("");
	    locationField.setText("");
	    creatorField.setText("");
	    dailyPriceField.setText("");
	    availabilityField.setText("");
	    dueDateField.setText("");
	    pagesField.setText("");
	    publisherField.setText("");
	    publicationDateField.setText("");
	    lengthField.setText("");
	    releaseDateField.setText("");
	}

	
	public static void main(String[] args)
	{
		ItemGUI itemGUI = new ItemGUI();
		itemGUI.showWindow();
	}
}
