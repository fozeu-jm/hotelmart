package com.kaizerwebdesign.hotelapp.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.entities.Customer;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Room;
import com.kaizerwebdesign.hotelapp.forms.ReservationForm;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {
		// inject Dao
		@Autowired
		private ReservationDAO reservationDAO;

		@Autowired
		private RoomDAO roomDAO;

		@Autowired
		private CustomerDAO customerDAO;
		
	@GetMapping("")
	public List<Reservation> allReservation() {
		return reservationDAO.findAll();
	}
	
	@GetMapping("/{id}")
	public Reservation getReservation(@PathVariable Integer id) {
		Reservation reservation = reservationDAO.findById(id);
		if(reservation == null) {throw new ResourceNotFoundException("Reservation not found");}
		
		return reservation;
	}
	
	@GetMapping("code/{code}")
	public Reservation getReservation(@PathVariable String code) {
		Reservation reservation = reservationDAO.findByCode(code);
		if(reservation == null) {throw new ResourceNotFoundException("Reservation not found");}
		
		return reservation;
	}
	
	// add mapping to add room type through api
		@PostMapping("")
		public Reservation addRoom(@RequestBody ReservationForm theForm) {
			theForm.FieldValidation(reservationDAO);
			if(!theForm.getErrors().isEmpty()) {throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());}
			theForm.setId(null);
			Reservation reservation = new Reservation(theForm);
			Customer customer = customerDAO.findById(theForm.getCustomer());
			List<Room> rooms = new ArrayList<Room>();
			for(Integer id : theForm.getRooms()) {
				Room room = roomDAO.findById(id);
				if(room==null) {throw new ResourceNotFoundException("Associated resource not found");}
				rooms.add(room);
			}
			if(customer==null) {throw new ResourceNotFoundException("Associated resource not found");}
			reservation.setCustomer(customer);
			reservation.setRooms(rooms);
			
			reservationDAO.save(reservation);
			return reservation;
		}
		
		// mapping to update customer
		@PutMapping("")
		public Reservation editReservation(@RequestBody ReservationForm theForm) {
			theForm.FieldValidation(reservationDAO);
			if(!theForm.getErrors().isEmpty()) {throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());}
			Reservation reservation = new Reservation(theForm);
			Customer customer = customerDAO.findById(theForm.getCustomer());
			List<Room> rooms = new ArrayList<Room>();
			for(Integer id : theForm.getRooms()) {
				Room room = roomDAO.findById(id);
				if(room==null) {throw new ResourceNotFoundException("Associated resource not found");}
				rooms.add(room);
			}
			
			if(customer==null) {throw new ResourceNotFoundException("Associated resource not found");}
			reservation.setCustomer(customer);
			reservation.setRooms(rooms);
			
			reservationDAO.save(reservation);
			return reservation;
		}
		
		// rest mapping to delete customer
		@DeleteMapping("/{id}")
		public String deleteRESERVATION(@PathVariable Integer id) {
			Reservation rv = reservationDAO.findById(id);

			if (rv == null) {
				throw new ResourceNotFoundException("Reservation not found");
			}
			
			reservationDAO.delete(id);

			return "Reservation deleted sucessfully";
		}
		
		@PostMapping("/rate")
		public ValueResponse getRoomRate(@RequestBody ReservationForm form) {
			
			form.FieldValidation(reservationDAO);
			
			System.out.println(form);
			
			if (!form.getErrors().isEmpty()) {
				throw new ResourceNotFoundException("Request error: " + form.getErrors());
			}
			
			Reservation reservation = new Reservation(form);
			long diff = noTime(reservation.getDeparture()).getTime() - noTime(reservation.getArrival()).getTime();
			diff= Math.round(diff / (double) 86400000);

			long days = diff;
			if (days == 0) {
				days = days + 1;
			}
			Double fee = 0.0;
			
			
			List<Room> rooms = new ArrayList<Room>();
			for(Integer id : form.getRooms()) {
				Room room = roomDAO.findById(id);
				if(room==null) {throw new ResourceNotFoundException("Associated resource not found");}
				rooms.add(room);
			}
			
			
			for (Room rm : rooms) {
				fee += rm.getType().getRate();
			}
			fee *= days;
			
			return new ValueResponse("Reservation flat rate", fee.toString());
		}
		
		@PostMapping("/days/{id}")
		public ValueResponse getDays(@PathVariable Integer id) {
			Reservation reservation = reservationDAO.findById(id);

			if (reservation == null) {
				throw new ResourceNotFoundException("Reservation not found");
			}
			
			long diff = noTime(reservation.getDeparture()).getTime() - noTime(reservation.getArrival()).getTime();
			diff= Math.round(diff / (double) 86400000);

			Long days = diff;
			
			return new ValueResponse("Reservation days length", days.toString());
		}
		
		public Date noTime(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date result = null;
			try {
				result = sdf.parse(sdf.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
}
