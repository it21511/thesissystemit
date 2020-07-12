package com.thesis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thesis.entity.Student;
import com.thesis.service.StudentService;
import com.thesis.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private UserDetailsManager userDetailsManager;
	
	//need to inject our student service
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserService userService;
		
	@GetMapping("/list")
	public String listStudents(Model theModel) {
		

		List<Student> theStudents = null;
		
		theStudents = studentService.getStudents();

		//add the students to the model
		theModel.addAttribute("students" , theStudents);
						
		return "list-students";
		
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create model attribute to bind form data
		Student theStudent = new Student();
		
		theModel.addAttribute("student",theStudent);
		theModel.addAttribute("isUpdate", "0");
		
		return "student-form";
	}

	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute("student") Student theStudent) {
		
		//save the student using our service
		
		final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encodedPassword = passwordEncoder.encode(theStudent.getPassword());
		
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_STUDENT");

		 // create user object (from Spring Security framework)
        User tempUser = new User(theStudent.getUsername(), "{bcrypt}" + encodedPassword, true, true, true, true, authorities);

        // save user in the database
        userDetailsManager.createUser(tempUser);
        
        // prepend the encoding algorithm id
        theStudent.setPassword("{bcrypt}" + encodedPassword);
		
		studentService.saveStudent(theStudent);
		
		return "redirect:/student/list";
	}
	
	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute("student") Student theStudent) {
		
		if(theStudent.getPassword().startsWith("{bcrypt}")) {
			
		}
		else {
			final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			String encodedPassword = passwordEncoder.encode(theStudent.getPassword());

	        // prepend the encoding algorithm id
	        theStudent.setPassword("{bcrypt}" + encodedPassword);
		}
		
		studentService.saveStudent(theStudent);
		
		return "redirect:/student/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {
		
		//get the student from  our service
		Student theStudent = studentService.getStudent(theId);
		
		//set student as a model attribute to pre-populate the form
		theModel.addAttribute("student",theStudent);
		theModel.addAttribute("isUpdate", "1");
		
		//send over to our form
		return "student-form";
	}
	
	@GetMapping("/thesis")
	public String thesis(Model theModel) {		
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		Student theStudent = studentService.getStudentByUsername(username);
				
		//set student as a model attribute to pre-populate the form
		theModel.addAttribute("email",theStudent.getEmail());
		theModel.addAttribute("is_undergraduate", theStudent.getIsUndergraduate());
		
		//send over to our form
		return "list-thesis";
	}
	
	@GetMapping("/thesis/save")
	public String thesis_save(@RequestParam("thesisId") int theId, Model theModel) {
				
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		//get the student from  our service
		//Student theStudent = studentService.getStudent(student.getId());
		
		Student theStudent = studentService.getStudentByUsername(username);
		
		//set student as a model attribute to pre-populate the form
		theModel.addAttribute("student",theStudent);
		theModel.addAttribute("isUpdate", "1");
		
		//send over to our form
		return "redirect:/student/thesis";
	}
	
	@GetMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int theId) {
		
		Student theStudent = studentService.getStudent(theId);
		
		userService.deleteUser(theStudent.getUsername());
		
		//delete the student
		studentService.deleteStudent(theId);
		
		
		return "redirect:/student/list";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(Model theModel) {
								
		return "access-denied";
		
	}
	
}


