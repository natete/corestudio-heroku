package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.HolidayBusinessLogic;
import com.onewingsoft.corestudio.model.Holiday;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
@RestController
@RequestMapping("/api/admin")
public class HolidaysRestService {

    @Autowired
    private HolidayBusinessLogic holidayBusinessLogic;

    @RequestMapping(value = "/holidays/{year}", method = RequestMethod.GET)
    public Iterable<Holiday> getAllHolidays(@PathVariable Integer year) {
        return holidayBusinessLogic.getAllHolidaysByYear(year);
    }

    @RequestMapping(value = "holidays", method = RequestMethod.POST)
    public ResponseEntity<Holiday> createHoliday(@Validated @RequestBody final Holiday holiday) {
        try {
            Holiday result = holidayBusinessLogic.saveHoliday(holiday);
            return ResponseEntity.created(new URI("/api/admin/holidays" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("día festivo", result.getId().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "holidays", method = RequestMethod.PUT)
    public ResponseEntity<Holiday> updateHoliday(@Validated @RequestBody final Holiday holiday) {
        try {
            Holiday result = holidayBusinessLogic.updateHoliday(holiday);
            return ResponseEntity.created(new URI("/api/admin/holidays" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("día festivo", result.getId().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "holidays/{id}", method = RequestMethod.DELETE)
    public void deleteHoliday(@PathVariable final Long id) {
        holidayBusinessLogic.deleteHoliday(id);
    }
}
