package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.Holiday;
import com.onewingsoft.corestudio.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
@Service
public class HolidayBusinessLogic extends BaseBusinessLogic<Holiday> {

    @Autowired
    private HolidayRepository holidayRepository;

    public boolean isHoliday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Holiday holiday = holidayRepository.findByDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        return holiday != null;
    }

    @Override
    protected Holiday processEntity(Holiday holiday) {
        return holiday;
    }

    @Override
    protected void validateEntity(Holiday holiday) throws IllegalArgumentException {
        if (holiday.getDate() == null) {
            throw new IllegalArgumentException("La fecha es necesaria");
        }
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return holidayRepository;
    }
}
