package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.MonthlySession;
import com.onewingsoft.corestudio.repository.MonthlySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Service
public class MonthlySessionBusinessLogic extends BaseBusinessLogic<MonthlySession> {

    @Autowired
    private MonthlySessionRepository monthlySessionRepository;

    public Iterable<MonthlySession> getSessionsByClientAndYear(final Long professorId, final Integer year) {
        return monthlySessionRepository.findByProfessorIdAndYear(professorId, year);
    }

    @Override
    protected MonthlySession processEntity(MonthlySession entity) {
        return entity;
    }

    @Override
    protected void validateEntity(MonthlySession monthlySession) throws IllegalArgumentException {
        if (monthlySession.getProfessor() == null) {
            throw new IllegalArgumentException("El registro de sesiones requiere un profesor");
        }
        if (monthlySession.getMonth() == null || monthlySession.getMonth() < 0 || monthlySession.getMonth() > 11) {
            throw new IllegalArgumentException("El mes seleccionado tiene un valor inválido");
        }
        if (monthlySession.getYear() == null) {
            throw new IllegalArgumentException("El año seleccionado tiene un valor inválido");
        }
        if(monthlySession.getNumberOfSessions() == null || monthlySession.getNumberOfSessions() < 0) {
            throw new IllegalArgumentException("El número de sesiones tiene un valor inválido");
        }
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return monthlySessionRepository;
    }
}
