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

import com.kaizerwebdesign.hotelapp.dao.ServiceDAO;
import com.kaizerwebdesign.hotelapp.entities.Service;
import com.kaizerwebdesign.hotelapp.forms.ServiceForm;

@RestController
@RequestMapping("/api/services")
public class ServiceRestController {
	@Autowired
	private ServiceDAO serviceDAO;

	// expose "/types" and return list of customers
	@GetMapping("")
	public List<Service> findAll() {
		return serviceDAO.findAll();
	}

	// add mapping to get type by id
	@GetMapping("/{id}")
	public Service getRoomType(@PathVariable Integer id) {
		// get room type from DAO
		Service service = serviceDAO.findById(id);
		if (service == null) {
			throw new ResourceNotFoundException("Service not found");
		}
		return service;
	}

	// add mapping to add room type through api
	@PostMapping("")
	public Service addService(@RequestBody ServiceForm theForm) {
		// set id to null in case it was set in Json
		theForm.setId(null);

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			Service service = new Service(theForm);

			// then save resulting type
			serviceDAO.save(service);
			return service;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// add mapping to add room type through api
	@PutMapping("")
	public Service editService(@RequestBody ServiceForm theForm) {

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			Service service = new Service(theForm);

			// then save resulting type
			serviceDAO.save(service);
			return service;
		} else {
			throw new ResourceNotFoundException("Errors Occured: " + theForm.getErrors());
		}
	}

	// rest mapping to delete customer
	@DeleteMapping("/{id}")
	public String deleteService(@PathVariable Integer id) {

		// get the customer by id
		Service service = serviceDAO.findById(id);

		if (service == null) {
			throw new ResourceNotFoundException("Service not found");
		}

		serviceDAO.delete(id);
		return "Service type deleted Sucessfully";
	}
}
