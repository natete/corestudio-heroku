package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.AccountDTO;
import com.onewingsoft.corestudio.dto.IncomesDTO;
import com.onewingsoft.corestudio.dto.SalaryDTO;
import com.onewingsoft.corestudio.model.MonthlySession;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.model.Professor;
import com.onewingsoft.corestudio.repository.MonthlySessionRepository;
import com.onewingsoft.corestudio.repository.PassRepository;
import com.onewingsoft.corestudio.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
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

    public AccountDTO getAccounts(int year, int month) {
        AccountDTO result = new AccountDTO();
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

        result.addIncomes(groupAccounting);
        result.addIncomes(individualAccounting);

        Iterable<Professor> professors = professorRepository.findAll();

        for (Professor professor : professors) {
            MonthlySession monthlySalary = monthlySessionRepository.findByProfessorIdAndYearAndMonth(professor.getId(), year, month);
            if(monthlySalary != null) {
                SalaryDTO salary = new SalaryDTO(professor.toString(), monthlySalary.getNumberOfSessions());
                result.addSalary(salary);
            }
        }
        return result;
    }

    private void processPass(int month, IncomesDTO accounting, Pass pass) {
        Calendar cal = Calendar.getInstance();

        for (Date date : pass.getConsumedDates()) {
            cal.setTime(date);
            if (cal.get(Calendar.MONTH) + 1 == month) {
                accounting.addPassTypeIncome(pass.getPassType().getActivity().getName(), pass.getPassType().toString(), pass.getPricePerSession());
            }
        }
    }
}
