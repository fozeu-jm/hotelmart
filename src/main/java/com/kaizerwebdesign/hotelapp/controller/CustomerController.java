/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaizerwebdesign.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.entities.Customer;
import com.kaizerwebdesign.hotelapp.forms.customerForm;

@Controller
@RequestMapping("web/customers")
public class CustomerController {
	// inject customerDAO
	@Autowired
	private CustomerDAO customerdao;

	// create get mapping to show customer's list page
	@GetMapping("/")
	public String listCustomer(Model themodel) {
		// insert customer list in model
		themodel.addAttribute("customers", customerdao.findAll());

		// return to view
		return "customers";
	}

	// create get mapping to show add customer form
	@GetMapping("/add")
	public String addCustomer(Model model) {

		customerForm form = new customerForm();
		model.addAttribute("form", form);
		return "customer-form";
	}

	// define post mapping to save customer
	@PostMapping("/add")
	public String saveCustomer(@ModelAttribute("form") customerForm theForm, Model model) {

		// validate form fields
		theForm.formValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate customer from form
			Customer theCustomer = new Customer(theForm);

			// then save resulting customer
			customerdao.save(theCustomer);

			// redirect to customer register page
			if(theForm.getId() == null) {
				return "redirect:/web/customers/";
			}else {
				return "redirect:/web/customers/";
			}
			
		} else {
			// forward back to form with error messages
			model.addAttribute("form", theForm);
			return "customer-form";
		}
	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteCustomer(@RequestParam("id") Integer theid, Model model) {

		Customer theCustomer = customerdao.findById(theid);
		
		if(theCustomer == null) {
			return "redirect:/web/customers/";
		}else {
			// delete customer
			customerdao.delete(theid);

			return "redirect:/web/customers/";
		}
	}

	// define getMapping for update form
	@GetMapping("/update")
	public String updateCustomer(@RequestParam("id") Integer theid, Model model) {

		// get the customer by id
		Customer customer = customerdao.findById(theid);

		if (customer == null) {
			return "redirect:/web/customers/";
		} else {
			// send customer to form
			customerForm cf = new customerForm(customer);

			// send form to view
			model.addAttribute("form", cf);

			return "customer-form";
		}
	}
}
