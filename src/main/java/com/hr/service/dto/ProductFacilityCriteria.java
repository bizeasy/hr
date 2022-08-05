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
 * Criteria class for the {@link com.hr.domain.ProductFacility} entity. This class is used
 * in {@link com.hr.web.rest.ProductFacilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-facilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductFacilityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter minimumStock;

    private BigDecimalFilter reorderQty;

    private IntegerFilter daysToShip;

    private BigDecimalFilter lastInventoryCount;

    private LongFilter productId;

    private LongFilter facilityId;

    public ProductFacilityCriteria() {
    }

    public ProductFacilityCriteria(ProductFacilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.minimumStock = other.minimumStock == null ? null : other.minimumStock.copy();
        this.reorderQty = other.reorderQty == null ? null : other.reorderQty.copy();
        this.daysToShip = other.daysToShip == null ? null : other.daysToShip.copy();
        this.lastInventoryCount = other.lastInventoryCount == null ? null : other.lastInventoryCount.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
    }

    @Override
    public ProductFacilityCriteria copy() {
        return new ProductFacilityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(BigDecimalFilter minimumStock) {
        this.minimumStock = minimumStock;
    }

    public BigDecimalFilter getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(BigDecimalFilter reorderQty) {
        this.reorderQty = reorderQty;
    }

    public IntegerFilter getDaysToShip() {
        return daysToShip;
    }

    public void setDaysToShip(IntegerFilter daysToShip) {
        this.daysToShip = daysToShip;
    }

    public BigDecimalFilter getLastInventoryCount() {
        return lastInventoryCount;
    }

    public void setLastInventoryCount(BigDecimalFilter lastInventoryCount) {
        this.lastInventoryCount = lastInventoryCount;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductFacilityCriteria that = (ProductFacilityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(minimumStock, that.minimumStock) &&
            Objects.equals(reorderQty, that.reorderQty) &&
            Objects.equals(daysToShip, that.daysToShip) &&
            Objects.equals(lastInventoryCount, that.lastInventoryCount) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(facilityId, that.facilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        minimumStock,
        reorderQty,
        daysToShip,
        lastInventoryCount,
        productId,
        facilityId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductFacilityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (minimumStock != null ? "minimumStock=" + minimumStock + ", " : "") +
                (reorderQty != null ? "reorderQty=" + reorderQty + ", " : "") +
                (daysToShip != null ? "daysToShip=" + daysToShip + ", " : "") +
                (lastInventoryCount != null ? "lastInventoryCount=" + lastInventoryCount + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
            "}";
    }

}
