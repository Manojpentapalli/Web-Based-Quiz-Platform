package com.cdac.project.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test_marks")
@Getter
@Setter
public class TestMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("test_id")  // Map JSON field to Java field
    @Column(name = "test_id")
    private int testId;

    @JsonProperty("student_id")
    @Column(name = "student_id")
    private int studentId;

    @JsonProperty("marks_obtained")
    @Column(name = "marks_obtained")
    private int marksObtained;
}

