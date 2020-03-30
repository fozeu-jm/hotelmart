package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.Payment;

public interface PaymentInterface {
	public List<Payment> findAll();

	public void save(Payment thepayment);

	public Payment findById(Integer theId);

	public void delete(Integer theId);
}
