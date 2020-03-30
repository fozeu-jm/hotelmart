package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.Room;

public interface RoomInterface {
	public List<Room> findAll();

	public void save(Room theRoom);

	public Room findById(Integer theId);

	public void delete(Integer theId);
}
