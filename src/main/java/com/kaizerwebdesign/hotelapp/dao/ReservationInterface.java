package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;
import com.kaizerwebdesign.hotelapp.entities.Reservation;

public interface ReservationInterface {
	public List<Reservation> findAll();

	public void save(Reservation theReservation);

	public Reservation findById(Integer theId);
	
	public Reservation findByCode(String theCode);

	public void delete(Integer theId);
}
