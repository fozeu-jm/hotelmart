package com.kaizerwebdesign.hotelapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaizerwebdesign.hotelapp.dao.UserDAO;
import com.kaizerwebdesign.hotelapp.entities.User;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Not found: " + username);
		}else {
			return new MyUserDetails(user);
		}
	}

}
