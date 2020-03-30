package com.kaizerwebdesign.hotelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.AuthorityDAO;
import com.kaizerwebdesign.hotelapp.dao.UserDAO;
import com.kaizerwebdesign.hotelapp.entities.Authority;
import com.kaizerwebdesign.hotelapp.entities.User;
import com.kaizerwebdesign.hotelapp.forms.EditForm;
import com.kaizerwebdesign.hotelapp.forms.Module;
import com.kaizerwebdesign.hotelapp.forms.UserForm;

@Controller
@RequestMapping("web/users")
public class UserController {
	@Autowired
	UserDAO userDAO;

	@Autowired
	AuthorityDAO authorityDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// define mapping to get type list
	@GetMapping("/")
	public String userList(Model themodel, Authentication authentication) {
		List<User> users = userDAO.findAll();
		User user = userDAO.findByUsername(authentication.getName());

		users.remove(user);
		// insert type list in model
		themodel.addAttribute("users", users);

		return "users";
	}

	@GetMapping("/add")
	public String addUser(Model model) {
		UserForm form = new UserForm();

		model.addAttribute("form", form);

		return "user-form";
	}

	@GetMapping("/update")
	public String updateUser(@RequestParam("id") String username, Model model) {
		// get the user by username
		User user = userDAO.findByUsername(username);

		if (user != null) {
			UserForm form = new UserForm();

			form.setUsername(user.getUsername());

			List<Authority> authorities = user.getAuthorities();

			List<Module> modules = new ArrayList<Module>();

			for (Authority auth : authorities) {
				modules.add(Module.valueOf(auth.getAuthority()));
			}
			modules.remove(Module.valueOf("USER"));
			form.getAllPrivileges().removeAll(modules);

			model.addAttribute("sauth", modules);

			model.addAttribute("form", form);

			return "modify-form";
		} else {
			return "redirect:/web/users/";
		}
	}

	@PostMapping("/add")
	public String saveUser(@ModelAttribute("form") UserForm theForm, Model model, Authentication authentication) {

		theForm.fieldValidation("add", userDAO);

		// test if there are no errors in the form fields
		if (theForm.getErrors().isEmpty()) {
			User user = new User();
			user.setUsername(theForm.getUsername());
			user.setPassword(passwordEncoder.encode(theForm.getPassword()));
			user.setEnabled(true);
			// List<Authority> authorities = new ArrayList<Authority>();

			userDAO.save(user);

			for (Module mod : theForm.getModules()) {
				authorityDAO.save(new Authority(user, "ROLE_"+mod.toString()));
			}
			authorityDAO.save(new Authority(user, "ROLE_"+Module.USER.toString()));

			return "redirect:/web/users/";
		} else {
			model.addAttribute("form", theForm);

			return "user-form";
		}
	}

	@PostMapping("/update")
	public String updateUser(@ModelAttribute("form") UserForm theForm, Model model, Authentication authentication) {

		theForm.fieldValidation("edit", userDAO);

		// test if there are no errors in the form fields
		if (theForm.getErrors().isEmpty()) {

			User user = userDAO.findByUsername(theForm.getUsername());

			if (user == null) {
				return "redirect:/web/users/";
			} else {

				if (theForm.getModify() == true) {
					user.setPassword(passwordEncoder.encode(theForm.getPassword()));
					userDAO.save(user);
				}

				List<Authority> auths = user.getAuthorities();

				for (Authority auth : auths) {
					theForm.getModules().remove(Module.valueOf(auth.getAuthority()));
				}

				for (Module mod : theForm.getModules()) {
					authorityDAO.save(new Authority(user, "ROLE_"+mod.toString()));
				}

				return "redirect:/web/users/";
			}
		} else {
			model.addAttribute("form", theForm);

			return "modify-form";
		}
	}

	@GetMapping("/edit")
	public String editPass(Model model) {

		EditForm form = new EditForm();

		model.addAttribute("form", form);

		return "edit-form";
	}

	@PostMapping("/edit")
	public String saveEdit(@ModelAttribute("form") EditForm theForm, Model model, Authentication authentication) {

		// validate form fields
		theForm.FieldValidation(userDAO, authentication.getName(), passwordEncoder);

		// test if there are no errors in the form fields
		if (theForm.getErrors().isEmpty()) {
			User user = userDAO.findByUsername(authentication.getName());

			// Man! We gotta encode new password before saving to the database. Bcrypt is
			// bae !
			user.setPassword(passwordEncoder.encode(theForm.getNewPass()));

			userDAO.save(user);

			return "redirect:/web/home";
		} else {
			model.addAttribute("form", theForm);

			return "edit-form";
		}
	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteType(@RequestParam("id") String username, Model model) {

		// get the roomtype by id
		User user = userDAO.findByUsername(username);
		
		if (user == null) {
			return "redirect:/web/users/";
		} else {
			// delete customer
			userDAO.delete(username);

			return "redirect:/web/users/";
		}
	}
}
