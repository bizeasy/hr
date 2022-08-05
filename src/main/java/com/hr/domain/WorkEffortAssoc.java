package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A WorkEffortAssoc.
 */
@Entity
@Table(name = "work_effort_assoc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEffortAssoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "thru_date")
    private ZonedDateTime thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortAssocs", allowSetters = true)
    private WorkEffortAssocType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortAssocs", allowSetters = true)
    private WorkEffort weIdFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortAssocs", allowSetters = true)
    private WorkEffort weIdTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public WorkEffortAssoc sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public WorkEffortAssoc fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public WorkEffortAssoc thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public WorkEffortAssocType getType() {
        return type;
    }

    public WorkEffortAssoc type(WorkEffortAssocType workEffortAssocType) {
        this.type = workEffortAssocType;
        return this;
    }

    public void setType(WorkEffortAssocType workEffortAssocType) {
        this.type = workEffortAssocType;
    }

    public WorkEffort getWeIdFrom() {
        return weIdFrom;
    }

    public WorkEffortAssoc weIdFrom(WorkEffort workEffort) {
        this.weIdFrom = workEffort;
        return this;
    }

    public void setWeIdFrom(WorkEffort workEffort) {
        this.weIdFrom = workEffort;
    }

    public WorkEffort getWeIdTo() {
        return weIdTo;
    }

    public WorkEffortAssoc weIdTo(WorkEffort workEffort) {
        this.weIdTo = workEffort;
        return this;
    }

    public void setWeIdTo(WorkEffort workEffort) {
        this.weIdTo = workEffort;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEffortAssoc)) {
            return false;
        }
        return id != null && id.equals(((WorkEffortAssoc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortAssoc{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
