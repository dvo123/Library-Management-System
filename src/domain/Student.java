package domain;



import javax.persistence.*;

@Entity
@Table(name="students")
public class Student
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq")
    @SequenceGenerator(name = "students_seq", sequenceName = "students_library_id_seq", allocationSize = 1)
    @Column(name = "library_id")
	private int libraryId;
	
	@Column(name="bronco_id")
	private String broncoId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="course")
	private String course;
	
	@Column(name="has_current_loan")
	private boolean hasCurrentLoan;
	
	public Student(String broncoId, String name, String course)
	{
		this.broncoId = broncoId;
		this.name = name;
		this.course = course;
	}
	
	public Student()
	{
		
	}
	
	public int getLibraryId()
	{
		return libraryId;
	}

	public void setLibraryId(int libraryId)
	{
		this.libraryId = libraryId;
	}

	public String getBroncoId()
	{
		return broncoId;
	}
	public void setBroncoId(String broncoId)
	{
		this.broncoId = broncoId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCourse()
	{
		return course;
	}
	public void setCourse(String course)
	{
		this.course = course;
	}
	
	public boolean getHasCurrentLoan()
	{
		return hasCurrentLoan;
	}

	public void setHasCurrentLoan(boolean hasCurrentLoan)
	{
		this.hasCurrentLoan = hasCurrentLoan;
	}

	@Override
	public String toString()
	{
		return "Student [libraryId=" + libraryId + ", broncoId=" + broncoId + ", name=" + name + ", "
				+ "course=" + course + "]";
	}
}
