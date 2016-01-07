package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class IncomesDTO {

    private String type;
    private List<ActivityIncomesDTO> activitiesIncomes;

    public IncomesDTO(String type) {
        this.activitiesIncomes = new ArrayList<>();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ActivityIncomesDTO> getActivitiesIncomes() {
        return activitiesIncomes;
    }

    public void setActivitiesIncomes(List<ActivityIncomesDTO> activitiesIncomes) {
        this.activitiesIncomes = activitiesIncomes;
    }

    public void addPassTypeIncome(String activity, String passType, Long incomes) {
        ActivityIncomesDTO activityIncomes = this.getActivityIncomes(activity);
        activityIncomes.addPassTypeIncomes(passType, incomes);
    }

    @JsonIgnore
    private ActivityIncomesDTO getActivityIncomes(String activity) {
        for (ActivityIncomesDTO activitiesIncome : activitiesIncomes) {
            if (activity.equals(activitiesIncome.getActivityName())) {
                return activitiesIncome;
            }
        }
        ActivityIncomesDTO result = new ActivityIncomesDTO(activity);
        activitiesIncomes.add(result);
        return result;
    }

    public Long getTotalIncomes() {
        Long result = 0L;
        for (ActivityIncomesDTO activitiesIncome : activitiesIncomes) {
            result += activitiesIncome.getActivityIncomes() * 100;
        }
        return result / 100;
    }

    public Integer getTotalSessions() {
        Integer result = 0;
        for (ActivityIncomesDTO activitiesIncome : activitiesIncomes) {
            result += activitiesIncome.getActivitySessions();
        }
        return result;
    }

}
