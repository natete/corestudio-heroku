package com.onewingsoft.corestudio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onewingsoft.corestudio.model.Client;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
	
	
}
