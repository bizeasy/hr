package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Facility.
 */
@Entity
@Table(name = "facility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facility implements Serializable {

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

    @Size(max = 20)
    @Column(name = "facility_code", length = 20)
    private String facilityCode;

    @Column(name = "facility_size", precision = 21, scale = 2)
    private BigDecimal facilitySize;

    @Size(max = 25)
    @Column(name = "cost_center_code", length = 25)
    private String costCenterCode;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private FacilityType facilityType;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Facility parentFacility;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Party ownerParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private FacilityGroup facilityGroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Uom sizeUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private GeoPoint geoPoint;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private InventoryItemType inventoryItemType;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Status usageStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private User reviewedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private User approvedBy;

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

    public Facility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Facility description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public Facility facilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
        return this;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public BigDecimal getFacilitySize() {
        return facilitySize;
    }

    public Facility facilitySize(BigDecimal facilitySize) {
        this.facilitySize = facilitySize;
        return this;
    }

    public void setFacilitySize(BigDecimal facilitySize) {
        this.facilitySize = facilitySize;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public Facility costCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
        return this;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public Facility facilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
        return this;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public Facility getParentFacility() {
        return parentFacility;
    }

    public Facility parentFacility(Facility facility) {
        this.parentFacility = facility;
        return this;
    }

    public void setParentFacility(Facility facility) {
        this.parentFacility = facility;
    }

    public Party getOwnerParty() {
        return ownerParty;
    }

    public Facility ownerParty(Party party) {
        this.ownerParty = party;
        return this;
    }

    public void setOwnerParty(Party party) {
        this.ownerParty = party;
    }

    public FacilityGroup getFacilityGroup() {
        return facilityGroup;
    }

    public Facility facilityGroup(FacilityGroup facilityGroup) {
        this.facilityGroup = facilityGroup;
        return this;
    }

    public void setFacilityGroup(FacilityGroup facilityGroup) {
        this.facilityGroup = facilityGroup;
    }

    public Uom getSizeUom() {
        return sizeUom;
    }

    public Facility sizeUom(Uom uom) {
        this.sizeUom = uom;
        return this;
    }

    public void setSizeUom(Uom uom) {
        this.sizeUom = uom;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public Facility geoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
        return this;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public InventoryItemType getInventoryItemType() {
        return inventoryItemType;
    }

    public Facility inventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
        return this;
    }

    public void setInventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
    }

    public Status getStatus() {
        return status;
    }

    public Facility status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getUsageStatus() {
        return usageStatus;
    }

    public Facility usageStatus(Status status) {
        this.usageStatus = status;
        return this;
    }

    public void setUsageStatus(Status status) {
        this.usageStatus = status;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public Facility reviewedBy(User user) {
        this.reviewedBy = user;
        return this;
    }

    public void setReviewedBy(User user) {
        this.reviewedBy = user;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public Facility approvedBy(User user) {
        this.approvedBy = user;
        return this;
    }

    public void setApprovedBy(User user) {
        this.approvedBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facility)) {
            return false;
        }
        return id != null && id.equals(((Facility) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facility{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", facilityCode='" + getFacilityCode() + "'" +
            ", facilitySize=" + getFacilitySize() +
            ", costCenterCode='" + getCostCenterCode() + "'" +
            "}";
    }
}
