package com.kaizerwebdesign.hotelapp.forms;

import java.util.HashMap;
import java.util.Map;

import com.kaizerwebdesign.hotelapp.entities.Room;

public class RoomForm {
	// define attributes

	private Integer id;

	private String roomNo;

	private Integer floor;

	private Integer capacity;

	private Double surfaceArea;

	private Integer type;

	private Map<String, String> errors;

	public RoomForm() {
		this.errors = new HashMap<String, String>();
	}

	public RoomForm(Room room) {
		this.id = room.getId();
		this.roomNo = room.getRoomNo();
		this.floor = room.getFloor();
		this.capacity = room.getCapacity();
		this.surfaceArea = room.getSurfaceArea();
		this.type = room.getType().getId();
		this.errors = new HashMap<String, String>();
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void fieldValidation() {
		roomNo = FieldValidation.trimmer(roomNo);
		if ( roomNo == null) {
			errors.put("roomNo", "Invalid, empty input");
		}
		
		if(floor == null) {
			errors.put("floor", "Invalid, empty input");
		}
		
		if(capacity == null || capacity < 0) {
			errors.put("capacity", "Invalid input");
		}
	
		if(surfaceArea == null || surfaceArea < 0) {
			errors.put("surfaceArea", "Invalid input");
		}
		
		if(type == null) {
			errors.put("type", "Invalid, empty input");
		}
	}

}
