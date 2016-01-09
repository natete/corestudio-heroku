package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ActivityDTO;
import com.onewingsoft.corestudio.model.Activity;
import com.onewingsoft.corestudio.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business Logic to manage activities.
 *
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@Service
public class ActivityBusinessLogic extends BaseBusinessLogic<Activity> {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Return all activities as a dto to include the number of related groups.
     *
     * @return all {@link ActivityDTO}
     */
    @Override
    public Iterable<ActivityDTO> getAllEntities() {
        Iterable<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> dtos = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityDTO dto = new ActivityDTO(activity);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Overrides @see BaseBusinessLogic#createEntity to make it return the dto.
     *
     * @param activity the activity to be persisted.
     * @return {@link ActivityDTO} the activity persisted.
     * @throws IllegalArgumentException if validation fails.
     */
    @Override
    public ActivityDTO createEntity(final Activity activity) throws IllegalArgumentException {
        return new ActivityDTO((Activity) super.createEntity(activity));
    }

    /**
     * Overrides @see BaseBusinessLogic#updateEntity to make it return the dto.
     *
     * @param activity the activity to be updated.
     * @return {@link ActivityDTO} the activity updated.
     * @throws IllegalArgumentException if validation fails.
     */
    @Override
    public ActivityDTO updateEntity(final Activity activity) throws IllegalArgumentException {
        return new ActivityDTO((Activity) super.updateEntity(activity));
    }

    /**
     * Retrieve all group activities.
     *
     * @return all group activities.
     */
    public Iterable<Activity> getGroupActivities() {
        return activityRepository.findByGroupActivity(true);
    }

    @Override
    protected void validateEntity(Activity entity) throws IllegalArgumentException {
        if (entity.getName() == null) {
            throw new IllegalArgumentException("El nombre es necesario");
        }
    }

    @Override
    protected PagingAndSortingRepository<Activity, Long> getRepository() {
        return this.activityRepository;
    }
}
