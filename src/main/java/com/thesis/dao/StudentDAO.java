package com.thesis.dao;

import java.util.List;

import com.thesis.entity.Student;

public interface StudentDAO {
	
	public List<Student> getStudents();
	
	public List<Student> getStudentsByDep(int dep);
	
	public Student getStudentByUsername(String username);

	public void saveStudent(Student theStudent);

	public Student getStudent(int theId);

	public void deleteStudent(int theId);
}
