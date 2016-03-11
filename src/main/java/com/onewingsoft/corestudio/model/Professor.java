package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @JsonIgnore
    private String passwordHash;

    @Column
    @Enumerated(EnumType.STRING)
    private CorestudioRole role;

    @Column
    @Size(min = 0, max = 300)
    private String qualification;

    @Column
    @Size(min = 0, max = 300)
    private String training;

    @Column
    private Integer holidaysUsed;

    public Professor() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    @JsonProperty
    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public CorestudioRole getRole() {
        return role;
    }

    public void setRole(CorestudioRole role) {
        this.role = role;
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
