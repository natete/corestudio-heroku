package com.OneWingSoft.corestudio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.OneWingSoft.corestudio.model.CorestudioProfessor;
import com.OneWingSoft.corestudio.repository.CorestudioProfessorRepository;

@RestController
@RequestMapping("/RSProfessor")
public class CorestudioRestProfessor {

	@Autowired
	private CorestudioProfessorRepository corestudioProfessorRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public CorestudioProfessor addProfessor(@RequestBody CorestudioProfessor professor) {
		//TODO work in progress
		professor.setPasswordHash((new BCryptPasswordEncoder()).encode(professor.getPasswordHash()));
		return corestudioProfessorRepository.save(professor);
	}
}
