package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ProfessorDTO;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/01/16.
 */
@Service
public class ProfessorBusinessLogic extends BaseBusinessLogic<Professor> {

    @Autowired
    private ProfessorRepository professorRepository;

    public Iterable<ProfessorDTO> getAllDtos() {
        List<ProfessorDTO> result = new ArrayList<>();

        Iterable<Professor> professors = professorRepository.findAll();

        for (Professor professor : professors) {
            ProfessorDTO dto = new ProfessorDTO();
            dto.setName(professor.getFullName());
        }

        return result;
    }

    @Override
    protected Professor processEntity(Professor professor) {
        professor.setPasswordHash((new BCryptPasswordEncoder()).encode(professor.getPasswordHash()));
        return professor;
    }

    @Override
    protected void validateEntity(Professor entity) throws IllegalArgumentException {

    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return professorRepository;
    }
}
