package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.AccountsBusinessLogic;
import com.onewingsoft.corestudio.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 01/01/16.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountsRestService {

    @Autowired
    private AccountsBusinessLogic accountsBusinessLogic;

    @RequestMapping(value = "/{year}/{month}", method = RequestMethod.GET)
    public AccountDTO getAccounts(@PathVariable final int year, @PathVariable final int month) {
        return accountsBusinessLogic.getAccounts(year, month);
    }
}
