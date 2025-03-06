package com.cdac.project.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "student_details")
public class StudentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", initialValue = 1001, allocationSize = 1)
    private int studentId;
    

    @NotBlank(message = "Student name cannot be blank")
    @Column(nullable = false, length = 255)
    private String studentName;
    

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(name = "student_email" , nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String studentPassword;
    
    private String studentPhoneNumber;

    public StudentDetails() {
		super();
	}

    public StudentDetails(int studentId, @NotBlank(message = "Student name cannot be blank") String studentName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String studentEmail,
			@NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password must be at least 6 characters") String studentPassword,
			String studentPhoneNumber) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.email = studentEmail;
		this.studentPassword = studentPassword;
		this.studentPhoneNumber = studentPhoneNumber;
	}


	public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return email;
    }

    public void setStudentEmail(String studentEmail) {
        this.email = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
    	this.studentPassword = studentPassword;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

}