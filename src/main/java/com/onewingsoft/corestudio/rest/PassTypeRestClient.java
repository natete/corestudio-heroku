package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.PassTypeBusinessLogic;
import com.onewingsoft.corestudio.model.PassType;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@RestController
@RequestMapping("api/admin")
public class PassTypeRestClient {

    @Autowired
    private PassTypeBusinessLogic passTypeBusinessLogic;

    @RequestMapping(value ="/passTypes", method = RequestMethod.GET)
    public Iterable<PassType> getAllPassTypes() {
        return (Iterable<PassType>) passTypeBusinessLogic.getAllEntities();
    }

    @RequestMapping(value = "/passTypes", method = RequestMethod.POST)
    public ResponseEntity<PassType> createPassType(@Validated @RequestBody final PassType passType) {
        try {
            PassType result = (PassType) passTypeBusinessLogic.createEntity(passType);
            return ResponseEntity.created(new URI("/api/admin/passType" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(" tipo de abono ", result.getId().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "/passTypes", method = RequestMethod.PUT)
    public ResponseEntity<PassType> updateActivity(@Validated @RequestBody final PassType passType) {
        try {
            PassType result = (PassType) passTypeBusinessLogic.updateEntity(passType);
            return ResponseEntity.created(new URI("/api/admin/activity" + result.getId()))
                    .headers(HeaderUtil.createEntityUpdateAlert(" tipo de abono ", result.getId().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "/passTypes/{id}", method = RequestMethod.DELETE)
    public void deletePassType(@PathVariable final Long id) {
        passTypeBusinessLogic.deleteEntity(id);
    }
}
