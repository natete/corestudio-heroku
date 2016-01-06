package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {

    @Column
    @NotNull
    private String name;

    @Column
    private boolean groupActivity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    @JsonIgnore
    private Set<Group> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGroupActivity() {
        return groupActivity;
    }

    public void setGroupActivity(boolean groupActivity) {
        this.groupActivity = groupActivity;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return name;
    }
}
