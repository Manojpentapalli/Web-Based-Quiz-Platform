package com.cdac.project.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cdac.project.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestRetrievalService 
{

    private final JdbcTemplate jdbcTemplate;

    public TestRetrievalService(JdbcTemplate jdbcTemplate) 
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Question> retrieveQuestions(String topic) {
        String sql = "SELECT question, option1, option2, option3, option4, correct_option "
                   + "FROM `" + topic + "` ORDER BY RAND()"; // Backticks (`) to avoid SQL error

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Question> questionList = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Question question = new Question(
                (String) row.get("question"),
                (String) row.get("option1"),
                (String) row.get("option2"),
                (String) row.get("option3"),
                (String) row.get("option4"),
                (String) row.get("correct_option") // Stored but not returned
            );
            questionList.add(question);
        }

        return questionList;
    }

}
