package com.onewingsoft.corestudio;

import com.onewingsoft.corestudio.business.HolidayBusinessLogic;
import com.onewingsoft.corestudio.model.Holiday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
@ContextConfiguration
@SpringApplicationConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class HolidayBusinessLogicTest {

    @Test(expected = Exception.class)
    public void noDateHoliday() {
        Holiday holiday = new Holiday();
        HolidayBusinessLogic holidayBL = new HolidayBusinessLogic();

        holidayBL.saveHoliday(holiday);
    }
}
