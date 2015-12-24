package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.PassBusinessLogic;
import com.onewingsoft.corestudio.model.Pass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@RestController
@RequestMapping("/api/pass")
public class PassRestService extends BaseRestService<Pass> {

    @Autowired
    private PassBusinessLogic passBusinessLogic;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Pass> getAll() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Pass> savePass(@Validated @RequestBody final Pass pass) {
        return super.saveEntity(pass);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Pass> updatePass(@Validated @RequestBody final Pass pass) {
        return super.updateEntity(pass);
    }

    @RequestMapping(value = "/getByClient/{clientId}", method = RequestMethod.GET)
    public Iterable<Pass> getByClient(@PathVariable final Long clientId) {
        return passBusinessLogic.getByClient(clientId);
    }

    @RequestMapping(value = "/getByClientAndYear/{clientId}/{year}", method = RequestMethod.GET)
    public Iterable<Pass> getByClientAndYear(@PathVariable final Long clientId, @PathVariable final Integer year) {
        return passBusinessLogic.getByClientAndYear(clientId, year);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePass(@PathVariable final Long id) {
        super.deleteEntity(id);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return this.passBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/pass/";
    }

    @Override
    protected String getEntityName() {
        return " abono ";
    }

    @Override
    protected String getParameter(Pass pass) {
        return pass.getInitialDate() + " " + pass.getPrice();
    }
}
