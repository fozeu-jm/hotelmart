package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import com.kaizerwebdesign.hotelapp.entities.Service;

public interface ServiceInterface {
	public List<Service> findAll();

	public void save(Service theService);

	public Service findById(Integer theId);

	public void delete(Integer theId);
}
