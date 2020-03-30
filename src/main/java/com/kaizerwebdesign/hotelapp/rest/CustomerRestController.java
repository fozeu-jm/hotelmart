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

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.entities.Customer;
import com.kaizerwebdesign.hotelapp.forms.customerForm;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// inject customer DAO using anotation
	@Autowired
	private CustomerDAO customerDAO;

	// expose "/customers" and return list of customers
	@GetMapping("/customers")
	public List<Customer> findAll() {
		return customerDAO.findAll();
	}

	// add mapping to get customer by id
	@GetMapping("/customers/{id}")
	public Customer getCustomer(@PathVariable Integer id) {
		// get customer from DAO
		Customer theCustomer = customerDAO.findById(id);
		if (theCustomer == null) {
			throw new RuntimeException("Customer not found");
		}
		return theCustomer;
	}

	// add mapping to add customer through api
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody customerForm theForm) {
		// set id to null in case it was set in Json
		theForm.setId(null);

		// validate form fields
		theForm.formValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate customer from form
			Customer theCustomer = new Customer(theForm);

			// then save resulting customer
			customerDAO.save(theCustomer);
			return theCustomer;
		} else {
			throw new RuntimeException("Errors Occured: " + theForm.getErrors());
		}
	}

	// mapping to update customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody customerForm theForm) {
		// validate form fields
		theForm.formValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate customer from form
			Customer theCustomer = new Customer(theForm);

			// then save resulting customer
			customerDAO.save(theCustomer);
			return theCustomer;
		} else {
			throw new RuntimeException("Errors Occured: " + theForm.getErrors());
		}
	}

	// rest mapping to delete customer
	@DeleteMapping("/customers/{id}")
	public String deleteCustomer(@PathVariable Integer id) {

		// get the customer by id
		Customer customer = customerDAO.findById(id);

		if (customer == null) {
			throw new RuntimeException("Customer not found");
		}
		
		customerDAO.delete(id);
		return "Customer deleted Sucessfully";
	}

}
