package com.onewingsoft.corestudio.dto;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
public class ProfessorDTO {
    private Long id;
    private String name;
    private Integer pendingSessions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPendingSessions() {
        return pendingSessions;
    }

    public void setPendingSessions(Integer pendingSessions) {
        this.pendingSessions = pendingSessions;
    }
}
