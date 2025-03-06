package com.cdac.project.service;

import com.cdac.project.model.StudentDetails;
import com.cdac.project.repository.StudentDetailsRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsService {

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

	public Optional<StudentDetails> loginByemail(String email){
		return studentDetailsRepository.findByEmail(email);
	}
	

    public StudentDetails save(StudentDetails studentDetails) {
        return studentDetailsRepository.save(studentDetails);
    }
    
    public String getStudentNameById(Integer studentId) {
        return studentDetailsRepository.findStudentNameById(studentId);
    }
    
    public Optional<StudentDetails> findById(Long studentId) {
        return studentDetailsRepository.findByStudentId(studentId);
    }

    public void updateStudent(StudentDetails student) {
        studentDetailsRepository.save(student);
    }
}
