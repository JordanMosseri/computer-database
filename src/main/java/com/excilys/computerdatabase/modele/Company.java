package com.excilys.computerdatabase.modele;

import java.io.Serializable;

public class Company implements Serializable {
	
	public String name=null;
	public int id=-1;
	
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
		return this.name;
	}


	
	
	
	
	
	
	
	public String getName() {
		return name;
	}


	public int getId() {
		return id;
	}
}
