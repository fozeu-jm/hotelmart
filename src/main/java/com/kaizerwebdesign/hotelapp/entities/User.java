package com.kaizerwebdesign.hotelapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@OneToMany(mappedBy = "user", targetEntity = Authority.class, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Authority> authorities;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public List<String> authorityList() {
		List<String> authorities1 = new ArrayList<String>();
		for (Authority auth : authorities) {
			authorities1.add(auth.getAuthority());
		}
		return authorities1;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + ", authorities="
				+ authorities + "]";
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User) obj;
		return (user.getUsername() == this.getUsername());
	}
	
	

}
