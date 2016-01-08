package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class PassTypeIncomesDTO {

    private String passTypeName;
    private Integer numberOfSessions = 0;
    private Long incomes = 0L;

    public PassTypeIncomesDTO(String passTypeName) {
        this.passTypeName = passTypeName;
    }

    public String getPassTypeName() {
        return passTypeName;
    }

    public void setPassTypeName(String passTypeName) {
        this.passTypeName = passTypeName;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    @JsonIgnore
    public Long getIncomes() {
        return incomes;
    }

    public double getMoney() {
        return (double) incomes / 100;
    }

    public void setIncomes(Long incomes) {
        this.incomes = incomes;
    }

    public void increaseNumberOfSessions() {
        numberOfSessions++;
    }

    public void addToIncomes(Long value) {
        incomes += value;
    }

}
