package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "time_sheet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "over_time_days")
    private Integer overTimeDays;

    @Column(name = "leaves")
    private Integer leaves;

    @Column(name = "unapplied_leaves")
    private Integer unappliedLeaves;

    @Column(name = "half_days")
    private Integer halfDays;

    @Column(name = "total_working_hours")
    private Double totalWorkingHours;

    @ManyToOne
    @JsonIgnoreProperties(value = "timeSheets", allowSetters = true)
    private CustomTimePeriod timePeriod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOverTimeDays() {
        return overTimeDays;
    }

    public TimeSheet overTimeDays(Integer overTimeDays) {
        this.overTimeDays = overTimeDays;
        return this;
    }

    public void setOverTimeDays(Integer overTimeDays) {
        this.overTimeDays = overTimeDays;
    }

    public Integer getLeaves() {
        return leaves;
    }

    public TimeSheet leaves(Integer leaves) {
        this.leaves = leaves;
        return this;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    public Integer getUnappliedLeaves() {
        return unappliedLeaves;
    }

    public TimeSheet unappliedLeaves(Integer unappliedLeaves) {
        this.unappliedLeaves = unappliedLeaves;
        return this;
    }

    public void setUnappliedLeaves(Integer unappliedLeaves) {
        this.unappliedLeaves = unappliedLeaves;
    }

    public Integer getHalfDays() {
        return halfDays;
    }

    public TimeSheet halfDays(Integer halfDays) {
        this.halfDays = halfDays;
        return this;
    }

    public void setHalfDays(Integer halfDays) {
        this.halfDays = halfDays;
    }

    public Double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public TimeSheet totalWorkingHours(Double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
        return this;
    }

    public void setTotalWorkingHours(Double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public CustomTimePeriod getTimePeriod() {
        return timePeriod;
    }

    public TimeSheet timePeriod(CustomTimePeriod customTimePeriod) {
        this.timePeriod = customTimePeriod;
        return this;
    }

    public void setTimePeriod(CustomTimePeriod customTimePeriod) {
        this.timePeriod = customTimePeriod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheet)) {
            return false;
        }
        return id != null && id.equals(((TimeSheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheet{" +
            "id=" + getId() +
            ", overTimeDays=" + getOverTimeDays() +
            ", leaves=" + getLeaves() +
            ", unappliedLeaves=" + getUnappliedLeaves() +
            ", halfDays=" + getHalfDays() +
            ", totalWorkingHours=" + getTotalWorkingHours() +
            "}";
    }
}
