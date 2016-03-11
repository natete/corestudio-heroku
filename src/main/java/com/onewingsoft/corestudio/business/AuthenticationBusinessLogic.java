package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.PasswordDTO;
import com.onewingsoft.corestudio.dto.PrincipalDTO;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import com.onewingsoft.corestudio.security.SecurityUtils;
import com.onewingsoft.corestudio.security.services.UserService;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/03/16.
 */
@Service
public class AuthenticationBusinessLogic {

    @Autowired
    private ProfessorRepository professorRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserService userService;

    public PrincipalDTO changePassword (PasswordDTO passwordDTO) throws CorestudioException {
        User user = SecurityUtils.getCurrentUser();
        Professor professor = professorRepository.findByUsername(user.getUsername());

        if(passwordEncoder.matches(passwordDTO.getOldPassword(), professor.getPasswordHash())) {
            professor.setPasswordHash(passwordEncoder.encode(passwordDTO.getNewPassword()));
            professorRepository.save(professor);
            return new PrincipalDTO(professor.getFullName(), user.getAuthorities());
        } else {
            throw new CorestudioException("La contraseña proporcionada no es válida");
        }
    }

    public PrincipalDTO getPrincipal() {
        return userService.getUserWithAuthorities();
    }
}
