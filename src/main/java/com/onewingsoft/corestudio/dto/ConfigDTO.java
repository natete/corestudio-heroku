package com.onewingsoft.corestudio.dto;

import com.onewingsoft.corestudio.utils.Day;
import com.onewingsoft.corestudio.utils.Frequency;
import com.onewingsoft.corestudio.utils.Level;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 12/12/15.
 */
public class ConfigDTO {

    private Map<String, String> days;
    private Map<String, String> levels;
    private Map<String, String> frequencies;

    public ConfigDTO() {
        this.days = new LinkedHashMap<>();
        this.levels = new LinkedHashMap<>();
        this.frequencies = new LinkedHashMap<>();

        for (Day day : Day.values()) {
            this.days.put(day.name(), day.getShortName());
        }

        for (Level level : Level.values()) {
            this.levels.put(level.name(), level.getValue());
        }

        for (Frequency frequency : Frequency.values()) {
            this.frequencies.put(frequency.name(), frequency.getValue());
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

    public Map<String, String> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Map<String, String> frequencies) {
        this.frequencies = frequencies;
    }
}
