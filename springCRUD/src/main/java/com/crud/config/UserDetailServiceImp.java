package com.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crud.dao.StudentRepository;
import com.crud.entity.Student;

public class UserDetailServiceImp implements UserDetailsService {
    
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Student student = studentRepository.getStudent(email);
		
		if(student==null) {
			throw new UsernameNotFoundException("could not found Student");
		}
		CustomeUserDetails customeUserDetails=new CustomeUserDetails(student);
		
		
		return customeUserDetails;
	}

}
