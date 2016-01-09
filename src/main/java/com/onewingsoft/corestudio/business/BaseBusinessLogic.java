package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.utils.LoggingUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Provides a common pattern to perform CRUD operations using Template Method Pattern.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
public abstract class BaseBusinessLogic<T extends BaseEntity> {

    /**
     * Retrieves all entities of the given T class.
     *
     * @return {@link Iterable} of entities.
     */
    public Iterable<?> getAllEntities() {
        return this.getRepository().findAll();
    }

    /**
     * Retrieves a T entity with the given id.
     *
     * @param id the id of the entity to be retrieved.
     * @return the requested entity or null.
     */
    public T getEntity(final Long id) {
        return this.getRepository().findOne(id);
    }

    /**
     * Persists the given entity if it passes validation and if it does not exist.
     *
     * @param entity the entity to be persisted.
     * @return An object that represents the persisted entity. It is an object to enable extension.
     * @throws IllegalArgumentException if validation fails.
     */
    public Object createEntity(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            this.validateEntity(entity);
            entity = this.getRepository().save(entity);
            LoggingUtil.writeInfoLog("Created entity " + entity.toString());
            return entity;
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    /**
     * Updates the given entity if it passes validation and if it exists in the database.
     *
     * @param entity the entity to be updated.
     * @return An object that represents the updated entity. It is an object to enable extension.
     * @throws IllegalArgumentException if validation fails.
     */
    public Object updateEntity(final T entity) throws IllegalArgumentException {
        this.validateEntity(entity);
        T persistedEntity = this.getRepository().findOne(entity.getId());
        if (persistedEntity == null) {
            throw new IllegalArgumentException("La entidad que quiere actualizar no existe");
        } else {
            persistedEntity = this.getRepository().save(entity);
            LoggingUtil.writeInfoLog("Updated entity " + entity.toString());
            return persistedEntity;
        }
    }

    /**
     * Deletes the entity with the given id from database.
     *
     * @param id the id of the entity to be deleted.
     * @return The deleted entity.
     * @throws IllegalArgumentException if the entity does not exist.
     */
    public T deleteEntity(final Long id) throws IllegalArgumentException {
        try {
            T entity = getEntity(id);
            this.getRepository().delete(id);
            LoggingUtil.writeInfoLog("Deleted entity " + entity.toString());
            return entity;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("La entidad que quiere eliminar no existe");
        }
    }

    /**
     * Validates the given entity.
     *
     * @param entity the entity to be validated.
     * @throws IllegalArgumentException if validation fails.
     */
    protected abstract void validateEntity(T entity) throws IllegalArgumentException;

    /**
     * Get the repository to perform database actions.
     *
     * @return {@link PagingAndSortingRepository} repository.
     */
    protected abstract PagingAndSortingRepository<T, Long> getRepository();
}
