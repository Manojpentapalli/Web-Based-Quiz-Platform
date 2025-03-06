package com.cdac.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.project.repository.AdminDetailsRepository;
import com.cdac.project.repository.StudentDetailsRepository;
import com.cdac.project.model.AdminDetails;
import com.cdac.project.model.StudentDetails;

@RestController
@RequestMapping("/quiznow")
public class RegistrationController {
    @Autowired
    private AdminDetailsRepository adminRepo;

    @Autowired
    private StudentDetailsRepository studentRepo;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String,String> registrationData){
         String email = registrationData.get("email");
         if(email == null || email.isEmpty()){
             return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
         }
        String name = registrationData.get("name");
        if(name == null || name.isEmpty()){
            return new ResponseEntity<>("Name is required", HttpStatus.BAD_REQUEST);
        }

        String password = registrationData.get("password");
        if(password == null || password.isEmpty()){
            return new ResponseEntity<>("password is required", HttpStatus.BAD_REQUEST);
        }

        String phoneNumber = registrationData.get("phoneNumber");
        if(phoneNumber == null || phoneNumber.isEmpty()){
            return new ResponseEntity<>("phone number is required", HttpStatus.BAD_REQUEST);
        }

        if (email.endsWith("cdac@gmail.com")){
            AdminDetails adminDetails = new AdminDetails();
            adminDetails.setAdminName(name);
            adminDetails.setEmail(email);
            adminDetails.setAdminPassword(password);
            adminDetails.setAdminPhoneNumber(phoneNumber);
            adminRepo.save(adminDetails);
            return new ResponseEntity<>("Admin Registered Successfully", HttpStatus.OK);
        }
        else{
           StudentDetails studentDetails = new StudentDetails();
           studentDetails.setStudentName(name);
           studentDetails.setStudentEmail(email);
           studentDetails.setStudentPassword(password);
           studentDetails.setStudentPhoneNumber(phoneNumber);
           studentRepo.save(studentDetails);
            return new ResponseEntity<>("Student Registered Successfully", HttpStatus.OK);
        }

    }

}

