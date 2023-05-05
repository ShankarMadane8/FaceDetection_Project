package com.crud.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crud.entity.Student;
import com.crud.service.StudentService;

@Controller
public class MainFaceController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/newface")
	public String face(Model model ,Principal principal) {
		System.out.println("current login user email: "+principal.getName());
		Student student = studentService.getStudent(principal.getName());
		
		String fileName = student.getName();
		System.out.println("current login user name: "+fileName);
		model.addAttribute("fileName",fileName);
		
		return "newface";
		
	}

}
