package com.excilys.computerdatabase.modele;

public class ComputerDTO {
	
	private int id;
	private String name;
	private String dateAdded;
	private String dateRemoved;
	private String companyName;
	private int companyId;
	
	public ComputerDTO(int id, String name, String dateAdded,
			String dateRemoved, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.dateAdded = dateAdded;
		this.dateRemoved = dateRemoved;
		this.companyName = company.getName();
		this.companyId = company.getId();
	}
	
	
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public String getDateAddedString() {
		return dateAdded;
	}
	public String getDateRemovedString() {
		return dateRemoved;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}



	public void setDateRemoved(String dateRemoved) {
		this.dateRemoved = dateRemoved;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
