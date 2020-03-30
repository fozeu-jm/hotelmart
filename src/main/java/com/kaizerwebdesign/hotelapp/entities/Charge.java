package com.kaizerwebdesign.hotelapp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaizerwebdesign.hotelapp.forms.ChargeForm;

@Entity
@Table(name = "charge")
public class Charge {

	// define attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Column(name = "price")
	private Double price;

	@Column(name = "quantity")
	private Integer quantity;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private Service service;

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	@JsonBackReference
	private Reservation reservation;

	// define constructors
	public Charge() {

	}

	public Charge(ChargeForm chf) {
		super();
		this.id = chf.getId();
		this.date = chf.getDate();
		this.price = chf.getPrice();
		this.quantity = chf.getQuantity();
	}

	// define getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Charge [id=" + id + ", date=" + date + ", price=" + price + ", quantity=" + quantity + ", service="
				+ service + "]";
	}

}
