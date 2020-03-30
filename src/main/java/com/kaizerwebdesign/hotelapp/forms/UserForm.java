package com.kaizerwebdesign.hotelapp.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.dao.UserDAO;
import com.kaizerwebdesign.hotelapp.entities.User;

public class UserForm {
	private String username;

	private String password;

	private String confirm;

	private Boolean modify;

	private List<Module> modules;

	private Map<String, String> errors;

	private List<Module> allPrivileges;

	public UserForm() {
		this.errors = new HashMap<String, String>();

		this.allPrivileges = new ArrayList<Module>();
		allPrivileges.add(Module.ADMIN);
		allPrivileges.add(Module.CHARGES);
		allPrivileges.add(Module.CUSTOMERS);
		allPrivileges.add(Module.PAYMENTS);
		allPrivileges.add(Module.RESERVATIONS);
		allPrivileges.add(Module.ROOM_TYPES);
		allPrivileges.add(Module.ROOMS);
		allPrivileges.add(Module.SERVICES);
	}

	public UserForm(User user) {
		this.username = user.getUsername();

		this.allPrivileges = new ArrayList<Module>();
		allPrivileges.add(Module.ADMIN);
		allPrivileges.add(Module.CHARGES);
		allPrivileges.add(Module.CUSTOMERS);
		allPrivileges.add(Module.PAYMENTS);
		allPrivileges.add(Module.RESERVATIONS);
		allPrivileges.add(Module.ROOM_TYPES);
		allPrivileges.add(Module.ROOMS);
		allPrivileges.add(Module.SERVICES);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public Boolean getModify() {
		return modify;
	}

	public void setModify(Boolean modify) {
		this.modify = modify;
	}

	public List<Module> getAllPrivileges() {
		return allPrivileges;
	}

	public void setAllPrivileges(List<Module> allPrivileges) {
		this.allPrivileges = allPrivileges;
	}

	public void fieldValidation(String mode, UserDAO userDAO) {
		if (mode == "add") {
			username = FieldValidation.trimmer(username);

			if (username == null) {
				errors.put("username", "Invalid, empty input");
			} else {
				User user = userDAO.findByUsername(username);
				if (user != null) {
					errors.put("username", "Invalid, username already exist");
				} else {
					try {
						username = FieldValidation.usernameConfirmation(username);
					} catch (Exception e) {
						errors.put("username", e.getMessage());
					}
				}
			}
			password = FieldValidation.trimmer(password);
			confirm = FieldValidation.trimmer(confirm);
			if (password == null) {
				errors.put("password", "Invalid, empty input");
			}

			if (confirm == null) {
				errors.put("confirm", "Invalid, empty input");
			}

			if (password != null && confirm != null) {
				try {
					password = FieldValidation.confirmPassword(password, confirm);
				} catch (Exception e) {
					errors.put("password", e.getMessage());

					errors.put("confirm", e.getMessage());
				}
			}
		}

		if (mode == "edit") {
			username = FieldValidation.trimmer(username);
			if (username == null) {
				errors.put("username", "Invalid, empty input");
			}

			if (modify == null) {
				errors.put("modify", "Invalid, empty input");
			}

			if (modify == true && modify != null) {
				password = FieldValidation.trimmer(password);
				confirm = FieldValidation.trimmer(confirm);
				if (password == null) {
					errors.put("password", "Invalid, empty input");
				}

				if (confirm == null) {
					errors.put("confirm", "Invalid, empty input");
				}

				if (password != null && confirm != null) {
					try {
						password = FieldValidation.confirmPassword(password, confirm);
					} catch (Exception e) {
						errors.put("password", e.getMessage());

						errors.put("confirm", e.getMessage());
					}
				}
			}
		}

		if (modules == null) {
			errors.put("modules", "Invalid, empty input");
		}

	}

}
