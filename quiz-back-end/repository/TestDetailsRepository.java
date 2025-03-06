package com.cdac.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.project.model.TestDetails;

import org.springframework.stereotype.Repository;

@Repository
public interface TestDetailsRepository extends JpaRepository<TestDetails, Long> {

	@Query("SELECT td.testName FROM TestDetails td WHERE td.testId = :testId")
	String findTestNameByTestId(@Param("testId") int testId);

	boolean existsByTestId(int testId);
}


