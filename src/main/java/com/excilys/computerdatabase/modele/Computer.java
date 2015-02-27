package com.excilys.computerdatabase.modele;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.excilys.computerdatabase.util.Constantes;

public class Computer implements Serializable {
	
	public int id;
	public String name;
	private Date dateAdded;//LocalDateTime
	private Date dateRemoved;
	public Company company;
	
	public Computer() { }
	
	public long getDateAddedLong() {
		if (dateAdded==null) {
			return 0;
		}
		else {
			return dateAdded.getTime();
		}
	}
	public long getDateRemovedLong() {
		if (dateRemoved == null) {
			return 0;
		}
		else {
			return dateRemoved.getTime();
		}
	}
	
	public String getDateAddedString() {
		if (dateAdded == null) {
			return "";
		}
		else{
			return Constantes.dateFormat.format(dateAdded);
		}
	}
	
	public String getDateRemovedString() {
		if (dateRemoved == null) {
			return "";
		}
		else{
			return Constantes.dateFormat.format(dateRemoved);
		}
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getDateRemoved() {
		return dateRemoved;
	}
	public void setDateRemoved(Date dateRemoved) {
		this.dateRemoved = dateRemoved;
	}


	public Computer(int pid, String pname, Date pdateAdded, Date pdateRemoved, Company pcompany) {
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
		return "\nComputer " + nameToShow + "\t added " + dateAdded
				+ "\t company " + company + "";
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
	public Company getCompany() {
		return company;
	}
	
}
