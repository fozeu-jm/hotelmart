package com.kaizerwebdesign.hotelapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kaizerwebdesign.hotelapp.forms.ServiceForm;

@Entity
@Table(name = "service")
public class Service {

	// declare entity attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "label")
	private String label;

	@Column(name = "description")
	private String description;

	// define constructors
	public Service() {

	}

	public Service(ServiceForm sf) {
		this.id = sf.getId();
		this.label = sf.getLabel();
		this.description = sf.getDescription();
	}

	// define getters and setters
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

	@Override
	public String toString() {
		return "Service [id=" + id + ", label=" + label + ", description=" + description + "]";
	}

}
