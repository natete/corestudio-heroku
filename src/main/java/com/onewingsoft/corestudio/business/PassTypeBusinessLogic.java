package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.PassType;
import com.onewingsoft.corestudio.repository.PassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@Service
public class PassTypeBusinessLogic extends BaseBusinessLogic {

    @Autowired
    private PassTypeRepository repository;


    @Override
    protected void validateEntity(BaseEntity entity) throws IllegalArgumentException {
        PassType passType = (PassType) entity;
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return repository;
    }
}
