package com.onewingsoft.corestudio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
public class AccountDTO {
    private List<SalaryDTO> salaries;
    private List<IncomesDTO> incomes;

    public AccountDTO() {
        this.salaries = new ArrayList<>();
        this.incomes = new ArrayList<>();
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

    public void addIncomes(IncomesDTO incomes) {
        this.incomes.add(incomes);
    }

    public void addSalary(SalaryDTO salary) {
        this.salaries.add(salary);
    }
}
