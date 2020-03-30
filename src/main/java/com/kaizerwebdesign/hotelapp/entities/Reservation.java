package com.kaizerwebdesign.hotelapp.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaizerwebdesign.hotelapp.forms.ReservationForm;

@Entity
public class Reservation {
	// declare attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "code")
	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrival")
	private Date arrival;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "departure")
	private Date departure;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "rate_fee")
	private Double rate_fee;

	@Column(name = "deposit")
	private Double deposit;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reservation_room", joinColumns = @JoinColumn(name = "reserv_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
	@JsonManagedReference
	private List<Room> rooms;
	
	@JsonManagedReference
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Charge> charges;

	@OneToOne(mappedBy = "reservation", cascade = {CascadeType.REMOVE,CascadeType.MERGE})
	@JsonManagedReference
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	// define constructors
	public Reservation() {

	}

	public Reservation(ReservationForm rf) {
		this.id = rf.getId();
		this.arrival = rf.getArrival();
		this.departure = rf.getDeparture();
		this.status = Status.valueOf(rf.getStatus());
		this.rate_fee = rf.getRate_fee();
		this.deposit = rf.getDeposit();
		this.code = rf.getCode();
	}

	// define getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Status getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getRate_fee() {
		return rate_fee;
	}

	public void setRate_fee(Double rate_fee) {
		this.rate_fee = rate_fee;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	// override toString and equal method
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", arrival=" + arrival + ", departure=" + departure + ", status=" + status
				+ ", rate_fee=" + rate_fee + ", deposit=" + deposit + ", rooms=" + rooms + ", customer=" + customer
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		Reservation rv = (Reservation) obj;
		return (rv.getId() == this.getId());
	}

}
