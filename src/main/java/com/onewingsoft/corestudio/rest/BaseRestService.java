package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@RestController
public abstract class BaseRestService<T extends BaseEntity> {

    @RequestMapping(method = RequestMethod.GET)
    public Page<T> getAll(@PathParam("page") Integer page, @PathParam("size") Integer size, @PathParam("sortBy") String sortBy, @PathParam("direction") String direction) {
        return this.getBusinessLogic().getAllEntities(page, size, sortBy, direction);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<T> getEntity(@PathVariable Long id) {
        T entity = this.getBusinessLogic().getEntity(id);
        if (null != entity) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert("La entidad buscada no existe"))
                    .body(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<T> saveEntity(@RequestBody T entity) {
        try {
            T result = this.getBusinessLogic().createEntity(entity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createEntityAlert(this.getMessage(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<T> updateEntity(@RequestBody T entity) {
        try {
            T result = this.getBusinessLogic().updateEntity(entity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.updateEntityAlert(this.getMessage(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<T> deleteEntity(@PathVariable Long id) {
        try {
            T result = this.getBusinessLogic().deleteEntity(id);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.deleteEntityAlert(this.getMessage(result)))
                    .body(null);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    protected abstract BaseBusinessLogic<T> getBusinessLogic();

    protected abstract String getUri();

    protected abstract String getMessage(Object entity);
}
