package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}
