package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.Payment;

public class PaymentForm {
	
		// define attributes
		private Integer id;
		
		private Double amount;
		
		private String method;
		
		private Integer reservation;
		
		private Map<String, String> errors;
		
		//define constructors
		public PaymentForm() {
			this.errors = new HashMap<String, String>();
		}
		
		public PaymentForm(Payment pay) {
			this.id = pay.getId();
			this.amount = pay.getAmount();
			this.method = pay.getMethod().toString();
		
			this.reservation = pay.getReservation().getId();
			this.errors = new HashMap<String, String>();
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

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Integer getReservation() {
			return reservation;
		}

		public void setReservation(Integer reservation) {
			this.reservation = reservation;
		}

		public Map<String, String> getErrors() {
			return errors;
		}

		@Override
		public String toString() {
			return "PaymentForm [id=" + id + ", amount=" + amount + ", method=" + method + ", date=" + ", fdate="
					+  ", reservation=" + reservation + ", errors=" + errors + "]";
		}
		
		public void fieldValidation() {
			method = FieldValidation.trimmer(method);
			
			if(method == null) {
				errors.put("method", "Invalid, empty input");
			}
			
			if(amount == null || amount < 0) {
				errors.put("amount", "Invalid, empty input");
			}
			
			if(reservation == null) {
				errors.put("reservation", "Invalid, empty input");
			}
			
		}
}
