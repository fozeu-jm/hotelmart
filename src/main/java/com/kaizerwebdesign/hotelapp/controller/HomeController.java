/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaizerwebdesign.hotelapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.dao.PaymentDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.entities.Payment;
import com.kaizerwebdesign.hotelapp.entities.Reservation;

/**
 *
 * @author Fatih
 */
@Controller
public class HomeController{
	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	ReservationDAO reservationDAO;

	@Autowired
	RoomDAO roomDAO;

	// create mapping to home ppage
	@GetMapping("web/home")
	public String homePage(Model model) {
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

		model.addAttribute("customers", customers);
		model.addAttribute("rooms", rooms);
		model.addAttribute("earnings", earnings);
		model.addAttribute("reservations", reservations);

		return "home";
	}

	/*@GetMapping("web/error")
	public String handleErrors(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				System.out.println("404");
				return "error-404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				System.out.println("500");
				return "error-500";
			}
		}
		System.out.println("NOTHING SHIT !!");
		return "error";
	}*/
	
	@GetMapping("/error/403")
	public String handle403(HttpServletRequest request) {
		return "error-403";
	}

	/*@Override
	public String getErrorPath() {
		return "web/";
	}*/
}
