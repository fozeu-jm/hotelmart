package com.kaizerwebdesign.hotelapp.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.dao.PaymentDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.entities.Payment;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Status;
import com.kaizerwebdesign.hotelapp.forms.PaymentForm;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

	// inject payment Dao
	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private ReservationDAO reservationDAO;

	@GetMapping("/list/{id}")
	public Payment getReservationPayment(@PathVariable Integer id) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(id);

		if (theReservation != null) {
			// get list of charges of retrieved reservation
			Payment payment = theReservation.getPayment();

			return payment;
		} else {
			throw new ResourceNotFoundException("Reservation not found");
		}
	}
	

	@GetMapping("/{id}")
	public Payment findPayment(@PathVariable Integer id) {
		Payment pay = paymentDAO.findById(id);
		if (pay != null) {
			return pay;
		} else {
			throw new ResourceNotFoundException("Payment not found in system");
		}
	}
	
	// define post mapping to save room type
		@PostMapping("")
		public Payment savePayment(@RequestBody PaymentForm theForm) {
			theForm.setId(null);
			theForm.fieldValidation();
			
			// test if there are no errors
			if (theForm.getErrors().isEmpty()) {
				Payment pay = new Payment(theForm);
				
				Reservation rv = reservationDAO.findById(theForm.getReservation());

				if (rv == null ) {
					throw new ResourceNotFoundException("Associated resources not found");
				}
				
				if(rv.getPayment() != null) {
					throw new ResourceNotFoundException("Reservation already has a payment registered");
				}

				pay.setReservation(rv);
				pay.setDate(new Date());

				paymentDAO.save(pay);
				rv.setStatus(Status.CHECKED_OUT);
				reservationDAO.save(rv);
				return pay;
			} else {
				throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
			}
			
		}
		
		// rest mapping to delete customer
		@DeleteMapping("/delete/{id}")
		public String deletePayment(@PathVariable Integer id) {
			Payment pay = paymentDAO.findById(id);

			if (pay == null) {
				throw new ResourceNotFoundException("Payment not found");
			}
			
			paymentDAO.delete(id);

			return "Payment deleted sucessfully";
		}
	
}
