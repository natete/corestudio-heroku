package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.ActivityBusinessLogic;
import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@RestController
@RequestMapping("/api/admin/activities")
public class ActivityRestClient extends BaseRestService<Activity> {

    @Autowired
    private ActivityBusinessLogic activityBusinessLogic;

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
