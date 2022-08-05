package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A FacilityContactMech.
 */
@Entity
@Table(name = "facility_contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FacilityContactMech implements Serializable {

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
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
    private ContactMech contactMech;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
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

    public FacilityContactMech fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public FacilityContactMech thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityContactMech facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public FacilityContactMech contactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
        return this;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }

    public ContactMechPurpose getContactMechPurpose() {
        return contactMechPurpose;
    }

    public FacilityContactMech contactMechPurpose(ContactMechPurpose contactMechPurpose) {
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
        if (!(o instanceof FacilityContactMech)) {
            return false;
        }
        return id != null && id.equals(((FacilityContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityContactMech{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
