package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.PassType;
import com.onewingsoft.corestudio.repository.PassTypeRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Business logic to manage pass types.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@Service
public class PassTypeBusinessLogic extends BaseBusinessLogic<PassType> {

    @Autowired
    private PassTypeRepository repository;

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(PassType passType) throws CorestudioException {
        if (passType.getActivity() == null) {
            throw new CorestudioException("Un tipo de bono debe estar asociado a una actividad");
        }
        if (passType.getNumberOfSessions() <= 0) {
            throw new CorestudioException("Un tipo de bono debe tener un número positivo de sesiones");
        }
        if (passType.getBasePrice() <= 0) {
            throw new CorestudioException("Un tipo de bono debe tener un precio base");
        }
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository<PassType, Long> getRepository() {
        return repository;
    }
}
