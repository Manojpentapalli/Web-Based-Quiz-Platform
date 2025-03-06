package com.cdac.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int questionNumber;
    @Column(columnDefinition = "TEXT")
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
}
