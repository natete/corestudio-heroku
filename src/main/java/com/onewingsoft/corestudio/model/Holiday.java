package com.onewingsoft.corestudio.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Entity to persist the holidays the user wants the application to consider
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/2015
 */
@Entity
@Table(name = "holiday")
public class Holiday extends BaseEntity {

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    @Size(min = 0, max = 25)
    private String description;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "ES"));
        return formatter.format(date) + " " + description;
    }
}
