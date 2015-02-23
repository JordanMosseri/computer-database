package modele;

public class Company {
	
	public String name;

	public Company(String nomFabricant) {
		super();
		this.name = nomFabricant;
	}
	

	@Override
	public String toString() {
		return this.name;
	}
}
