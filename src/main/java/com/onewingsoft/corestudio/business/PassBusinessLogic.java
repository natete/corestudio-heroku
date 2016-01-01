package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDateDTO;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.PassRepository;
import com.onewingsoft.corestudio.utils.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

        if (pass.getPendingSessions() > 0) {
            pass.addConsumedDate(clientDateDTO.getDate());

            if (pass.getPendingDates().contains(clientDateDTO.getDate())) {
                pass.getPendingDates().remove(clientDateDTO.getDate());
            } else {
                if (pass.getFrozenDates().contains(clientDateDTO.getDate())) {
                    pass.getFrozenDates().remove(clientDateDTO.getDate());
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

    public Pass releaseDate(ClientDateDTO clientDateDTO) {
        Pass pass = repository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientDateDTO.getClientId(), clientDateDTO.getDate());

        if (pass.isGroupPass()) {
            if (pass.isGroupDate(clientDateDTO.getDate())) {
                if (pass.getFrozenDates().contains(clientDateDTO.getDate())) {
                    pass.removeLastDate();
                }
//                pass.releaseDate(clientDateDTO.getDate());
                pass.addPendingDate(clientDateDTO.getDate());
            } else {
//                pass.releaseDate(clientDateDTO.getDate());

                Date nextDate = findNextDate(pass.getLastDate(), pass);

                pass.updateLastDate(nextDate);
            }
        }
        pass.releaseDate(clientDateDTO.getDate());
        this.updateEntity(pass);

        return pass;
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
