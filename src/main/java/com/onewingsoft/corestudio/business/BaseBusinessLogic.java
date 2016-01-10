package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * @param page the page number to be returned.
     * @param size the maximum results.
     * @param sortBy the sort condition.
     * @param direction the sort direction.
     * @return {@link Page} of entities.
     */
    public Page<T> getAllEntities(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = null;
        if (sortBy != null) {
            sort = new Sort(Sort.Direction.fromString(direction), sortBy);
        }
        Pageable pageRequest = null;
        if(page != null) {
            pageRequest = new PageRequest(page, size, sort);
        }
        return this.getRepository().findAll(pageRequest);
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
     * @throws CorestudioException if validation fails.
     */
    public T createEntity(T entity) throws CorestudioException {
        if (entity.getId() == null) {
            this.validateEntity(entity);
            T persistedEntity = this.getRepository().save(entity);
            LoggerUtil.writeInfoLog("Created entity " + persistedEntity.toString());
            return persistedEntity;
        } else {
            throw new CorestudioException("Un nuevo registro no debe tener id");
        }
    }

    /**
     * Updates the given entity if it passes validation and if it exists in the database.
     *
     * @param entity the entity to be updated.
     * @return An object that represents the updated entity. It is an object to enable extension.
     * @throws CorestudioException if validation fails.
     */
    public T updateEntity(final T entity) throws CorestudioException {
        this.validateEntity(entity);
        T persistedEntity = this.getRepository().findOne(entity.getId());
        if (persistedEntity == null) {
            throw new CorestudioException("La entidad que quiere actualizar no existe");
        } else {
            persistedEntity = this.getRepository().save(entity);
            LoggerUtil.writeInfoLog("Updated entity " + entity.toString());
            return persistedEntity;
        }
    }

    /**
     * Deletes the entity with the given id from database.
     *
     * @param id the id of the entity to be deleted.
     * @return The deleted entity.
     * @throws CorestudioException if the entity does not exist.
     */
    public T deleteEntity(final Long id) throws CorestudioException {
        try {
            T entity = getEntity(id);
            this.getRepository().delete(id);
            LoggerUtil.writeInfoLog("Deleted entity " + entity.toString());
            return entity;
        } catch (EmptyResultDataAccessException e) {
            String message = "La entidad que quiere eliminar no existe";
            LoggerUtil.writeErrorLog(message, e);
            throw new CorestudioException(message);
        }
    }

    /**
     * Validates the given entity.
     *
     * @param entity the entity to be validated.
     * @throws CorestudioException if validation fails.
     */
    protected abstract void validateEntity(T entity) throws CorestudioException;

    /**
     * Get the repository to perform database actions.
     *
     * @return {@link PagingAndSortingRepository} repository.
     */
    protected abstract PagingAndSortingRepository<T, Long> getRepository();
}
