package com.excilys.computerdatabase.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

public interface CompanyPaginationRep extends JpaRepository<Company, Integer> {

}
