package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
public abstract class BaseBusinessLogic {

    public Iterable<? extends BaseEntity> getAllEntities() {
        return this.getRepository().findAll();
    }

    public BaseEntity getEntity(final Long id) {
        return (BaseEntity) this.getRepository().findOne(id);
    }

    public BaseEntity createEntity(final BaseEntity entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            this.validateEntity(entity);
            return (BaseEntity) this.getRepository().save(entity);
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    public BaseEntity updateEntity(final BaseEntity entity) throws IllegalArgumentException {
        this.validateEntity(entity);
        BaseEntity persistedEntity = (BaseEntity) this.getRepository().findOne(entity.getId());
        if(persistedEntity == null) {
            throw new IllegalArgumentException("La entidad que quiere actualizar no existe");
        } else {
            return (BaseEntity) this.getRepository().save(entity);
        }
    }

    public void deleteEntity(final Long id) {
        this.getRepository().delete(id);
    }

    protected abstract void validateEntity(BaseEntity entity) throws IllegalArgumentException;

    protected abstract PagingAndSortingRepository getRepository();
}
