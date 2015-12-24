package com.onewingsoft.corestudio.business;

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
public class PassTypeBusinessLogic extends BaseBusinessLogic<PassType> {

    @Autowired
    private PassTypeRepository repository;


    @Override
    protected PassType processEntity(PassType passType) {
        return passType;
    }

    @Override
    protected void validateEntity(PassType passType) throws IllegalArgumentException {
        if(passType.getActivity() == null) {
            throw new IllegalArgumentException("Un tipo de bono debe estar asociado a una actividad");
        }
        if(passType.getNumberOfSessions() <= 0) {
            throw new IllegalArgumentException("Un tipo de bono debe tener un número positivo de sesiones");
        }
        if(passType.getBasePrice() <= 0) {
            throw new IllegalArgumentException("Un tipo de bono debe tener un precio base");
        }
    }

    @Override
    protected PagingAndSortingRepository getRepository() {
        return repository;
    }
}
