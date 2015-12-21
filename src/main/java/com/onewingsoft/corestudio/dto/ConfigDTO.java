package com.onewingsoft.corestudio.dto;

import com.onewingsoft.corestudio.utils.Day;
import com.onewingsoft.corestudio.utils.Level;

import java.util.*;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 12/12/15.
 */
public class ConfigDTO {

    private Map<String, String> days;
    private Map<String, String> levels;

    public ConfigDTO() {
        this.days = new LinkedHashMap<>();
        this.levels = new LinkedHashMap<>();

        for (Day day : Day.values()) {
            this.days.put(day.name(), day.getValue());
        }

        for (Level level : Level.values()) {
            this.levels.put(level.name(), level.getValue());
        }
    }

    public Map<String, String> getLevels() {
        return levels;
    }

    public void setLevels(Map<String, String> levels) {
        this.levels = levels;
    }

    public Map<String, String> getDays() {
        return days;
    }

    public void setDays(Map<String, String> days) {
        this.days = days;
    }
}
