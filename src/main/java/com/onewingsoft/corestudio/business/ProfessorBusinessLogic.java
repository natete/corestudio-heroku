package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

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

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Professor professor) throws IllegalArgumentException {
        if (professor.getName() == null) {
            throw new IllegalArgumentException("Un profesor debe tener un nombre");
        }
        if (professor.getFirstSurname() == null) {
            throw new IllegalArgumentException("Un profesor debe tener un primer apellido");
        }
        if (professor.getFirstPhone() == null) {
            throw new IllegalArgumentException("Un profesor debe tener al menos un teléfono principal");
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
