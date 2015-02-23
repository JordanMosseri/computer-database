package modele;

import java.time.LocalDateTime;
import java.util.Date;

public class Computer {
	
	public int id;
	public String name;
	public Date dateAdded;//LocalDateTime
	public Date dateRemoved;
	public Company company;
	
	
	public Computer(String pname, Date pdateAdded, Date pdateRemoved, Company pcompany) {
		super();
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
	
}
