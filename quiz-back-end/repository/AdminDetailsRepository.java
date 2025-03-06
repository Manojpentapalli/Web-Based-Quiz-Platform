package com.cdac.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cdac.project.model.AdminDetails;
import java.util.Optional;

@Repository
public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Integer> {
    
    // Change from findById to findByAdminId (adminId is the primary key)
    Optional<AdminDetails> findByAdminId(Long adminId);
    
    // If you also need to find by email
    Optional<AdminDetails> findByAdminEmail(String adminEmail);
}
