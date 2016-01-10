package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Holiday;
import com.onewingsoft.corestudio.repository.HolidayRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Business logic to manage holidays operations.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
@Service
public class HolidayBusinessLogic extends BaseBusinessLogic<Holiday> {

    @Autowired
    private HolidayRepository holidayRepository;

    /**
     * Retrieves registered holidays for the given year.
     *
     * @param year the year to find by.
     * @return list of {@link Holiday} for the given year.
     */
    public Iterable<Holiday> getByYear(final Integer year) {
        return holidayRepository.findByYear(year);
    }

    /**
     * Checks if the given day is registered as a holiday.
     *
     * @param date the date to be checked.
     * @return true if it is registered, false otherwise.
     */
    public boolean isHoliday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Holiday holiday = holidayRepository.findByDate(cal.getTime());
        return holiday != null;
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Holiday holiday) throws CorestudioException {
        if (holiday.getDate() == null) {
            throw new CorestudioException("La fecha es necesaria");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository()
     */
    @Override
    protected PagingAndSortingRepository<Holiday, Long> getRepository() {
        return holidayRepository;
    }
}
