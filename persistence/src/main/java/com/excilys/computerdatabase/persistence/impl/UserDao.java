package com.excilys.computerdatabase.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.modele.User;
import com.excilys.computerdatabase.modele.UserRole;
import com.excilys.computerdatabase.persistence.IUserDao;

@Repository
public class UserDao extends JdbcDaoSupport implements IUserDao {
 
	@Autowired 
	public UserDao(DataSource dataSource) {
	    super();
	    setDataSource(dataSource);
	}
 
	//@SuppressWarnings("unchecked")
	public User findByUsername(String username) {
 
		List<User> users = new ArrayList<User>();
		
		users = getJdbcTemplate().query(
				"SELECT * FROM users  WHERE username=?", new Object[] { username },
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new User(rs.getString("username"), rs.getString("password"), rs.getInt("enabled")==1);
					}
				}
			);
		
		List<UserRole> roles = getJdbcTemplate().query(
				"SELECT user_roles.ROLE FROM users LEFT OUTER JOIN user_roles ON users.username = user_roles.username WHERE users.username=?", new Object[] { username },
				new RowMapper<UserRole>() {
					@Override
					public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new UserRole(null, rs.getString("user_roles.ROLE"));
					}
				}
			);
		
		Set<UserRole> rolesSet = new HashSet<UserRole>(roles);
		for (User user : users) {
			user.setUserRole(rolesSet);
		}
 
		/*users = sessionFactory.getCurrentSession()
			.createQuery("from User where username=?")
			.setParameter(0, username)
			.list();*/
 
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
 
	}
 
}
