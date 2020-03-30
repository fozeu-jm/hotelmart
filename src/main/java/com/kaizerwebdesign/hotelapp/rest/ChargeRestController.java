package com.kaizerwebdesign.hotelapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.dao.ChargeDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.ServiceDAO;
import com.kaizerwebdesign.hotelapp.entities.Charge;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Service;
import com.kaizerwebdesign.hotelapp.forms.ChargeForm;

@RestController
@RequestMapping("/api")
public class ChargeRestController {

	@Autowired
	private ChargeDAO chargeDAO;

	@Autowired
	private ReservationDAO reservationDAO;

	@Autowired
	private ServiceDAO serviceDAO;

//	@Autowired
//	private ServiceDAO serviceDAO;

	@GetMapping("/charges/list/{id}")
	public List<Charge> allCharges(@PathVariable Integer id) {
		// get reservation from id parameter
		Reservation theReservation = reservationDAO.findById(id);

		if (theReservation != null) {
			// get list of charges of retrieved reservation
			List<Charge> charges = theReservation.getCharges();

			return charges;
		} else {
			throw new ResourceNotFoundException("Reservation not found");
		}
	}

	@GetMapping("/charges/{id}")
	public Charge findCharge(@PathVariable Integer id) {
		Charge charge = chargeDAO.findById(id);
		if (charge != null) {
			return charge;
		} else {
			throw new ResourceNotFoundException("Charge was not found");
		}
	}

	// define post mapping to save room type
	@PostMapping("/charges/add")
	public Charge saveCharge(@RequestBody ChargeForm theForm) {
		theForm.setId(null);
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			Charge charge = new Charge(theForm);

			Service sv = serviceDAO.findById(theForm.getService());
			Reservation rv = reservationDAO.findById(theForm.getReservation());

			if (rv == null || sv == null) {
				throw new ResourceNotFoundException("Associated resources not found");
			}

			charge.setReservation(rv);
			charge.setService(sv);

			chargeDAO.save(charge);

			return charge;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// rest mapping to delete customer
	@DeleteMapping("/charges/delete/{id}")
	public String deleteCharge(@PathVariable Integer id) {
		Charge charge = chargeDAO.findById(id);

		if (charge == null) {
			throw new ResourceNotFoundException("Charge not found");
		}
		
		chargeDAO.delete(id);

		return "Charge deleted sucessfully";
	}
	
}
