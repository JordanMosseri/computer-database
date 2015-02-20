package modele;

public class Fabriquant {
	
	public String nomFabricant;

	public Fabriquant(String nomFabricant) {
		super();
		this.nomFabricant = nomFabricant;
	}
	
	public Fabriquant() {
		super();
		this.nomFabricant = "AUCUN";
	}

	@Override
	public String toString() {
		return this.nomFabricant;
	}
}
