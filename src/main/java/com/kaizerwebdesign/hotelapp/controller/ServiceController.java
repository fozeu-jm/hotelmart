package com.kaizerwebdesign.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.ServiceDAO;
import com.kaizerwebdesign.hotelapp.entities.Service;
import com.kaizerwebdesign.hotelapp.forms.ServiceForm;

@Controller
@RequestMapping("web/services")
public class ServiceController {
	@Autowired
	private ServiceDAO serviceDAO;

	// define mapping to get list
	@GetMapping("/")
	public String getRoomList(Model themodel) {
		// insert type list in model
		themodel.addAttribute("services", serviceDAO.findAll());

		return "services";
	}

	@GetMapping("/add")
	public String addService(Model model) {
		ServiceForm svform = new ServiceForm();

		// send form to view for data binding
		model.addAttribute("form", svform);

		return "service-form";
	}

	// define post mapping to save entity
	@PostMapping("/add")
	public String saveService(@ModelAttribute("form") ServiceForm theForm, Model model) {

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors in form
		if (theForm.getErrors().isEmpty()) {
			// Instantiate type from form
			Service theService = new Service(theForm);
			
			System.out.println(theService);
			// then save resulting type
			serviceDAO.save(theService);

			// redirect to type register page
			return "redirect:/web/services/";
		} else {
			// forward back to form with error messages
			model.addAttribute("form", theForm);
			return "service-form";
		}
	}

	// define getMapping for update form
	@GetMapping("/update")
	public String updateService(@RequestParam("id") Integer theid, Model model) {

		// get the roomtype by id
		Service theservice = serviceDAO.findById(theid);

		if (theservice == null) {
			return "redirect:/web/services/";
		} else {
			// send type to form
			ServiceForm svf = new ServiceForm(theservice);

			// send form to view
			model.addAttribute("form", svf);

			return "service-form";
		}
	}
	
	// define getMapping for delete
		@GetMapping("/remove")
		public String deleteType(@RequestParam("id") Integer theid, Model model) {

			// get the roomtype by id
			Service service = serviceDAO.findById(theid);

			if (service == null) {
				return "redirect:/web/services/";
			} else {
				// delete customer
				serviceDAO.delete(theid);

				return "redirect:/web/services/";
			}
		}

}
