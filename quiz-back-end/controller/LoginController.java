package com.cdac.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.project.dto.UserProfileUpdateRequest;
import com.cdac.project.model.AdminDetails;
import com.cdac.project.model.StudentDetails;
import com.cdac.project.service.AdminDetailsService;
import com.cdac.project.service.StudentDetailsService;

import java.util.Optional;
@RestController
@RequestMapping("/quiznow")
public class LoginController {

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Autowired
    private StudentDetailsService studentDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (email.endsWith("cdac@gmail.com")) {
            Optional<AdminDetails> optionalAdmin = adminDetailsService.loginByEmail(email);
            if (optionalAdmin.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse((Integer) null, "Admin not found", null));
            }

            AdminDetails dbAdmin = optionalAdmin.get();
            if (password.equals(dbAdmin.getAdminPassword())) {
                return ResponseEntity.ok(new LoginResponse(dbAdmin.getAdminId(), dbAdmin.getAdminName(), "Admin"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse((Integer) null, "Invalid Credentials", null));
            }
        } else {
            Optional<StudentDetails> optionalStudent = studentDetailsService.loginByemail(email);
            if (optionalStudent.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse((Integer) null, "Student not found", null));
            }

            StudentDetails dbStudent = optionalStudent.get();
            if (password.equals(dbStudent.getStudentPassword())) {
                return ResponseEntity.ok(new LoginResponse(dbStudent.getStudentId(), dbStudent.getStudentName(), "Student"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse((Integer) null, "Invalid Credentials", null));
            }
        }
    }

    
    // ✅ Get Student details by ID
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<StudentDetails> student = studentDetailsService.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Student not found with ID: " + id);
        }
    }

    // ✅ Get Admin details by ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        Optional<AdminDetails> admin = adminDetailsService.findById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Admin not found with ID: " + id);
        }
    }


    // ✅ Update User Profile (Admin or Student)
    @PutMapping("/update-profile/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody UserProfileUpdateRequest request) {
        // Validate input
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Student name cannot be blank");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be blank");
        }
        
        // Check if user exists
        Optional<StudentDetails> studentOpt = studentDetailsService.findById(id);
        if (studentOpt.isPresent()) {
            StudentDetails student = studentOpt.get();
            student.setStudentName(request.getName());
            student.setStudentEmail(request.getEmail());
            student.setStudentPhoneNumber(request.getPhoneNumber());
            studentDetailsService.save(student);
            return ResponseEntity.ok("Student profile updated successfully");
        }

        Optional<AdminDetails> adminOpt = adminDetailsService.findById(id);
        if (adminOpt.isPresent()) {
            AdminDetails admin = adminOpt.get();
            admin.setAdminName(request.getName());
            admin.setEmail(request.getEmail());
            admin.setAdminPhoneNumber(request.getPhoneNumber());
            adminDetailsService.save(admin);
            return ResponseEntity.ok("Admin profile updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
    }

}

// DTO for login request
class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

//DTO for update profile request
class UpdateProfileRequest {
 private String role; // "admin" or "student"
 private String oldPassword;
 private String newName;
 private String newPassword;

 public String getRole() {
     return role;
 }

 public String getOldPassword() {
     return oldPassword;
 }

 public String getNewName() {
     return newName;
 }

 public String getNewPassword() {
     return newPassword;
 }

}

//✅ DTO for login response
class LoginResponse {
 private int id;
 private String name;
 private String role;

 public LoginResponse(int id, String name, String role) {
     this.id = id;
     this.name = name;
     this.role = role;
 }

 public int getId() {
     return id;
 }

 public String getName() {
     return name;
 }
 
 public String getRole() {
	 return role;
 }
}