package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Expense;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Repository
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {
    Iterable<Expense> findOrderByDateDesc();
}
