package com.kaizerwebdesign.hotelapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kaizerwebdesign.hotelapp.forms.customerForm;

@Entity
@Table(name = "customer")
public class Customer {

	// define attributes with anotations
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "email")
	private String email;

	// define needed constructors
	public Customer() {
	}

	public Customer(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Customer(customerForm form) {
		this.email = form.getEmail();
		this.firstName = form.getFirstName();
		this.lastName = form.getLastName();
		this.id = form.getId() == null ? null : Integer.parseInt(form.getId());
	}

	// define getters and setters with toStringg method
	public Integer getId() {
		return id;
	}
	
	public void copy(customerForm form) {
		this.email = form.getEmail();
		this.firstName = form.getFirstName();
		this.lastName = form.getLastName();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}
