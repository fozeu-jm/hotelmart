package com.kaizerwebdesign.hotelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomTypeDAO;
import com.kaizerwebdesign.hotelapp.entities.Room;
import com.kaizerwebdesign.hotelapp.entities.RoomType;
import com.kaizerwebdesign.hotelapp.forms.RoomForm;

@Controller
@RequestMapping("web/rooms")
public class RoomController {
	// inject Dao
	@Autowired
	private RoomDAO roomDAO;

	@Autowired
	private RoomTypeDAO typeDAO;

	// define mapping to get list
	@GetMapping("/")
	public String getRoomList(Model themodel) {
		// insert type list in model
		themodel.addAttribute("rooms", roomDAO.findAll());

		return "rooms";
	}

	// create get mapping to show add form
	@GetMapping("/add")
	public String addType(Model model) {
		RoomForm roomform = new RoomForm();

		model.addAttribute("types", typeDAO.findAll());

		// send form to view for data binding
		model.addAttribute("form", roomform);

		return "room-form";
	}

	// define post mapping to save room type
	@PostMapping("/add")
	public String saveRoom(@ModelAttribute("form") RoomForm theForm, Model model) {

		// validate form fields
		theForm.fieldValidation();

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			// Instantiate from form
			Room theroom = new Room(theForm);

			// get type from form
			theroom.setType(typeDAO.findById(theForm.getType()));

			// then save resulting type
			roomDAO.save(theroom);

			// redirect to type register page
			return "redirect:/web/rooms/";
		} else {
			model.addAttribute("types", typeDAO.findAll());
			// forward back to form with error messages
			model.addAttribute("form", theForm);
			return "room-form";
		}
	}

	// define getMapping for update form
	@GetMapping("/update")
	public String updateRoom(@RequestParam("id") Integer theid, Model model) {

		// get the entity by id
		Room theRoom = roomDAO.findById(theid);

		if (theRoom == null) {
			return "redirect:/web/rooms/";
		} else {
			// get list of types
			List<RoomType> rtList = typeDAO.findAll();

			// remove selected entity type from list so as to remain with a list of unselected items
			rtList.remove(theRoom.getType());

			// send selected entity to form
			model.addAttribute("room", theRoom);

			// send list to view
			model.addAttribute("types", rtList);

			// send enttiy to form
			RoomForm rf = new RoomForm(theRoom);

			// send form to view
			model.addAttribute("form", rf);

			return "room-form";
		}
	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteCustomer(@RequestParam("id") Integer theid, Model model) {

		// get the entity by id
		Room theRoom = roomDAO.findById(theid);

		if (theRoom == null) {
			return "redirect:/web/rooms/";
		} else {
			// delete entity
			roomDAO.delete(theid);

			return "redirect:/web/rooms/";
		}
	}
}
