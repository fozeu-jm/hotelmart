package com.kaizerwebdesign.hotelapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.PaymentDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.entities.Charge;
import com.kaizerwebdesign.hotelapp.entities.Payment;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Status;
import com.kaizerwebdesign.hotelapp.forms.PaymentForm;

@Controller
@RequestMapping("web/payments")
public class PaymentController {
	// inject payment Dao
	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private ReservationDAO reservationDAO;

	@GetMapping("/add")
	public String chargeform(@RequestParam("id") Integer theid, Model model) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(theid);
		if (theReservation != null && theReservation.getPayment() == null) {
			model.addAttribute("reservation", theReservation);

			PaymentForm pForm = new PaymentForm();

			// send form to view for data binding
			model.addAttribute("form", pForm);

			// get charge subtotal
			Double subtotal = 0.0;

			for (Charge ch : theReservation.getCharges()) {
				subtotal += (ch.getPrice() * ch.getQuantity());
			}

			Double total = (subtotal);

			pForm.setAmount(total);

			return "payment-form";
		} else {
			return "redirect:/web/reservations/";
		}

	}

	// define post mapping to save room type
	@PostMapping("/add")
	public String savePayment(@ModelAttribute("form") PaymentForm theForm, Model model) {
		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// get associated reservation and service
			Reservation reservation = reservationDAO.findById(theForm.getReservation());

			if (reservation != null && reservation.getPayment() == null) {
				// Instantiate type from form
				Payment payment = new Payment(theForm);
				
				reservation.setStatus(Status.CHECKED_OUT);
				
				reservationDAO.save(reservation);
				
				payment.setReservation(reservation);

				Date now = new Date();

				payment.setDate(now);
				// then save
				paymentDAO.save(payment);

				// redirect to type register page
				// return "redirect:/web/charges/list?id=" + reservation.getId();
				return "redirect:/web/reservations/";
			} else {
				return "redirect:/web/reservations/";
			}

		} else {
			Reservation theReservation = reservationDAO.findById(theForm.getReservation());

			model.addAttribute("reservation", theReservation);

			// forward back to form with error messages
			model.addAttribute("form", theForm);

			return "payment-form";
		}
	}

	@GetMapping("/list")
	public String viewPayment(@RequestParam("id") Integer theid, Model model) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(theid);
		if (theReservation != null) {
			model.addAttribute("reservation", theReservation);
			// get list of charges of retrieved reservation
			model.addAttribute("payment", theReservation.getPayment());

			return "payments";
		} else {
			return "redirect:/web/reservations/";
		}
	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deletePayment(@RequestParam("id") Integer theid, Model model) {

		// get the entity by id
		Payment payment = paymentDAO.findById(theid);

		if (payment == null) {
			return "redirect:/web/reservations/";
		} else {
			// delete entity
			paymentDAO.delete(theid);

			return "redirect:/web/reservations/";
		}
	}
}
