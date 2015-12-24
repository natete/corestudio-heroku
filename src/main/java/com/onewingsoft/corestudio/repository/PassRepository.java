package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Pass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Repository
public interface PassRepository extends PagingAndSortingRepository<Pass, Long> {

    Iterable<Pass> findByClientId(Long clientId);

    @Query("SELECT p FROM Pass p WHERE p.client.id = :clientId AND (year(p.initialDate) = :year OR year(p.lastDate) = :year)")
    Iterable<Pass> findByClientIdAndYear(@Param("clientId") Long clientId, @Param("year") Integer year);
}
