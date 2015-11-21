package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.Holiday;
import com.onewingsoft.corestudio.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
@Service
public class HolidayBusinessLogic {

    @Autowired
    private HolidayRepository holidayRepository;

    public Holiday saveHoliday(Holiday holiday) {
        if(holiday.getDate() != null) {
            return holidayRepository.save(holiday);
        } else {
            throw new IllegalArgumentException("Date is mandatory");
        }
    }
}
