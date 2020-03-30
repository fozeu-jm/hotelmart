package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.kaizerwebdesign.hotelapp.entities.Room;

@Repository
public class RoomDAO implements RoomInterface {

	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public RoomDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Room> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Room> theQuery = session.createQuery("from Room", Room.class);

		// execute the query and get the result list from the query
		List<Room> rooms = theQuery.getResultList();
		
		for(Room room : rooms) {
			room.defineStatus();
		}

		// return the result list
		return rooms;
	}

	@Override
	@Transactional
	public void save(Room theRoom) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(theRoom);

	}

	@Override
	@Transactional
	public Room findById(Integer theId) {

		// get session
		Session session = em.unwrap(Session.class);

		// get entity
		Room theRoom = session.find(Room.class, theId);
		
		if(theRoom != null) {
			theRoom.defineStatus();
		}

		return theRoom;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Room where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();

	}

}
