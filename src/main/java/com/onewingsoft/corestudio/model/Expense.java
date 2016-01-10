package com.onewingsoft.corestudio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Date expenseDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    @Min(0)
    private Long amount;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Long getAmount() {
        return amount;
    }

    @JsonIgnore
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * Returns persisted Long price converted to double adding decimals
     * @return amount with decimals
     */
    @Transient
    public double getMoney() {
        return (double) amount / 100;
    }

    /**
     * Sets the price removing the decimals to convert it to Long
     * @param amount base price with decimals
     */
    @Transient
    public void setMoney(double amount) {
        this.amount = Math.round(amount * 100);
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
        result.append(" euros en concepto de ");
        result.append(description);

        return result.toString();
    }
}
