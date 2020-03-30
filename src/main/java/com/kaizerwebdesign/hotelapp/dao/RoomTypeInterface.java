package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;
import com.kaizerwebdesign.hotelapp.entities.RoomType;

public interface RoomTypeInterface {
	public List<RoomType> findAll();

	public void save(RoomType theType);

	public RoomType findById(Integer theId);

	public void delete(Integer theId);
}
