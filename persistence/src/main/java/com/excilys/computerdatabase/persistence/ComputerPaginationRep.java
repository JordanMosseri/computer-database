package com.excilys.computerdatabase.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.modele.Computer;

@Repository
public interface ComputerPaginationRep extends JpaRepository<Computer, Integer> {
	
	public Page<Computer> findByNameLike(String name, Pageable pageable);
}
