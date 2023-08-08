package api;

import domain.*;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DocumentaryDAO
{
	public static void createDocumentary(String title, String description, 
			String location, double dailyPrice,
			int length, Date releaseDate, Director director) 
	{
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Author.class)
								.addAnnotatedClass(Book.class)
								.addAnnotatedClass(Creator.class)
								.addAnnotatedClass(Director.class)
								.addAnnotatedClass(Documentary.class)
								.addAnnotatedClass(Item.class)
								.addAnnotatedClass(Loan.class)
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try
		{
			session.beginTransaction();
			
			// Note that doing this will have the Item's ID 
			// be DocumentaryID - 1. I can't figure out a way around this.
			Item tempItem = new Item();
			Documentary tempDocumentary = new Documentary();
			
			tempItem.setTitle(title);
			tempItem.setDescription(description);
			tempItem.setLocation(location);
			tempItem.setDailyPrice(dailyPrice);
			tempItem.setIsOnLoan(false);
			
			tempDocumentary.setTitle(title);
			tempDocumentary.setDescription(description);
			tempDocumentary.setLocation(location);
			tempDocumentary.setDailyPrice(dailyPrice);
			tempDocumentary.setIsOnLoan(false);
			tempDocumentary.setLength(length);
			tempDocumentary.setReleaseDate(releaseDate);
			tempDocumentary.setDirector(director);
			
			session.save(tempItem);
			session.save(tempDocumentary);
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Documentary readDocumentary(String title)
	{
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(Creator.class)
				.addAnnotatedClass(Director.class)
				.addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Item.class)
				.addAnnotatedClass(Loan.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try
		{
			session.beginTransaction();
			
			Documentary tempDocumentary = new Documentary();
			
			tempDocumentary.setTitle(title);
			
			// need to use the Class name, not table name
			String hql = "FROM Documentary WHERE title=:title";
			
			@SuppressWarnings("unchecked")
			List<Documentary> documentaries = session.createQuery(hql)
					.setParameter("title", tempDocumentary.getTitle())
					.list();
			
			tempDocumentary = (documentaries.isEmpty()) ? null : documentaries.get(0);
			
			session.getTransaction().commit();
			
			return tempDocumentary;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Documentary readDocumentaryByID(int documentaryId)
	{
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(Creator.class)
				.addAnnotatedClass(Director.class)
				.addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Item.class)
				.addAnnotatedClass(Loan.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try
		{
			session.beginTransaction();
			
			Documentary tempDocumentary = session.get(Documentary.class, documentaryId);
			
			return tempDocumentary;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static void updateDocumentary(Documentary documentary)
	{
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(Creator.class)
				.addAnnotatedClass(Director.class)
				.addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Item.class)
				.addAnnotatedClass(Loan.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try
		{
			session.beginTransaction();
			
			// documentaryCode should be itemCode + 1
			int itemCode = documentary.getCode();
//			int documentaryCode = itemCode + 1;
			
			Item tempItem = session.get(Item.class, itemCode);
			
			tempItem.setTitle(documentary.getTitle());
			tempItem.setDescription(documentary.getDescription());
			tempItem.setLocation(documentary.getLocation());
			tempItem.setDailyPrice(documentary.getDailyPrice());
			tempItem.setIsOnLoan(documentary.getIsOnLoan());
			
			Documentary tempDocumentary = session.get(Documentary.class, itemCode);
			
			tempDocumentary.setTitle(documentary.getTitle());
			tempDocumentary.setDescription(documentary.getDescription());
			tempDocumentary.setLocation(documentary.getLocation());
			tempDocumentary.setDailyPrice(documentary.getDailyPrice());
			tempDocumentary.setIsOnLoan(documentary.getIsOnLoan());
			tempDocumentary.setLength(documentary.getLength());
			tempDocumentary.setReleaseDate(documentary.getReleaseDate());
			tempDocumentary.setDirector(documentary.getDirector());			
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static boolean deleteDocumentary(Documentary documentary)
	{
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(Creator.class)
				.addAnnotatedClass(Director.class)
				.addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Item.class)
				.addAnnotatedClass(Loan.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try
		{
			session.beginTransaction();
			
			String hql = "FROM Loan";
			// Retrive all loans
			List<Loan> loans = session.createQuery(hql, Loan.class).getResultList();
			
			
			// If there is a loan with this item, we can't delete the item
			for (int i = 0; i < loans.size(); i++)
			{
				Loan currentLoan = loans.get(i);
				
				if (currentLoan.getItem().getTitle().equals(documentary.getTitle()))
				{
					return false;
				}
			}
			
			// Remember that the ItemID is the Documentary - 1.
			Item tempItem = session.get(Item.class, documentary.getCode() - 1);
					
			session.delete(session.get(Documentary.class, documentary.getCode()));
			session.delete(session.get(Item.class, tempItem.getCode()));
			
			session.getTransaction().commit();
			
			return true;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
}
