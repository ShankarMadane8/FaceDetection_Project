package com.crud.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.entity.Student;
import com.crud.service.StudentService;

@Controller
@RequestMapping("/special")
public class SpecialController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("dashboard")
	public String specialDashboard(Model model,Principal principal) {
		List<Student> list = (ArrayList<Student>)studentService.getAllStudent();
		Student student = studentService.getStudent(principal.getName());
		model.addAttribute("stu",student);
		model.addAttribute("list",list);
		model.addAttribute("dash_info","Special DashBoard");
		return "dashboard";
	}

}
