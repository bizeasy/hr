package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmplPositionFulfillment.
 */
@Entity
@Table(name = "empl_position_fulfillment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmplPositionFulfillment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionFulfillments", allowSetters = true)
    private EmplPosition emplPosition;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionFulfillments", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionFulfillments", allowSetters = true)
    private Party reportingTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionFulfillments", allowSetters = true)
    private Party managedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public EmplPositionFulfillment fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public EmplPositionFulfillment thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getComments() {
        return comments;
    }

    public EmplPositionFulfillment comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public EmplPosition getEmplPosition() {
        return emplPosition;
    }

    public EmplPositionFulfillment emplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
        return this;
    }

    public void setEmplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
    }

    public Party getParty() {
        return party;
    }

    public EmplPositionFulfillment party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Party getReportingTo() {
        return reportingTo;
    }

    public EmplPositionFulfillment reportingTo(Party party) {
        this.reportingTo = party;
        return this;
    }

    public void setReportingTo(Party party) {
        this.reportingTo = party;
    }

    public Party getManagedBy() {
        return managedBy;
    }

    public EmplPositionFulfillment managedBy(Party party) {
        this.managedBy = party;
        return this;
    }

    public void setManagedBy(Party party) {
        this.managedBy = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmplPositionFulfillment)) {
            return false;
        }
        return id != null && id.equals(((EmplPositionFulfillment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmplPositionFulfillment{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
