package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.MonthlySession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Repository
public interface MonthlySessionRepository extends PagingAndSortingRepository<MonthlySession, Long> {

    @Query("SELECT m FROM MonthlySession m WHERE m.professor.id = :professorId AND m.year = :year")
    Iterable<MonthlySession> findByProfessorIdAndYear(@Param("professorId") Long professorId, @Param("year") Integer year);

    @Query("SELECT m FROM MonthlySession m WHERE m.professor.id = :professorId AND m.year = :year AND m.month = :month")
    MonthlySession findByProfessorIdAndYearAndMonth(@Param("professorId") Long professorId, @Param("year") Integer year, @Param("month") Integer month);
}
