package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContactMech.
 */
@Entity
@Table(name = "contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactMech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 255)
    @Column(name = "info_string", length = 255)
    private String infoString;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactMeches", allowSetters = true)
    private ContactMechType contactMechType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoString() {
        return infoString;
    }

    public ContactMech infoString(String infoString) {
        this.infoString = infoString;
        return this;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }

    public ContactMechType getContactMechType() {
        return contactMechType;
    }

    public ContactMech contactMechType(ContactMechType contactMechType) {
        this.contactMechType = contactMechType;
        return this;
    }

    public void setContactMechType(ContactMechType contactMechType) {
        this.contactMechType = contactMechType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactMech)) {
            return false;
        }
        return id != null && id.equals(((ContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactMech{" +
            "id=" + getId() +
            ", infoString='" + getInfoString() + "'" +
            "}";
    }
}
