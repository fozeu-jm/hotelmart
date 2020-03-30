package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.User;

public interface UserInterface {
	public List<User> findAll();

	public void save(User user);

	public User findById(Integer theId);
	
	public User findByUsername(String username);
	
	public User findByCredentials(String username, String password);

	public void delete(String username);
}
