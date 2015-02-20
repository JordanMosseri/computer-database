package modele;

import java.util.Date;

public class Computer {
	public String nom;
	public Date dateAjout;
	public Fabriquant fabriquant;
	
	
	public Computer(String nom, Date dateAjout, Fabriquant fabriquant) {
		super();
		this.nom = nom;
		this.dateAjout = dateAjout;
		this.fabriquant = fabriquant;
	}


	@Override
	public String toString() {
		return "Computer [nom=" + nom + ", dateAjout=" + dateAjout
				+ ", fabriquant=" + fabriquant + "]";
	}
	
	
}
