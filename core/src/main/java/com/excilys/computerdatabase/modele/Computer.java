package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;


@Entity
@Table(name="computer")
//TODO contraintes etc
public class Computer implements Serializable {
	
	private static final long serialVersionUID = 4682196630919234988L;

	private int id;
	private String name;
	private LocalDateTime dateAdded;
	private LocalDateTime dateRemoved;
	private Company company;
	
	public Computer() { }
	

	public Computer(int pid, String pname, LocalDateTime pdateAdded, LocalDateTime pdateRemoved, Company pcompany) {
		super();
		this.id = pid;
		this.name = pname;
		this.dateAdded = pdateAdded;
		this.dateRemoved = pdateRemoved;
		this.company = pcompany;
	}


	@Override
	public String toString() {
		String nameToShow = name.length()>=30 ? name.substring(0, 30) : String.format("%30s", name);
		return "\n" + id + "-" + nameToShow + "\t added " + dateAdded + "\t removed " + dateRemoved
				+ "\t company " + (company != null ? company.toString() : "null");
	}
	
	/**
	 * Doesn't check computer id and company object.
	 */
	@Override
	public boolean equals(Object obj) {
		
		//Check references
        if (obj==this) {
            return true;
        }
 
        //Check typeof obj
        if (obj instanceof Computer) {
        	
            //Check members
        	Computer other = (Computer) obj;
 
            //If not the same object
            if (this.name != other.name) {
                //Check with equals
                if (this.name == null || !this.name.equals(other.name)) {
                    return false; 
                }
            }
 
            //If not the same object
            if (this.dateAdded != other.dateAdded) {
                //Check with equals
                if (this.dateAdded == null || !this.dateAdded.equals(other.dateAdded)) {
                    return false; 
                }
            }
 
            //If not the same object
            if (this.dateRemoved != other.dateRemoved) {
                //Check with equals
                if (this.dateRemoved == null || !this.dateRemoved.equals(other.dateRemoved)) {
                    return false; 
                }
            }
 
            //If not the same object
            /*if (this.company != other.company) {
                //Check with equals
                if (this.company == null || !this.company.equals(other.company)) {
                    return false; 
                }
            }*/
 
            //Here all members are the same
            return true;
        }
 
        return false;
	}

	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="introduced")
	@Convert(converter = LocalDatePersistenceConverter.class)
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}

	@Column(name="discontinued")
	@Convert(converter = LocalDatePersistenceConverter.class)
	public LocalDateTime getDateRemoved() {
		return dateRemoved;
	}

	@ManyToOne(optional=true, fetch = FetchType.EAGER)//, cascade=CascadeType.ALL
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return company;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setDateRemoved(LocalDateTime dateRemoved) {
		this.dateRemoved = dateRemoved;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
