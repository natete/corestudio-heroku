package com.onewingsoft.corestudio.dto;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 31/12/15.
 */
public class ClientDateDTO {

    private Long clientId;
    private Date date;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "ES"));
        return formatter.format(date);
    }
}
