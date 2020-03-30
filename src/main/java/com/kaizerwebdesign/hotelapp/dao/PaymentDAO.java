package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Payment;

@Repository
public class PaymentDAO implements PaymentInterface {

	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public PaymentDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Payment> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Payment> theQuery = session.createQuery("from Payment", Payment.class);

		// execute the query and get the result list from the query
		List<Payment> payments = theQuery.getResultList();

		// return the result list
		return payments;
	}

	@Override
	@Transactional
	public void save(Payment thepayment) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(thepayment);
	}

	@Override
	@Transactional
	public Payment findById(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// get entity
		Payment payment = session.find(Payment.class, theId);

		return payment;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Payment where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();

	}

}
