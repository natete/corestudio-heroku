package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class AccountsDTO {

    private String type;
    private List<ActivityAccountsDTO> activitiesAccounts;

    public AccountsDTO(String type) {
        this.activitiesAccounts = new ArrayList<>();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ActivityAccountsDTO> getActivitiesAccounts() {
        return activitiesAccounts;
    }

    public void setActivitiesAccounts(List<ActivityAccountsDTO> activitiesAccounts) {
        this.activitiesAccounts = activitiesAccounts;
    }

    public void addPassTypeAccount(String activity, String passType, Long incomes) {
        ActivityAccountsDTO activityAccounts = this.getActivityAccounts(activity);
        activityAccounts.addPassTypeAccount(passType, incomes);
    }

    @JsonIgnore
    private ActivityAccountsDTO getActivityAccounts(String activity) {
        for (ActivityAccountsDTO activitiesAccount : activitiesAccounts) {
            if (activity.equals(activitiesAccount.getActivityName())) {
                return activitiesAccount;
            }
        }
        ActivityAccountsDTO result = new ActivityAccountsDTO(activity);
        activitiesAccounts.add(result);
        return result;
    }

    public Long getTotalIncomes() {
        Long result = 0L;
        for (ActivityAccountsDTO activitiesAccount : activitiesAccounts) {
            result += activitiesAccount.getActivityIncomes();
        }
        return result;
    }

    public Integer getTotalSessions() {
        Integer result = 0;
        for (ActivityAccountsDTO activitiesAccount : activitiesAccounts) {
            result += activitiesAccount.getActivitySessions();
        }
        return result;
    }

}
