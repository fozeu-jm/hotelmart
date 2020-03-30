package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Service;

@Repository
public class ServiceDAO implements ServiceInterface {
	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public ServiceDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<Service> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<Service> theQuery = session.createQuery("from Service", Service.class);

		// execute the query and get the result list from the query
		List<Service> services = theQuery.getResultList();

		// return the result list
		return services;
	}

	@Override
	@Transactional
	public void save(Service theService) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(theService);

	}

	@Override
	@Transactional
	public Service findById(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// get entity
		Service theService = session.find(Service.class, theId);

		return theService;
	}

	@Override
	@Transactional
	public void delete(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from Service where id=:theid");

		thequery.setParameter("theid", theId);

		thequery.executeUpdate();

	}

}
