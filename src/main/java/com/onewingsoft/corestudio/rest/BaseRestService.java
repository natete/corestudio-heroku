package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.utils.HeaderUtil;
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
public abstract class BaseRestService<T extends BaseEntity> {

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<?> getAll() {
        return (Iterable<T>) this.getBusinessLogic().getAllEntities();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEntity(@PathVariable Long id) {
        Object entity = this.getBusinessLogic().getEntity(id);
        if (null != entity) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert("La entidad buscada no existe"))
                    .body(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveEntity(@RequestBody T entity) {
        try {
            Object result = this.getBusinessLogic().createEntity(entity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createEntityAlert(this.getMessage(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateEntity(@RequestBody T entity) {
        try {
            Object result = this.getBusinessLogic().updateEntity(entity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.updateEntityAlert(this.getMessage(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEntity(@PathVariable Long id) {
        try {
            Object result = this.getBusinessLogic().deleteEntity(id);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.deleteEntityAlert(this.getMessage(result)))
                    .body(null);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    protected abstract BaseBusinessLogic getBusinessLogic();

    protected abstract String getUri();

    protected abstract String getMessage(Object entity);
}
