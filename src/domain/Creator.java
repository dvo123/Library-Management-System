package domain;

import javax.persistence.*;

@Entity
@Table(name="creators")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Creator
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creators_seq")
    @SequenceGenerator(name = "creators_seq", sequenceName = "creators_id_seq", allocationSize = 1)
    @Column(name = "id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="nationality")
	private String nationality;
	
	public Creator(String name, String nationality)
	{
		this.name = name;
		this.nationality = nationality;
	}
	
	public Creator()
	{
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	@Override
	public String toString()
	{
		return "Creator [name=" + name + ", nationality=" + nationality + "]";
	}
	
}
