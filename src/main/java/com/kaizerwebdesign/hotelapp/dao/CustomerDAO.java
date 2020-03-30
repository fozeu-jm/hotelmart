package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Customer;

@Repository
public class CustomerDAO implements CustomerInterface {

	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public CustomerDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Customer> findAll() {

		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Customer> theQuery = session.createQuery("from Customer", Customer.class);

		// execute the query and get the result list from the query
		List<Customer> customers = theQuery.getResultList();

		// return the result list
		return customers;
	}

	@Override
	@Transactional
	public void save(Customer theCustomer) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer findById(Integer theId) {

		// get session
		Session session = em.unwrap(Session.class);

		// get customer
		Customer thecustomer = session.find(Customer.class, theId);

		return thecustomer;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Customer where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();
	}

}
