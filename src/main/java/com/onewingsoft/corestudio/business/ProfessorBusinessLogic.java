package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Business logic to manage Professor.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
@Service
public class ProfessorBusinessLogic extends BaseBusinessLogic<Professor> {

    @Autowired
    private ProfessorRepository professorRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Professor professor) throws CorestudioException {
        LoggerUtil.writeErrorLog("Password " + professor.getPasswordHash());
        professor.setPasswordHash(passwordEncoder.encode(professor.getPasswordHash()));

        if (professor.getName() == null) {
            throw new CorestudioException("Un profesor debe tener un nombre");
        }
        if (professor.getFirstSurname() == null) {
            throw new CorestudioException("Un profesor debe tener un primer apellido");
        }
        if (professor.getFirstPhone() == null) {
            throw new CorestudioException("Un profesor debe tener al menos un teléfono principal");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<Professor, Long> getRepository() {
        return professorRepository;
    }
}
