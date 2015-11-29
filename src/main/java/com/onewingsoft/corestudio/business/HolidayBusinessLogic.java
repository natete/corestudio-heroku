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

    public Holiday saveHoliday(Holiday holiday) throws IllegalArgumentException {
        validateHoliday(holiday);
        return holidayRepository.save(holiday);
    }

    public Iterable<Holiday> getAllHolidaysByYear(Integer year) {
        return holidayRepository.findByYear(year);
    }

    public Holiday updateHoliday(Holiday holiday) throws IllegalArgumentException {
        validateHoliday(holiday);
        Holiday persistedHoliday = holidayRepository.findOne(holiday.getId());
        if (persistedHoliday == null) {
            throw new IllegalArgumentException("El festivo que quiere actualizar no está registrado");
        } else {
            holidayRepository.save(holiday);
            return holiday;
        }
    }

    public void deleteHoliday(final Long id) {
        holidayRepository.delete(id);
    }

    private void validateHoliday(final Holiday holiday) throws IllegalArgumentException {
        if(holiday.getDate() == null) {
            throw new IllegalArgumentException("La fecha es necesaria");
        }
    }
}
