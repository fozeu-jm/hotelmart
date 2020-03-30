package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.RoomType;

public class RoomTypeForm {
	// define field attributes

	private Integer id;

	private String label;

	private String description;

	private Double rate;

	private Map<String, String> errors;

	public RoomTypeForm() {
		this.errors = new HashMap<String, String>();
	}
	
	public RoomTypeForm(RoomType type) {
		id = type.getId();
		label = type.getLabel();
		description = type.getDescription();
		rate = type.getRate();
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "RoomTypeForm [id=" + id + ", label=" + label + ", description=" + description + ", rate=" + rate + "]";
	}

	// form validation method
	public void fieldValidation() {
		label = FieldValidation.trimmer(label);

		if (label == null) {
			errors.put("label", "Invalid, empty input");
		}
		if (rate == null) {
			errors.put("rate", "Invalid, empty input");
		}else {
			if(rate < 0) {
				errors.put("rate", "Invalid, negative values not allowed");
			}
		}
	}

}
