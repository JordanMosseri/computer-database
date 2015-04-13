package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.modele.User;

public interface IUserDao {
	 
	User findByUsername(String username);
 
}
