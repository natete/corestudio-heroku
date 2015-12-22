package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.ActivityBusinessLogic;
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
@RequestMapping("/api/admin")
public class ActivityRestClient {

    @Autowired
    private ActivityBusinessLogic activityBusinessLogic;

    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    public Iterable<ActivityDTO> getAllActivities() {
        return activityBusinessLogic.getAllActivities();
    }

    @RequestMapping(value = "/activities/getGroupActivities", method = RequestMethod.GET)
    public Iterable<Activity> getGroupActivities(){
        return activityBusinessLogic.getGroupActivities();
    }

    @RequestMapping(value = "/activities", method = RequestMethod.POST)
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

    @RequestMapping(value = "/activities", method = RequestMethod.PUT)
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


    @RequestMapping(value = "/activities/{id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable final Long id) {
        activityBusinessLogic.deleteActivity(id);
    }
}
