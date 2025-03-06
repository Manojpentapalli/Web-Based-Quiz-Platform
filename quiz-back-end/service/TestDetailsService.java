package com.cdac.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cdac.project.model.TestDetails;
import com.cdac.project.repository.TestDetailsRepository;

@Service
public class TestDetailsService {

    @Autowired
    private TestDetailsRepository testDetailsRepository;

    // Check if testId already exists
    public boolean isTestIdExists(int testId) {
        return testDetailsRepository.existsByTestId(testId);
    }

    // Save test details only if testId does not exist
    public TestDetails saveTestDetails(int testId, String testName, int duration, int totalMarks) {
        if (isTestIdExists(testId)) {
            throw new RuntimeException("Test ID already exists. Cannot add duplicate test.");
        }

        TestDetails testDetails = new TestDetails();
        testDetails.setTestId(testId);
        testDetails.setTestName(testName);
        testDetails.setDuration(duration);
        testDetails.setTotalMarks(totalMarks);
        return testDetailsRepository.save(testDetails);
    }

    // Method to get all test details
    public List<TestDetails> getAllTestDetails() {
        return testDetailsRepository.findAll();
    }
    
    public String getTestNameById(int testId) {
        String testName = testDetailsRepository.findTestNameByTestId(testId);
        return testName != null ? testName : "Unknown Test";
    }
}

