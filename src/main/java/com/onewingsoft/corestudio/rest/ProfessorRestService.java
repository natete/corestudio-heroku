package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ProfessorBusinessLogic;
import com.onewingsoft.corestudio.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professor")
public class ProfessorRestService extends BaseRestService<Professor>{

	@Autowired
	private ProfessorBusinessLogic professorBusinessLogic;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Professor> getAllProfessors() {
		return professorBusinessLogic.getAllEntities();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Professor> addProfessor(@RequestBody Professor professor) {
		return super.saveEntity(professor);
	}

	@Override
	protected BaseBusinessLogic getBusinessLogic() {
		return professorBusinessLogic;
	}

	@Override
	protected String getUri() {
		return null;
	}

	@Override
	protected String getEntityName() {
		return null;
	}

	@Override
	protected String getParameter(Professor entity) {
		return null;
	}
}
