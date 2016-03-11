package com.onewingsoft.corestudio.security.services;

import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom class to build authentication from the login details.
 *
 * @author Ignacio González Bullón <natete981@gmail.com>
 * @since 18 oct. 2015
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfessorRepository professorRepository;

    /**
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Professor professor = professorRepository.findByUsername(username);

        if (professor == null) {
            throw new UsernameNotFoundException("Nombre de usuario o contraseña no válidos");
        }

        return buildUserForAuthentication(professor, new SimpleGrantedAuthority(professor.getRole().toString()));
    }

    /**
     * Builds a new {@link User} based on the given professor and authority.
     *
     * @param professor the professor used as base to the user.
     * @param authority {@link GrantedAuthority} the professor has
     * @return {@link User} based on the professor.
     */
    private User buildUserForAuthentication(Professor professor, GrantedAuthority authority) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new User(professor.getUsername(), professor.getPasswordHash(), authorities);
    }
}
