package com.excilys.computerdatabase.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.modele.UserRole;
import com.excilys.computerdatabase.persistence.IUserDao;

/**
 * Service to get authorizations for Spring security
 * @author excilys
 *
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserDao userDao;
 
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) 
		throws UsernameNotFoundException {
 
		com.excilys.computerdatabase.modele.User user = userDao.findByUsername(username);
		List<GrantedAuthority> authorities = 
                                      buildUserAuthority(user.getUserRole());
 
		return buildUserForAuthentication(user, authorities);
 
	}
 
	/**
	 * Converts com.excilys.computerdatabase.modele.User user to 
	 * org.springframework.security.core.userdetails.User
	 * @param user
	 * @param authorities
	 * @return
	 */
	private User buildUserForAuthentication(com.excilys.computerdatabase.modele.User user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), 
			user.isEnabled(), true, true, true, authorities);
	}
 
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
 
		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
 
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
 
		return Result;
	}
 
}
