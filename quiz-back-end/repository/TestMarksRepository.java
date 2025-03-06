package com.cdac.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cdac.project.model.TestMarks;

@Repository
public interface TestMarksRepository extends JpaRepository<TestMarks, Long> {

    // Custom query to find a record by studentId and testId
    boolean existsByStudentIdAndTestId(int studentId, int testId);
    

    // Leaderboard: Get the average marks per student across all tests
    @Query("SELECT tm.studentId, AVG(tm.marksObtained) " +
           "FROM TestMarks tm GROUP BY tm.studentId ORDER BY AVG(tm.marksObtained) DESC LIMIT 10")
    List<Object[]> getLeaderboard();

    // Result Analysis: Get marks per test, sorted by highest scores
    @Query("SELECT tm.testId, tm.studentId, tm.marksObtained " +
           "FROM TestMarks tm ORDER BY tm.testId, tm.marksObtained DESC")
    List<Object[]> getResultAnalysis();
    
    // Progress: Get marks of students as per previous tests
    @Query("SELECT td.testName, tm.marksObtained " +
    	       "FROM TestMarks tm " +
    	       "JOIN TestDetails td ON tm.testId = td.testId " +
    	       "WHERE tm.studentId = :studentId " +
    	       "ORDER BY td.testName, tm.testId")
    	List<Object[]> findTestProgressByStudentId(@Param("studentId") int studentId);

}
