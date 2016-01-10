package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.CoreMessage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 09/01/16.
 */
@Repository
public interface MessageRepository extends PagingAndSortingRepository<CoreMessage, Long> {
}
