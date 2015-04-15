package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.modele.User;

/**
 * Sub interface, for the DAO in charge to get users' authorization from the database, to use with JPA. Holds custom methods declarations
 * @author Jordan Mosseri
 *
 */
public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String userName);
}
