package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Pass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Repository
public interface PassRepository extends PagingAndSortingRepository<Pass, Long> {

    Page<Pass> findByClientId(Long clientId, Pageable pageable);

    @Query("SELECT p FROM Pass p WHERE p.client.id = :clientId AND (year(p.initialDate) = :year OR year(p.lastDate) = :year)")
    Iterable<Pass> findByClientIdAndYear(@Param("clientId") Long clientId, @Param("year") Integer year);

    @Query("SELECT p FROM Pass p WHERE p.client.id = :clientId AND (:date MEMBER OF p.consumedDates OR :date MEMBER OF p.frozenDates OR :date MEMBER OF p.pendingDates)")
    Pass findByClientIdAndDate(@Param("clientId") Long clientId, @Param("date") Date date);

    Pass findFirstByClientIdAndInitialDateLessThanEqualOrderByInitialDateDesc(Long clientId, Date date);

    @Query("SELECT p FROM Pass p WHERE :date MEMBER OF p.pendingDates")
    Iterable<Pass> findByPendingDate(@Param("date") Date date);

    @Query("SELECT p FROM Pass p WHERE (((year(p.initialDate) = :year) AND month(p.initialDate) = :month) OR (year(p.lastDate) = :year) AND month(p.lastDate) = :month)")
    Iterable<Pass> findUsedPasses(@Param("year") int year, @Param("month") int month);
}
