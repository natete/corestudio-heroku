package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.ActivityBusinessLogic;
import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.dto.ActivityDTO;
import com.onewingsoft.corestudio.model.Activity;
import com.onewingsoft.corestudio.utils.CorestudioException;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import com.onewingsoft.corestudio.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@RestController
@RequestMapping("/api/admin/activities")
public class ActivityRestService extends BaseRestService<Activity> {

    @Autowired
    private ActivityBusinessLogic activityBusinessLogic;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Page<ActivityDTO> getAllDtos(Integer page, Integer size, String sortBy, String direction) {
        return activityBusinessLogic.getAllDtos(page, size, sortBy, direction);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody Activity activity) {
        try {
            ActivityDTO activityDTO = activityBusinessLogic.createActivity(activity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.createEntityAlert(this.getMessage(activityDTO)))
                    .body(activityDTO);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ActivityDTO> updateActivity(@RequestBody Activity activity) {
        try {
            ActivityDTO activityDTO = activityBusinessLogic.updateActivity(activity);
            return ResponseEntity.created(new URI(this.getUri()))
                    .headers(HeaderUtil.updateEntityAlert(this.getMessage(activityDTO)))
                    .body(activityDTO);
        } catch (URISyntaxException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CorestudioException e) {
            LoggerUtil.writeErrorLog(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.errorAlert(e.getMessage()))
                    .body(null);
        }
    }

    @RequestMapping(value = "/getGroupActivities", method = RequestMethod.GET)
    public Iterable<Activity> getGroupActivities() {
        return activityBusinessLogic.getGroupActivities();
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
    protected String getMessage(Object activity) {
        return " la actividad " + activity.toString();
    }
}
