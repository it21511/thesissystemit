package com.thesis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.entity.ThesisUser;
import com.thesis.entity.Student;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDetailsManager userDetailsManager;

	@Override
	public List<ThesisUser> getUsers() {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		
		Query<ThesisUser> theQuery = currentSession.createQuery("from ThesisUser order by username " , 
															ThesisUser.class);
					
		//execute query and get result list
		List<ThesisUser> users = theQuery.getResultList();
		
		//return the results
		return users;
	}
	
	@Override
	public ThesisUser getUserByUsername(String username) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		
		Query<ThesisUser> theQuery = currentSession.createQuery("from ThesisUser WHERE username =:u order by username" , 
															ThesisUser.class);
		
		theQuery.setParameter("u", username);
			
		ThesisUser user = null;
		
		//execute query and get result list
		if(theQuery.getResultList().size() != 0)
			user = theQuery.getSingleResult();
		
		//return the results
		return user;
	}

	@Override
	public void saveUser(ThesisUser theUser) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save/update the student
		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public void deleteUser(String username) {
		
	//	Session currentSession = sessionFactory.getCurrentSession();
				
		userDetailsManager.deleteUser(username);
				
	}

}
