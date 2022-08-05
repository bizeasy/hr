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
 * Criteria class for the {@link com.hr.domain.WorkEffortProduct} entity. This class is used
 * in {@link com.hr.web.rest.WorkEffortProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-effort-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkEffortProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private DoubleFilter quantity;

    private LongFilter workEffortId;

    private LongFilter productId;

    public WorkEffortProductCriteria() {
    }

    public WorkEffortProductCriteria(WorkEffortProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.workEffortId = other.workEffortId == null ? null : other.workEffortId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public WorkEffortProductCriteria copy() {
        return new WorkEffortProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public LongFilter getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(LongFilter workEffortId) {
        this.workEffortId = workEffortId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkEffortProductCriteria that = (WorkEffortProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(workEffortId, that.workEffortId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        quantity,
        workEffortId,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (workEffortId != null ? "workEffortId=" + workEffortId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
