package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Expense;
import com.onewingsoft.corestudio.repository.ExpenseRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * @see BaseBusinessLogic#getAllEntities(Integer page, Integer size, String sortBy, String direction) adding sorting.
     */
    @Override
    public Page<Expense> getAllEntities(Integer page, Integer size, String sortBy, String direction) {
        Sort sort;
        if (sortBy != null) {
            sort = new Sort(Sort.Direction.fromString(direction), sortBy);
        } else {
            sort = new Sort(Sort.Direction.DESC, "expenseDate");
        }
        Pageable pageRequest = new PageRequest(page, size, sort);
        return expenseRepository.findAll(pageRequest);
    }

    public Page<Expense> getAllByType(String frequency, Integer page, Integer size, String sortBy, String direction) {
        Sort sort;
        if (sortBy != null) {
            sort = new Sort(Sort.Direction.fromString(direction), sortBy);
        } else {
            sort = new Sort(Sort.Direction.DESC, "expenseDate");
        }
        Pageable pageRequest = new PageRequest(page, size, sort);
        if(frequency.equals(Frequency.EXCEPTIONAL.toString())) {
            return expenseRepository.findByFrequency(Frequency.EXCEPTIONAL, pageRequest);
        } else {
            return expenseRepository.findByFrequencyNot(Frequency.EXCEPTIONAL, pageRequest);
        }
    }

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Expense expense) throws CorestudioException {
        if (expense.getExpenseDate() == null) {
            throw new CorestudioException("Un gasto debe tener una fecha de registro");
        }
        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            throw new CorestudioException("Un gasto debe tener una cantidad mayor que cero");
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
