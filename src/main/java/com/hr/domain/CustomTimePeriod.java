package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CustomTimePeriod.
 */
@Entity
@Table(name = "custom_time_period")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomTimePeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "thru_date")
    private Instant thruDate;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "period_name")
    private String periodName;

    @Column(name = "period_num")
    private Integer periodNum;

    @ManyToOne
    @JsonIgnoreProperties(value = "customTimePeriods", allowSetters = true)
    private PeriodType periodType;

    @ManyToOne
    @JsonIgnoreProperties(value = "customTimePeriods", allowSetters = true)
    private Party organisationParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "customTimePeriods", allowSetters = true)
    private CustomTimePeriod parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public CustomTimePeriod fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getThruDate() {
        return thruDate;
    }

    public CustomTimePeriod thruDate(Instant thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(Instant thruDate) {
        this.thruDate = thruDate;
    }

    public Boolean isIsClosed() {
        return isClosed;
    }

    public CustomTimePeriod isClosed(Boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getPeriodName() {
        return periodName;
    }

    public CustomTimePeriod periodName(String periodName) {
        this.periodName = periodName;
        return this;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Integer getPeriodNum() {
        return periodNum;
    }

    public CustomTimePeriod periodNum(Integer periodNum) {
        this.periodNum = periodNum;
        return this;
    }

    public void setPeriodNum(Integer periodNum) {
        this.periodNum = periodNum;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public CustomTimePeriod periodType(PeriodType periodType) {
        this.periodType = periodType;
        return this;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public Party getOrganisationParty() {
        return organisationParty;
    }

    public CustomTimePeriod organisationParty(Party party) {
        this.organisationParty = party;
        return this;
    }

    public void setOrganisationParty(Party party) {
        this.organisationParty = party;
    }

    public CustomTimePeriod getParent() {
        return parent;
    }

    public CustomTimePeriod parent(CustomTimePeriod customTimePeriod) {
        this.parent = customTimePeriod;
        return this;
    }

    public void setParent(CustomTimePeriod customTimePeriod) {
        this.parent = customTimePeriod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomTimePeriod)) {
            return false;
        }
        return id != null && id.equals(((CustomTimePeriod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomTimePeriod{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", isClosed='" + isIsClosed() + "'" +
            ", periodName='" + getPeriodName() + "'" +
            ", periodNum=" + getPeriodNum() +
            "}";
    }
}
