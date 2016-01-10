package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Professor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {

    Professor findByUsername(String username);
}
