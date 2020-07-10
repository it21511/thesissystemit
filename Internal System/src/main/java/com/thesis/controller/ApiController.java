package com.thesis.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thesis.dao.UserDAO;
import com.thesis.entity.ThesisUser;
import com.thesis.entity.Student;
import com.thesis.service.StudentService;
import com.thesis.service.UserService;

@RestController
@Transactional
public class ApiController {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	StudentService studentService;
		
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
						
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public @ResponseBody String checkCredentials(HttpServletRequest req, @RequestParam("username") String username, @RequestParam String password) throws FileNotFoundException, IOException {
		//Check credentials

		Student student = studentService.getStudentByUsername(username);
		
		if (student != null){
        	final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        	String dbPassword = (student.getPassword());
        	dbPassword =  dbPassword.substring(8,dbPassword.length());
            if (pwEncoder.matches(password, dbPassword)) {
            	if(student.getEnabled() == 1) {
    				return "1";
    			}
                return "-1";
            }
            else{
                return "0";
            }
        }
        else {
        	return "0";
        }
	}
	
		
}
 