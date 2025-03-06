package com.cdac.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.project.model.AdminDetails;
import com.cdac.project.repository.AdminDetailsRepository;

@Service
public class AdminDetailsService {

	@Autowired
	public AdminDetailsRepository adminDetailsRepository;

	public Optional<AdminDetails> loginByEmail(String email) {
		return adminDetailsRepository.findByAdminEmail(email);
	}

    public AdminDetails save(AdminDetails adminDetails) {
        return adminDetailsRepository.save(adminDetails);
    }
    
    public Optional<AdminDetails> findById(Long Id) {
        return adminDetailsRepository.findByAdminId(Id);
    }

    public void updateAdmin(AdminDetails admin) {
        adminDetailsRepository.save(admin);
    }
}


