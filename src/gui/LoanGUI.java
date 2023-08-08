package gui;

import javax.swing.*;

import api.*;
import domain.*;

import java.awt.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("serial")
public class LoanGUI extends JFrame {
    
    // components
    private JLabel loanNumberLabel, loanItemLabel, loanStudentLabel, loanStartLabel, loanEndLabel, loanReturnLabel;
    private JTextField loanNumberField, loanItemField, loanStudentField, loanStartField, loanEndField, loanReturnField;
    private JButton enterButton, searchButton, updateButton, deleteButton, displayOpenLoansButton, displayOverDueLoansButton, generateRevenueReportButton;
    private JTextArea receiptArea;
    private JRadioButton bookButton, documentaryButton;
    private ButtonGroup itemGroup;

    
    public LoanGUI() {
        // set up frame
        setTitle("Loan Management");
        setSize(800, 600);
        setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // create components
        loanNumberLabel = new JLabel("Loan Number: ");
        loanItemLabel = new JLabel("Loan Item Name: ");
        loanStudentLabel = new JLabel("Student Name: ");
        loanStartLabel = new JLabel("Start Date (yyyy-[m]m-[d]d): ");
        loanEndLabel = new JLabel("End Date (yyyy-[m]m-[d]d): ");
        loanReturnLabel = new JLabel("Return Date (yyyy-[m]m-[d]d): ");
        
        loanNumberField = new JTextField(10);
        loanItemField = new JTextField(10);
        loanStudentField = new JTextField(10);
        loanStartField = new JTextField(10);
        loanEndField = new JTextField(10);
        loanReturnField = new JTextField(10);
        
        enterButton = new JButton("Enter");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        displayOpenLoansButton = new JButton("Display Open Loans");
        displayOverDueLoansButton = new JButton("Display Overdue Loans");
        generateRevenueReportButton = new JButton("Generate Revenue Report For Student & Time Interval");
        
        // Create radio buttons
        bookButton = new JRadioButton("Book");
        documentaryButton = new JRadioButton("Documentary");
        
        // Create item radio button group and add radio buttons
        itemGroup = new ButtonGroup();
        itemGroup.add(bookButton);
        itemGroup.add(documentaryButton);
        
        receiptArea = new JTextArea(10, 40);
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        
        // set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // add components to layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loanNumberLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(loanNumberField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(loanItemLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(loanItemField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(loanStudentLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loanStudentField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loanStartLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(loanStartField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(loanEndLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(loanEndField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(loanReturnLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(loanReturnField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(bookButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(documentaryButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(enterButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(searchButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 7;
        add(updateButton, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 7;
        add(deleteButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        add(scrollPane, gbc);   
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(displayOpenLoansButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 9;
        add(displayOverDueLoansButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(generateRevenueReportButton, gbc);

        // Create loan
        enterButton.addActionListener(e -> {
        	String itemName = loanItemField.getText();
            String studentName = loanStudentField.getText();
            Date startDate = Date.valueOf(loanStartField.getText());
            Date endDate = Date.valueOf(loanEndField.getText());
                  
            // check if book is selected, and when creating the loan, check if the item is on loan or not.
            if (bookButton.isSelected())
            {
            	 boolean success = LoanDAO.createLoan(itemName, studentName, startDate, endDate, true);
            	 Book tempBook = BookDAO.readBook(itemName);
            	 
            	 if (!success)
            	 {
            		 JOptionPane.showMessageDialog(null, "Book: [" + itemName + "] either may not exist, is already on loan, or the student already has an open loan!");
                	 return;
            	 }
            		 
            	 JOptionPane.showMessageDialog(null, "Loan For Book: [" + itemName + "] For Student: [" + studentName + "] created."
            			 + "\nStart Date: [" + startDate + "]"
     					+ "\nDue Date: [" + endDate + "]"
     					+ "\nTotal Price will be [$" + tempBook.getDailyPrice() + "] plus [$" + (tempBook.getDailyPrice() * 0.10) + "] per day the item is overdue.");
            	 
            	 clearFields();
            }
                      
            else if (documentaryButton.isSelected())
            {
            	boolean success = LoanDAO.createLoan(itemName, studentName, startDate, endDate, false);
            	Documentary tempDocumentary = DocumentaryDAO.readDocumentary(itemName);
            	
            	if (!success)
           	 {
           		 JOptionPane.showMessageDialog(null, "Documentary: [" + itemName + "] either may not exist, is already on loan, or the student already has an open loan!");
               	 return;
           	 }
            	
            	JOptionPane.showMessageDialog(null, "Loan For Documentary: [" + itemName + "] For Student: [" + studentName + "] created."
            			+ "\nStart Date: [" + startDate + "]"
    					+ "\nDue Date: [" + endDate + "]"
    					+ "\nTotal Price will be [$" + tempDocumentary.getDailyPrice() + "] plus [$" + (tempDocumentary.getDailyPrice() * 0.10) + "] per day the item is overdue.");
           	 	
            	clearFields();
            }
            
            else 
            {
            	JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
            }  
        });
        
        // read loan
        searchButton.addActionListener(e -> {
        	int loanNumber = Integer.parseInt(loanNumberField.getText());
        	
        	Loan tempLoan = LoanDAO.readLoan(loanNumber);
        	
        	if (tempLoan == null)
        	{
        		JOptionPane.showMessageDialog(null, "No loan found!");
        		return;
        	}
        	
        	if (bookButton.isSelected())
        	{
        		Book tempBook = BookDAO.readBookByID(tempLoan.getItem().getCode());
        		Student tempStudent = StudentDAO.readStudentByID(tempLoan.getStudent().getLibraryId());
            	
            	if (tempBook == null)
            	{
            		JOptionPane.showMessageDialog(null, "No book found!");
            		return;
            	}
            	
            	if (tempStudent == null)
            	{
            		JOptionPane.showMessageDialog(null, "No student found!");
            		return;
            	}
            	
            	loanItemField.setText(tempBook.getTitle());
            	loanStudentField.setText(tempStudent.getName());
            	loanStartField.setText(tempLoan.getStartDate().toString());
            	loanEndField.setText(tempLoan.getDueDate().toString());
            	loanReturnField.setText( (tempLoan.getReturnDate() == null) ? "" : tempLoan.getReturnDate().toString());
        	}
        	
        	else if (documentaryButton.isSelected())
        	{
        		Documentary tempDocumentary = DocumentaryDAO.readDocumentaryByID(tempLoan.getItem().getCode());
        		Student tempStudent = StudentDAO.readStudentByID(tempLoan.getStudent().getLibraryId());
            	
            	if (tempDocumentary == null)
            	{
            		JOptionPane.showMessageDialog(null, "No documentary found!");
            		return;
            	}
            	
            	if (tempStudent == null)
            	{
            		JOptionPane.showMessageDialog(null, "No student found!");
            		return;
            	}
            	
            	loanItemField.setText(tempDocumentary.getTitle());
            	loanStudentField.setText(tempStudent.getName());
            	loanStartField.setText(tempLoan.getStartDate().toString());
            	loanEndField.setText(tempLoan.getDueDate().toString());
            	loanReturnField.setText( (tempLoan.getReturnDate() == null) ? "" : tempLoan.getReturnDate().toString());
        	}
        	
        	else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}
        });
        
        // Update loan
        updateButton.addActionListener(e -> {
        	
        	Loan tempLoan = new Loan();
        	Student tempStudent = StudentDAO.readStudent(loanStudentField.getText());
        	
        	tempLoan.setNumber(Integer.parseInt(loanNumberField.getText()));
        	tempLoan.setStartDate(Date.valueOf(loanStartField.getText()));
        	tempLoan.setDueDate(Date.valueOf(loanEndField.getText()));
        	tempLoan.setReturnDate( (loanReturnField.getText().equals("")) ? null : Date.valueOf(loanReturnField.getText()));
        	tempLoan.setStudent(tempStudent);
        	
        	if (bookButton.isSelected())
        	{
        		Book tempBook = BookDAO.readBook(loanItemField.getText()); 		
        		tempLoan.setItem(tempBook);
        		
        		LoanDAO.updateLoan(tempLoan);
        		
        		clearFields();
        		
        		if (tempLoan.getReturnDate() != null)
        		{
        			double totalFine = calculateFine(tempLoan);

        			JOptionPane.showMessageDialog(null, "Loan For Book: [" + tempBook.getTitle() + "] For Student: [" + tempStudent.getName() + "] updated."
            				+ "\nStart Date: [" + tempLoan.getStartDate() + "]"
        					+ "\nDue Date: [" + tempLoan.getDueDate() + "]"
        					+ "\nReturn Date: [" + tempLoan.getReturnDate() + "]"
            				+ "\nTotal Price: [$" + String.valueOf(totalFine) + "]");
        		}
        		
        		else
        		{  			
        			JOptionPane.showMessageDialog(null, "Loan For Book: [" + tempBook.getTitle() + "] For Student: [" + tempStudent.getName() + "] updated."
        					+ "\nStart Date: [" + tempLoan.getStartDate() + "]"
        					+ "\nDue Date: [" + tempLoan.getDueDate() + "]"
            				+ "\nTotal Price will be [$" + tempLoan.getItem().getDailyPrice() + "] plus [$" + (tempLoan.getItem().getDailyPrice() * 0.10) + "] per day the item is overdue.");
        		}	       		
        	}
        	
        	else if (documentaryButton.isSelected())
        	{
        		Documentary tempDocumentary = DocumentaryDAO.readDocumentary(loanItemField.getText()); 
        		tempLoan.setItem(tempDocumentary);
        		
        		LoanDAO.updateLoan(tempLoan);
        		
        		clearFields();
        		
        		if (tempLoan.getReturnDate() != null)
        		{
        			double totalFine = calculateFine(tempLoan);

        			JOptionPane.showMessageDialog(null, "Loan For Documentary: [" + tempDocumentary.getTitle() + "] For Student: [" + tempStudent.getName() + "] updated."
        					+ "\nStart Date: [" + tempLoan.getStartDate() + "]"
        					+ "\nDue Date: [" + tempLoan.getDueDate() + "]"
        					+ "\nReturn Date: [" + tempLoan.getReturnDate() + "]"
            				+ "\nTotal Price: [$" + String.valueOf(totalFine) + "]");
        		}
        		
        		else
        		{
        			JOptionPane.showMessageDialog(null, "Loan For Documentary: [" + tempDocumentary.getTitle() + "] For Student: [" + tempStudent.getName() + "] updated."
        					+ "\nStart Date: [" + tempLoan.getStartDate() + "]"
        					+ "\nDue Date: [" + tempLoan.getDueDate() + "]"
        					+ "\nTotal Price will be [$" + tempLoan.getItem().getDailyPrice() + "] plus [$" + (tempLoan.getItem().getDailyPrice() * 0.10) + "] per day the item is overdue.");
        		}	 
        	}
        	
        	else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}         
        });
        
        // Delete loan
        deleteButton.addActionListener(e -> {
        	
            if (bookButton.isSelected())
            {
            	Book tempBook = BookDAO.readBook(loanItemField.getText());
            	Student tempStudent = StudentDAO.readStudent(loanStudentField.getText());
            	
            	Loan tempLoan = new Loan();
            	
            	tempLoan.setNumber(Integer.parseInt(loanNumberField.getText()));
            	tempLoan.setStartDate(Date.valueOf(loanStartField.getText()));
            	tempLoan.setDueDate(Date.valueOf(loanEndField.getText()));
            	tempLoan.setReturnDate((loanReturnField.getText().equals("")) ? null : Date.valueOf(loanReturnField.getText())); 
            	tempLoan.setStudent(tempStudent);
            	tempLoan.setItem(tempBook);
            		
            	LoanDAO.deleteLoan(tempLoan);
            	
            	clearFields();
            	
            	JOptionPane.showMessageDialog(null, "Loan For Book: [" + tempBook.getTitle() + "] For Student: [" + tempStudent.getName() + "] deleted.");
            }
            
            else if (documentaryButton.isSelected())
            {
            	Documentary tempDocumentary = DocumentaryDAO.readDocumentary(loanItemField.getText());
            	Student tempStudent = StudentDAO.readStudent(loanStudentField.getText());
            	
            	Loan tempLoan = new Loan();
            	
            	tempLoan.setNumber(Integer.parseInt(loanNumberField.getText()));
            	tempLoan.setStartDate(Date.valueOf(loanStartField.getText()));
            	tempLoan.setDueDate(Date.valueOf(loanEndField.getText()));
            	tempLoan.setReturnDate((loanReturnField.getText().equals("")) ? null : Date.valueOf(loanReturnField.getText())); 
            	tempLoan.setStudent(tempStudent);
            	tempLoan.setItem(tempDocumentary);
            		
            	LoanDAO.deleteLoan(tempLoan);
            	
            	clearFields();
            	
            	JOptionPane.showMessageDialog(null, "Loan For Documentary: [" + tempDocumentary.getTitle() + "] For Student: [" + tempStudent.getName() + "] deleted.");
            }
            
            else 
        	{
        		JOptionPane.showMessageDialog(null, "ERROR: Please select Book or Documentary.");
        		return;
        	}    
        });
        
        // display all loans button
        displayOpenLoansButton.addActionListener(e -> {
        	displayOpenLoans();
        });
        
        // display overdue loans button
        displayOverDueLoansButton.addActionListener(e -> {
        	displayOverDueLoans();
        });
        
        generateRevenueReportButton.addActionListener(e -> {
        	String studentName = loanStudentField.getText();
        	Date startDate = Date.valueOf(loanStartField.getText());
        	Date endDate = Date.valueOf(loanEndField.getText());
        	
        	generateRevenueReport(studentName, startDate, endDate);
        });
        
    }
    
	private void clearFields()
	{
		loanNumberField.setText("");
		loanItemField.setText("");
		loanStudentField.setText("");
	    loanStartField.setText("");
	    loanEndField.setText("");
	    loanReturnField.setText("");	
	    receiptArea.setText("");
	}
	
	// Display any loans that are either still open or are overdue
	private void displayOpenLoans()
	{
		List<Loan> overDueLoans = LoanDAO.getOpenLoans();
        String loansText = "";
        
        for (int i = 0; i < overDueLoans.size(); i++)
        {
        	loansText += overDueLoans.get(i) + "\n";
        }
        
        receiptArea.setText(loansText);
	}
	
	// Display all loans that are overdue
	private void displayOverDueLoans()
	{
		List<Loan> overDueLoans = LoanDAO.getOpenLoans();
        String overDueLoansText = "";
        
        for (int i = 0; i < overDueLoans.size(); i++)
        {
        	Loan currentLoan = overDueLoans.get(i);

        	// Convert to java.sql.Date
        	Date currentDate = Date.valueOf(LocalDate.now());
        	
        	if (currentDate.compareTo(currentLoan.getDueDate()) > 0)
        	{
        		overDueLoansText += currentLoan + "\n";
        	}
        }
        
        receiptArea.setText(overDueLoansText);
	}
	
	private double calculateFine(Loan loan)
	{
		// Calculate number of days after the due date until the book was returned
		LocalDateTime dueDate = loan.getDueDate().toLocalDate().atStartOfDay();
		LocalDateTime returnDate = loan.getReturnDate().toLocalDate().atStartOfDay();
		
		// if they turned it in early or on time, they pay $0
		if (returnDate.isBefore(dueDate))
		{
			loan.setTotalLoanPrice(0.0);
			LoanDAO.updateLoan(loan);
			return 0.0;
		}
		
		Duration duration = Duration.between(dueDate, returnDate);
		long days = duration.toDays();
		
		double loanItemPrice = loan.getItem().getDailyPrice();

		// totalFine is the loan item price + (10% of the price per extra day)
		double totalFine =  (loanItemPrice + (days * (loanItemPrice * 0.10)));
		totalFine = (totalFine < 0.0) ? 0.0 : totalFine;
		
		// update the loans total price
		loan.setTotalLoanPrice(totalFine);
		
		LoanDAO.updateLoan(loan);
		
		return totalFine;
	}

	private void generateRevenueReport(String studentName, Date startDate, Date endDate)
	{
		// Get all loans first
		List<Loan> loans = LoanDAO.getAllLoans();
        String revenueReportText = "Revenue Info For: [" + studentName + "] for Period: [" + startDate + " to " + endDate + "]";
        
        double totalPayments = 0.0;
        
        // Check if the current loan has our student, and if its in between the period we are checking
        for (int i = 0; i < loans.size(); i++)
        {
        	Loan currentLoan = loans.get(i);
        	String currentStudentName = currentLoan.getStudent().getName();
        	Date currentReturnDate = currentLoan.getReturnDate();
        	
        	// if the item hasn't even been returned, don't bother
        	if (currentReturnDate == null)
        	{
        		continue;
        	}
        	
        	// if the current student is not the student we are looking for, we don't care
        	if (!currentStudentName.equals(studentName))
        	{
        		continue;
        	}
        	
        	// if the current date is not what we are looking for, we don't care
        	if ( !(currentReturnDate.after(startDate) && currentReturnDate.before(endDate)) )
        	{
        		continue;
        	}
        	
        	double currentLoanPayment = currentLoan.getTotalLoanPrice();
        	totalPayments += currentLoanPayment;
        	
        	revenueReportText += "\n\tPayment of: [$" + currentLoanPayment + "] on Date: [" + currentReturnDate + "]";
        }
        
        revenueReportText += "\nTotal Payments: [$" + totalPayments + "]"; 
        
        receiptArea.setText(revenueReportText);
	}
    
    public void showWindow()
	{
		setVisible(true);
	}
    
    public static void main(String[] args) {
        LoanGUI loanGUI = new LoanGUI();
        loanGUI.showWindow();
    }
}




