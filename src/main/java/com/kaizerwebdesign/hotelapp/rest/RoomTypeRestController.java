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

import com.kaizerwebdesign.hotelapp.dao.RoomTypeDAO;
import com.kaizerwebdesign.hotelapp.entities.RoomType;
import com.kaizerwebdesign.hotelapp.forms.RoomTypeForm;

@RestController
@RequestMapping("/api")
public class RoomTypeRestController {
	// inject customer DAO using anotation
	@Autowired
	private RoomTypeDAO roomtypeDAO;

	// expose "/types" and return list of customers
	@GetMapping("/roomtypes")
	public List<RoomType> findAll() {
		return roomtypeDAO.findAll();
	}

	// add mapping to get type by id
	@GetMapping("/roomtypes/{id}")
	public RoomType getRoomType(@PathVariable Integer id) {
		// get room type from DAO
		RoomType thetype = roomtypeDAO.findById(id);
		if (thetype == null) {
			throw new ResourceNotFoundException("Type not found");
		}
		return thetype;
	}

	// add mapping to add room type through api
	@PostMapping("/roomtypes")
	public RoomType addRoomType(@RequestBody RoomTypeForm theForm) {
		// set id to null in case it was set in Json
		theForm.setId(null);

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			RoomType thetype = new RoomType(theForm);

			// then save resulting type
			roomtypeDAO.save(thetype);
			return thetype;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// mapping to update customer
	@PutMapping("/roomtypes")
	public RoomType editType(@RequestBody RoomTypeForm theForm) {
		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			RoomType thetype = new RoomType(theForm);

			// then save resulting type
			roomtypeDAO.save(thetype);
			return thetype;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// rest mapping to delete customer
	@DeleteMapping("/roomtypes/{id}")
	public String deleteCustomer(@PathVariable Integer id) {

		// get the customer by id
		RoomType thetype = roomtypeDAO.findById(id);

		if (thetype == null) {
			throw new ResourceNotFoundException("Type not found");
		}

		roomtypeDAO.delete(id);
		return "Room type deleted Sucessfully";
	}

}
