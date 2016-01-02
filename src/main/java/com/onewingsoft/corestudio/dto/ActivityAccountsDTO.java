package com.onewingsoft.corestudio.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
public class ActivityAccountsDTO {

    private Map<String, PassTypeAccountsDTO> map;

    public ActivityAccountsDTO() {
        this.map = new HashMap<>();
    }

    public Map<String, PassTypeAccountsDTO> getMap() {
        return map;
    }

    public void setMap(Map<String, PassTypeAccountsDTO> map) {
        this.map = map;
    }

    public PassTypeAccountsDTO getAccount(String passType) {
        return map.get(passType);
    }

    public void putAccount(String passType, PassTypeAccountsDTO accounts) {
        map.put(passType, accounts);
    }
}

