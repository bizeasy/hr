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
 * Criteria class for the {@link com.hr.domain.WorkEffortInventoryAssign} entity. This class is used
 * in {@link com.hr.web.rest.WorkEffortInventoryAssignResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-effort-inventory-assigns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkEffortInventoryAssignCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantity;

    private LongFilter workEffortId;

    private LongFilter inventoryItemId;

    private LongFilter statusId;

    public WorkEffortInventoryAssignCriteria() {
    }

    public WorkEffortInventoryAssignCriteria(WorkEffortInventoryAssignCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.workEffortId = other.workEffortId == null ? null : other.workEffortId.copy();
        this.inventoryItemId = other.inventoryItemId == null ? null : other.inventoryItemId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public WorkEffortInventoryAssignCriteria copy() {
        return new WorkEffortInventoryAssignCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(LongFilter inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkEffortInventoryAssignCriteria that = (WorkEffortInventoryAssignCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(workEffortId, that.workEffortId) &&
            Objects.equals(inventoryItemId, that.inventoryItemId) &&
            Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity,
        workEffortId,
        inventoryItemId,
        statusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortInventoryAssignCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (workEffortId != null ? "workEffortId=" + workEffortId + ", " : "") +
                (inventoryItemId != null ? "inventoryItemId=" + inventoryItemId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
