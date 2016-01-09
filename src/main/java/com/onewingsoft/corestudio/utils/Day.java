package com.onewingsoft.corestudio.utils;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
public enum Day {
    SUNDAY(1, "Do"),
    MONDAY(2, "Lu"),
    TUESDAY(3, "Ma"),
    WEDNESDAY(4, "Mi"),
    THURSDAY(5, "Ju"),
    FRIDAY(6, "Vi"),
    SATURDAY(7, "Sa");

    private int value;
    private String shortName;

    Day(int value, String shortName) {
        this.value = value;
        this.shortName = shortName;
    }

    public int getValue() {
        return this.value;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}
