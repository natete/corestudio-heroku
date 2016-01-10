package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long basePrice;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    @JsonIgnore
    public Long getBasePrice() {
        return basePrice;
    }

    @JsonIgnore
    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Returns persisted Long price converted to double adding decimals
     * @return base price with decimals
     */
    @Transient
    public double getMoney() {
        return (double) basePrice / 100;
    }

    /**
     * Sets the price removing the decimals to convert it to Long
     * @param price base price with decimals
     */
    @Transient
    public void setMoney(double price) {
        this.basePrice = Math.round(price * 100);
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

    @Override
    public String toString() {
        return activity.getName() + " - " + numberOfSessions + " sesiones";
    }

}
