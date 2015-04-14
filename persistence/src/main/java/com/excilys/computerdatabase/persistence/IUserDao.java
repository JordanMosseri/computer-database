package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.modele.User;

/**
 * Interface for the DAO in charge to get users' authorization from the database
 * @author excilys
 *
 */
public interface IUserDao {
	 
	User findByUsername(String username);
 
}
