package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;


@Entity
@Table(name="computer")
public class Computer implements Serializable {
	
	private static final long serialVersionUID = 4682196630919234988L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="introduced")
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDateTime dateAdded;
	
	@Column(name="discontinued")
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDateTime dateRemoved;
	
	@ManyToOne(optional=true)//, fetch = FetchType.EAGER//@JoinColumn(name="company.id")
	private Company company;
	
	protected Computer() { }
	

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
				+ "\t company " + company.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		// Vérification de l'égalité des références
        if (obj==this) {
            return true;
        }
 
        // Vérification du type du paramètre
        if (obj instanceof Computer) {
            // Vérification des valeurs des attributs
        	Computer other = (Computer) obj;
 
            //compare les references
            if (this.name != other.name) {
                //compare avec equals
                if (this.name == null || !this.name.equals(other.name)) {
                    return false; 
                }
            }
 
            // Si on arrive ici c'est que tous les attributs sont égaux :
            return true;
        }
 
        return false;
	}

	
	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDateAdded() {
		return dateAdded;
	}

	public LocalDateTime getDateRemoved() {
		return dateRemoved;
	}

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
