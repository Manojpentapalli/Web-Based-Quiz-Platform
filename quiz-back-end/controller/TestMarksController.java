package com.cdac.project.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cdac.project.dto.LeaderboardEntry;
import com.cdac.project.dto.ResultAnalysisEntry;
import com.cdac.project.model.TestMarks;
import com.cdac.project.repository.TestMarksRepository;
import com.cdac.project.service.StudentDetailsService;
import com.cdac.project.service.TestDetailsService;
import com.cdac.project.service.TestMarksService;

@RestController
@RequestMapping("/quiznow/testmarks")

public class TestMarksController {

    @Autowired
    private TestMarksService testMarksService;
    
    @Autowired
    private TestMarksRepository testMarksRepository;

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Autowired
    private TestDetailsService testDetailsService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitTestMarks(@RequestBody TestMarks testMarks) {
        System.out.println("Received: " + testMarks);
        
        try {
            testMarksService.saveTestMarks(testMarks);
            return ResponseEntity.ok("Test marks saved successfully.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving test marks");
        }
    }
    
    @GetMapping("/leaderboard")
    // Leaderboard: Get student names with their average marks
    public List<LeaderboardEntry> getLeaderboard() {
        return testMarksRepository.getLeaderboard().stream().map(row -> {
            Integer studentId = (Integer) row[0];
            Double averageMarks = (Double) row[1];

            String studentName = studentDetailsService.getStudentNameById(studentId);
            return new LeaderboardEntry(studentName, averageMarks);
        }).collect(Collectors.toList());
    }

    @GetMapping("/resultanalysis")
    // Result Analysis: Get test names, student names, and marks per test
    public List<ResultAnalysisEntry> getResultAnalysis() {
        return testMarksRepository.getResultAnalysis().stream().map(row -> {
            Integer testId = (Integer) row[0];
            Integer studentId = (Integer) row[1];

            Number marksObtainedNumber = (Number) row[2]; // Safe conversion
            Double marksObtained = marksObtainedNumber.doubleValue();

            String testName = testDetailsService.getTestNameById(testId);
            String studentName = studentDetailsService.getStudentNameById(studentId);
            return new ResultAnalysisEntry(testName, studentName, marksObtained);
        }).collect(Collectors.toList());
    }


    @GetMapping("/progress/{studentId}")
    // Progress: Get marks of students as per previous tests
    public ResponseEntity<Map<String, List<Double>>> getStudentProgress(@PathVariable int studentId) {
        // Fetch the progress data from the service layer
        Map<String, List<Integer>> progress = testMarksService.getStudentProgress(studentId);

        // Convert List<Integer> to List<Double> (if required)
        Map<String, List<Double>> progressDouble = progress.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                              .map(Integer::doubleValue)
                              .collect(Collectors.toList())
            ));

        return ResponseEntity.ok(progressDouble);
    }

}


