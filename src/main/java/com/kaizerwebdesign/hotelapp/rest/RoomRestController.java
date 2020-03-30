package com.kaizerwebdesign.hotelapp.rest;

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

import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomTypeDAO;
import com.kaizerwebdesign.hotelapp.entities.Room;
import com.kaizerwebdesign.hotelapp.entities.RoomType;
import com.kaizerwebdesign.hotelapp.forms.RoomForm;

@RestController
@RequestMapping("/api")
public class RoomRestController {

	// inject customer DAO using anotation
	@Autowired
	private RoomDAO roomDAO;
	@Autowired
	private RoomTypeDAO roomtypeDAO;

	// expose "/types" and return list of customers
	@GetMapping("/rooms")
	public List<Room> findAll() {
		return roomDAO.findAll();
	}

	// add mapping to get entity by id
	@GetMapping("/rooms/{id}")
	public Room getRoom(@PathVariable Integer id) {
		// get entity from DAO
		Room theroom = roomDAO.findById(id);
		if (theroom == null) {
			throw new RuntimeException("Room not found");
		}
		return theroom;
	}

	// add mapping to add room type through api
	@PostMapping("/rooms")
	public Room addRoom(@RequestBody RoomForm theForm) {
		// set id to null in case it was set in Json
		theForm.setId(null);

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			Room theroom = new Room(theForm);

			// get type from form
			RoomType type = roomtypeDAO.findById(theForm.getType());
			
			
			if(type == null) {
				throw new ResourceNotFoundException("Associated room type not found");
			}
			
			theroom.setType(type);

			// then save resulting type
			roomDAO.save(theroom);
			return theroom;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// mapping to update customer
	@PutMapping("/rooms")
	public Room editRoom(@RequestBody RoomForm theForm) {
		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			Room theroom = new Room(theForm);

			// get type from form
			theroom.setType(roomtypeDAO.findById(theForm.getType()));

			// then save resulting type
			roomDAO.save(theroom);
			return theroom;
		} else {
			throw new RuntimeException("Errors Occured: " + theForm.getErrors());
		}
	}

	// rest mapping to delete customer
	@DeleteMapping("/rooms/{id}")
	public String deleteCustomer(@PathVariable Integer id) {

		// get entity from DAO
		Room theroom = roomDAO.findById(id);
		if (theroom == null) {
			throw new ResourceNotFoundException("Room not found");
		}

		roomDAO.delete(id);
		return "Room was deleted Sucessfully";
	}
}
