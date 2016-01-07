package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.Expense;
import com.onewingsoft.corestudio.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Service
public class ExpenseBusinessLogic extends BaseBusinessLogic<Expense> {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Iterable<Expense> getAllEntities() {
        return expenseRepository.findAll(new Sort(Sort.Direction.DESC, "expenseDate"));
    }

//    /**
//     * Create a new expense and adds an exceptional expense if the expense to be saved is not exceptional
//     * @param expense the expense to be saved
//     * @return the saved expense
//     */
//    @Override
//    public Expense createEntity(Expense expense) throws IllegalArgumentException {
//        expense = (Expense) super.createEntity(expense);
//        if(expense.getFrequency() != Frequency.EXCEPTIONAL) {
//            Expense punctualExpense = new Expense();
//            punctualExpense.setAmount(expense.getAmount());
//            punctualExpense.setDate(expense.getDate());
//            punctualExpense.setFrequency(Frequency.EXCEPTIONAL);
//            expenseRepository.save(punctualExpense);
//        }
//        return expense;
//    }

    @Override
    protected Expense processEntity(Expense expense) {
        return expense;
    }

    @Override
    protected void validateEntity(Expense expense) throws IllegalArgumentException {
        if(expense.getExpenseDate() == null) {
            throw new IllegalArgumentException("Un gasto debe tener una fecha de registro");
        }
        if(expense.getAmount() == null || expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Un gasto debe tener una cantidad mayor que cero");
        }
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return expenseRepository;
    }
}
