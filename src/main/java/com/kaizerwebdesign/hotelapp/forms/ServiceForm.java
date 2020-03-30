package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.Service;

public class ServiceForm {
	// declare entity attributes

	private Integer id;

	private String label;

	private String description;

	private Map<String, String> errors;

	// define constructors
	public ServiceForm() {
		this.errors = new HashMap<String, String>();
	}

	public ServiceForm(Service s) {
		this.id = s.getId();
		this.label = s.getLabel();
		this.description = s.getDescription();
		this.errors = new HashMap<String, String>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	// field validation method
	public void fieldValidation() {
		// trim fields to remove leading and trailing spaces
		label = FieldValidation.trimmer(label);
		description = FieldValidation.trimmer(description);

		// test if field are empty
		if (label == null) {
			errors.put("label", "Invalid, empty input");
		}

		if (description == null) {
			errors.put("description", "Invalid, empty input");
		}

	}

}
