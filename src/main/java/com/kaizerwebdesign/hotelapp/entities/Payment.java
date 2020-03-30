package com.kaizerwebdesign.hotelapp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kaizerwebdesign.hotelapp.forms.PaymentForm;

@Entity
@Table(name = "payment")
public class Payment {
	// define attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "amount")
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "method")
	private PaymentMethod method;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;
	
	@OneToOne
	@JoinColumn(name = "reservation_id")
	@JsonBackReference
	private Reservation reservation;
	
	//define constructors
	public Payment() {
		
	}

	public Payment(PaymentForm pay) {
		this.id = pay.getId();
		this.amount = pay.getAmount();
		this.method = PaymentMethod.valueOf(pay.getMethod());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentMethod getMethod() {
		return method;
	}

	public void setMethod(PaymentMethod method) {
		this.method = method;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", method=" + method + ", date=" + date + ", reservation="
				+ reservation + "]";
	}
}
