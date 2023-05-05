package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.dao.StudentRepository;
import com.crud.entity.Student;

@Service
public class StudentServiceImp implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public String insertStudent(Student stu) {
		 studentRepository.save(stu);
		return "save student..";
	}

	@Override
	public String DeleteStudent(int id) {
		studentRepository.deleteById(id);
		return "delete Student";
		
	}

	@Override
	public String UpdateStudent(Student stu,int id) {
		stu.setId(id);
		studentRepository.save(stu);
		return null;
	}

	@Override
	public List<Student> getAllStudent() {
		List<Student> list = studentRepository.findAll();
		return list;
	}

	@Override
	public Student getStudent(int id) {
		Optional<Student> optional = studentRepository.findById(id);
		Student student = optional.get();
		return student;
	}

	@Override
	public boolean existsEmailAndPassword(String email, String password) {
		boolean b = studentRepository.existsByEmailAndPassword(email, password);
		return b;
	}

	@Override
	public Student getStudent(String email) {
		Student student = studentRepository.getStudent(email);
		return student;
	}


	
	
	
}
