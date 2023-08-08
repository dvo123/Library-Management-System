package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JFrame frame;
    private JPanel panel;

    private JButton loanButton;
    private JButton authorButton;
    private JButton directorButton;
    private JButton itemButton;
    private JButton studentButton;

    public MainGUI() {
        frame = new JFrame("Library Management System");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        loanButton = new JButton("Loan");
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoanGUI loanGUI = new LoanGUI();
                loanGUI.showWindow();
            }
        });

        authorButton = new JButton("Author");
        authorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthorGUI authorGUI = new AuthorGUI();
                authorGUI.showWindow();
            }
        });

        directorButton = new JButton("Director");
        directorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirectorGUI directorGUI = new DirectorGUI();
                directorGUI.showWindow();
            }
        });

        itemButton = new JButton("Item");
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemGUI itemGUI = new ItemGUI();
                itemGUI.showWindow();
            }
        });

        studentButton = new JButton("Student");
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentGUI studentGUI = new StudentGUI();
                studentGUI.showWindow();
            }
        });

        panel.add(loanButton);
        panel.add(authorButton);
        panel.add(directorButton);
        panel.add(itemButton);
        panel.add(loanButton);
        panel.add(studentButton);

        frame.add(panel);

        frame.setVisible(true);
    }

    public void showWindow()
	{
		frame.setVisible(true);
	}
    
    public static void main(String[] args) {
        MainGUI mainGUI = new MainGUI();
        mainGUI.showWindow();
    }
}