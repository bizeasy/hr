package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A FacilityGroup.
 */
@Entity
@Table(name = "facility_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FacilityGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60, unique = true)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityGroups", allowSetters = true)
    private FacilityGroupType facilityGroupType;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityGroups", allowSetters = true)
    private FacilityGroup facilityGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FacilityGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public FacilityGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FacilityGroupType getFacilityGroupType() {
        return facilityGroupType;
    }

    public FacilityGroup facilityGroupType(FacilityGroupType facilityGroupType) {
        this.facilityGroupType = facilityGroupType;
        return this;
    }

    public void setFacilityGroupType(FacilityGroupType facilityGroupType) {
        this.facilityGroupType = facilityGroupType;
    }

    public FacilityGroup getFacilityGroup() {
        return facilityGroup;
    }

    public FacilityGroup facilityGroup(FacilityGroup facilityGroup) {
        this.facilityGroup = facilityGroup;
        return this;
    }

    public void setFacilityGroup(FacilityGroup facilityGroup) {
        this.facilityGroup = facilityGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacilityGroup)) {
            return false;
        }
        return id != null && id.equals(((FacilityGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
