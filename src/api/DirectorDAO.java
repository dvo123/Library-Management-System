package api;

import domain.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DirectorDAO 
{
	public static void createDirector(String name, String nationality, String style) 
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
			// be DirectorID - 1. I can't figure out a way around this.
			Creator tempCreator = new Creator(name, nationality);
			Director tempDirector = new Director(name, nationality, style);
			
			session.save(tempCreator);
			session.save(tempDirector);
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Director readDirector(String name)
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
			
			Director tempDirector = new Director();
			
			tempDirector.setName(name);
			
			// need to use the Class name, not table name
			// ie uppercase Director like the class
			// not lowercase directors like the table in pgAdmin
			String hql = "FROM Director WHERE name=:name";
			
			
			@SuppressWarnings("unchecked")
			List<Director> directors = session.createQuery(hql)
					.setParameter("name", tempDirector.getName())
					.list();
			
			tempDirector = (directors.isEmpty()) ? null : directors.get(0);
			
			session.getTransaction().commit();
			
			return tempDirector;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static void updateDirector(Director director)
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
			
			// Retrieve the director from the database using their id
			String hql = "FROM Director WHERE id=:id";
			Director tempDirector = (Director) session.createQuery(hql)
			                                    .setParameter("id", director.getId())
			                                    .uniqueResult();
			
			tempDirector = session.get(Director.class, tempDirector.getId());
					
			// Update the director object with the correct ID
//			director.setId(tempDirector.getId());
			
			hql = "UPDATE Director SET name=:name, nationality=:nationality, style=:style WHERE id=:id";
			
			session.createQuery(hql)
				.setParameter("name", director.getName())
				.setParameter("nationality", director.getNationality())
				.setParameter("style", director.getStyle())
				.setParameter("id", director.getId())
				.executeUpdate();
			
			// Retrieve the creator from the database using their name
			hql = "FROM Creator WHERE id=:id";
			Creator tempCreator = (Creator) session.createQuery(hql)
						                         .setParameter("id", tempDirector.getId() - 1) // CreatorID = DirectorID - 1
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
	
	public static boolean deleteDirector(Director director)
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
			
			// Retrieve the director from the database using their name
			String hql = "FROM Director WHERE name=:name";
			Director tempDirector = (Director) session.createQuery(hql)
			                                    .setParameter("name", director.getName())
			                                    .uniqueResult();
			
			
			hql = "FROM Documentary";
			// Retrive all documentaries
			List<Documentary> documentaries = session.createQuery(hql, Documentary.class).getResultList();
			
			
			// If there is a documentary with this director, we can't delete the director
			for (int i = 0; i < documentaries.size(); i++)
			{
				Documentary currentDocumentary = documentaries.get(i);
				
				if (currentDocumentary.getDirector().getName().equals(tempDirector.getName()))
				{
					return false;
				}
			}
			
			// Update the director object with the correct ID
			director.setId(tempDirector.getId());
			
			session.delete(session.get(Director.class, director.getId()));
			
			// Retrieve the creator from the database using their name
			hql = "FROM Creator WHERE id=:id";
			Creator tempCreator = (Creator) session.createQuery(hql)
									               .setParameter("id", director.getId() - 1) // CreatorID = DirectorID - 1
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

