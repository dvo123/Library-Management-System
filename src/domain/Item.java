package domain;

import javax.persistence.*;

@Entity
@Table(name="items")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_seq")
    @SequenceGenerator(name = "items_seq", sequenceName = "items_code_seq", allocationSize = 1)
    @Column(name = "code")
	private int code;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="location")
	private String location;
	
	@Column(name="daily_price")
	private double dailyPrice;
	
	@Column(name="is_on_loan")
	private boolean isOnLoan;

	public Item (int code, String title, String description, 
			String location, double dailyPrice, boolean isOnLoan)
	{
		this.code = code;
		this.title = title;
		this.description = description;
		this.location = location;
		this.dailyPrice = dailyPrice;
		this.isOnLoan = isOnLoan;
	}
	
	public Item()
	{
		
	}
	
	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public double getDailyPrice()
	{
		return dailyPrice;
	}

	public void setDailyPrice(double dailyPrice)
	{
		this.dailyPrice = dailyPrice;
	}

	public boolean getIsOnLoan()
	{
		return isOnLoan;
	}

	public void setIsOnLoan(boolean isOnLoan)
	{
		this.isOnLoan = isOnLoan;
	}
	
	@Override
	public String toString()
	{
		return "Item [code=" + code + ", title=" + title + ", "
				+ "description=" + description + ", location=" + location
				+ ", dailyPrice=" + dailyPrice + ", isOnLoan=" + isOnLoan + "]";
	}
	
	
	
}
