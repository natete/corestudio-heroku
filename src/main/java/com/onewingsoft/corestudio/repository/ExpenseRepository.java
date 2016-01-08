package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Repository
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.frequency = 'EXCEPTIONAL' AND (year(e.expenseDate) = :year) AND (month(e.expenseDate) = :month)")
    Collection<Expense> findExceptionalByDate(@Param("year") int year, @Param("month") int month);

    @Query("SELECT e FROM Expense e WHERE e.frequency <> 'EXCEPTIONAL' AND (e.expenseDate <= :initialDate) AND (e.endDate >= :date OR e.endDate IS NULL)")
    Iterable<Expense> findActiveRegularExpenseByDate(@Param("initialDate") Date initialDate, @Param("date")Date date);
}
