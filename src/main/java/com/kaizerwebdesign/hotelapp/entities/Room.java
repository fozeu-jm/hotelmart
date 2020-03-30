package com.kaizerwebdesign.hotelapp.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaizerwebdesign.hotelapp.forms.RoomForm;

@Entity
@Table(name = "room")
public class Room {

	// define attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "floor")
	private Integer floor;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "room_no")
	private String roomNo;

	@Column(name = "surface_area")
	private Double surfaceArea;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private RoomType type;

	@ManyToMany(mappedBy = "rooms")
	@JsonBackReference
	private List<Reservation> reservations;
	
	@Transient
	private String status;

	public Room() {

	}

	public Room(RoomForm theForm) {
		this.id = theForm.getId();
		this.floor = theForm.getFloor();
		this.capacity = theForm.getCapacity();
		this.roomNo = theForm.getRoomNo();
		this.surfaceArea = theForm.getSurfaceArea();
	}

	// getters and setters and toString method
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Double getSurfaceArea() {
		return surfaceArea;
	}

	public void setSurfaceArea(Double surfaceArea) {
		this.surfaceArea = surfaceArea;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void defineStatus() {
		int checked = 0;
		if(this.reservations.isEmpty() || this.reservations == null) {
			this.status = "FREE";
		}else {
			for(Reservation reservation : this.reservations) {
				if(reservation.getStatus() != Status.CHECKED_OUT) {
					checked++;
					break;
				}
			}
			if(checked > 0) {
				this.setStatus("BUSY");
			}else {
				this.setStatus("FREE");
			}
		}
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomNo=" + roomNo + ", floor=" + floor + ", capacity=" + capacity
				+ ", surfaceArea=" + surfaceArea + ", type=" + type + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Room rm = (Room) obj;
		return (rm.getId() == this.getId());
	}

}
