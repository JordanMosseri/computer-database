package main;

public interface Constantes {
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	public static final String MOTIF = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	//"[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"
	
	public static final String[] POSSIBILITES = {
			"List computers", 
			"List companies", 
			"Show computer details (the detailed information of only one computer)", 
			"Create a computer", 
			"Update a computer", 
			"Delete a computer",
			"Quitter"
			};
	
}
