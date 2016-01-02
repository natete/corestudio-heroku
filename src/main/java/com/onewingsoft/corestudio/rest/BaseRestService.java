package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
public abstract class BaseRestService<T extends BaseEntity> {

    protected Iterable<T> getAll() {
        return (Iterable<T>) this.getBusinessLogic().getAllEntities();
    }

    protected ResponseEntity<T> getEntity(Long id) {
        T entity = (T) this.getBusinessLogic().getEntity(id);
        if (null != entity) {
            return ResponseEntity.ok().body(entity);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    protected ResponseEntity<T> saveEntity(T entity) {
        try {
            T result = (T) this.getBusinessLogic().createEntity(entity);
            return ResponseEntity.created(new URI(this.getUri() + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(this.getEntityName(), this.getParameter(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    protected ResponseEntity<T> updateEntity(T entity) {
        try {
            T result = (T) this.getBusinessLogic().updateEntity(entity);
            return ResponseEntity.created(new URI(this.getUri() + result.getId()))
                    .headers(HeaderUtil.createEntityUpdateAlert(this.getEntityName(), this.getParameter(result)))
                    .body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    protected void deleteEntity(Long id) {
        this.getBusinessLogic().deleteEntity(id);
    }

    protected abstract BaseBusinessLogic getBusinessLogic();

    protected abstract String getUri();

    protected abstract String getEntityName();

    protected abstract String getParameter(T entity);
}
