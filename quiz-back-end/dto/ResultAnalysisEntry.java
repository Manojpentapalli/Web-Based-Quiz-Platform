package com.cdac.project.dto;

public class ResultAnalysisEntry {
    private String testName;
    private String studentName;
    private Double marksObtained;

    public ResultAnalysisEntry(String testName, String studentName, Double marksObtained) {
        this.testName = testName;
        this.studentName = studentName;
        this.marksObtained = marksObtained;
    }

    public String getTestName() { return testName; }
    public String getStudentName() { return studentName; }
    public Double getMarksObtained() { return marksObtained; }
}

