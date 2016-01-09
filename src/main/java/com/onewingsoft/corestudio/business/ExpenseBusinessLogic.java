package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Expense;
import com.onewingsoft.corestudio.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Business logic to manage expenses.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Service
public class ExpenseBusinessLogic extends BaseBusinessLogic<Expense> {

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * @see BaseBusinessLogic#getAllEntities() adding sorting.
     */
    @Override
    public Iterable<Expense> getAllEntities() {
        return expenseRepository.findAll(new Sort(Sort.Direction.DESC, "expenseDate"));
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Expense expense) throws IllegalArgumentException {
        if (expense.getExpenseDate() == null) {
            throw new IllegalArgumentException("Un gasto debe tener una fecha de registro");
        }
        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Un gasto debe tener una cantidad mayor que cero");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<Expense, Long> getRepository() {
        return expenseRepository;
    }
}
