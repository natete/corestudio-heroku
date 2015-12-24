package com.onewingsoft.corestudio.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@Entity
@Table(name = "passType")
public class PassType extends BaseEntity {

    @Column
    @NotNull
    private Integer numberOfSessions;

    @Column
    @NotNull
    private Integer basePrice;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Transient
    public boolean isGroupActivity() {
        return this.activity.isGroupActivity();
    }
}
