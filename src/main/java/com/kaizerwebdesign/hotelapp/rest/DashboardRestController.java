package com.kaizerwebdesign.hotelapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.dao.PaymentDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.entities.Payment;
import com.kaizerwebdesign.hotelapp.entities.Reservation;

@RestController
public class DashboardRestController {
	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	ReservationDAO reservationDAO;

	@Autowired
	RoomDAO roomDAO;

	// create mapping to home ppage
	@GetMapping("api/dashboard")
	public SummaryResponse Dashboard() {
		Integer customers = customerDAO.findAll().size();
		Integer rooms = roomDAO.findAll().size();
		Integer reservations = reservationDAO.findAll().size();

		Double earnings = 0.0;

		for (Payment pay : paymentDAO.findAll()) {
			earnings += pay.getAmount();
		}

		for (Reservation rv : reservationDAO.findAll()) {
			earnings += rv.getRate_fee();
		}
		
		return new SummaryResponse(earnings, reservations, customers, rooms);
	}
	
	@GetMapping("api/403")
	public void  handle() {
		throw new ResourceNotFoundException("403");
	}
}
