package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 2736843274120613787L;

	private int id=-1;
	private String name=null;
	private Set<Computer> computers = new HashSet<Computer>(0);
	
	public Company() { }

	public Company(String nomFabricant) {
		super();
		this.name = nomFabricant;
	}
	

	public Company(int id) {
		super();
		this.id = id;
	}
	

	public Company(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}


	@Override
	public String toString() {
		return this.id + "-" + this.name;
	}
	
	
	
	/**
	 * Checks id and name.
	 */
	@Override
	public boolean equals(Object obj) {
		
		//Check references
        if (obj==this) {
            return true;
        }
 
        //Check typeof obj
        if (obj instanceof Company) {
        	
            //Check members
        	Company other = (Company) obj;
 
            //If not equal
            if (this.id != other.id) {
            	return false; 
            }
            
			//If not the same object
            if (this.name != other.name) {
                //Check with equals
                if (this.name == null || !this.name.equals(other.name)) {
                    return false; 
                }
            }
 
            //Here all members are the same
            return true;
        }
 
        return false;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	//For JPA :
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<Computer> getComputers() {
		return this.computers;
	}
 
	public void setComputers(Set<Computer> computers) {
		this.computers = computers;
	}
	
}
