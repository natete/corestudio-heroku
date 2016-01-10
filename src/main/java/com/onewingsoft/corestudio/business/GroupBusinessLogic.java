package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.BaseEntity;
import com.onewingsoft.corestudio.model.Group;
import com.onewingsoft.corestudio.repository.GroupRepository;
import com.onewingsoft.corestudio.utils.CorestudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * Business logic to manage group operations.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@Service
public class GroupBusinessLogic extends BaseBusinessLogic<Group> {

    @Autowired
    private GroupRepository groupRepository;

    /**
     * @see BaseBusinessLogic#validateEntity(BaseEntity).
     */
    @Override
    protected void validateEntity(Group entity) throws CorestudioException {
        if (entity.getDays() == null || entity.getDays().isEmpty()) {
            throw new CorestudioException("Un grupo debe tener unas fechas asociadas");
        }
        if (entity.getHour() == null || entity.getHour() < 7 || entity.getHour() > 22) {
            throw new CorestudioException("La fecha no está presente o tiene un valor inválido");
        }
        if (entity.getActivity() == null) {
            throw new CorestudioException("Un grupo debe tener una actividad asociada");
        }
        if (entity.getLevel() == null) {
            throw new CorestudioException("Un grupo debe tener un nivel");
        }
    }

    /**
     * Retrieves all groups related to the activity with the given id.
     *
     * @param activityId the id of the activity to find groups.
     * @return list of {@link Group} related to the given activity.
     */
    public Iterable<Group> getByActivity(Long activityId) {
        return groupRepository.findByActivityId(activityId);
    }

    /**
     * @see BaseBusinessLogic#getRepository().
     */
    @Override
    protected PagingAndSortingRepository getRepository() {
        return this.groupRepository;
    }
}
