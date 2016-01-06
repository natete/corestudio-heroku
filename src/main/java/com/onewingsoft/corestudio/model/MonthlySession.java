package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Entity
@Table(name = "monthlySession")
public class MonthlySession extends BaseEntity {

    @Column
    @NotNull
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column
    @NotNull
    private Integer year;

    @Column
    @NotNull
    @Min(0)
    @Max(11)
    private Integer month;

    @Column
    @NotNull
    private Integer numberOfSessions;

    @JsonIgnore
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    @Override
    public String toString() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        DateFormat formatter = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
        return numberOfSessions + " sesiones en " + formatter.format(cal.getTime()) + " de " + year;
    }
}
