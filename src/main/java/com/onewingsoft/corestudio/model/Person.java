package com.onewingsoft.corestudio.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 */
@MappedSuperclass
public class Person extends BaseEntity {

	@Column
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[a-zA-z ñáàâäéèêëíìîïóòôöúùûüçÑÁÀÂÄÉÈÊËÍÌÎÏÓÒÔÖÚÙÛÜÇ-]*")
	private String name;

	@Column
	@NotNull
	@Size(min = 1, max = 35)
	@Pattern(regexp = "[a-zA-z ñáàâäéèêëíìîïóòôöúùûüçÑÁÀÂÄÉÈÊËÍÌÎÏÓÒÔÖÚÙÛÜÇ-]*")
	private String firstSurname;

	@Column
	@NotNull
	@Size(min = 1, max = 35)
	@Pattern(regexp = "[a-zA-z ñáàâäéèêëíìîïóòôöúùûüçÑÁÀÂÄÉÈÊËÍÌÎÏÓÒÔÖÚÙÛÜÇ-]*")
	private String secondSurname;

	@Column
	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@Column
	@Temporal(TemporalType.DATE)
	private Date admissionDate;

	@Column
	@Size(min = 9, max = 9)
	@NotNull
	@Pattern(regexp = "[0-9]*")
	private String firstPhone;

	@Column
	@Size(min = 0, max = 50)
	@Pattern(regexp = "^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "{invalid.email}")
	private String email;

	@Column
	@Size(min = 0, max = 100)
	private String photo;

	@Column
	@Size(min = 0, max = 100)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getFirstPhone() {
		return firstPhone;
	}

	public void setFirstPhone(String firstPhone) {
		this.firstPhone = firstPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
}
