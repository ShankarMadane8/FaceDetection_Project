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
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("dashboard")
	public String adminDashboard(Model model,Principal principal) {
		List<Student> list = (ArrayList<Student>)studentService.getAllStudent();
		Student student = studentService.getStudent(principal.getName());
		model.addAttribute("stu",student);
		model.addAttribute("list",list);
		model.addAttribute("dash_info","Admin DashBoard");
		return "dashboard";
	}

}


//"C:\Users\madan\Downloads\opencv\sources\data\haarcascades\haarcascade_frontalface_alt_tree.xml"
//"C:\Users\madan\Downloads\opencv\sources\data\haarcascades\haarcascade_frontalface_alt2.xml"
//"C:\Users\madan\Downloads\opencv\sources\data\haarcascades\haarcascade_frontalface_alt.xml"
//

//"C:\Users\madan\Downloads\opencv\sources\data\haarcascades\haarcascade_eye.xml"