package com.thesis.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thesis.entity.ThesisUser;
import com.thesis.entity.Student;
import com.thesis.service.StudentService;
import com.thesis.service.UserService;

@Controller
@RequestMapping("/user")
@Transactional
public class UserController {
	
	@Autowired
	private UserDetailsManager userDetailsManager;
	
	//need to inject our student service
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;	
	
	@GetMapping("/list")
	public String listUsers(Model theModel) {
		
		List<ThesisUser> theUsers = null;
		
		
		theUsers = userService.getUsers();
	
		//add the students to the model
		theModel.addAttribute("users" , theUsers);
						
		return "list-users";
		
	}
		
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create model attribute to bind form data
		ThesisUser theUser = new ThesisUser();
		
		theModel.addAttribute("user",theUser);
		theModel.addAttribute("isUpdate", "0");
		
		return "user-form";
	}

	@PostMapping("/saveUser")
	public String saveUser(@Valid @ModelAttribute("user") ThesisUser thesisUser, 
			@RequestParam("authority") String authority,
			BindingResult theBindingResult, 
			Model theModel) {
		
		//save the student using our service
		
		ThesisUser findUser = userService.getUserByUsername(thesisUser.getUsername());
		if(findUser != null) {
			return "redirect:/user/list?alreadyExists=1";
		}
		
		final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encodedPassword = passwordEncoder.encode(thesisUser.getPassword());

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authority);

        // create user object (from Spring Security framework)
        User tempUser = new User(thesisUser.getUsername(), "{bcrypt}" + encodedPassword, thesisUser.getEnabled()==1?true:false, true, true, true, authorities);

        // save user in the database
        userDetailsManager.createUser(tempUser);
        
        
        //Department department = new Department(thesisUser.getUsername(), dep);
        
        //Session currentSession = sessionFactory.getCurrentSession();
        //currentSession.save(department);
		
		return "redirect:/user/list";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("user") ThesisUser theUser, 
			@RequestParam("authority") String authority,
			BindingResult theBindingResult, 
			Model theModel) {
				
		//theUser = userService.getUserByUsername(theUser.getUsername());
		
		if(theUser.getPassword().startsWith("{bcrypt}")) {
			
		}
		else {
			final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			String encodedPassword = passwordEncoder.encode(theUser.getPassword());

	        // prepend the encoding algorithm id
			theUser.setPassword("{bcrypt}" + encodedPassword);
		}
		
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authority);

        // create user object (from Spring Security framework)
        User tempUser = new User(theUser.getUsername(), theUser.getPassword(), (theUser.getEnabled()==1)?true:false, true, true, true, authorities);

        // save user in the database
        userDetailsManager.updateUser(tempUser);

		
		//userService.saveUser(theUser);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("username") String username, Model theModel) {
		
		//get the student from  our service
		ThesisUser theUser = userService.getUserByUsername(username);
								
		UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
				
		//set student as a model attribute to pre-populate the form
		theModel.addAttribute("user",theUser);
		theModel.addAttribute("authority",userDetails.getAuthorities().toArray()[0].toString());
		theModel.addAttribute("isUpdate", "1");
		
		//send over to our form
		return "user-form";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("username") String username) {
		
		UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
		
		if(userDetails.getAuthorities().toArray()[0].toString().equals("ROLE_STUDENT")) {
			Student theStudent = studentService.getStudentByUsername(username);
			studentService.deleteStudent(theStudent.getId());
		}
		
		userService.deleteUser(username);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(Model theModel) {
								
		return "access-denied";
		
	}
	
}
