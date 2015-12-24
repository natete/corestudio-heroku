package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.BaseBusinessLogic;
import com.onewingsoft.corestudio.business.GroupBusinessLogic;
import com.onewingsoft.corestudio.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@RestController
@RequestMapping(value = "/api/groups")
public class GroupRestService extends BaseRestService<Group> {

    @Autowired
    private GroupBusinessLogic groupBusinessLogic;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Group> getAllGroups() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@RequestBody final Group group) {
        return super.saveEntity(group);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Group> updateGroup(@RequestBody final Group group) {
        return super.updateEntity(group);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGroup(@PathVariable final Long id) {
        super.deleteEntity(id);
    }

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
    protected String getEntityName() {
        return " group ";
    }

    @Override
    protected String getParameter(Group entity) {
        return entity.getActivity().getName() + " " + entity.getDays().toString() + " " + entity.getHour();
    }
}
