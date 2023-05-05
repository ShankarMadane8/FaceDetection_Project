package com.crud.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crud.dao.StudentRepository;
import com.crud.entity.Student;
import com.crud.service.StudentService;

@Controller
public class MainController {
    
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping("face")
	public String faceDetecation() {
		return "home";
	}
	
	

	
	
	//------------------------- Signup --------------------------------------------
	
	@GetMapping("signup")
	public ModelAndView signup(Model model) {
		model.addAttribute("student",new Student());
		model.addAttribute("url","signup");
		model.addAttribute("title","Signup Form");
		return new ModelAndView("signupForm");
	}
	
	
	@PostMapping("signup")
	public String signupPost(@ModelAttribute Student stu,Model model,HttpServletRequest req){
	    if(stu.getEmail().equals("") || stu.getPassword().equals("")) {
	    	model.addAttribute("msg","Enter Complete Information !!!");
	    }
		
	    else if(stu.getPassword().equals(req.getParameter("confirm_password"))) {
			try {
				stu.setPassword(passwordEncoder.encode(stu.getPassword()));
				studentService.insertStudent(stu);
				model.addAttribute("msg","Congratulations "+stu.getName()+", your account has been successfully created.");
			} catch (Exception e) {
				model.addAttribute("msg","Already Registered User?");
			}
			
			
		}else {
			model.addAttribute("msg","passwords do not match.");
		}
		
		model.addAttribute("url","signup");
		model.addAttribute("student",new Student());
		model.addAttribute("title","Signup Form");
		return "signupForm";
	}
	 
	//----------------------------- Login ----------------------------------------
    
	@GetMapping("login")
	public String loginGet(Model model) {
		
		return "loginForm";
	}
	

	
	@PostMapping("login")
	public String loginPost(@ModelAttribute Student stu, Model model) {
		
		boolean flag = studentService.existsEmailAndPassword(stu.getEmail(), stu.getPassword());
		if (flag) {
			return"redirect:/normal/dashboard";
		}else {
			model.addAttribute("msg","incorrect email and password !!!");
			return "loginForm";
		}
		
		
		
	}
	
	//------------------------------ DashBoard -----------------------------------------
	
	@GetMapping("/normal/dashboard")
	public String dashboard(Model model,Principal principal) {
	
		List<Student> list = (ArrayList<Student>)studentService.getAllStudent();
		model.addAttribute("list",list);
		System.out.println(principal.getName());
		System.out.println(principal.toString());
		Student student = studentService.getStudent(principal.getName());
		model.addAttribute("stu",student);
		model.addAttribute("dash_info","Normal DashBoard");
		return "dashboard";
	}
	
	//------------------------
	@GetMapping("/checkDashboard")
	public String checkDashboard(Model model,Principal principal) {
	
	
		Student student = studentService.getStudent(principal.getName());
		String userType = student.getUserType();
		if(userType.equals("admin"))
			return "redirect:/admin/dashboard";
		else if(userType.equals("normal"))
			return "redirect:/normal/dashboard";
		else if(userType.equals("special"))
			return "redirect:/special/dashboard";
		else	
		    return "dashboard";
	}
	
	
	//-------------------------------- Delete ---------------------------------------
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		studentService.DeleteStudent(id);
		System.out.println("delete Successfully. !!!");
		return "redirect:/normal/dashboard";
		
	 }
	
	//---------------------------------- update -----------------------------------------------
	@GetMapping("update/{id}")
	public String updateGet(@PathVariable int id,Model model) {
		Student student = studentService.getStudent(id);
		model.addAttribute("student",student);
		model.addAttribute("title","update Form");
		model.addAttribute("url","/update/"+Integer.toString(id));
		return "signupForm";		
	}
	
	@PostMapping("update/{id}")
	public String updatePost(@ModelAttribute Student stu,@PathVariable int id,Model model,HttpServletRequest req) {
		
		if(stu.getPassword().equals(req.getParameter("confirm_password"))) {
			stu.setPassword(passwordEncoder.encode(stu.getPassword()));
			studentService.UpdateStudent(stu, id);
			model.addAttribute("msg","update Successfully.!!!");
		}else {
			model.addAttribute("msg","passwords do not match.");
		}
		
		Student student = studentService.getStudent(id);
		//model.addAttribute("url","/update/"+Integer.toString(id));
		model.addAttribute("student",student);
		model.addAttribute("title","update Form");
		return "signupForm";		
	}
	
	//-------------------------------------------------------------------------------------------
}
