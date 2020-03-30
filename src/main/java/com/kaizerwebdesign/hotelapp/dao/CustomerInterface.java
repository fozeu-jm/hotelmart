package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.Customer;

public interface CustomerInterface {
	public List<Customer> findAll();
	
	public void save(Customer theCustomer);
	
	public Customer findById(Integer theId);
	
	public void delete(Integer theId);
}
