package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmplLeave.
 */
@Entity
@Table(name = "empl_leave")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmplLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplLeaves", allowSetters = true)
    private Party employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplLeaves", allowSetters = true)
    private Party approver;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplLeaves", allowSetters = true)
    private EmplLeaveType leaveType;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplLeaves", allowSetters = true)
    private EmplLeaveReasonType reason;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplLeaves", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public EmplLeave description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public EmplLeave fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public EmplLeave thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Party getEmployee() {
        return employee;
    }

    public EmplLeave employee(Party party) {
        this.employee = party;
        return this;
    }

    public void setEmployee(Party party) {
        this.employee = party;
    }

    public Party getApprover() {
        return approver;
    }

    public EmplLeave approver(Party party) {
        this.approver = party;
        return this;
    }

    public void setApprover(Party party) {
        this.approver = party;
    }

    public EmplLeaveType getLeaveType() {
        return leaveType;
    }

    public EmplLeave leaveType(EmplLeaveType emplLeaveType) {
        this.leaveType = emplLeaveType;
        return this;
    }

    public void setLeaveType(EmplLeaveType emplLeaveType) {
        this.leaveType = emplLeaveType;
    }

    public EmplLeaveReasonType getReason() {
        return reason;
    }

    public EmplLeave reason(EmplLeaveReasonType emplLeaveReasonType) {
        this.reason = emplLeaveReasonType;
        return this;
    }

    public void setReason(EmplLeaveReasonType emplLeaveReasonType) {
        this.reason = emplLeaveReasonType;
    }

    public Status getStatus() {
        return status;
    }

    public EmplLeave status(Status status) {
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
        if (!(o instanceof EmplLeave)) {
            return false;
        }
        return id != null && id.equals(((EmplLeave) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmplLeave{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
