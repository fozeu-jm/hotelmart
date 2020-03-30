package com.kaizerwebdesign.hotelapp.rest;

public class SummaryResponse {
	private Double earnings;

	private Integer reservations;

	private Integer customers;

	private Integer rooms;

	public SummaryResponse() {

	}

	public SummaryResponse(Double earnings, Integer reservations, Integer customers, Integer rooms) {
		this.earnings = earnings;
		this.reservations = reservations;
		this.customers = customers;
		this.rooms = rooms;
	}

	public Double getEarnings() {
		return earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public Integer getReservations() {
		return reservations;
	}

	public void setReservations(Integer reservations) {
		this.reservations = reservations;
	}

	public Integer getCustomers() {
		return customers;
	}

	public void setCustomers(Integer customers) {
		this.customers = customers;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}

}
