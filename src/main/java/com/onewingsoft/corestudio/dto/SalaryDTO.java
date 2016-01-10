package com.onewingsoft.corestudio.dto;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
public class SalaryDTO {
    private String professorName;
    private Integer numberOfSessions;

    public SalaryDTO(String professorName, Integer numberOfSessions) {
        this.professorName = professorName;
        this.numberOfSessions = numberOfSessions;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }
}
