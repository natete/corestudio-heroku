package com.onewingsoft.corestudio.rest;

import com.onewingsoft.corestudio.business.GroupBusinessLogic;
import com.onewingsoft.corestudio.model.Group;
import com.onewingsoft.corestudio.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@RestController
@RequestMapping(value = "/api")
public class GroupRestService {

    @Autowired
    private GroupBusinessLogic groupBusinessLogic;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Iterable<Group> getAllGroups() {
        return groupBusinessLogic.getAllGroups();
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@RequestBody final Group group) {
        try {
            Group result = groupBusinessLogic.saveGroup(group);
            return ResponseEntity.created(new URI("/api/admin/activities" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(" grupo ", result.getActivity().getName() + " " + result.getDays().toString() + " " + result.getHour())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "/groups", method = RequestMethod.PUT)
    public ResponseEntity<Group> updateGroup(@RequestBody final Group group) {
        try {
            Group result = groupBusinessLogic.updateGroup(group);
            return ResponseEntity.created(new URI("/api/admin/group" + result.getId()))
                    .headers(HeaderUtil.createEntityUpdateAlert(" grupo ", result.getActivity().getName() + " " + result.getDays().toString() + " " + result.getHour())).body(result);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE)
    public void deleteGroup(@PathVariable final Long id) {
        groupBusinessLogic.deleteGroup(id);
    }
}
