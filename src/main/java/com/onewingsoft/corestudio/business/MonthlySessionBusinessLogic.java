package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.MonthlySession;
import com.onewingsoft.corestudio.repository.MonthlySessionRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Business logic to manage the sessions a professor gives per month
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Service
public class MonthlySessionBusinessLogic extends BaseBusinessLogic<MonthlySession> {

    @Autowired
    private MonthlySessionRepository monthlySessionRepository;

    /**
     * Retrieve the sessions the given professor has imparted the given year.
     *
     * @param professorId the professor whose sessions have to be retrieved.
     * @param year        the year.
     * @return the list of {@link MonthlySession} of the given professor for the given year.
     */
    public Iterable<MonthlySession> getSessionsByProfessorAndYear(final Long professorId, final Integer year) {
        return monthlySessionRepository.findByProfessorIdAndYear(professorId, year);
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(MonthlySession monthlySession) throws CorestudioException {
        if (monthlySession.getProfessor() == null) {
            throw new CorestudioException("El registro de sesiones requiere un profesor");
        } else if (monthlySession.getMonth() == null || monthlySession.getMonth() < 0 || monthlySession.getMonth() > 11) {
            throw new CorestudioException("El mes seleccionado tiene un valor inválido");
        } else if (monthlySession.getYear() == null) {
            throw new CorestudioException("El año seleccionado tiene un valor inválido");
        } else if (monthlySession.getNumberOfSessions() == null || monthlySession.getNumberOfSessions() < 0) {
            throw new CorestudioException("El número de sesiones tiene un valor inválido");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<MonthlySession, Long> getRepository() {
        return monthlySessionRepository;
    }
}
