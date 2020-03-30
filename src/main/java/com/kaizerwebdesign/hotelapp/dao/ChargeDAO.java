package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Charge;

@Repository
public class ChargeDAO implements ChargeInterface {

	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public ChargeDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Charge> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Charge> theQuery = session.createQuery("from Charge", Charge.class);

		// execute the query and get the result list from the query
		List<Charge> charges = theQuery.getResultList();

		// return the result list
		return charges;
	}

	@Override
	@Transactional
	public void save(Charge theCharge) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(theCharge);
	}

	@Override
	@Transactional
	public Charge findById(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// get entity
		Charge theCharge = session.find(Charge.class, theId);

		return theCharge;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Charge where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();
	}

}
