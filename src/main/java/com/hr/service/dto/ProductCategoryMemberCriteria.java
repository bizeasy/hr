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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.hr.domain.ProductCategoryMember} entity. This class is used
 * in {@link com.hr.web.rest.ProductCategoryMemberResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-category-members?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCategoryMemberCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private IntegerFilter sequenceNo;

    private LongFilter productId;

    private LongFilter productCategoryId;

    public ProductCategoryMemberCriteria() {
    }

    public ProductCategoryMemberCriteria(ProductCategoryMemberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
    }

    @Override
    public ProductCategoryMemberCriteria copy() {
        return new ProductCategoryMemberCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(ZonedDateTimeFilter fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTimeFilter getThruDate() {
        return thruDate;
    }

    public void setThruDate(ZonedDateTimeFilter thruDate) {
        this.thruDate = thruDate;
    }

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCategoryMemberCriteria that = (ProductCategoryMemberCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        sequenceNo,
        productId,
        productCategoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryMemberCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
