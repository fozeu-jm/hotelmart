package com.kaizerwebdesign.hotelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.ChargeDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.ServiceDAO;
import com.kaizerwebdesign.hotelapp.entities.Charge;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Service;
import com.kaizerwebdesign.hotelapp.forms.ChargeForm;

@Controller
@RequestMapping("web/charges")
public class ChargeController {

	@Autowired
	private ChargeDAO chargeDAO;

	@Autowired
	private ReservationDAO reservationDAO;

	@Autowired
	private ServiceDAO serviceDAO;

	@GetMapping("/add")
	public String chargeform(@RequestParam("id") Integer theid, Model model) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(theid);
		System.out.println(theReservation);
		if (theReservation != null) {
			model.addAttribute("reservation", theReservation);

			// get list of available services
			List<Service> services = serviceDAO.findAll();

			model.addAttribute("services", services);

			ChargeForm chForm = new ChargeForm();
			// send form to view for data binding
			model.addAttribute("form", chForm);

			return "charge-form";
		} else {
			return "redirect:/web/web/reservations/";
		}

	}

	@GetMapping("/list")
	public String listCharge(@RequestParam("id") Integer theid, Model model) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(theid);
		System.out.println(theReservation);
		if (theReservation != null) {
			model.addAttribute("reservation", theReservation);
			// get list of charges of retrieved reservation
			List<Charge> charges = theReservation.getCharges();
			model.addAttribute("charges", charges);

			return "charges";
		} else {
			return "redirect:/web/reservations/";
		}
	}

	// define post mapping to save room type
	@PostMapping("/add")
	public String saveCharge(@ModelAttribute("form") ChargeForm theForm, Model model) {
		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// get associated reservation and service
			Reservation reservation = reservationDAO.findById(theForm.getReservation());

			Service service = serviceDAO.findById(theForm.getService());

			// Instantiate type from form
			Charge theCharge = new Charge(theForm);

			theCharge.setReservation(reservation);

			theCharge.setService(service);

			// then save resulting type
			chargeDAO.save(theCharge);

			// redirect to type register page
			return "redirect:/web/charges/list?id=" + reservation.getId();
		} else {
			
			System.out.println(theForm);
			Reservation theReservation = reservationDAO.findById(theForm.getReservation());

			model.addAttribute("reservation", theReservation);

//			// get list of available services
			List<Service> services = serviceDAO.findAll();

			model.addAttribute("services", services);

			// forward back to form with error messages
			model.addAttribute("form", theForm);

			return "charge-form";
		}
	}
	

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteCharge(@RequestParam("id") Integer theid, Model model) {

		// get the entity by id
		Charge charge = chargeDAO.findById(theid);

		if (charge == null) {
			return "redirect:/web/reservations/";
		} else {
			// delete entity
			chargeDAO.delete(theid);

			return "redirect:/web/reservations/";
		}
	}
	
	

}
