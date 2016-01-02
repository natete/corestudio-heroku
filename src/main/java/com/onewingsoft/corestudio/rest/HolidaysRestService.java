package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.HolidayBusinessLogic;
import com.onewingsoft.corestudio.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
@RestController
@RequestMapping("/api/admin")
public class HolidaysRestService extends BaseRestService<Holiday> {

    @Autowired
    private HolidayBusinessLogic holidayBusinessLogic;

    @RequestMapping(value = "/holidays/{year}", method = RequestMethod.GET)
    public Iterable<Holiday> getAllHolidays(@PathVariable Integer year) {
        return super.getAll();
    }


    @RequestMapping(value = "holidays", method = RequestMethod.POST)
    public ResponseEntity<Holiday> createHoliday(@Validated @RequestBody final Holiday holiday) {
        return super.saveEntity(holiday);
    }

    @RequestMapping(value = "holidays", method = RequestMethod.PUT)
    public ResponseEntity<Holiday> updateHoliday(@Validated @RequestBody final Holiday holiday) {
        return super.updateEntity(holiday);
    }

    @RequestMapping(value = "holidays/{id}", method = RequestMethod.DELETE)
    public void deleteHoliday(@PathVariable final Long id) {
        super.deleteEntity(id);
//        holidayBusinessLogic.deleteHoliday(id);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return holidayBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/admin/holidays";
    }

    @Override
    protected String getEntityName() {
        return "día festivo";
    }

    @Override
    protected String getParameter(Holiday holiday) {
        return holiday.getDate().toString();
    }
}
