package com.cdac.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption; // This will be hidden when sending to frontend

    public Question(String question, String option1, String option2, String option3, String option4, String correctOption) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }

	@Override
	public String toString() {
		return "Question [question=" + question + ", option1=" + option1 + ", option2=" + option2 + ", option3="
				+ option3 + ", option4=" + option4 + "]";
	}
    
}
