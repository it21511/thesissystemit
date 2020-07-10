 package com.thesis.service;

import java.util.List;

import com.thesis.entity.ThesisUser;

public interface UserService {
	
	public List<ThesisUser> getUsers();
	
	public ThesisUser getUserByUsername(String username);


	public void saveUser(ThesisUser theUser);

	public void deleteUser(String username);
}
