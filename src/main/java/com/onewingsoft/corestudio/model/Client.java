package com.onewingsoft.corestudio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity to persist the clients
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 */
@Entity
@Table(name = "client")
public class Client extends Person {

    @Column
    @Size(min = 0, max = 9)
    @Pattern(regexp = "[0-9]*")
    private String secondPhone;

    @Column
    @Size(min = 0, max = 350)
    private String comments;

    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
