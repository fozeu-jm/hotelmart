package com.kaizerwebdesign.hotelapp.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Status;

public class ReservationForm {

	// declare attributes
	private Integer id;

	private Date arrival;

	private Date departure;

	private String farrival;

	private String fdeparture;

	private String status;

	private Double rate_fee;

	private Double deposit;

	private String code;

	private Integer customer;

	private Integer[] rooms;

	private Map<String, String> errors;

	private List<Status> statuses;

	// define constructors
	public ReservationForm() {
		this.errors = new HashMap<String, String>();

		// initialise list of statuses
		this.statuses = new ArrayList<Status>();
		statuses.add(Status.PAID);
		statuses.add(Status.CHECKED_IN);
		statuses.add(Status.CHECKED_OUT);
	}

	public ReservationForm(Reservation rv) {
		this.id = rv.getId();
		// set preferred date format for validation
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.farrival = dformat.format(rv.getArrival());
		this.fdeparture = dformat.format(rv.getDeparture());

		this.status = rv.getStatus().toString();
		this.rate_fee = rv.getRate_fee();
		this.deposit = rv.getDeposit();
		this.customer = rv.getCustomer().getId();
		this.code = rv.getCode();

		this.errors = new HashMap<String, String>();

		// initialise list of statuses
		this.statuses = new ArrayList<Status>();
		statuses.add(Status.PAID);
		statuses.add(Status.CHECKED_IN);
		statuses.add(Status.CHECKED_OUT);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFarrival() {
		return farrival;
	}

	public void setFarrival(String farrival) {
		this.farrival = farrival;
	}

	public String getFdeparture() {
		return fdeparture;
	}

	public void setFdeparture(String fdeparture) {
		this.fdeparture = fdeparture;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public Integer[] getRooms() {
		return rooms;
	}

	public void setRooms(Integer[] rooms) {
		this.rooms = rooms;
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

	public Map<String, String> getErrors() {
		return errors;
	}

	public void FieldValidation(ReservationDAO dao) {
		
		try {
			this.departure = FieldValidation.isValidDateTime(fdeparture);
		} catch (Exception e) {
			errors.put("departure", e.getMessage());
		}

		
		try {
			this.arrival = FieldValidation.isValidDateTime(farrival);
		} catch (Exception e) {
			errors.put("arrival", e.getMessage());
		}
		
		if(arrival != null && departure != null) {
			if(arrival.compareTo(departure) > 0 || arrival.compareTo(departure) == 0) {
				errors.put("arrival", "Invalid ! Arrival date cannot be after or equal to the departure date");
				errors.put("departure", "Invalid! Arrival date cannot be after or equal to the departure date");
			}
		}
		
		this.status = FieldValidation.trimmer(status);
		if (status == null) {
			errors.put("status", "Invalid, empty field");
		} else {
			try {
				@SuppressWarnings("unused")
				Status stat = Status.valueOf(status);
			} catch (Exception e) {
				errors.put("status", "Unrecognised status !");
			}
		}

		if (rate_fee == null) {
			errors.put("rate_fee", "Invalid, empty input");
		}

		if (deposit == null) {
			errors.put("deposit", "Invalid, empty input");
		}

		if (customer == null) {
			errors.put("customer", "Invalid, empty input");
		}

		if (rooms == null) {
			errors.put("rooms", "Invalid, empty input");
		}

		this.code = FieldValidation.trimmer(this.code);
		if (code == null) {
			errors.put("code", "Invalid, empty input");
		} else {
			Reservation test = dao.findByCode(this.code);
			if (test != null) {
				errors.put("code", "Reservation code already exists, choose another ?");
			}
		}
	}

	@Override
	public String toString() {
		return "ReservationForm [id=" + id + ", arrival=" + arrival + ", departure=" + departure + ", farrival="
				+ farrival + ", fdeparture=" + fdeparture + ", status=" + status + ", rate_fee=" + rate_fee
				+ ", deposit=" + deposit + ", code=" + code + ", customer=" + customer + ", rooms="
				+ Arrays.toString(rooms) + ", errors=" + errors + ", statuses=" + statuses + "]";
	}
	
	

}
