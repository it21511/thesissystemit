package com.thesis.dao;

import java.util.List;

import com.thesis.entity.ThesisUser;

public interface UserDAO {
	
	public List<ThesisUser> getUsers();
	
	public ThesisUser getUserByUsername(String username);

	public void saveUser(ThesisUser theUser);

	public void deleteUser(String username);

}
