package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.PassBusinessLogic;
import com.onewingsoft.corestudio.dto.ClientDateDTO;
import com.onewingsoft.corestudio.model.Pass;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@RestController
@RequestMapping("/api/pass")
public class PassRestService extends BaseRestService<Pass> {

    @Autowired
    private PassBusinessLogic passBusinessLogic;

    @RequestMapping(value = "/getByClient/{clientId}", method = RequestMethod.GET)
    public Iterable<Pass> getByClient(@PathVariable final Long clientId) {
        return passBusinessLogic.getByClient(clientId);
    }

    @RequestMapping(value = "/getByClientAndYear/{clientId}/{year}", method = RequestMethod.GET)
    public Iterable<Pass> getByClientAndYear(@PathVariable final Long clientId, @PathVariable final Integer year) {
        return passBusinessLogic.getByClientAndYear(clientId, year);
    }

    @RequestMapping(value = "/freezeDate", method = RequestMethod.POST)
    public ResponseEntity<Pass> freezeDate(@RequestBody ClientDateDTO clientDateDTO) {
        try {
            Pass result = passBusinessLogic.freezeDate(clientDateDTO);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createAlert("Se ha congelado la fecha: " + clientDateDTO.toString()))
                    .body(result);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }

    }

    @RequestMapping(value = "/consumeDate", method = RequestMethod.POST)
    public ResponseEntity<Pass> consumeDate(@RequestBody ClientDateDTO clientDateDTO) {
        try {
            Pass result = passBusinessLogic.consumeDate(clientDateDTO);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createAlert("Se ha consumido la fecha: " + clientDateDTO.toString()))
                    .body(result);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/releaseDate", method = RequestMethod.POST)
    public ResponseEntity<Pass> releaseDate(@RequestBody ClientDateDTO clientDateDTO) {
        try {
            Pass result = passBusinessLogic.releaseDate(clientDateDTO);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createAlert("Se ha liberado la fecha: " + clientDateDTO.toString()))
                    .body(result);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createAlert(e.getMessage()))
                    .body(null);
        }
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
    protected String getMessage(Object pass) {
        return " el abono " + pass.toString();
    }
}
