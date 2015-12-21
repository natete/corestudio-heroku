package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.dto.ActivityDTO;
import com.onewingsoft.corestudio.model.Activity;
import com.onewingsoft.corestudio.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@Service
public class ActivityBusinessLogic {

    @Autowired
    private ActivityRepository activityRepository;

    public Iterable<ActivityDTO> getAllActivities() {
        Iterable<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> dtos = new ArrayList<>();
        for (Activity activity: activities) {
            ActivityDTO dto = new ActivityDTO(activity);
            dtos.add(dto);
        }
        return dtos;
    }

    public ActivityDTO saveActivity(final Activity activity) throws IllegalArgumentException {
        if(activity.getId() == null) {
            validateActivity(activity);
            return new ActivityDTO(activityRepository.save(activity));
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    public ActivityDTO updateActivity(final Activity activity) throws IllegalArgumentException {
        validateActivity(activity);
        Activity persistedActivity = activityRepository.findOne(activity.getId());
        if(persistedActivity == null) {
            throw new IllegalArgumentException("La actividad que quiere actualizar no existe");
        } else {
            return new ActivityDTO(activityRepository.save(activity));
        }
    }

    public void deleteActivity(final Long id) {
        activityRepository.delete(id);
    }

    private void validateActivity(final Activity activity) throws IllegalArgumentException {
        if(activity.getName() == null) {
            throw new IllegalArgumentException("El nombre es necesario");
        }
        if(activity.getBasePrice() == null) {
            throw new IllegalArgumentException("El precio es necesario");
        }
    }
}
