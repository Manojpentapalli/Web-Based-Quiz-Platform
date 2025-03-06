package com.cdac.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.cdac.project.model.TestDetails;
import com.cdac.project.service.TestDetailsService;

@RestController
@RequestMapping("/quiznow/testdetails")
public class TestDetailsController {

    @Autowired
    private TestDetailsService testDetailsService;

    // Endpoint to get all test details
    @GetMapping("/getTestDetails")
    public ResponseEntity<List<TestDetails>> getAllTestDetails() {
        List<TestDetails> testDetailsList = testDetailsService.getAllTestDetails();
        return ResponseEntity.ok(testDetailsList);
    }
}

