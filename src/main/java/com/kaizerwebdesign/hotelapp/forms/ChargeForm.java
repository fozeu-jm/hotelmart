package com.kaizerwebdesign.hotelapp.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.Charge;

public class ChargeForm {
	// define attributes
	private Integer id;

	private String fdate;

	private Date date;

	private Double price;

	private Integer quantity;

	private Integer service;

	private Integer reservation;

	private Map<String, String> errors;

	// define constructors
	public ChargeForm() {
		this.errors = new HashMap<String, String>();
	}

	public ChargeForm(Charge ch) {
		// set preferred date format for validation
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.fdate = dformat.format(ch.getDate());

		this.price = ch.getPrice();

		this.quantity = ch.getQuantity();

		this.id = ch.getId();

		this.errors = new HashMap<String, String>();
	}

	// define getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public Integer getReservation() {
		return reservation;
	}

	public void setReservation(Integer reservation) {
		this.reservation = reservation;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ChargeForm [id=" + id + ", fdate=" + fdate + ", date=" + date + ", price=" + price + ", quantity="
				+ quantity + ", service=" + service + ", errors=" + errors + "]";
	}

	public void fieldValidation() {
		try {
			this.date = FieldValidation.isValidDateTime(fdate);
		} catch (Exception e) {
			errors.put("date", e.getMessage());
		}

		if (price == null || price < 0) {
			errors.put("price", "Invalid, empty input");
		}

		if (quantity == null || quantity <= 0) {
			errors.put("quantity", "Invalid, empty input");
		}

		if (service == null) {
			errors.put("service", "Invalid, empty input");
		}
		
		if(reservation == null) {
			errors.put("reservation", "Invalid, empty input");
		}
	}

}
