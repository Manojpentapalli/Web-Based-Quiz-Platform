package com.cdac.project.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cdac.project.dto.LeaderboardEntry;
import com.cdac.project.dto.ResultAnalysisEntry;
import com.cdac.project.model.TestMarks;
import com.cdac.project.repository.TestMarksRepository;

@Service
public class TestMarksService {

    @Autowired
    private TestMarksRepository testMarksRepository;
    
    @Autowired
    private StudentDetailsService studentDetailsService; // Fetch student names

    @Autowired
    private TestDetailsService testDetailsService; // Fetch test names

    public TestMarks saveTestMarks(TestMarks testMarks) {
        // Check if a test record already exists for the given studentId and testId
        if (testMarksRepository.existsByStudentIdAndTestId(testMarks.getStudentId(), testMarks.getTestId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "This student has already attempted this test."
            );
        }
        
        // Save the test marks in the database
        return testMarksRepository.save(testMarks);
    }
    
 // Leaderboard: Get student names with their average marks
    public List<LeaderboardEntry> getLeaderboard() {
        return testMarksRepository.getLeaderboard().stream().map(row -> {
            Integer studentId = (Integer) row[0];
            Double averageMarks = (Double) row[1];

            // Fetch student name
            String studentName = studentDetailsService.getStudentNameById(studentId);

            // Return DTO
            return new LeaderboardEntry(studentName, averageMarks);
        }).collect(Collectors.toList());
    }

    // Result Analysis: Get test names, student names, and marks per test
    public List<ResultAnalysisEntry> getResultAnalysis() {
        return testMarksRepository.getResultAnalysis().stream().map(row -> {
            Integer testId = (Integer) row[0];
            Integer studentId = (Integer) row[1];
            Double marksObtained = (Double) row[2];

            // Fetch test name and student name
            String testName = testDetailsService.getTestNameById(testId);
            String studentName = studentDetailsService.getStudentNameById(studentId);

            // Return DTO
            return new ResultAnalysisEntry(testName, studentName, marksObtained);
        }).collect(Collectors.toList());
    }

    // Progress: Get marks of students as per previous tests
    public Map<String, List<Integer>> getStudentProgress(int studentId) {
        List<Object[]> results = testMarksRepository.findTestProgressByStudentId(studentId);

        Map<String, List<Integer>> progressMap = new LinkedHashMap<>();
        
        for (Object[] row : results) {
            String testTopic = (String) row[0];  // Extract test name
            Integer marks = (Integer) row[1];   // Extract marks
            
            progressMap.putIfAbsent(testTopic, new ArrayList<>());
            progressMap.get(testTopic).add(marks);
        }

        return progressMap;
    }

}
