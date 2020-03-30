package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Reservation;

@Repository
public class ReservationDAO implements ReservationInterface {

	// define entity manager
	private EntityManager em;

	// setup constructor injection of entity manager
	@Autowired
	public ReservationDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Reservation> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Reservation> theQuery = session.createQuery("from Reservation", Reservation.class);

		// execute the query and get the result list from the query
		List<Reservation> reservations = theQuery.getResultList();

		// return the result list
		return reservations;
	}

	@Override
	@Transactional
	public void save(Reservation theReservation) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.clear();
		session.saveOrUpdate(theReservation);
	}

	@Override
	@Transactional
	public Reservation findById(Integer theId) {

		// get session
		Session session = em.unwrap(Session.class);

		// get entity
		Reservation theReservation = session.find(Reservation.class, theId);

		return theReservation;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Reservation where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();
	}

	@Override
	@Transactional
	public Reservation findByCode(String theCode) {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		try {
			Query<Reservation> theQuery = session.createQuery("from Reservation where code=:code", Reservation.class);

			theQuery.setParameter("code", theCode);

			// execute the query and get the result from the query
			Reservation reservation = theQuery.getSingleResult();

			// return the result
			return reservation;
		} catch (NoResultException e) {
			return null;
		}

	}

}
