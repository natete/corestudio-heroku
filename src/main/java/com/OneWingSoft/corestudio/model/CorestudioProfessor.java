package com.OneWingSoft.corestudio.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "professors")
public class CorestudioProfessor {

	public enum CorestudioRole {
		ADMIN, USER
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long professorId;
	
	@Column
	@NotNull
	@Size(min = 1, max = 25)
	private String username;
	
	@Column
	@NotNull
	@Size(min = 1, max = 25)
	private String passwordHash;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CorestudioRole role;
	
	@Column
	@Size(min = 1, max = 50)
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."	
	        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	             message="{invalid.email}")
	private String email;
	
	@Column
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[a-z ñáàâäéèêëíìîïóòôöúùûü-]*")
	private String name;
	
	@Column
	@NotNull
	@Size(min = 1, max = 35)
	@Pattern(regexp = "[a-z ñáàâäéèêëíìîïóòôöúùûü-]*")
	private String surName1;
	
	@Column
	@NotNull
	@Size(min = 1, max = 35)
	@Pattern(regexp = "[a-z ñáàâäéèêëíìîïóòôöúùûü-]*")
	private String surName2;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@Column
	@Size(min = 0, max = 9)
	private String phone1;
	
	@Column
	@Size(min = 0, max = 100)
	private String photo;
	
	@Column
	@Size(min = 0, max = 250)
	private String address;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date admissionDate;
	
	@Column
	@Size(min = 0, max = 300)
	private String qualification;
	
	@Column
	@Size(min = 0, max = 300)
	private String training;
	
	@Column
	private Integer holidaysUsed;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName1() {
		return surName1;
	}

	public void setSurName1(String surName1) {
		this.surName1 = surName1;
	}

	public String getSurName2() {
		return surName2;
	}

	public void setSurName2(String surName2) {
		this.surName2 = surName2;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
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

	public Long getProfessorId() {
		return professorId;
	}
}
