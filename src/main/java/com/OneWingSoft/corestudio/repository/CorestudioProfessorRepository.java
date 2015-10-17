package com.OneWingSoft.corestudio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.OneWingSoft.corestudio.model.CorestudioProfessor;

@Repository
public interface CorestudioProfessorRepository extends PagingAndSortingRepository<CorestudioProfessor, Long> {
	
	public CorestudioProfessor findByUsername(String username);
}
