package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onewingsoft.corestudio.utils.Day;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/12/15.
 */
@Entity
@Table(name = "pass")
public class Pass extends BaseEntity {

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date initialDate;

    @Column
    @NotNull
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "passType_id")
    private PassType passType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ElementCollection(fetch = FetchType.EAGER)
    @Temporal(TemporalType.DATE)
    private List<Date> frozenDates = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Temporal(TemporalType.DATE)
    private List<Date> consumedDates = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Temporal(TemporalType.DATE)
    private List<Date> pendingDates = new ArrayList<>();

    @Column
    @Temporal(TemporalType.DATE)
    private Date lastDate;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Date> getFrozenDates() {
        return frozenDates;
    }

    public void setFrozenDates(List<Date> frozenDates) {
        this.frozenDates = frozenDates;
    }

    public List<Date> getConsumedDates() {
        return consumedDates;
    }

    public void setConsumedDates(List<Date> consumedDates) {
        this.consumedDates = consumedDates;
    }

    public List<Date> getPendingDates() {
        return pendingDates;
    }

    public void setPendingDates(List<Date> pendingDates) {
        this.pendingDates = pendingDates;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    @Transient
    @JsonIgnore
    public int getNumberOfSession() {
        return this.passType.getNumberOfSessions();
    }

    @Transient
    @JsonIgnore
    public boolean isGroupPass() {
        return this.passType.isGroupActivity();
    }

    @Transient
    @JsonIgnore
    public List<Day> getDays() {
        return this.group.getDays();
    }

    @Transient
    @JsonIgnore
    public int getPendingSessions() {
        return getNumberOfSession() - consumedDates.size();
    }

    public void addPendingDate(Date date) {
        pendingDates.add(date);
        Collections.sort(pendingDates);
    }

    public void addFrozenDate(Date date) {
        frozenDates.add(date);
        Collections.sort(frozenDates);
    }

    public void addConsumedDate(Date date) {
        consumedDates.add(date);
        Collections.sort(consumedDates);
    }

    public void freezeDate(Date date) {
        addFrozenDate(date);

        if (consumedDates.contains(date)) {
            consumedDates.remove(date);
        } else if (pendingDates.contains(date)) {
            pendingDates.remove(date);
        }
    }

    public void removeLastDate() {
        pendingDates.remove(lastDate);
        lastDate = pendingDates.get(pendingDates.size() - 1);
    }
}
