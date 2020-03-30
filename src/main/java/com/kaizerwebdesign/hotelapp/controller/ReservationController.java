package com.kaizerwebdesign.hotelapp.controller;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaizerwebdesign.hotelapp.dao.CustomerDAO;
import com.kaizerwebdesign.hotelapp.dao.ReservationDAO;
import com.kaizerwebdesign.hotelapp.dao.RoomDAO;
import com.kaizerwebdesign.hotelapp.entities.Charge;
import com.kaizerwebdesign.hotelapp.entities.Customer;
import com.kaizerwebdesign.hotelapp.entities.Reservation;
import com.kaizerwebdesign.hotelapp.entities.Room;
import com.kaizerwebdesign.hotelapp.entities.Status;
import com.kaizerwebdesign.hotelapp.forms.ReservationForm;

@Controller
@RequestMapping("web/reservations")
public class ReservationController {
	// inject Dao
	@Autowired
	private ReservationDAO reservationDAO;

	@Autowired
	private RoomDAO roomDAO;

	@Autowired
	private CustomerDAO customerDAO;

	// define mapping to get list
	@GetMapping("/")
	public String getRoomList(Model themodel) {

		// insert type list in model
		themodel.addAttribute("reservations", reservationDAO.findAll());

		return "reservations";
	}

	// create get mapping to show add customer form
	@GetMapping("/add")
	public String RegistrationForm(@RequestParam("cid") Integer cid, Model model) {

		Customer cm = customerDAO.findById(cid);

		if (cm != null) {
			ReservationForm form = new ReservationForm();
			form.setCustomer(cm.getId());
			model.addAttribute("form", form);

			model.addAttribute("customer", cm);

			List<Room> rooms = new ArrayList<Room>();

			for (Room rm : roomDAO.findAll()) {
				if (rm.getStatus() == "FREE") {
					rooms.add(rm);
				}
			}

			model.addAttribute("rooms", rooms);

			return "reservation-form";
		} else {
			return "redirect:/web/customers/";
		}
	}

	// define post mapping to save customer
	@PostMapping("/details")
	public String saveDetails(@ModelAttribute("form") ReservationForm theForm, Model model) {
		theForm.setRate_fee(0.0);

		// validate form fields
		theForm.FieldValidation(reservationDAO);

		// test if there are no errors
		if (theForm.getErrors().isEmpty()) {
			long diff = noTime(theForm.getDeparture()).getTime() - noTime(theForm.getArrival()).getTime();
			diff = Math.round(diff / (double) 86400000);

			long days = diff;
			if (days == 0) {
				days = days + 1;
			}
			Double fee = 0.0;
			for (Integer id : theForm.getRooms()) {
				Room rm = roomDAO.findById(id);
				fee += rm.getType().getRate();
			}
			fee *= days;
			theForm.setRate_fee(fee);

			model.addAttribute("form", theForm);
			return "rate-form";

		} else {
			// forward back to form with error messages
			model.addAttribute("form", theForm);

			List<Room> rooms = new ArrayList<Room>();

			for (Room rm : roomDAO.findAll()) {
				if (rm.getStatus() == "FREE") {
					rooms.add(rm);
				}
			}
			model.addAttribute("rooms", rooms);
			Customer cm = customerDAO.findById(theForm.getCustomer());
			model.addAttribute("customer", cm);

			return "reservation-form";
		}
	}

	// define post mapping to save customer
	@PostMapping("/confirmation")
	public String confirmation(@ModelAttribute("form") ReservationForm theForm, Model model) {
		theForm.FieldValidation(reservationDAO);
		if (theForm.getErrors().isEmpty()) {
			Customer customer = customerDAO.findById(theForm.getCustomer());
			List<Room> rooms = new ArrayList<Room>();
			for (Integer id : theForm.getRooms()) {
				rooms.add(roomDAO.findById(id));
			}
			model.addAttribute("customer", customer);
			model.addAttribute("rooms", rooms);
			model.addAttribute("form", theForm);
			return "confirmation-form";
		} else {
			model.addAttribute("form", theForm);
			return "rate-form";
		}
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("form") ReservationForm theForm, Model model) {
		theForm.FieldValidation(reservationDAO);
		Reservation rv = new Reservation(theForm);
		Customer cm = customerDAO.findById(theForm.getCustomer());

		rv.setCustomer(cm);

		List<Room> rooms = new ArrayList<Room>();
		for (Integer id : theForm.getRooms()) {
			rooms.add(roomDAO.findById(id));
		}

		rv.setRooms(rooms);

		reservationDAO.save(rv);

		return "redirect:/web/reservations/";
	}

	@GetMapping("/update")
	public String updateReservation(@RequestParam("id") Integer theid, Model model) {
		Reservation rv = reservationDAO.findById(theid);

		// test if reservation found
		if (rv != null) {
			// create form from entity
			ReservationForm rf = new ReservationForm(rv);

			List<Room> rooms = new ArrayList<Room>();

			for (Room rm : roomDAO.findAll()) {
				if (rm.getStatus() == "FREE") {
					rooms.add(rm);
				}
			}

			// remove selected rooms from full list
			rooms.removeAll(rv.getRooms());

			rf.getStatuses().remove(rv.getStatus());

			model.addAttribute("rooms", rooms);

			model.addAttribute("srooms", rv.getRooms());

			model.addAttribute("customer", rv.getCustomer());

			model.addAttribute("update", "update");

			model.addAttribute("form", rf);

			return "reservation-form";
		} else {
			return "redirect:/web/reservations/";
		}

	}

	// define getMapping for delete
	@GetMapping("/remove")
	public String deleteCustomer(@RequestParam("id") Integer theid, Model model) {
		Reservation rv = reservationDAO.findById(theid);

		// test if reservation found
		if (rv != null) {

			reservationDAO.delete(theid);
			return "redirect:/web/reservations/";
		} else {
			return "redirect:/web/reservations/";
		}
	}

	public Date noTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date result = null;
		try {
			result = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/invoice")
	public String invoice(@RequestParam("id") Integer theid, Model model) {
		// get selected reservation
		Reservation reservation = reservationDAO.findById(theid);
		if (reservation != null) {
			// calculate flat rate fee if not already set
			Double fee = calculateFlatRate(reservation);
			reservation.setRate_fee(fee);

			// get current date
			ZoneId defaultZoneId = ZoneId.systemDefault();
			Date now = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
			Format formatter = new SimpleDateFormat("MMM dd, yyyy");
			String current = formatter.format(now);
			String due = formatter.format(reservation.getDeparture());

			long diff = noTime(reservation.getDeparture()).getTime() - noTime(reservation.getArrival()).getTime();
			diff = Math.round(diff / (double) 86400000);

			long days = diff;

			// get charge subtotal
			Double subtotal = 0.0;

			for (Charge ch : reservation.getCharges()) {
				subtotal += (ch.getPrice() * ch.getQuantity());
			}

			Double total = (subtotal);

			model.addAttribute("total", total);
			model.addAttribute("charge_sub", subtotal);
			model.addAttribute("days", days);
			model.addAttribute("current", current);
			model.addAttribute("due", due);
			model.addAttribute("reservation", reservation);

			return "invoice";
		} else {
			return "redirect:/web/reservations/";
		}

	}

	// method to calculate a reservation flat rate fee
	public Double calculateFlatRate(Reservation reservation) {
		long diff = noTime(reservation.getDeparture()).getTime() - noTime(reservation.getArrival()).getTime();
		diff = Math.round(diff / (double) 86400000);

		long days = diff;
		if (days == 0) {
			days = days + 1;
		}
		Double fee = 0.0;
		for (Room rm : reservation.getRooms()) {
			fee += rm.getType().getRate();
		}
		fee *= days;
		return fee;
	}

	@GetMapping("/checkin")
	public String checkIn(@RequestParam("id") Integer theid) {
		// get selected reservation
		Reservation reservation = reservationDAO.findById(theid);
		if (reservation != null) {
			reservation.setStatus(Status.CHECKED_IN);
			reservationDAO.save(reservation);
			return "redirect:/web/reservations/";
		} else {
			return "redirect:/web/reservations/";
		}
	}

	@GetMapping("/checkout")
	public String checkOut(@RequestParam("id") Integer theid) {
		// get selected reservation
		Reservation reservation = reservationDAO.findById(theid);
		if (reservation != null) {
			reservation.setStatus(Status.CHECKED_OUT);
			reservationDAO.save(reservation);
			return "redirect:/web/reservations/";
		} else {
			return "redirect:/web/reservations/";
		}

	}
}
