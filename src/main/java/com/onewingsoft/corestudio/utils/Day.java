package com.onewingsoft.corestudio.utils;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
public enum Day {
    MONDAY("Lu"),
    TUESDAY("Ma"),
    WEDNESDAY("Mi"),
    THURSDAY("Ju"),
    FRIDAY("Vi"),
    SATURDAY("Sa"),
    SUNDAY("Do");

    private String value;

    Day(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
