package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.Customer;

public class customerForm {

	// define attributes
	private String id;

	private String firstName;

	private String lastName;

	private String email;

	private Map<String, String> errors;

	// define constructors
	public customerForm() {
		this.errors = new HashMap<String, String>();
	}

	public customerForm(Customer theCustomer) {
		this.email = theCustomer.getEmail();
		this.firstName = theCustomer.getFirstName();
		this.lastName = theCustomer.getLastName();
		this.id = theCustomer.getId().toString();
		this.errors = new HashMap<String, String>();
	}

	// define getters and setters

	public String getFirstName() {
		return firstName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	// form validation method
	public void formValidation() {
		// validate names
		try {
			this.firstName = FieldValidation.isValidName(firstName);
		} catch (Exception e) {
			errors.put("firstName", e.getMessage());
		}
		try {
			this.lastName = FieldValidation.isValidName(lastName);
		} catch (Exception e) {
			errors.put("lastName", e.getMessage());
		}

		// validate email
		try {
			this.email = FieldValidation.isEmailValid(email);
		} catch (Exception e) {
			errors.put("email", e.getMessage());
		}
		
		//validate id
		try {
			id = FieldValidation.idValidation(id);
		} catch (Exception e) {
			errors.put("id", e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "customerForm [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}
}
