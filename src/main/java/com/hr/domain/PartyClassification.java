package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PartyClassification.
 */
@Entity
@Table(name = "party_classification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "thru_date")
    private ZonedDateTime thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyClassifications", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyClassifications", allowSetters = true)
    private PartyClassificationGroup classificationGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public PartyClassification fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public PartyClassification thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Party getParty() {
        return party;
    }

    public PartyClassification party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public PartyClassificationGroup getClassificationGroup() {
        return classificationGroup;
    }

    public PartyClassification classificationGroup(PartyClassificationGroup partyClassificationGroup) {
        this.classificationGroup = partyClassificationGroup;
        return this;
    }

    public void setClassificationGroup(PartyClassificationGroup partyClassificationGroup) {
        this.classificationGroup = partyClassificationGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyClassification)) {
            return false;
        }
        return id != null && id.equals(((PartyClassification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyClassification{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
