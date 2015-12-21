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
    @NotNull
    private Integer basePrice;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    @JsonIgnore
    private Set<Group> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
