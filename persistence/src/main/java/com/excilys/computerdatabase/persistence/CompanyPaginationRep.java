package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.modele.Company;

public interface CompanyPaginationRep extends JpaRepository<Company, Integer> {

}
