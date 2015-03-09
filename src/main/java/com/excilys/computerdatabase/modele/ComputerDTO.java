package com.excilys.computerdatabase.modele;

public class ComputerDTO {
	
	public int id;
	public String name;
	public String dateAdded;
	public String dateRemoved;
	public Company company;
	
	public ComputerDTO(int id, String name, String dateAdded,
			String dateRemoved, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.dateAdded = dateAdded;
		this.dateRemoved = dateRemoved;
		this.company = company;
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
	public String getDateAddedString() {
		return dateAdded;
	}
	public String getDateRemovedString() {
		return dateRemoved;
	}
}
