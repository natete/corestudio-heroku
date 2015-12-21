package com.onewingsoft.corestudio.utils;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
public enum Level {
    LOW("Bajo"),
    MEDIUM("Medio"),
    HIGH("Alto");

    private String value;

    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
