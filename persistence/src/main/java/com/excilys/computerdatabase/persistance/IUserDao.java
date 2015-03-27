package com.excilys.computerdatabase.persistance;

import com.excilys.computerdatabase.modele.User;

public interface IUserDao {
	 
	User findByUserName(String username);
 
}
