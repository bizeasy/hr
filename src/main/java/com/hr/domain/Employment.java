package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Employment.
 */
@Entity
@Table(name = "employment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private Reason terminationReason;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private TerminationType terminationType;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private Party employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private Party fromParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private RoleType roleTypeFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "employments", allowSetters = true)
    private Party roleTypeTo;

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

    public Employment fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Employment thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Reason getTerminationReason() {
        return terminationReason;
    }

    public Employment terminationReason(Reason reason) {
        this.terminationReason = reason;
        return this;
    }

    public void setTerminationReason(Reason reason) {
        this.terminationReason = reason;
    }

    public TerminationType getTerminationType() {
        return terminationType;
    }

    public Employment terminationType(TerminationType terminationType) {
        this.terminationType = terminationType;
        return this;
    }

    public void setTerminationType(TerminationType terminationType) {
        this.terminationType = terminationType;
    }

    public Party getEmployee() {
        return employee;
    }

    public Employment employee(Party party) {
        this.employee = party;
        return this;
    }

    public void setEmployee(Party party) {
        this.employee = party;
    }

    public Party getFromParty() {
        return fromParty;
    }

    public Employment fromParty(Party party) {
        this.fromParty = party;
        return this;
    }

    public void setFromParty(Party party) {
        this.fromParty = party;
    }

    public RoleType getRoleTypeFrom() {
        return roleTypeFrom;
    }

    public Employment roleTypeFrom(RoleType roleType) {
        this.roleTypeFrom = roleType;
        return this;
    }

    public void setRoleTypeFrom(RoleType roleType) {
        this.roleTypeFrom = roleType;
    }

    public Party getRoleTypeTo() {
        return roleTypeTo;
    }

    public Employment roleTypeTo(Party party) {
        this.roleTypeTo = party;
        return this;
    }

    public void setRoleTypeTo(Party party) {
        this.roleTypeTo = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employment)) {
            return false;
        }
        return id != null && id.equals(((Employment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employment{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
