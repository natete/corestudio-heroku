package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.HolidayBusinessLogic;
import com.onewingsoft.corestudio.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/11/15.
 */
@RestController
@RequestMapping("/api/admin/holidays")
public class HolidaysRestService extends BaseRestService<Holiday> {

    @Autowired
    private HolidayBusinessLogic holidayBusinessLogic;

    @RequestMapping(value = "/getByYear/{year}", method = RequestMethod.GET)
    public Iterable<Holiday> getByYear(@PathVariable Integer year) {
        return holidayBusinessLogic.getByYear(year);
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
    protected String getMessage(Object holiday) {
        return " el festivo " + holiday.toString();
    }
}
