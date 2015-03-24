package com.excilys.computerdatabase.persistance;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.excilys.computerdatabase.modele.Computer;

public interface ComputerPaginationRep extends JpaRepository<Computer, Integer> {
	
	/*@Query(
			//"from Personne p where p.nom = ?1 and p.prenom = ?2"
			"SELECT *, computer.name AS computer_name, company.name AS company_name, company.id AS company_id"
			+ " FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id "
			+ " WHERE computer.name LIKE '%?1%' || company.name LIKE '%?1%' LIMIT ?2, ?3" //word word offset limit
			)
	public List<Computer> myQueryGetPart(String word, int offset, int limit);
	
	@Query("UPDATE computer SET name=?1, introduced=?2, discontinued=?3, company_id=?4 WHERE id=?5")
	public List<Computer> update(String name, LocalDateTime introduced, LocalDateTime discontinued, int company_id, int id);*/
}
