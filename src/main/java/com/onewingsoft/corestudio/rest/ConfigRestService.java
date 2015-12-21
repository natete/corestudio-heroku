package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.dto.ConfigDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 12/12/15.
 */
@RestController
@RequestMapping(value = "/api")
public class ConfigRestService {

    @RequestMapping(value = "/config")
    public ConfigDTO getConfig() {
        return new ConfigDTO();
    }
}
