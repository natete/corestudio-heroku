package com.onewingsoft.corestudio.business;

import com.onewingsoft.corestudio.model.Group;
import com.onewingsoft.corestudio.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@Service
public class GroupBusinessLogic {

    @Autowired
    private GroupRepository groupRepository;

    public Iterable<Group> getAllGroups() {
       return groupRepository.findAll();
    }

    public Group saveGroup(final Group group) throws IllegalArgumentException {
        if(group.getId() == null) {
            validateGroup(group);
            return groupRepository.save(group);
        } else {
            throw new IllegalArgumentException("Un nuevo registro no debe tener id");
        }
    }

    public Group updateGroup(final Group group) throws IllegalArgumentException {
        validateGroup(group);
        Group persistedGroup = groupRepository.findOne(group.getId());
        if(persistedGroup == null) {
            throw new IllegalArgumentException("El grupo que quiere actualizar no existe");
        } else {
            return groupRepository.save(group);
        }
    }

    public void deleteGroup(final Long groupId) {
        groupRepository.delete(groupId);
    }

    private void validateGroup(final Group group) throws IllegalArgumentException {
        if(group.getDays() == null || group.getDays().isEmpty()) {
            throw new IllegalArgumentException("Un grupo debe tener unas fechas asociadas");
        }
        if(group.getHour() == null || group.getHour() < 7 || group.getHour() > 22) {
            throw new IllegalArgumentException("La fecha no está presente o tiene un valor inválido");
        }
        if(group.getActivity() == null) {
            throw new IllegalArgumentException("Un grupo debe tener una actividad asociada");
        }
        if(group.getLevel() == null) {
            throw new IllegalArgumentException("Un grupo debe tener un nivel");
        }
    }
}
