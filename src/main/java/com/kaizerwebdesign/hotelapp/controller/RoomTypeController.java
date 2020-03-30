package com.kaizerwebdesign.hotelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.RoomTypeDAO;
import com.kaizerwebdesign.hotelapp.entities.RoomType;
import com.kaizerwebdesign.hotelapp.forms.RoomTypeForm;

@Controller
@RequestMapping("web/types")
public class RoomTypeController {
	// inject roomtype Dao
	@Autowired
	private RoomTypeDAO roomtypeDAO;

	// define mapping to get type list
	@GetMapping("/")
	public String getTypesList(Model themodel) {
		// insert type list in model
		themodel.addAttribute("types", roomtypeDAO.findAll());

		return "room-types";
	}

	// create get mapping to show add roomtype form
	@GetMapping("/add")
	public String addType(Model model) {

		RoomTypeForm typeform = new RoomTypeForm();
		model.addAttribute("form", typeform);
		return "type-form";
	}

	// define post mapping to save room type
	@PostMapping("/add")
	public String saveCustomer(@ModelAttribute("form") RoomTypeForm theForm, Model model) {

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// instanciate type from form
			RoomType theroomType = new RoomType(theForm);

			// then save resulting type
			roomtypeDAO.save(theroomType);

			// redirect to type register page
			return "redirect:/web/types/";
		} else {
			// forward back to form with error messages
			model.addAttribute("form", theForm);
			return "type-form";
		}
	}

	// define getMapping for update form
	@GetMapping("/update")
	public String updateCustomer(@RequestParam("id") Integer theid, Model model) {

		// get the roomtype by id
		RoomType type = roomtypeDAO.findById(theid);

		if (type == null) {
			return "redirect:/web/types/";
		} else {
			// send type to form
			RoomTypeForm tf = new RoomTypeForm(type);

			// send form to view
			model.addAttribute("form", tf);

			return "type-form";
		}

	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteType(@RequestParam("id") Integer theid, Model model) {

		// get the roomtype by id
		RoomType type = roomtypeDAO.findById(theid);

		if (type == null) {
			return "redirect:/web/types/";
		} else {
			// delete customer
			roomtypeDAO.delete(theid);

			return "redirect:/web/types/";
		}
	}

}
