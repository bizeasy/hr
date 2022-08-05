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

/**
 * Criteria class for the {@link com.hr.domain.WorkEffort} entity. This class is used
 * in {@link com.hr.web.rest.WorkEffortResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-efforts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkEffortCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter code;

    private DoubleFilter batchSize;

    private DoubleFilter minYieldRange;

    private DoubleFilter maxYieldRange;

    private DoubleFilter percentComplete;

    private StringFilter validationType;

    private IntegerFilter shelfLife;

    private DoubleFilter outputQty;

    private LongFilter productId;

    private LongFilter ksmId;

    private LongFilter typeId;

    private LongFilter purposeId;

    private LongFilter statusId;

    private LongFilter facilityId;

    private LongFilter shelfListUomId;

    private LongFilter productCategoryId;

    private LongFilter productStoreId;

    public WorkEffortCriteria() {
    }

    public WorkEffortCriteria(WorkEffortCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.batchSize = other.batchSize == null ? null : other.batchSize.copy();
        this.minYieldRange = other.minYieldRange == null ? null : other.minYieldRange.copy();
        this.maxYieldRange = other.maxYieldRange == null ? null : other.maxYieldRange.copy();
        this.percentComplete = other.percentComplete == null ? null : other.percentComplete.copy();
        this.validationType = other.validationType == null ? null : other.validationType.copy();
        this.shelfLife = other.shelfLife == null ? null : other.shelfLife.copy();
        this.outputQty = other.outputQty == null ? null : other.outputQty.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.ksmId = other.ksmId == null ? null : other.ksmId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.purposeId = other.purposeId == null ? null : other.purposeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.shelfListUomId = other.shelfListUomId == null ? null : other.shelfListUomId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productStoreId = other.productStoreId == null ? null : other.productStoreId.copy();
    }

    @Override
    public WorkEffortCriteria copy() {
        return new WorkEffortCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public DoubleFilter getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(DoubleFilter batchSize) {
        this.batchSize = batchSize;
    }

    public DoubleFilter getMinYieldRange() {
        return minYieldRange;
    }

    public void setMinYieldRange(DoubleFilter minYieldRange) {
        this.minYieldRange = minYieldRange;
    }

    public DoubleFilter getMaxYieldRange() {
        return maxYieldRange;
    }

    public void setMaxYieldRange(DoubleFilter maxYieldRange) {
        this.maxYieldRange = maxYieldRange;
    }

    public DoubleFilter getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(DoubleFilter percentComplete) {
        this.percentComplete = percentComplete;
    }

    public StringFilter getValidationType() {
        return validationType;
    }

    public void setValidationType(StringFilter validationType) {
        this.validationType = validationType;
    }

    public IntegerFilter getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(IntegerFilter shelfLife) {
        this.shelfLife = shelfLife;
    }

    public DoubleFilter getOutputQty() {
        return outputQty;
    }

    public void setOutputQty(DoubleFilter outputQty) {
        this.outputQty = outputQty;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getKsmId() {
        return ksmId;
    }

    public void setKsmId(LongFilter ksmId) {
        this.ksmId = ksmId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(LongFilter purposeId) {
        this.purposeId = purposeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getShelfListUomId() {
        return shelfListUomId;
    }

    public void setShelfListUomId(LongFilter shelfListUomId) {
        this.shelfListUomId = shelfListUomId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(LongFilter productStoreId) {
        this.productStoreId = productStoreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkEffortCriteria that = (WorkEffortCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(code, that.code) &&
            Objects.equals(batchSize, that.batchSize) &&
            Objects.equals(minYieldRange, that.minYieldRange) &&
            Objects.equals(maxYieldRange, that.maxYieldRange) &&
            Objects.equals(percentComplete, that.percentComplete) &&
            Objects.equals(validationType, that.validationType) &&
            Objects.equals(shelfLife, that.shelfLife) &&
            Objects.equals(outputQty, that.outputQty) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(ksmId, that.ksmId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(purposeId, that.purposeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(shelfListUomId, that.shelfListUomId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productStoreId, that.productStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        code,
        batchSize,
        minYieldRange,
        maxYieldRange,
        percentComplete,
        validationType,
        shelfLife,
        outputQty,
        productId,
        ksmId,
        typeId,
        purposeId,
        statusId,
        facilityId,
        shelfListUomId,
        productCategoryId,
        productStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (batchSize != null ? "batchSize=" + batchSize + ", " : "") +
                (minYieldRange != null ? "minYieldRange=" + minYieldRange + ", " : "") +
                (maxYieldRange != null ? "maxYieldRange=" + maxYieldRange + ", " : "") +
                (percentComplete != null ? "percentComplete=" + percentComplete + ", " : "") +
                (validationType != null ? "validationType=" + validationType + ", " : "") +
                (shelfLife != null ? "shelfLife=" + shelfLife + ", " : "") +
                (outputQty != null ? "outputQty=" + outputQty + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (ksmId != null ? "ksmId=" + ksmId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (purposeId != null ? "purposeId=" + purposeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (shelfListUomId != null ? "shelfListUomId=" + shelfListUomId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productStoreId != null ? "productStoreId=" + productStoreId + ", " : "") +
            "}";
    }

}
