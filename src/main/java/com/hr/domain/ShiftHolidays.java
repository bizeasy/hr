package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.hr.domain.enumeration.DayOfheWeek;

/**
 * A ShiftHolidays.
 */
@Entity
@Table(name = "shift_holidays")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShiftHolidays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_ofhe_week")
    private DayOfheWeek dayOfheWeek;

    @Column(name = "monthly_paid_leaves")
    private Integer monthlyPaidLeaves;

    @Column(name = "yearly_paid_leaves")
    private Integer yearlyPaidLeaves;

    @ManyToOne
    @JsonIgnoreProperties(value = "shiftHolidays", allowSetters = true)
    private Shift shift;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfheWeek getDayOfheWeek() {
        return dayOfheWeek;
    }

    public ShiftHolidays dayOfheWeek(DayOfheWeek dayOfheWeek) {
        this.dayOfheWeek = dayOfheWeek;
        return this;
    }

    public void setDayOfheWeek(DayOfheWeek dayOfheWeek) {
        this.dayOfheWeek = dayOfheWeek;
    }

    public Integer getMonthlyPaidLeaves() {
        return monthlyPaidLeaves;
    }

    public ShiftHolidays monthlyPaidLeaves(Integer monthlyPaidLeaves) {
        this.monthlyPaidLeaves = monthlyPaidLeaves;
        return this;
    }

    public void setMonthlyPaidLeaves(Integer monthlyPaidLeaves) {
        this.monthlyPaidLeaves = monthlyPaidLeaves;
    }

    public Integer getYearlyPaidLeaves() {
        return yearlyPaidLeaves;
    }

    public ShiftHolidays yearlyPaidLeaves(Integer yearlyPaidLeaves) {
        this.yearlyPaidLeaves = yearlyPaidLeaves;
        return this;
    }

    public void setYearlyPaidLeaves(Integer yearlyPaidLeaves) {
        this.yearlyPaidLeaves = yearlyPaidLeaves;
    }

    public Shift getShift() {
        return shift;
    }

    public ShiftHolidays shift(Shift shift) {
        this.shift = shift;
        return this;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShiftHolidays)) {
            return false;
        }
        return id != null && id.equals(((ShiftHolidays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShiftHolidays{" +
            "id=" + getId() +
            ", dayOfheWeek='" + getDayOfheWeek() + "'" +
            ", monthlyPaidLeaves=" + getMonthlyPaidLeaves() +
            ", yearlyPaidLeaves=" + getYearlyPaidLeaves() +
            "}";
    }
}
