package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A EmplPosition.
 */
@Entity
@Table(name = "empl_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmplPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "max_budget", precision = 21, scale = 2)
    private BigDecimal maxBudget;

    @Column(name = "min_budget", precision = 21, scale = 2)
    private BigDecimal minBudget;

    @Column(name = "estimated_from_date")
    private LocalDate estimatedFromDate;

    @Column(name = "estimated_thru_date")
    private LocalDate estimatedThruDate;

    @Column(name = "paid_job")
    private Boolean paidJob;

    @Column(name = "is_fulltime")
    private Boolean isFulltime;

    @Column(name = "is_temporary")
    private Boolean isTemporary;

    @Column(name = "actual_from_date")
    private LocalDate actualFromDate;

    @Column(name = "actual_thru_date")
    private LocalDate actualThruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositions", allowSetters = true)
    private EmplPositionType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositions", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositions", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMaxBudget() {
        return maxBudget;
    }

    public EmplPosition maxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
        return this;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }

    public BigDecimal getMinBudget() {
        return minBudget;
    }

    public EmplPosition minBudget(BigDecimal minBudget) {
        this.minBudget = minBudget;
        return this;
    }

    public void setMinBudget(BigDecimal minBudget) {
        this.minBudget = minBudget;
    }

    public LocalDate getEstimatedFromDate() {
        return estimatedFromDate;
    }

    public EmplPosition estimatedFromDate(LocalDate estimatedFromDate) {
        this.estimatedFromDate = estimatedFromDate;
        return this;
    }

    public void setEstimatedFromDate(LocalDate estimatedFromDate) {
        this.estimatedFromDate = estimatedFromDate;
    }

    public LocalDate getEstimatedThruDate() {
        return estimatedThruDate;
    }

    public EmplPosition estimatedThruDate(LocalDate estimatedThruDate) {
        this.estimatedThruDate = estimatedThruDate;
        return this;
    }

    public void setEstimatedThruDate(LocalDate estimatedThruDate) {
        this.estimatedThruDate = estimatedThruDate;
    }

    public Boolean isPaidJob() {
        return paidJob;
    }

    public EmplPosition paidJob(Boolean paidJob) {
        this.paidJob = paidJob;
        return this;
    }

    public void setPaidJob(Boolean paidJob) {
        this.paidJob = paidJob;
    }

    public Boolean isIsFulltime() {
        return isFulltime;
    }

    public EmplPosition isFulltime(Boolean isFulltime) {
        this.isFulltime = isFulltime;
        return this;
    }

    public void setIsFulltime(Boolean isFulltime) {
        this.isFulltime = isFulltime;
    }

    public Boolean isIsTemporary() {
        return isTemporary;
    }

    public EmplPosition isTemporary(Boolean isTemporary) {
        this.isTemporary = isTemporary;
        return this;
    }

    public void setIsTemporary(Boolean isTemporary) {
        this.isTemporary = isTemporary;
    }

    public LocalDate getActualFromDate() {
        return actualFromDate;
    }

    public EmplPosition actualFromDate(LocalDate actualFromDate) {
        this.actualFromDate = actualFromDate;
        return this;
    }

    public void setActualFromDate(LocalDate actualFromDate) {
        this.actualFromDate = actualFromDate;
    }

    public LocalDate getActualThruDate() {
        return actualThruDate;
    }

    public EmplPosition actualThruDate(LocalDate actualThruDate) {
        this.actualThruDate = actualThruDate;
        return this;
    }

    public void setActualThruDate(LocalDate actualThruDate) {
        this.actualThruDate = actualThruDate;
    }

    public EmplPositionType getType() {
        return type;
    }

    public EmplPosition type(EmplPositionType emplPositionType) {
        this.type = emplPositionType;
        return this;
    }

    public void setType(EmplPositionType emplPositionType) {
        this.type = emplPositionType;
    }

    public Party getParty() {
        return party;
    }

    public EmplPosition party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Status getStatus() {
        return status;
    }

    public EmplPosition status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmplPosition)) {
            return false;
        }
        return id != null && id.equals(((EmplPosition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmplPosition{" +
            "id=" + getId() +
            ", maxBudget=" + getMaxBudget() +
            ", minBudget=" + getMinBudget() +
            ", estimatedFromDate='" + getEstimatedFromDate() + "'" +
            ", estimatedThruDate='" + getEstimatedThruDate() + "'" +
            ", paidJob='" + isPaidJob() + "'" +
            ", isFulltime='" + isIsFulltime() + "'" +
            ", isTemporary='" + isIsTemporary() + "'" +
            ", actualFromDate='" + getActualFromDate() + "'" +
            ", actualThruDate='" + getActualThruDate() + "'" +
            "}";
    }
}
