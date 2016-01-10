package com.onewingsoft.corestudio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onewingsoft.corestudio.model.Activity;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 13/12/15.
 */
public class ActivityDTO {

    private Activity activity;
    private Integer relatedGroups;

    public ActivityDTO(Activity activity) {
        this.activity = activity;
        this.relatedGroups = activity.getGroups() == null ? 0 : activity.getGroups().size();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getRelatedGroups() {
        return relatedGroups;
    }

    public void setRelatedGroups(Integer relatedGroups) {
        this.relatedGroups = relatedGroups;
    }

    @JsonIgnore
    public Long getId() {
        return activity.getId();
    }

    @JsonIgnore
    public String getName() {
        return activity.getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
