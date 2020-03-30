package com.kaizerwebdesign.hotelapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kaizerwebdesign.hotelapp.forms.RoomTypeForm;

@Entity
@Table(name = "roomtype")
public class RoomType {

	// define field attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "label")
	private String label;

	@Column(name = "description")
	private String description;

	@Column(name = "rate")
	private Double rate;

	// define constructors
	public RoomType() {

	}

	public RoomType(RoomTypeForm theForm) {
		id = theForm.getId();
		label = theForm.getLabel();
		description = theForm.getDescription();
		rate = theForm.getRate();
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	// define toString method
	@Override
	public String toString() {
		return "RoomType [id=" + id + ", label=" + label + ", description=" + description + ", rate=" + rate + "]";
	}

	@Override
	public boolean equals(Object obj) {
		RoomType rt = (RoomType) obj;
		return (rt.getId() == this.getId());
	}

}
