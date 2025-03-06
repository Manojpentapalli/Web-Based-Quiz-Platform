package com.cdac.project.repository;

import com.cdac.project.model.StudentDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Integer> {

    @Query(value = "SELECT student_name FROM student_details WHERE student_id = ?1", nativeQuery = true)
    String findStudentNameById(Integer studentId);
    
    // Change from findById to findByStudentId (studentId is the primary key)
    Optional<StudentDetails> findByStudentId(Long Id);
	
 // If you also need to find by email
    Optional<StudentDetails> findByEmail(String email);	
}
