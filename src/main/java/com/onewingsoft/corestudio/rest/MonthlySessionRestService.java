package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.MonthlySessionBusinessLogic;
import com.onewingsoft.corestudio.model.MonthlySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@RestController
@RequestMapping(value = "/api/professor/session")
public class MonthlySessionRestService extends BaseRestService<MonthlySession> {

    @Autowired
    private MonthlySessionBusinessLogic monthlySessionBusinessLogic;

    @RequestMapping(value = "/{professorId}/{year}", method = RequestMethod.GET)
    public Iterable<MonthlySession> getByProfessorAndYear(@PathVariable final Long professorId, @PathVariable final Integer year) {
        return monthlySessionBusinessLogic.getSessionsByProfessorAndYear(professorId, year);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return monthlySessionBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/professor/sessions";
    }

    @Override
    protected String getMessage(Object entity) {
        return " las sesiones" + entity.toString();
    }
}
