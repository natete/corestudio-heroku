package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Pass;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Repository
public interface PassRepository extends PagingAndSortingRepository<Pass, Long> {

    Iterable<Pass> findByClientId(Long clientId);
}
