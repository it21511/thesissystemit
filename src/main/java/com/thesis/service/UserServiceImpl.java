package com.thesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.dao.UserDAO;
import com.thesis.entity.ThesisUser;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	
	@Override
	@Transactional
	public ThesisUser getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}
	
	@Override
	@Transactional
	public List<ThesisUser> getUsers(){
		return userDAO.getUsers();
	}

	@Override
	@Transactional
	public void saveUser(ThesisUser theUser) {
		userDAO.saveUser(theUser);
		
	}

	@Override
	@Transactional
	public void deleteUser(String username) {
		userDAO.deleteUser(username);
		
	}
}
