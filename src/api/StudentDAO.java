package api;

import domain.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudentDAO 
{
	public static void createStudent(String broncoId, String name, String course) 
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
			
			Student tempStudent = new Student(broncoId, name, course);
			
			session.save(tempStudent);
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Student readStudent(String name)
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
			
			Student tempStudent = new Student();
			
			tempStudent.setName(name);
			
			// need to use the Class name, not table name
			String hql = "FROM Student WHERE name=:name";
			
			
			@SuppressWarnings("unchecked")
			List<Student> students = session.createQuery(hql)
					.setParameter("name", tempStudent.getName())
					.list();
			
			tempStudent = (students.isEmpty()) ? null : students.get(0);
			
			session.getTransaction().commit();
			
			return tempStudent;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static Student readStudentByID(int libraryId)
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
			
			Student tempStudent = session.get(Student.class, libraryId);
			
			return tempStudent;
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
		
	public static void updateStudent(Student student)
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
			
			Student tempStudent = session.get(Student.class, student.getLibraryId());
			
			tempStudent.setBroncoId(student.getBroncoId());
			tempStudent.setName(student.getName());
			tempStudent.setCourse(student.getCourse());
			tempStudent.setHasCurrentLoan(student.getHasCurrentLoan());
			
			session.getTransaction().commit();
		}
		
		finally
		{
			session.close();
			factory.close();
		}
	}
	
	public static boolean deleteStudent(Student student)
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
			
			// Retrieve the student from the database using their name
			String hql = "FROM Student WHERE name=:name";
			Student tempStudent = (Student) session.createQuery(hql)
			                                    .setParameter("name", student.getName())
			                                    .uniqueResult();
			
			// if student has a current loan, they can't be deleted
			if (tempStudent.getHasCurrentLoan())
			{
				return false;
			}

			// Update the customer object with the correct ID
			student.setLibraryId(tempStudent.getLibraryId());
			
			session.delete(session.get(Student.class, student.getLibraryId()));
			
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

