package com.cdac.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.project.service.TestDetailsService;
import com.cdac.project.service.TestRetrievalService;
import com.cdac.project.service.TestService;
import com.cdac.project.dto.CsvResponse;
import com.cdac.project.model.Question;

@RestController
@RequestMapping("/quiznow/tests")
@CrossOrigin(origins = "http://192.168.5.139:3000")
public class TestController 
{

    private final TestService testService;
    private final TestRetrievalService testRetrievalService;
    @Autowired
    private TestDetailsService TestDetailsService;

    public TestController(TestService testService, TestRetrievalService testRetrievalService) 
    {
        this.testService = testService;
        this.testRetrievalService = testRetrievalService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CsvResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("testId") int testId,
            @RequestParam("testName") String testName,
            @RequestParam("duration") int duration,
            @RequestParam("totalMarks") int totalMarks) 
    {
        try {
            // Step 1: Check if the test ID already exists
            boolean testExists = TestDetailsService.isTestIdExists(testId);

            if (testExists) {
                return ResponseEntity.badRequest().body(new CsvResponse("Test ID already exists. Cannot add duplicate test.", 0, 0, 0, null));
            }

            // Step 2: Save the course details
            TestDetailsService.saveTestDetails(testId, testName, duration, totalMarks);

            // Step 3: Process the CSV file using TestService
            CsvResponse response = testService.processCSV(file);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CsvResponse("Error processing file", 0, 0, 0, null));
        }
    }

    
    @GetMapping("/{topic}")
    public List<Question> getQuestionsByTopic(@PathVariable String topic) 
    {
        return testRetrievalService.retrieveQuestions(topic);
    }
}
