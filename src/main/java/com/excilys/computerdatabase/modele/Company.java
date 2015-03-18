package com.excilys.computerdatabase.modele;

import java.io.Serializable;

public class Company implements Serializable {
	
	private String name=null;
	private int id=-1;
	
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

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
