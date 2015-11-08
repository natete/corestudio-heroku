package com.onewingsoft.corestudio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "professor")
public class Professor extends Person {

	public enum CorestudioRole {
		ADMIN, USER
	}
	
	@Column
	@NotNull
	@Size(min = 1, max = 25)
	private String username;
	
	@Column
	@NotNull
	@Size(min = 1, max = 60)
	private String passwordHash;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CorestudioRole role;
	
	@Column
	@Size(min = 0, max = 250)
	private String address;
	
	@Column
	@Size(min = 0, max = 300)
	private String qualification;
	
	@Column
	@Size(min = 0, max = 300)
	private String training;
	
	@Column
	private Integer holidaysUsed;

	public Professor(String username, String password) {
		this.username = username;
		this.passwordHash = (new BCryptPasswordEncoder()).encode(password);
	}
	
	public Professor() {
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public CorestudioRole getRole() {
		return role;
	}

	public void setRole(CorestudioRole role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public Integer getHolidaysUsed() {
		return holidaysUsed;
	}

	public void setHolidaysUsed(Integer holidaysUsed) {
		this.holidaysUsed = holidaysUsed;
	}
}
