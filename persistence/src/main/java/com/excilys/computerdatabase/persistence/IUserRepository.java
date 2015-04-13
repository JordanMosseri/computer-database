package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.User;
import com.excilys.computerdatabase.modele.UserRole;

public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String userName);
}
