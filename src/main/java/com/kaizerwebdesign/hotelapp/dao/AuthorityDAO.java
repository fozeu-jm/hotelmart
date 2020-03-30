package com.kaizerwebdesign.hotelapp.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaizerwebdesign.hotelapp.entities.Authority;

@Repository
public class AuthorityDAO {

	// define entity manager
		private EntityManager em;

		// setup constructor injection
		@Autowired
		public AuthorityDAO(EntityManager em) {
			this.em = em;
		}
		
		@Transactional
		public void save(Authority authority) {
			// get session
			Session session = em.unwrap(Session.class);

			// save or update depending if entity has id or not
			session.saveOrUpdate(authority);
		}
}
