package com.excilys.computerdatabase.modele;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2736843274120613787L;

	@Column(name="name")
	private String name=null;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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
