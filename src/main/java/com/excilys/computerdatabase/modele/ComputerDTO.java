package com.excilys.computerdatabase.modele;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.excilys.computerdatabase.validator.MyDate;

public class ComputerDTO {
	
	
	private int id;
	
	@NotEmpty(message="{NotEmpty}")
	private String name;
	
	//@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	
	@MyDate
	private String dateAdded;
	
	@MyDate
	private String dateRemoved;
	
	private String companyName;
	
	private int companyId;
	
	public ComputerDTO() {
		super();
	}
	
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
	public String getDateAdded() {
		return dateAdded;
	}
	public String getDateRemoved() {
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
