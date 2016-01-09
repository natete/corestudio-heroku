package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ClientDateDTO;
import com.onewingsoft.corestudio.model.BaseEntity;
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
 * Business logic to manage Pass operations.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Service
public class PassBusinessLogic extends BaseBusinessLogic<Pass> {

    @Autowired
    private PassRepository passRepository;

    private final int WEEK_DAYS = 7;

    @Autowired
    private HolidayBusinessLogic holidayBusinessLogic;

    /**
     * Creates a Pass and sets the dates pending dates if it is a group pass.
     *
     * @param pass the pass to be persisted.
     * @return the persisted {@link Pass}
     * @throws IllegalArgumentException if validation fails.
     */
    @Override
    public Pass createEntity(Pass pass) throws IllegalArgumentException {
        if (pass.getId() == null) {
            this.validateEntity(pass);
            pass = processEntity(pass);
            pass = passRepository.save(pass);
            LoggingUtil.writeInfoLog("Created pass " + pass.toString());
            return pass;
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    /**
     * Updates a Pass and sets the dates pending dates if it is a group pass.
     *
     * @param pass the pass to be updated.
     * @return the updated {@link Pass}
     * @throws IllegalArgumentException if validation fails.
     */
    @Override
    public Pass updateEntity(Pass pass) throws IllegalArgumentException {
        this.validateEntity(pass);
        pass = this.processEntity(pass);
        Pass persistedEntity = passRepository.findOne(pass.getId());
        if (persistedEntity == null) {
            throw new IllegalArgumentException("La entidad que quiere actualizar no existe");
        } else {
            pass = passRepository.save(pass);
            LoggingUtil.writeInfoLog("Updated pass " + pass.toString());
            return pass;
        }
    }

    /**
     * Get the passes of the given client.
     *
     * @param clientId the client whose passes have to be retrieved.
     * @return the list of {@link Pass} of the given client.
     */
    public Iterable<Pass> getByClient(Long clientId) {
        return passRepository.findByClientId(clientId);
    }

    /**
     * Get the passes of the given client for the given year.
     *
     * @param clientId the client whose passes have to be retrieved.
     * @param year     the year to be retrieved.
     * @return the list of {@link Pass} of the given client for the given year.
     */
    public Iterable<Pass> getByClientAndYear(Long clientId, Integer year) {
        return passRepository.findByClientIdAndYear(clientId, year);
    }

    /**
     * Processes the pass to set consumed and pending dates if it is a group pass.
     *
     * @param pass the pass to be processed.
     * @return the processed {@link Pass}.
     */
    private Pass processEntity(Pass pass) {
        if (pass.isGroupPass()) {
            // Clears all dates
            pass.getConsumedDates().clear();
            pass.getFrozenDates().clear();
            pass.getPendingDates().clear();

            Date lastDate = new Date(pass.getInitialDate().getTime());

            pass.addConsumedDate(pass.getInitialDate());
            for (int i = 1; i < pass.getNumberOfSession(); i++) {
                lastDate = findNextDate(lastDate, pass);
                if (lastDate.after(new Date())) {
                    pass.addPendingDate(lastDate);
                } else {
                    pass.addConsumedDate(lastDate);
                }
            }

            pass.setLastDate(lastDate);
        }
        return pass;
    }

    /**
     * Finds next planned Date for the given Pass according to the given
     * initial date taking into account holidays.
     *
     * @param initialDate the initial date to start the search from.
     * @param pass        the Pass to find the next planned date.
     * @return {@link Date} next planned date.
     */
    private Date findNextDate(Date initialDate, Pass pass) {
        Date result;
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        cal.setTime(initialDate);
        cal.add(Calendar.DATE, 1);

        int lastDay = cal.get(Calendar.DAY_OF_WEEK);
        int nextDay = pass.getDays().get(0).getValue();

        // Find next week day according to the group.
        for (Day day : pass.getDays()) {
            if (day.getValue() >= lastDay) {
                nextDay = day.getValue();
                break;
            }
        }

        // Find next date according the week day obtained before.
        while (lastDay != nextDay) {
            cal.add(Calendar.DATE, 1);
            lastDay++;
            if (lastDay > WEEK_DAYS) {
                lastDay = lastDay % WEEK_DAYS;
            }
        }

        result = cal.getTime();

        // If the found day is holiday restart the finding with a new initial date.
        if (holidayBusinessLogic.isHoliday(result)) {
            return findNextDate(result, pass);
        }

        return result;
    }

    /**
     * Sets the given date as frozen for the given client recalculating pending dates.
     *
     * @param clientDateDTO client and date to be frozen.
     * @return the modified Pass.
     */
    public Pass freezeDate(ClientDateDTO clientDateDTO) {
        Pass pass = passRepository.findByClientIdAndDate(clientDateDTO.getClientId(), clientDateDTO.getDate());

        // Sets the date as frozen.
        pass.freezeDate(clientDateDTO.getDate());

        // Finds next last date to update it according to the new frozen date.
        Date nextDate = findNextDate(pass.getLastDate(), pass);

        // Updates last date of the pass.
        pass.updateLastDate(nextDate);

        passRepository.save(pass);
        LoggingUtil.writeInfoLog("Frozen date: " +clientDateDTO.getDate() + " for " + pass.getClient().toString());
        return pass;
    }

    /**
     * Sets the given date as consumed for the given client recalculating pending dates.
     *
     * @param clientDateDTO client and date to be consumed.
     * @return the modified Pass.
     */
    public Pass consumeDate(ClientDateDTO clientDateDTO) throws IllegalArgumentException {

        Pass pass = passRepository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientDateDTO.getClientId(), clientDateDTO.getDate());

        if (pass.getPendingSessions() > 0) {
            pass.addConsumedDate(clientDateDTO.getDate());

            if (pass.getPendingDates().contains(clientDateDTO.getDate())) {
                pass.getPendingDates().remove(clientDateDTO.getDate());
            } else {
                if (pass.getFrozenDates().contains(clientDateDTO.getDate())) {
                    pass.getFrozenDates().remove(clientDateDTO.getDate());
                }
                pass.removeLastDate();
            }

            if (!pass.isGroupPass()) {
                pass.setLastDate(clientDateDTO.getDate());
            }

            if (pass.getPendingSessions() == 0) {
                // TODO create message
            }

            passRepository.save(pass);
            LoggingUtil.writeInfoLog("Consumed date: " +clientDateDTO.getDate() + " for " + pass.getClient().toString());
            return pass;
        } else {
            throw new IllegalArgumentException("El cliente no tiene clases disponibles en el bono");
        }
    }

    /**
     * Sets the given date as pending or non consumed for the given client recalculating pending dates if needed.
     *
     * @param clientDateDTO client and date to be released.
     * @return the modified Pass.
     */
    public Pass releaseDate(ClientDateDTO clientDateDTO) {
        Pass pass = passRepository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientDateDTO.getClientId(), clientDateDTO.getDate());

        // Group past require set the date as pending.
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
        passRepository.save(pass);
        LoggingUtil.writeInfoLog("Released date: " +clientDateDTO.getDate() + " for " + pass.getClient().toString());
        return pass;
    }

    /**
     * Scheduled tasks that consumes all pending sessions for the current date.
     * It is triggered every day each 6 hours (0:00, 6:00, 12:00 and 18:00)
     */
    @Scheduled(cron = "0 0 */6 * * *")
    public void consumeCurrentDate() {
        Calendar cal = Calendar.getInstance();
        // Set time values to 0 to allow comparison when removing pending date.
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date currentDate = cal.getTime();

        LoggingUtil.writeInfoLog("Scheduled task taking place on " + currentDate);

        Iterable<Pass> passes = passRepository.findByPendingDate(currentDate);

        LoggingUtil.writeInfoLog("Updating " + ((Collection<?>) passes).size() + " passes");

        for (Pass pass : passes) {
            pass.getPendingDates().remove(currentDate);
            pass.addConsumedDate(currentDate);
            if (!pass.isGroupPass()) {
                pass.setLastDate(currentDate);
            }
            passRepository.save(pass);
            if (pass.getPendingSessions() == 0) {
                // TODO create message
            }
        }
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
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

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<Pass, Long> getRepository() {
        return this.passRepository;
    }

    /**
     * Retrieves the current pass of the given client if any.
     *
     * @param clientId the client whose pass has to be retrieved.
     * @return the current {@link Pass} of the given client if any, null otherwise.
     */
    public Pass getCurrentPass(Long clientId) {
        return passRepository.findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(clientId, new Date());
    }
}
