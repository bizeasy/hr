package com.hr.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.hr.domain.Facility} entity. This class is used
 * in {@link com.hr.web.rest.FacilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /facilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FacilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter facilityCode;

    private BigDecimalFilter facilitySize;

    private StringFilter costCenterCode;

    private LongFilter facilityTypeId;

    private LongFilter parentFacilityId;

    private LongFilter ownerPartyId;

    private LongFilter facilityGroupId;

    private LongFilter sizeUomId;

    private LongFilter geoPointId;

    private LongFilter inventoryItemTypeId;

    private LongFilter statusId;

    private LongFilter usageStatusId;

    private LongFilter reviewedById;

    private LongFilter approvedById;

    public FacilityCriteria() {
    }

    public FacilityCriteria(FacilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.facilityCode = other.facilityCode == null ? null : other.facilityCode.copy();
        this.facilitySize = other.facilitySize == null ? null : other.facilitySize.copy();
        this.costCenterCode = other.costCenterCode == null ? null : other.costCenterCode.copy();
        this.facilityTypeId = other.facilityTypeId == null ? null : other.facilityTypeId.copy();
        this.parentFacilityId = other.parentFacilityId == null ? null : other.parentFacilityId.copy();
        this.ownerPartyId = other.ownerPartyId == null ? null : other.ownerPartyId.copy();
        this.facilityGroupId = other.facilityGroupId == null ? null : other.facilityGroupId.copy();
        this.sizeUomId = other.sizeUomId == null ? null : other.sizeUomId.copy();
        this.geoPointId = other.geoPointId == null ? null : other.geoPointId.copy();
        this.inventoryItemTypeId = other.inventoryItemTypeId == null ? null : other.inventoryItemTypeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.usageStatusId = other.usageStatusId == null ? null : other.usageStatusId.copy();
        this.reviewedById = other.reviewedById == null ? null : other.reviewedById.copy();
        this.approvedById = other.approvedById == null ? null : other.approvedById.copy();
    }

    @Override
    public FacilityCriteria copy() {
        return new FacilityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(StringFilter facilityCode) {
        this.facilityCode = facilityCode;
    }

    public BigDecimalFilter getFacilitySize() {
        return facilitySize;
    }

    public void setFacilitySize(BigDecimalFilter facilitySize) {
        this.facilitySize = facilitySize;
    }

    public StringFilter getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(StringFilter costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public LongFilter getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(LongFilter facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    public LongFilter getParentFacilityId() {
        return parentFacilityId;
    }

    public void setParentFacilityId(LongFilter parentFacilityId) {
        this.parentFacilityId = parentFacilityId;
    }

    public LongFilter getOwnerPartyId() {
        return ownerPartyId;
    }

    public void setOwnerPartyId(LongFilter ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }

    public LongFilter getFacilityGroupId() {
        return facilityGroupId;
    }

    public void setFacilityGroupId(LongFilter facilityGroupId) {
        this.facilityGroupId = facilityGroupId;
    }

    public LongFilter getSizeUomId() {
        return sizeUomId;
    }

    public void setSizeUomId(LongFilter sizeUomId) {
        this.sizeUomId = sizeUomId;
    }

    public LongFilter getGeoPointId() {
        return geoPointId;
    }

    public void setGeoPointId(LongFilter geoPointId) {
        this.geoPointId = geoPointId;
    }

    public LongFilter getInventoryItemTypeId() {
        return inventoryItemTypeId;
    }

    public void setInventoryItemTypeId(LongFilter inventoryItemTypeId) {
        this.inventoryItemTypeId = inventoryItemTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getUsageStatusId() {
        return usageStatusId;
    }

    public void setUsageStatusId(LongFilter usageStatusId) {
        this.usageStatusId = usageStatusId;
    }

    public LongFilter getReviewedById() {
        return reviewedById;
    }

    public void setReviewedById(LongFilter reviewedById) {
        this.reviewedById = reviewedById;
    }

    public LongFilter getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(LongFilter approvedById) {
        this.approvedById = approvedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FacilityCriteria that = (FacilityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(facilityCode, that.facilityCode) &&
            Objects.equals(facilitySize, that.facilitySize) &&
            Objects.equals(costCenterCode, that.costCenterCode) &&
            Objects.equals(facilityTypeId, that.facilityTypeId) &&
            Objects.equals(parentFacilityId, that.parentFacilityId) &&
            Objects.equals(ownerPartyId, that.ownerPartyId) &&
            Objects.equals(facilityGroupId, that.facilityGroupId) &&
            Objects.equals(sizeUomId, that.sizeUomId) &&
            Objects.equals(geoPointId, that.geoPointId) &&
            Objects.equals(inventoryItemTypeId, that.inventoryItemTypeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(usageStatusId, that.usageStatusId) &&
            Objects.equals(reviewedById, that.reviewedById) &&
            Objects.equals(approvedById, that.approvedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        facilityCode,
        facilitySize,
        costCenterCode,
        facilityTypeId,
        parentFacilityId,
        ownerPartyId,
        facilityGroupId,
        sizeUomId,
        geoPointId,
        inventoryItemTypeId,
        statusId,
        usageStatusId,
        reviewedById,
        approvedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (facilityCode != null ? "facilityCode=" + facilityCode + ", " : "") +
                (facilitySize != null ? "facilitySize=" + facilitySize + ", " : "") +
                (costCenterCode != null ? "costCenterCode=" + costCenterCode + ", " : "") +
                (facilityTypeId != null ? "facilityTypeId=" + facilityTypeId + ", " : "") +
                (parentFacilityId != null ? "parentFacilityId=" + parentFacilityId + ", " : "") +
                (ownerPartyId != null ? "ownerPartyId=" + ownerPartyId + ", " : "") +
                (facilityGroupId != null ? "facilityGroupId=" + facilityGroupId + ", " : "") +
                (sizeUomId != null ? "sizeUomId=" + sizeUomId + ", " : "") +
                (geoPointId != null ? "geoPointId=" + geoPointId + ", " : "") +
                (inventoryItemTypeId != null ? "inventoryItemTypeId=" + inventoryItemTypeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (usageStatusId != null ? "usageStatusId=" + usageStatusId + ", " : "") +
                (reviewedById != null ? "reviewedById=" + reviewedById + ", " : "") +
                (approvedById != null ? "approvedById=" + approvedById + ", " : "") +
            "}";
    }

}
