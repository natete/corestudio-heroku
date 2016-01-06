package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
public abstract class BaseBusinessLogic<T extends BaseEntity> {

    public Iterable<?> getAllEntities() {
        return this.getRepository().findAll();
    }

    public Object getEntity(final Long id) {
            return this.getRepository().findOne(id);
    }

    public Object createEntity(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            this.validateEntity(entity);
            entity = processEntity(entity);
            return this.getRepository().save(entity);
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    public Object updateEntity(final T entity) throws IllegalArgumentException {
        this.validateEntity(entity);
        BaseEntity persistedEntity = (BaseEntity) this.getRepository().findOne(entity.getId());
        if (persistedEntity == null) {
            throw new IllegalArgumentException("La entidad que quiere actualizar no existe");
        } else {
            return this.getRepository().save(entity);
        }
    }

    protected abstract T processEntity(T entity);

    public Object deleteEntity(final Long id) throws IllegalArgumentException {
        try {
            Object entity = getEntity(id);
            this.getRepository().delete(id);
            return entity;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("La entidad que quiere eliminar no existe");
        }
    }

    protected abstract void validateEntity(T entity) throws IllegalArgumentException;

    protected abstract PagingAndSortingRepository getRepository();
}
