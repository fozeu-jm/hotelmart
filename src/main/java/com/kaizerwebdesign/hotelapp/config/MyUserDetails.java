package com.kaizerwebdesign.hotelapp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kaizerwebdesign.hotelapp.entities.User;

public class MyUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private boolean enabled;
	private List<GrantedAuthority> authorities;

	public MyUserDetails(User user) {
		authorities = new ArrayList<GrantedAuthority>();
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.getEnabled();
		for(String auth : user.authorityList()) {
			authorities.add(new SimpleGrantedAuthority(auth));
		}
	}

	public MyUserDetails() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
