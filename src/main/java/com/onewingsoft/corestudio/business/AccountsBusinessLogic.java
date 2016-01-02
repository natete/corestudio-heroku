package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.AccountsDTO;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.repository.PassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
@Service
public class AccountsBusinessLogic {

    @Autowired
    private PassRepository passRepository;


    public Iterable<AccountsDTO> getAccounts(int year, int month) {

        AccountsDTO groupAccounting = new AccountsDTO("Actividades de grupo");
        AccountsDTO individualAccounting = new AccountsDTO("Actividades individuales");

        Iterable<Pass> passes = passRepository.findUsedPasses(year, month);


        for (Pass pass : passes) {
            if (pass.isGroupPass()) {
                processPass(month, groupAccounting, pass);
            } else {
                processPass(month, individualAccounting, pass);
            }
        }

        List<AccountsDTO> result = new ArrayList<>();
        result.add(individualAccounting);
        result.add(groupAccounting);
        return result;
    }

    private void processPass(int month, AccountsDTO accounting, Pass pass) {
        Calendar cal = Calendar.getInstance();
//        ActivityAccountsDTO activityAccounts = accounting.getActivityAccounts(pass.getPassType().getActivity().getName());
//        if(activityAccounts == null) {
//            activityAccounts = new ActivityAccountsDTO();
//            accounting.putActivityAccount(pass.getPassType().getActivity().getName(),activityAccounts);
//        }
//
//        PassTypeAccountsDTO account = activityAccounts.getAccount(pass.getPassType().toString());
//        if (account == null) {
//            account = new PassTypeAccountsDTO();
//            activityAccounts.putAccount(pass.getPassType().toString(), account);
//        }

        for (Date date : pass.getConsumedDates()) {
            cal.setTime(date);
            if(cal.get(Calendar.MONTH) + 1 == month) {
                accounting.addPassTypeAccount(pass.getPassType().getActivity().getName(), pass.getPassType().toString(), pass.getPricePerSession());
//                account.increaseNumberOfSessions();
//                account.addToIncomes(pass.getPricePerSession());
            }
        }
    }
}
