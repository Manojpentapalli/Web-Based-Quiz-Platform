package com.cdac.project.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cdac.project.dto.CsvResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TestService 
{
    private final JdbcTemplate jdbcTemplate;

    public TestService(JdbcTemplate jdbcTemplate) 
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CsvResponse processCSV(MultipartFile file) throws Exception 
    {
        List<String> skippedLines = new ArrayList<>();
        int validCount = 0, invalidCount = 0;

        // Extracting topic from filename (supports topic-test-number.txt)
        String topic = extractTopicFromFilename(file.getOriginalFilename());
        if (topic == null) 
        {
            return new CsvResponse("Invalid filename format. Expected: topic-test-number.txt", 0, 0, 0, null);
        }

        // Creating table dynamically if not exists
        createTableIfNotExists(topic);

        // **Truncate the table before inserting new questions**
        truncateTableIfExists(topic);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] values = parseCSVLine(line);

                if (validateMCQFormat(values)) 
                {
                    insertIntoTable(topic, values);
                    validCount++;
                } else 
                {
                    skippedLines.add("Invalid: " + line);
                    invalidCount++;
                }
            }
        }

        return new CsvResponse(
            "File processed successfully and stored in '" + topic + "' table!",
            validCount, invalidCount, 0, skippedLines
        );
    }

    private String extractTopicFromFilename(String filename) 
    {
        // Matching pattern like "topic-test-number.txt" (e.g., "sql-test-1.txt")
        if (filename == null || !Pattern.matches("([a-zA-Z]+)-test-\\d+\\.txt", filename)) 
        {
            return null;
        }
        return filename.split("-test-")[0];  // Extracting "topic"
    }

    private void createTableIfNotExists(String topic) 
    {
        String sql = "CREATE TABLE IF NOT EXISTS " + topic + " ("
                + "id SERIAL PRIMARY KEY, "
                + "question_no INT, "
                + "question TEXT, "
                + "option1 TEXT, "
                + "option2 TEXT, "
                + "option3 TEXT, "
                + "option4 TEXT, "
                + "correct_option TEXT)";
        jdbcTemplate.execute(sql);
    }

    private void truncateTableIfExists(String topic) 
    {
        String sql = "TRUNCATE TABLE " + topic;
        jdbcTemplate.execute(sql);
    }

    private void insertIntoTable(String topic, String[] values) 
    {
        String sql = "INSERT INTO " + topic + " (question_no, question, option1, option2, option3, option4, correct_option) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    private boolean validateMCQFormat(String[] values) 
    {
        return values.length == 7 && values[0].matches("\\d+");
    }

    private String[] parseCSVLine(String line) 
    {
        line = line.trim();
        if (line.startsWith("\"") && line.endsWith("\"")) 
        {
            line = line.substring(1, line.length() - 1);
        }
        return line.split("\",\"");
    }
}
