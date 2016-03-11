package com.onewingsoft.corestudio.security.services;

import com.onewingsoft.corestudio.dto.PrincipalDTO;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import com.onewingsoft.corestudio.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public PrincipalDTO getUserWithAuthorities() {
        User user = SecurityUtils.getCurrentUser();
        Professor professor = professorRepository.findByUsername(user.getUsername());
        professor.getRole(); // eagerly load the association
        PrincipalDTO principal = new PrincipalDTO(professor.getFullName(), user.getAuthorities());

        return principal;
    }
}
