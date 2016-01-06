package com.onewingsoft.corestudio.model;

import com.onewingsoft.corestudio.utils.Frequency;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 06/01/16.
 */
@Entity
@Table(name = "expense")
public class Expense extends BaseEntity {

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    @NotNull
    @Min(0)
    private Long amount;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(frequency.getValue());
        result.append(" de ");
        result.append(amount / 100);
        result.append("€");

        return result.toString();
    }
}
