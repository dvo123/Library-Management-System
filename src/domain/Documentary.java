package domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="documentaries")
public class Documentary extends Item
{
	
	// length is an integer representing time in minutes
	@Column(name="length")
	private int length;
	
	@Column(name="release_date")
	private Date releaseDate;

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="director_id")
	private Creator director;
	
	public Documentary(int code, String title, String description, 
			String location, double dailyPrice, boolean isOnLoan,
			int length, Date releaseDate, Creator director)
	{
		super(code, title, description, location, dailyPrice, isOnLoan);
		this.length = length;
		this.releaseDate = releaseDate;
		this.director = director;
	}
	
	public Documentary()
	{
		super();
	}

	public int getLength()
	{
		return length;
	}
	public void setLength(int length)
	{
		this.length = length;
	}
	public Date getReleaseDate()
	{
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate)
	{
		this.releaseDate = releaseDate;
	}
	public Creator getDirector()
	{
		return director;
	}
	public void setDirector(Creator director)
	{
		this.director = director;
	}

	@Override
	public String toString()
	{
		return "Documentary [code=" + this.getCode() + ", title=" + this.getTitle() 
				+ 	", description=" + this.getDescription() + ", location=" + this.getLocation()
				+ 	", dailyPrice=" + this.getDailyPrice() + ", isOnLoan=" + this.getIsOnLoan() 
				+   ", length=" + length 
				+   ", releaseDate=" + releaseDate 
				+   ", director=" + director.toString() + "]";
	}
	
	
	
}
