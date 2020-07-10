package com.thesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.dao.StudentDAO;
import com.thesis.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {
	
	//need to inject student dao
	@Autowired
	private StudentDAO studentDAO;
	
	
	@Override
	@Transactional
	public List<Student> getStudents() {
		return studentDAO.getStudents();
	}
	
	@Override
	@Transactional
	public List<Student> getStudentsByDep(int dep) {
		return studentDAO.getStudentsByDep(dep);
	}
	
	@Override
	@Transactional
	public Student getStudentByUsername(String username) {
		return studentDAO.getStudentByUsername(username);
	}


	@Override
	@Transactional
	public void saveStudent(Student theStudent) {
		
		studentDAO.saveStudent(theStudent);
	}


	@Override
	@Transactional
	public Student getStudent(int theId) {
		
		return studentDAO.getStudent(theId);
	}


	@Override
	@Transactional
	public void deleteStudent(int theId) {
		
		studentDAO.deleteStudent(theId);
		
	}

}
