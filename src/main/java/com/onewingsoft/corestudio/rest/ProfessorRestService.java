package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ProfessorBusinessLogic;
import com.onewingsoft.corestudio.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professor")
public class ProfessorRestService extends BaseRestService<Professor> {

    @Autowired
    private ProfessorBusinessLogic professorBusinessLogic;

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return this.professorBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/professor";
    }

    @Override
    protected String getMessage(Object professor) {
        return " el profesor " + professor.toString();
    }
}
