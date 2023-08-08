package domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="loans")
public class Loan
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loans_seq")
    @SequenceGenerator(name = "loans_seq", sequenceName = "loans_number_seq", allocationSize = 1)
    @Column(name = "number")
	private int number;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="due_date")
	private Date dueDate;
	
	@Column(name="return_date")
	private Date returnDate;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="student_id")
	private Student student;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="item_id")
	private Item item;
	
	@Column(name="total_loan_price")
	private double totalLoanPrice;
	
	public Loan(int number, Date startDate, Date dueDate, Student student, Item item)
	{
		this.number = number;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.student = student;
		this.item = item;
	}
	
	public Loan()
	{
		
	}
	
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}
	public Date getReturnDate()
	{
		return returnDate;
	}
	public void setReturnDate(Date returnDate)
	{
		this.returnDate = returnDate;
	}
	public Student getStudent()
	{
		return student;
	}

	public void setStudent(Student student)
	{
		this.student = student;
	}
	public Item getItem()
	{
		return item;
	}
	public void setItem(Item item)
	{
		this.item = item;
	}

	public double getTotalLoanPrice()
	{
		return totalLoanPrice;
	}

	public void setTotalLoanPrice(double totalLoanPrice)
	{
		this.totalLoanPrice = totalLoanPrice;
	}

	@Override
	public String toString()
	{
		return "Loan [number=" + number + ", startDate=" + 
	startDate + ", dueDate=" + dueDate + ", returnDate="
				+ returnDate + ", student=" + student.toString() + ", item=" + item.toString() + "]";
	}
	
	
}
