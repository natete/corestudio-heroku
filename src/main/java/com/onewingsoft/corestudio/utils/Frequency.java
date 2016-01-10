package com.onewingsoft.corestudio.utils;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
public enum  Frequency {
    EXCEPTIONAL("Puntual"),
    WEEKLY("Semanal"),
    MONTHLY("Mensual"),
    ANNUAL("Anual");

    private String value;

    Frequency(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
