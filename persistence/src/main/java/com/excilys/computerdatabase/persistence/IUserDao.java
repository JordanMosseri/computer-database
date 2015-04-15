package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.modele.User;

/**
 * Interface for the DAO in charge to get users' authorization from the database
 * @author Jordan Mosseri
 *
 */
public interface IUserDao {
	
	/**
	 * Find a user given his name
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
 
}
