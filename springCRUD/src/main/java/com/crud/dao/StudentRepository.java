package com.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.crud.entity.Student;

/**
 * This is Student repository
 * using jparepository
 * 
 * @author Shankar Madane
 * @since 2022
 * @
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public boolean existsByEmailAndPassword(String email,String password);
	
	@Query("select stu from Student stu where stu.email= :email")
	public Student getStudent(@Param("email") String email);

}
