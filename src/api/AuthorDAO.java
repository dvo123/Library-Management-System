package api;

import domain.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AuthorDAO 
{
	public static void createAuthor(String name, String nationality, String subject) 
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
			
			// Note that doing this will have the Creator's ID 
			// be AuthorID - 1. I can't figure out a way around this.
			Creator tempCreator = new Creator(name, nationality);
			Author tempAuthor = new Author(name, nationality, subject);
			
			session.save(tempCreator);
			session.save(tempAuthor);
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Author readAuthor(String name)
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
			
			Author tempAuthor = new Author();
			
			tempAuthor.setName(name);
			
			// need to use the Class name, not table name
			// ie uppercase Author like the class
			// not lowercase authors like the table in pgAdmin
			String hql = "FROM Author WHERE name=:name";
			
			
			@SuppressWarnings("unchecked")
			List<Author> authors = session.createQuery(hql)
					.setParameter("name", tempAuthor.getName())
					.list();
			
			tempAuthor = (authors.isEmpty()) ? null : authors.get(0);
			
			session.getTransaction().commit();
			
			return tempAuthor;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static void updateAuthor(Author author)
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
			
			// Retrieve the author from the database using their id
			String hql = "FROM Author WHERE id=:id";
			Author tempAuthor = (Author) session.createQuery(hql)
			                                    .setParameter("id", author.getId())
			                                    .uniqueResult();
			
			tempAuthor = session.get(Author.class, tempAuthor.getId());
					
			// Update the author object with the correct ID
//			author.setId(tempAuthor.getId());
			
			hql = "UPDATE Author SET name=:name, nationality=:nationality, subject=:subject WHERE id=:id";
			
			session.createQuery(hql)
				.setParameter("name", author.getName())
				.setParameter("nationality", author.getNationality())
				.setParameter("subject", author.getSubject())
				.setParameter("id", author.getId())
				.executeUpdate();
			
			// Retrieve the creator from the database using their name
			hql = "FROM Creator WHERE id=:id";
			Creator tempCreator = (Creator) session.createQuery(hql)
						                         .setParameter("id", tempAuthor.getId() - 1) // CreatorID = AuthorID - 1
						                         .uniqueResult();
			
			// similar to item, once a creator is created, it can't be updated to follow its child's data...
			// important to mainly track the author/director or book/documentary itself rather than its corresponding creator/item
			hql = "UPDATE Creator SET name=:name, nationality=:nationality WHERE id=:id";
			
			session.createQuery(hql)
				.setParameter("name", tempCreator.getName())
				.setParameter("nationality", tempCreator.getNationality())
				.setParameter("id", tempCreator.getId())
				.executeUpdate();
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static boolean deleteAuthor(Author author)
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
			
			// Retrieve the author from the database using their name
			String hql = "FROM Author WHERE name=:name";
			Author tempAuthor = (Author) session.createQuery(hql)
			                                    .setParameter("name", author.getName())
			                                    .uniqueResult();
			
			hql = "FROM Book";
			// Retrive all books
			List<Book> books = session.createQuery(hql, Book.class).getResultList();
			
			
			// If there is a book with this author, we can't delete the author
			for (int i = 0; i < books.size(); i++)
			{
				Book currentBook = books.get(i);
				
				if (currentBook.getAuthor().getName().equals(tempAuthor.getName()))
				{
					return false;
				}
			}

			// Update the author object with the correct ID
			author.setId(tempAuthor.getId());
			
			session.delete(session.get(Author.class, author.getId()));
			
			// Retrieve the creator from the database using their name
			hql = "FROM Creator WHERE id=:id";
			Creator tempCreator = (Creator) session.createQuery(hql)
												   .setParameter("id", author.getId() - 1) // CreatorID = AuthorID - 1
												    .uniqueResult();
						
			session.delete(session.get(Creator.class, tempCreator.getId()));
			
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

