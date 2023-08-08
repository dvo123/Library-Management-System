package api;

import domain.*;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BookDAO 
{
	public static void createBook(String title, String description, 
			String location, double dailyPrice,
			int pages, String publisher, Date publicationDate, Author author) 
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
			// be BookID - 1. I can't figure out a way around this.
			Item tempItem = new Item();
			Book tempBook = new Book();
			
			tempItem.setTitle(title);
			tempItem.setDescription(description);
			tempItem.setLocation(location);
			tempItem.setDailyPrice(dailyPrice);
			tempItem.setIsOnLoan(false);
			
			tempBook.setTitle(title);
			tempBook.setDescription(description);
			tempBook.setLocation(location);
			tempBook.setDailyPrice(dailyPrice);
			tempBook.setIsOnLoan(false);
			tempBook.setPages(pages);
			tempBook.setPublisher(publisher);
			tempBook.setPublicationDate(publicationDate);
			tempBook.setAuthor(author);
			
			session.save(tempItem);
			session.save(tempBook);
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Book readBook(String title)
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
			
			Book tempBook = new Book();
			
			tempBook.setTitle(title);
			
			// need to use the Class name, not table name
			String hql = "FROM Book WHERE title=:title";
			
			@SuppressWarnings("unchecked")
			List<Book> books = session.createQuery(hql)
					.setParameter("title", tempBook.getTitle())
					.list();
			
			tempBook = (books.isEmpty()) ? null : books.get(0);
			
			session.getTransaction().commit();
			
			return tempBook;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Book readBookByID(int bookId)
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
			
			Book tempBook = session.get(Book.class, bookId);
			
			return tempBook;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static void updateBook(Book book)
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
			
			// bookCode should be itemCode + 1
			int itemCode = book.getCode();
//			int bookCode = itemCode + 1;
			
			Item tempItem = session.get(Item.class, itemCode);
			
			tempItem.setTitle(book.getTitle());
			tempItem.setDescription(book.getDescription());
			tempItem.setLocation(book.getLocation());
			tempItem.setDailyPrice(book.getDailyPrice());
			tempItem.setIsOnLoan(book.getIsOnLoan());
			
			Book tempBook = session.get(Book.class, itemCode);
			
			tempBook.setTitle(book.getTitle());
			tempBook.setDescription(book.getDescription());
			tempBook.setLocation(book.getLocation());
			tempBook.setDailyPrice(book.getDailyPrice());
			tempBook.setIsOnLoan(book.getIsOnLoan());
			tempBook.setPages(book.getPages());
			tempBook.setPublisher(book.getPublisher());
			tempBook.setPublicationDate(book.getPublicationDate());
			tempBook.setAuthor(book.getAuthor());	
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static boolean deleteBook(Book book)
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
				
				if (currentLoan.getItem().getTitle().equals(book.getTitle()))
				{
					return false;
				}
			}
			
			// Remember that the ItemID is the BookID - 1.
			Item tempItem = session.get(Item.class, book.getCode() - 1);
					
			session.delete(session.get(Book.class, book.getCode()));
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

