package com.onewingsoft.corestudio.dto;

import com.onewingsoft.corestudio.model.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
public class AccountDTO {
    private List<SalaryDTO> salaries;
    private List<IncomesDTO> incomes;
    private List<Expense> expenses;

    public AccountDTO() {
        this.salaries = new ArrayList<>();
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public List<SalaryDTO> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<SalaryDTO> salaries) {
        this.salaries = salaries;
    }

    public List<IncomesDTO> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<IncomesDTO> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addIncomes(IncomesDTO incomes) {
        this.incomes.add(incomes);
    }

    public void addSalary(SalaryDTO salary) {
        this.salaries.add(salary);
    }

}

