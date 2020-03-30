package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kaizerwebdesign.hotelapp.dao.UserDAO;
import com.kaizerwebdesign.hotelapp.entities.User;

public class EditForm {

	private String actual;

	private String newPass;

	private String confirm;

	// hashmap to store field validation errors
	private Map<String, String> errors;

	public EditForm() {
		this.errors = new HashMap<String, String>();
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void FieldValidation(UserDAO userDAO, String username, PasswordEncoder passwordEncoder) {
		User user = userDAO.findByUsername(username);
		newPass = FieldValidation.trimmer(newPass);
		confirm = FieldValidation.trimmer(confirm);
		actual = FieldValidation.trimmer(actual);
		
		if(actual == null) {
			errors.put("actual", "Invalid, empty input");
		}else {
			// test if entered password is OK, we never know right ? Can't trust those users sh*t !
			if (!passwordEncoder.matches(actual, user.getPassword())) {
				errors.put("actual", "Invalid, wrong password");
			}
		}
	
		
		if(newPass == null) {
			errors.put("newPass", "Invalid, empty input");
		}
		
		if(confirm == null) {
			errors.put("confirm", "Invalid, empty input");
		}

		if (newPass != null && confirm != null) {
			try {
				actual = FieldValidation.confirmPassword(newPass, confirm);
			} catch (Exception e) {
				errors.put("newPass", e.getMessage());
				
				errors.put("confirm", e.getMessage());
			}
		}

	}

}
