package com.onewingsoft.corestudio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onewingsoft.corestudio.model.Professor;

@Repository
public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
	
	public Professor findByUsername(String username);
}
