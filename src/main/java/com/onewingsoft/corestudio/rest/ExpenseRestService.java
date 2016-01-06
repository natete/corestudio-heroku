package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.ExpenseBusinessLogic;
import com.onewingsoft.corestudio.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@RestController
@RequestMapping(value = "/api/admin/expenses")
public class ExpenseRestService extends BaseRestService<Expense> {

    @Autowired
    private ExpenseBusinessLogic expenseBusinessLogic;

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return expenseBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/expenses";
    }

    @Override
    protected String getMessage(Object entity) {
        return " el gasto " + entity.toString();
    }
}
