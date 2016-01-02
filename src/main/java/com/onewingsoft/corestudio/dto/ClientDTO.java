package com.onewingsoft.corestudio.dto;

import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
public class ClientDTO {

    private Long id;
    private String name;
    private String firstSurname;
    private Integer pendingSessions;
    private Date lastDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPendingSessions() {
        return pendingSessions;
    }

    public void setPendingSessions(Integer pendingSessions) {
        this.pendingSessions = pendingSessions;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
