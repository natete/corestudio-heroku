package com.OneWingSoft.corestudio.security.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.OneWingSoft.corestudio.model.CorestudioProfessor;
import com.OneWingSoft.corestudio.repository.CorestudioProfessorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CorestudioProfessorRepository professorRepository;
	
	private static final String ADMIN_USER = "admin";

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		CorestudioProfessor professor = professorRepository.findByUsername(username);

		return buildUserForAuthentication(professor, new SimpleGrantedAuthority(professor.getRole().toString()));
	}

	private User buildUserForAuthentication(CorestudioProfessor professor, GrantedAuthority authority) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
		return new User(professor.getUsername(), professor.getPasswordHash(), authorities);
	}
}
