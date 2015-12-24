package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.ActivityBusinessLogic;
import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.dto.ActivityDTO;
import com.onewingsoft.corestudio.model.Activity;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@RestController
@RequestMapping("/api/admin/activities")
public class ActivityRestClient extends BaseRestService<Activity> {

    @Autowired
    private ActivityBusinessLogic activityBusinessLogic;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Activity> getAllActivities() {
        return super.getAll();
    }

    @RequestMapping(value = "/getAllDtos", method = RequestMethod.GET)
    public Iterable<ActivityDTO> getAllDtos() {
        return activityBusinessLogic.getAllDtos();
    }

    @RequestMapping(value = "/getGroupActivities", method = RequestMethod.GET)
    public Iterable<Activity> getGroupActivities() {
        return activityBusinessLogic.getGroupActivities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ActivityDTO> createActivity(@Validated @RequestBody final Activity activity) {
        try {
            ActivityDTO result = activityBusinessLogic.saveActivity(activity);
            return ResponseEntity.created(new URI("/api/admin/activities" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(" actividad ", result.getName().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ActivityDTO> updateActivity(@Validated @RequestBody final Activity activity) {
        try {
            ActivityDTO result = activityBusinessLogic.updateActivity(activity);
            return ResponseEntity.created(new URI("/api/admin/activity" + result.getId()))
                    .headers(HeaderUtil.createEntityUpdateAlert(" actividad ", result.getName().toString())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable final Long id) {
        super.deleteEntity(id);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return this.activityBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/admin/activities";
    }

    @Override
    protected String getEntityName() {
        return " actividad ";
    }

    @Override
    protected String getParameter(Activity entity) {
        return entity.getName().toString();
    }
}
