package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.AccountDTO;
import com.onewingsoft.corestudio.dto.IncomesDTO;
import com.onewingsoft.corestudio.dto.SalaryDTO;
import com.onewingsoft.corestudio.model.Expense;
import com.onewingsoft.corestudio.model.MonthlySession;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.ExpenseRepository;
import com.onewingsoft.corestudio.repository.MonthlySessionRepository;
import com.onewingsoft.corestudio.repository.PassRepository;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class to retrieve the accounts.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
@Service
public class AccountsBusinessLogic {

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MonthlySessionRepository monthlySessionRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Returns total accounts of the given month and year.
     *
     * @param year  the year to be retrieved.
     * @param month the month to be retrieved.
     * @return {@link AccountDTO} the accounts for the given month and year.
     */
    public AccountDTO getAccounts(int year, int month) {
        AccountDTO result = new AccountDTO();

        result.setIncomes(getIncomes(year, month));

        result.setSalaries(getSalaries(year, month));

        result.setExpenses(getExpenses(year, month));

        return result;
    }

    /**
     * Calculates group and individual incomes for the given year and month and adds it to the given accounts DTO.
     *
     * @param year  The year to be calculated.
     * @param month The month to be calculated.
     * @return a {@link List} of the required inconmes.
     */
    private List<IncomesDTO> getIncomes(int year, int month) {
        IncomesDTO groupAccounting = new IncomesDTO("Actividades de grupo");
        IncomesDTO individualAccounting = new IncomesDTO("Actividades individuales");

        Iterable<Pass> passes = passRepository.findUsedPasses(year, month);


        for (Pass pass : passes) {
            if (pass.isGroupPass()) {
                processPass(month, groupAccounting, pass);
            } else {
                processPass(month, individualAccounting, pass);
            }
        }
        List<IncomesDTO> result = new ArrayList<>();
        result.add(groupAccounting);
        result.add(individualAccounting);
        return result;
    }

    /**
     * Process the pass getting its consumed dates and adding it to the dto if the date is within the requested month.
     *
     * @param month      the month to check dates.
     * @param accounting {@link IncomesDTO} the dto where the incomes has to be added.
     * @param pass       {@link Pass} the pass to be processed.
     */
    private void processPass(int month, IncomesDTO accounting, Pass pass) {
        Calendar cal = Calendar.getInstance();

        for (Date date : pass.getConsumedDates()) {
            cal.setTime(date);
            if (cal.get(Calendar.MONTH) + 1 == month) {
                accounting.addPassTypeIncome(pass.getPassType().getActivity().getName(), pass.getPassType().toString(), pass.getPricePerSession());
            }
        }
    }

    /**
     * Returns all the salaries for a given month and year.
     *
     * @param year  the year to retrieve data.
     * @param month the month to retrieve data.
     * @return {@link List} of {@link SalaryDTO} containing the salaries for the given year and month.
     */
    private List<SalaryDTO> getSalaries(int year, int month) {
        List<SalaryDTO> salaries = new ArrayList<>();
        Iterable<Professor> professors = professorRepository.findAll();

        for (Professor professor : professors) {
            MonthlySession monthlySalary = monthlySessionRepository.findByProfessorIdAndYearAndMonth(professor.getId(), year, month);
            if (monthlySalary != null) {
                SalaryDTO salary = new SalaryDTO(professor.toString(), monthlySalary.getNumberOfSessions());
                salaries.add(salary);
            }
        }
        return salaries;
    }

    /**
     * Retrieves regular and exceptional expenses for the given year and month.
     *
     * @param year  the year to retrieve data.
     * @param month the month to retrieve data.
     * @return {@link List}<{@link Expense}> with all the expenses for the given year and month.
     */
    private List<Expense> getExpenses(int year, int month) {
        List<Expense> expenses = new ArrayList<>();
        expenses.addAll(expenseRepository.findExceptionalByDate(year, month));

        expenses.addAll(getRegularExpenses(year, month));

        return expenses;
    }

    /**
     * Obtains regular expenses for the given year and month. Takes into account frequency,
     * expense date and end date to add as many expenses as required.
     *
     * @param year  the year to retrieve data.
     * @param month the month to retrieve data.
     * @return {@link List}<{@link Expense}> with the regular expenses to compute for the given year and month.
     */
    private List<Expense> getRegularExpenses(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date initDate = cal.getTime();
        cal.set(year, month - 1, 1, 0, 0, 0);

        Iterable<Expense> activeExpenses = expenseRepository.findActiveRegularExpenseByDate(initDate, cal.getTime());

        List<Expense> result = new ArrayList<>();
        for (Expense expense : activeExpenses) {
            switch (expense.getFrequency()) {
                case ANNUAL:
                    if (month == 0) {
                        result.add(expense);
                    }
                    break;
                case MONTHLY:
                    result.add(expense);
                    break;
                case WEEKLY:
                    processWeeklyExpense(month, cal, result, expense);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * Processes the given expense to add it to the list of expenses if it should be added considering the dates.
     *
     * @param month    the month of the requested expenses.
     * @param cal      {@link Calendar} instance to calculate dates.
     * @param expenses the list of expenses where the expense has to be added.
     * @param expense  the expense to be processed.
     */
    private void processWeeklyExpense(int month, Calendar cal, List<Expense> expenses, Expense expense) {
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.add(Calendar.DATE, 1);
        }
        while ((cal.get(Calendar.MONTH) + 1) == month) {
            if (expense.getExpenseDate().before(cal.getTime()) || expense.getExpenseDate().equals(cal.getTime())) {
                expenses.add(expense);
            }
            cal.add(Calendar.DATE, 7);
        }
    }
}
