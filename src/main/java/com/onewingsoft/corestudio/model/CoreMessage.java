package com.onewingsoft.corestudio.model;

import com.onewingsoft.corestudio.utils.Priority;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
@Entity
@Table(name = "core_message")
public class CoreMessage extends BaseEntity{

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String body;

    @Column
    private Priority priority;

    @Column
    private boolean isRead;

    public CoreMessage() {
        super();
    }

    public CoreMessage(String body) {
        this.date = new Date();
        this.body = body;
        this.isRead = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean read) {
        this.isRead = read;
    }
}
