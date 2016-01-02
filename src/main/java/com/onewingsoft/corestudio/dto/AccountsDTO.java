package com.onewingsoft.corestudio.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class AccountsDTO {

    private String type;
    private Map<String, ActivityAccountsDTO> map;

    public AccountsDTO(String type) {
        this.map = new HashMap<>();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, ActivityAccountsDTO> getMap() {
        return map;
    }

    public void setMap(Map<String, ActivityAccountsDTO> map) {
        this.map = map;
    }

    public ActivityAccountsDTO getActivityAccounts(String activity) {
        return map.get(activity);
    }

    public void putActivityAccount(String activity, ActivityAccountsDTO accounts) {
        map.put(activity, accounts);
    }
}
