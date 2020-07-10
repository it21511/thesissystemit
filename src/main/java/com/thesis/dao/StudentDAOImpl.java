package com.thesis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.entity.Student;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Student> getStudents() {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		
		Query<Student> theQuery = currentSession.createQuery("from Student order by lastName" , Student.class);
					
		//execute query and get result list
		List<Student> students = theQuery.getResultList();
		
		//return the results
		return students;
	}
	
	@Override
	public List<Student> getStudentsByDep(int dep) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		
		Query<Student> theQuery = currentSession.createQuery("from Student WHERE department = :d order by lastName" , 
															Student.class);
		
		theQuery.setParameter("d", dep);
			
		//execute query and get result list
		List<Student> students = theQuery.getResultList();
		
		//return the results
		return students;
	}
	
	@Override
	public Student getStudentByUsername(String username) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query ... sort by last name
		
		Query<Student> theQuery = currentSession.createQuery("from Student WHERE username = :u" , 
															Student.class);
		
		theQuery.setParameter("u", username);
		
		if(theQuery.getResultList().size() == 0) {
			return null;
		}
		
		//execute query and get result list
		Student student = theQuery.getSingleResult();
		
		//return the results
		return student;
	}

	@Override
	public void saveStudent(Student theStudent) {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save/update the student
		currentSession.saveOrUpdate(theStudent);
		
	}

	@Override
	public Student getStudent(int theId) {
		
		//get the current hibernate session
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		//now retrieve/read from database using the primary key
		Student theStudent = currentSession.get(Student.class, theId);
		
		return theStudent;
	}

	@Override
	public void deleteStudent(int theId) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//delete object with primary key
		Query theQuery = currentSession.createQuery("delete from Student where id=:studentId");
		
		theQuery.setParameter("studentId", theId);
		theQuery.executeUpdate();
	}

}
