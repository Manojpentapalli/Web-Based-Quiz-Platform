package com.cdac.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cdac.project.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> 
{
	Optional<Test> findByQuestion(String question);
}
