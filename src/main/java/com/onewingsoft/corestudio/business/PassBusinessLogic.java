package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDateDTO;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.PassRepository;
import com.onewingsoft.corestudio.utils.Day;
import com.onewingsoft.corestudio.utils.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Service
public class PassBusinessLogic extends BaseBusinessLogic<Pass> {

    @Autowired
    private PassRepository repository;

    private final int WEEK_DAYS = 7;

    @Autowired
    private HolidayBusinessLogic holidayBusinessLogic;

    public Iterable<Pass> getByClient(Long clientId) {
        return repository.findByClientId(clientId);
    }

    public Iterable<Pass> getByClientAndYear(Long clientId, Integer year) {
        return repository.findByClientIdAndYear(clientId, year);
    }

    @Override
    protected Pass processEntity(Pass pass) {
        if (pass.isGroupPass()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pass.getInitialDate());

            Date lastDate = cal.getTime();

            pass.addPendingDate(pass.getInitialDate());
            // TODO adapt to make it common to update pass using getPendingSessions
            for (int i = 1; i < pass.getNumberOfSession(); i++) {
                lastDate = findNextDate(lastDate, pass);
                pass.addPendingDate(lastDate);
            }

            pass.setLastDate(lastDate);
        }
        return pass;
    }

    private Date findNextDate(Date initialDate, Pass entity) {
        Date result;
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);

        int lastDay = cal.get(Calendar.DAY_OF_WEEK);
        int nextDay = entity.getDays().get(0).getValue();

        for (Day day : entity.getDays()) {
            if (day.getValue() > lastDay) {
                nextDay = day.getValue();
                break;
            }
        }

        while (lastDay != nextDay) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
            lastDay++;
            if (lastDay > WEEK_DAYS) {
                lastDay = lastDay % WEEK_DAYS;
            }
        }

        result = cal.getTime();

        if (holidayBusinessLogic.isHoliday(result)) {
            return findNextDate(result, entity);
        }

        return result;
    }

    public Pass freezeDate(ClientDateDTO clientDateDTO) {
        Pass pass = repository.findByClientIdAndDate(clientDateDTO.getClientId(), clientDateDTO.getDate());

        pass.freezeDate(clientDateDTO.getDate());

        Date nextDate = findNextDate(pass.getLastDate(), pass);

        pass.updateLastDate(nextDate);

        this.updateEntity(pass);

        return pass;
    }

    public Pass consumeDate(ClientDateDTO clientDateDTO) throws IllegalArgumentException {

        Pass pass = repository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientDateDTO.getClientId(), clientDateDTO.getDate());

        return consumePassDate(clientDateDTO.getDate(), pass);
    }

    public Pass releaseDate(ClientDateDTO clientDateDTO) {
        Pass pass = repository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientDateDTO.getClientId(), clientDateDTO.getDate());

        if (pass.isGroupPass()) {
            if (pass.isGroupDate(clientDateDTO.getDate())) {
                if (pass.getFrozenDates().contains(clientDateDTO.getDate())) {
                    pass.removeLastDate();
                }
                pass.addPendingDate(clientDateDTO.getDate());
            } else {
                Date nextDate = findNextDate(pass.getLastDate(), pass);
                pass.updateLastDate(nextDate);
            }
        }
        pass.releaseDate(clientDateDTO.getDate());
        this.updateEntity(pass);

        return pass;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void consumeCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date currentDate = cal.getTime();

        LoggingUtil.writeInfoLog("Scheduled task taking place on " + currentDate);

        Iterable<Pass> passes = repository.findByPendingDate(currentDate);

        LoggingUtil.writeInfoLog("Updating " + ((Collection<?>) passes).size() + " passes");

        for (Pass pass : passes) {
            pass.getPendingDates().remove(currentDate);
            this.updateEntity(pass);
            if (pass.getPendingSessions() == 0) {
                // TODO create message
            }
        }
    }

    private Pass consumePassDate(Date currentDate, Pass pass) throws IllegalArgumentException {
        if (pass.getPendingSessions() > 0) {
            pass.addConsumedDate(currentDate);

            if (pass.getPendingDates().contains(currentDate)) {
                pass.getPendingDates().remove(currentDate);
            } else {
                if (pass.getFrozenDates().contains(currentDate)) {
                    pass.getFrozenDates().remove(currentDate);
                    pass.removeLastDate();
                }
            }

            if (pass.getPendingSessions() == 0) {
                // TODO create message
            }

            this.updateEntity(pass);

            return pass;
        } else {
            throw new IllegalArgumentException("El cliente no tiene clases disponibles en el bono");
        }
    }

    @Override
    protected void validateEntity(Pass entity) {
        if (entity.getInitialDate() == null) {
            throw new IllegalArgumentException("Un bono debe tener una fecha de inicio");
        }
        if (entity.getPaymentDate() == null) {
            throw new IllegalArgumentException("Un bono debe tener una fecha de pago");
        }
        if (entity.getPrice() == null || entity.getPrice() <= 0) {
            throw new IllegalArgumentException("Un bono debe tener un precio");
        }
        if (entity.getClient() == null) {
            throw new IllegalArgumentException("Un bono debe pertenecer a un cliente");
        }
        if (entity.getPassType() == null) {
            throw new IllegalArgumentException("Un bono debe ser de un tipo");
        }
        if (entity.getPassType().isGroupActivity() && entity.getGroup() == null) {
            throw new IllegalArgumentException("Este tipo de bono debe tener un grupo asociado");
        }
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return this.repository;
    }
}
