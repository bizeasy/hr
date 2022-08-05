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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.hr.domain.ItemIssuance} entity. This class is used
 * in {@link com.hr.web.rest.ItemIssuanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-issuances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemIssuanceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter message;

    private ZonedDateTimeFilter issuedDate;

    private StringFilter issuedBy;

    private BigDecimalFilter quantity;

    private BigDecimalFilter cancelQuantity;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private StringFilter equipmentId;

    private LongFilter orderId;

    private LongFilter orderItemId;

    private LongFilter inventoryItemId;

    private LongFilter issuedByUserLoginId;

    private LongFilter varianceReasonId;

    private LongFilter facilityId;

    private LongFilter statusId;

    public ItemIssuanceCriteria() {
    }

    public ItemIssuanceCriteria(ItemIssuanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.issuedDate = other.issuedDate == null ? null : other.issuedDate.copy();
        this.issuedBy = other.issuedBy == null ? null : other.issuedBy.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.cancelQuantity = other.cancelQuantity == null ? null : other.cancelQuantity.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.equipmentId = other.equipmentId == null ? null : other.equipmentId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.orderItemId = other.orderItemId == null ? null : other.orderItemId.copy();
        this.inventoryItemId = other.inventoryItemId == null ? null : other.inventoryItemId.copy();
        this.issuedByUserLoginId = other.issuedByUserLoginId == null ? null : other.issuedByUserLoginId.copy();
        this.varianceReasonId = other.varianceReasonId == null ? null : other.varianceReasonId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public ItemIssuanceCriteria copy() {
        return new ItemIssuanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public ZonedDateTimeFilter getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(ZonedDateTimeFilter issuedDate) {
        this.issuedDate = issuedDate;
    }

    public StringFilter getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(StringFilter issuedBy) {
        this.issuedBy = issuedBy;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getCancelQuantity() {
        return cancelQuantity;
    }

    public void setCancelQuantity(BigDecimalFilter cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
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

    public StringFilter getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(StringFilter equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(LongFilter orderItemId) {
        this.orderItemId = orderItemId;
    }

    public LongFilter getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(LongFilter inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public LongFilter getIssuedByUserLoginId() {
        return issuedByUserLoginId;
    }

    public void setIssuedByUserLoginId(LongFilter issuedByUserLoginId) {
        this.issuedByUserLoginId = issuedByUserLoginId;
    }

    public LongFilter getVarianceReasonId() {
        return varianceReasonId;
    }

    public void setVarianceReasonId(LongFilter varianceReasonId) {
        this.varianceReasonId = varianceReasonId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
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
        final ItemIssuanceCriteria that = (ItemIssuanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(issuedDate, that.issuedDate) &&
            Objects.equals(issuedBy, that.issuedBy) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(cancelQuantity, that.cancelQuantity) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(equipmentId, that.equipmentId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(orderItemId, that.orderItemId) &&
            Objects.equals(inventoryItemId, that.inventoryItemId) &&
            Objects.equals(issuedByUserLoginId, that.issuedByUserLoginId) &&
            Objects.equals(varianceReasonId, that.varianceReasonId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        message,
        issuedDate,
        issuedBy,
        quantity,
        cancelQuantity,
        fromDate,
        thruDate,
        equipmentId,
        orderId,
        orderItemId,
        inventoryItemId,
        issuedByUserLoginId,
        varianceReasonId,
        facilityId,
        statusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemIssuanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (issuedDate != null ? "issuedDate=" + issuedDate + ", " : "") +
                (issuedBy != null ? "issuedBy=" + issuedBy + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (cancelQuantity != null ? "cancelQuantity=" + cancelQuantity + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (equipmentId != null ? "equipmentId=" + equipmentId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (orderItemId != null ? "orderItemId=" + orderItemId + ", " : "") +
                (inventoryItemId != null ? "inventoryItemId=" + inventoryItemId + ", " : "") +
                (issuedByUserLoginId != null ? "issuedByUserLoginId=" + issuedByUserLoginId + ", " : "") +
                (varianceReasonId != null ? "varianceReasonId=" + varianceReasonId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
