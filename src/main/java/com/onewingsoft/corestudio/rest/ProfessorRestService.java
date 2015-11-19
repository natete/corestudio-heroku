package com.onewingsoft.corestudio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ProfessorRepository;

@RestController
@RequestMapping("/api/professor")
public class ProfessorRestService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Professor addProfessor(@RequestBody Professor professor) {
		//TODO work in progress
		professor.setPasswordHash((new BCryptPasswordEncoder()).encode(professor.getPasswordHash()));
		return professorRepository.save(professor);
	}
}
