package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.Charge;

public interface ChargeInterface {
	public List<Charge> findAll();

	public void save(Charge theCharge);

	public Charge findById(Integer theId);

	public void delete(Integer theId);
}
