package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
public abstract class BaseBusinessLogic<T extends BaseEntity> {

    public Iterable<T> getAllEntities() {
        return this.getRepository().findAll();
    }

    public T getEntity(final Long id) {
        return (T) this.getRepository().findOne(id);
    }

    public T createEntity(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            this.validateEntity(entity);
            entity = processEntity(entity);
            return (T) this.getRepository().save(entity);
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    public T updateEntity(final T entity) throws IllegalArgumentException {
        this.validateEntity(entity);
        BaseEntity persistedEntity = (BaseEntity) this.getRepository().findOne(entity.getId());
        if(persistedEntity == null) {
            throw new IllegalArgumentException("La entidad que quiere actualizar no existe");
        } else {
            return (T) this.getRepository().save(entity);
        }
    }

    protected abstract T processEntity(T entity);

    public void deleteEntity(final Long id) {
        this.getRepository().delete(id);
    }

    protected abstract void validateEntity(T entity) throws IllegalArgumentException;

    protected abstract PagingAndSortingRepository getRepository();
}
