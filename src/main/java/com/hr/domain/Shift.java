package com.hr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Shift.
 */
@Entity
@Table(name = "shift")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "working_hrs")
    private Float workingHrs;

    @Column(name = "monthly_paid_leaves")
    private Integer monthlyPaidLeaves;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Shift name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public Shift fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public Shift toTime(Instant toTime) {
        this.toTime = toTime;
        return this;
    }

    public void setToTime(Instant toTime) {
        this.toTime = toTime;
    }

    public Float getWorkingHrs() {
        return workingHrs;
    }

    public Shift workingHrs(Float workingHrs) {
        this.workingHrs = workingHrs;
        return this;
    }

    public void setWorkingHrs(Float workingHrs) {
        this.workingHrs = workingHrs;
    }

    public Integer getMonthlyPaidLeaves() {
        return monthlyPaidLeaves;
    }

    public Shift monthlyPaidLeaves(Integer monthlyPaidLeaves) {
        this.monthlyPaidLeaves = monthlyPaidLeaves;
        return this;
    }

    public void setMonthlyPaidLeaves(Integer monthlyPaidLeaves) {
        this.monthlyPaidLeaves = monthlyPaidLeaves;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shift)) {
            return false;
        }
        return id != null && id.equals(((Shift) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shift{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fromTime='" + getFromTime() + "'" +
            ", toTime='" + getToTime() + "'" +
            ", workingHrs=" + getWorkingHrs() +
            ", monthlyPaidLeaves=" + getMonthlyPaidLeaves() +
            "}";
    }
}
