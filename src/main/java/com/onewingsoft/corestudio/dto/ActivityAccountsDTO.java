package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class ActivityAccountsDTO {

    private String activityName;
    private List<PassTypeAccountsDTO> passTypeAccounts;

    public ActivityAccountsDTO(String activityName) {
        this.activityName = activityName;
        this.passTypeAccounts = new ArrayList<>();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<PassTypeAccountsDTO> getPassTypeAccounts() {
        return passTypeAccounts;
    }

    public void setPassTypeAccounts(List<PassTypeAccountsDTO> passTypeAccounts) {
        this.passTypeAccounts = passTypeAccounts;
    }

    public void addPassTypeAccount(String passType, Long incomes) {
        PassTypeAccountsDTO passTypeAccounts = this.getPassTypeAccount(passType);
        passTypeAccounts.addToIncomes(incomes);
        passTypeAccounts.increaseNumberOfSessions();
    }

    @JsonIgnore
    private PassTypeAccountsDTO getPassTypeAccount(String passType) {
        for (PassTypeAccountsDTO passTypeAccount : passTypeAccounts) {
            if (passType.equals(passTypeAccount.getPassTypeName())) {
                return passTypeAccount;
            }
        }
        PassTypeAccountsDTO result = new PassTypeAccountsDTO(passType);
        passTypeAccounts.add(result);
        return result;
    }

    public Long getActivityIncomes() {
        Long result = 0L;
        for (PassTypeAccountsDTO passTypeAccount : passTypeAccounts) {
            result += passTypeAccount.getIncomes();
        }
        return result;
    }

    public Integer getActivitySessions() {
        Integer result = 0;
        for (PassTypeAccountsDTO passTypeAccount : passTypeAccounts) {
            result += passTypeAccount.getNumberOfSessions();
        }
        return result;
    }
}

