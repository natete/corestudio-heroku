package com.onewingsoft.corestudio.dto;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class PassTypeAccountsDTO {

    private Integer numberOfSessions = 0;
    private Long incomes = 0L;

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public Long getIncomes() {
        return incomes;
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
