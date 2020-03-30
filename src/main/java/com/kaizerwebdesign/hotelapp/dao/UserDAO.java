package com.kaizerwebdesign.hotelapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.User;

@Repository
public class UserDAO implements UserInterface {
	// define entity manager
	private EntityManager em;

	// setup constructor injection
	@Autowired
	public UserDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<User> theQuery = session.createQuery("from User", User.class);

		// execute the query and get the result list from the query
		List<User> users = theQuery.getResultList();

		// return the result list
		return users;
	}

	@Override
	@Transactional
	public void save(User user) {
		// get session
		Session session = em.unwrap(Session.class);

		// save or update depending if entity has id or not
		session.saveOrUpdate(user);

	}

	@Override
	@Transactional
	public User findById(Integer theId) {
		// get session
		Session session = em.unwrap(Session.class);

		// get room type
		User user = session.find(User.class, theId);

		return user;
	}

	@Override
	@Transactional
	public User findByCredentials(String username, String password) {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);

		// create a query from session
		Query<User> theQuery = session.createQuery("from User where username=:user AND password=:pass", User.class);

		theQuery.setParameter("user", username).setParameter("pass", password);

		// execute the query and get the result from the query
		User user = theQuery.getSingleResult();

		// return the result list
		return user;
	}

	@Override
	@Transactional
	public void delete(String username) {
		// get session
		Session session = em.unwrap(Session.class);

		// create delete query
		@SuppressWarnings("rawtypes")
		Query thequery = session.createQuery("delete from User where username=:user");

		thequery.setParameter("user", username);

		thequery.executeUpdate();

	}

	@Override
	@Transactional
	public User findByUsername(String username) {
		// get the current hibernate session
		Session session = em.unwrap(Session.class);
		
		try {
			// create a query from session
			Query<User> theQuery = session.createQuery("from User where username=:user", User.class);
	
			theQuery.setParameter("user", username);
	
			// execute the query and get the result from the query
			User user = theQuery.getSingleResult();
	
			// return the result list
			return user;
		}catch (NoResultException e) {
			return null;
		}
	}

}
