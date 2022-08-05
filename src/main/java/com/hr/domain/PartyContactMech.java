package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PartyContactMech.
 */
@Entity
@Table(name = "party_contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyContactMech implements Serializable {

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
    @JsonIgnoreProperties(value = "partyContactMeches", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMeches", allowSetters = true)
    private ContactMech contactMech;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMeches", allowSetters = true)
    private ContactMechPurpose contactMechPurpose;

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

    public PartyContactMech fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public PartyContactMech thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Party getParty() {
        return party;
    }

    public PartyContactMech party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public PartyContactMech contactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
        return this;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }

    public ContactMechPurpose getContactMechPurpose() {
        return contactMechPurpose;
    }

    public PartyContactMech contactMechPurpose(ContactMechPurpose contactMechPurpose) {
        this.contactMechPurpose = contactMechPurpose;
        return this;
    }

    public void setContactMechPurpose(ContactMechPurpose contactMechPurpose) {
        this.contactMechPurpose = contactMechPurpose;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyContactMech)) {
            return false;
        }
        return id != null && id.equals(((PartyContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyContactMech{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
