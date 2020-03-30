package com.kaizerwebdesign.hotelapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.dao.AuthorityDAO;
import com.kaizerwebdesign.hotelapp.dao.UserDAO;
import com.kaizerwebdesign.hotelapp.entities.User;
import com.kaizerwebdesign.hotelapp.forms.EditForm;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
	@Autowired
	UserDAO userDAO;

	@Autowired
	AuthorityDAO authorityDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/edit")
	public String saveEdit(@RequestBody EditForm theForm, Authentication authentication) {
		// validate form fields
		theForm.FieldValidation(userDAO, authentication.getName(), passwordEncoder);

		// test if there are no errors in the form fields
		if (!theForm.getErrors().isEmpty()) {
			throw new ResourceNotFoundException("Errors in request body " + theForm.getErrors());
		}
		
		User user = userDAO.findByUsername(authentication.getName());
		
		user.setPassword(passwordEncoder.encode(theForm.getNewPass()));

		userDAO.save(user);

		return "Password changed successfully !";
	}
	
//	@GetMapping("/connect")
//	public String connect(Authentication authentication) {
//		return authentication.getName();
//	}
	
	@GetMapping("/connect")
	public User getAuthentified(Authentication authentication) {
		User user = userDAO.findByUsername(authentication.getName());
		return user;
	}
	
	
}
