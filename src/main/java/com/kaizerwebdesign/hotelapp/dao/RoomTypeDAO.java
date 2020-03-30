package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.kaizerwebdesign.hotelapp.entities.RoomType;

@Repository
public class RoomTypeDAO implements RoomTypeInterface {
	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public RoomTypeDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<RoomType> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<RoomType> theQuery = session.createQuery("from RoomType", RoomType.class);

		// execute the query and get the result list from the query
		List<RoomType> types = theQuery.getResultList();

		// return the result list
		return types;
	}

	@Override
	@Transactional
	public void save(RoomType theType) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(theType);
	}

	@Override
	@Transactional
	public RoomType findById(Integer theId) {

		// get session
		Session session = em.unwrap(Session.class);

		// get room type
		RoomType theType = session.find(RoomType.class, theId);

		return theType;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from RoomType where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();
	}

}
