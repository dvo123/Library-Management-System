package domain;

import javax.persistence.*;

@Entity
@Table(name="authors")
public class Author extends Creator
{
	
	@Column(name="subject")
	private String subject;

	public Author(String name, String nationality, String subject)
	{
		super(name, nationality);
		this.subject = subject;
	}
	
	public Author()
	{
		super();
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	@Override
	public String toString()
	{
		return "Author [name=" + this.getName() + ", nationality=" + this.getNationality() +
				", subject=" + subject + "]";
	}
	
}
