package com.crud.service;

import java.util.List;

import com.crud.entity.Student;



public interface StudentService {
	
	public String insertStudent(Student stu);
	public String DeleteStudent(int id);
	public String UpdateStudent(Student stu,int id);
	public List<Student> getAllStudent();
	public Student getStudent(int id);
	public boolean existsEmailAndPassword(String email,String password);
	public Student getStudent(String email);
	
}
