package com.cdac.project.dto;

public class LeaderboardEntry {
    private String studentName;
    private Double averageMarks;

    public LeaderboardEntry(String studentName, Double averageMarks) {
        this.studentName = studentName;
        this.averageMarks = averageMarks;
    }

    public String getStudentName() { return studentName; }
    public Double getAverageMarks() { return averageMarks; }
}

