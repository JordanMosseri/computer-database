package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.modele.Company;

/**
 * Company's DAO sub interface (to use with JPA)
 * @author Jordan Mosseri
 *
 */
public interface CompanyPaginationRep extends JpaRepository<Company, Integer> {
	
	public Company findByName(String name);
}
