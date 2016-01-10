package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the inconmes of a type (typically group or individual)
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class IncomesDTO {

    private String type;
    private List<ActivityIncomesDTO> activitiesIncomes;

    public IncomesDTO(String type) {
        this.type = type;
        this.activitiesIncomes = new ArrayList<>();
    }

    /**
     * Adds a passType income to with the given parameters.
     * @param activity the name of the activity.
     * @param passType the pass type name.
     * @param incomes the incomes of the pass type.
     */
    public void addPassTypeIncome(String activity, String passType, Long incomes) {
        ActivityIncomesDTO activityIncomes = this.getActivityIncomes(activity);
        activityIncomes.addPassTypeIncomes(passType, incomes);
    }

    /**
     * Returns an activity pass type incomes for the given activity.
     * @param activity the activity to be retrieved.
     * @return the {@link ActivityIncomesDTO} in the instance if exists or a new one otherwise.
     */
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

    /**
     * Calculates the total incomes of the instance.
     * @return the total incomes.
     */
    public Double getTotalIncomes() {
        Long result = 0L;
        for (ActivityIncomesDTO activitiesIncome : activitiesIncomes) {
            result += activitiesIncome.getActivityIncomes();
        }
        return (double) result / 100;
    }

    public Integer getTotalSessions() {
        Integer result = 0;
        for (ActivityIncomesDTO activitiesIncome : activitiesIncomes) {
            result += activitiesIncome.getActivitySessions();
        }
        return result;
    }

}
