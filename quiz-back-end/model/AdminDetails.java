package com.cdac.project.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "admin_details", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", initialValue = 1001, allocationSize = 1)
    private int adminId;

    @NotBlank(message = "Admin name cannot be blank")
    @Column(nullable = false, length = 255)
    private String adminName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String adminEmail;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String adminPassword;

    private String adminPhoneNumber;

	public AdminDetails() {
		super();
	}

	public AdminDetails(int adminId, @NotBlank(message = "Admin name cannot be blank") String adminName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email,
			@NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password must be at least 6 characters") String adminPassword,
			String adminPhoneNumber) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminEmail = email;
		this.adminPassword = adminPassword;
		this.adminPhoneNumber = adminPhoneNumber;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getEmail() {
		return adminEmail;
	}

	public void setEmail(String email) {
		this.adminEmail = email;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
	}

	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}

	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}

}