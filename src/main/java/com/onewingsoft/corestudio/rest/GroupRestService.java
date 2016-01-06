package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.GroupBusinessLogic;
import com.onewingsoft.corestudio.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@RestController
@RequestMapping(value = "/api/groups")
public class GroupRestService extends BaseRestService<Group> {

    @Autowired
    private GroupBusinessLogic groupBusinessLogic;

    @RequestMapping(value = "/getByActivity/{activityId}", method = RequestMethod.GET)
    public Iterable<Group> getByActivity(@PathVariable final Long activityId) {
        return groupBusinessLogic.getByActivity(activityId);
    }

    @Override
    protected BaseBusinessLogic getBusinessLogic() {
        return this.groupBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/admin/group";
    }

    @Override
    protected String getMessage(Object entity) {
        return " el grupo " + entity.toString();
    }
}
