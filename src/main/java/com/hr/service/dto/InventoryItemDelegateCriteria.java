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
 * Criteria class for the {@link com.hr.domain.InventoryItemDelegate} entity. This class is used
 * in {@link com.hr.web.rest.InventoryItemDelegateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-item-delegates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryItemDelegateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private ZonedDateTimeFilter effectiveDate;

    private BigDecimalFilter quantityOnHandDiff;

    private BigDecimalFilter availableToPromiseDiff;

    private BigDecimalFilter accountingQuantityDiff;

    private BigDecimalFilter unitCost;

    private StringFilter remarks;

    private LongFilter invoiceId;

    private LongFilter invoiceItemId;

    private LongFilter orderId;

    private LongFilter orderItemId;

    private LongFilter itemIssuanceId;

    private LongFilter inventoryTransferId;

    private LongFilter inventoryItemVarianceId;

    private LongFilter inventoryItemId;

    public InventoryItemDelegateCriteria() {
    }

    public InventoryItemDelegateCriteria(InventoryItemDelegateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.effectiveDate = other.effectiveDate == null ? null : other.effectiveDate.copy();
        this.quantityOnHandDiff = other.quantityOnHandDiff == null ? null : other.quantityOnHandDiff.copy();
        this.availableToPromiseDiff = other.availableToPromiseDiff == null ? null : other.availableToPromiseDiff.copy();
        this.accountingQuantityDiff = other.accountingQuantityDiff == null ? null : other.accountingQuantityDiff.copy();
        this.unitCost = other.unitCost == null ? null : other.unitCost.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.invoiceItemId = other.invoiceItemId == null ? null : other.invoiceItemId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.orderItemId = other.orderItemId == null ? null : other.orderItemId.copy();
        this.itemIssuanceId = other.itemIssuanceId == null ? null : other.itemIssuanceId.copy();
        this.inventoryTransferId = other.inventoryTransferId == null ? null : other.inventoryTransferId.copy();
        this.inventoryItemVarianceId = other.inventoryItemVarianceId == null ? null : other.inventoryItemVarianceId.copy();
        this.inventoryItemId = other.inventoryItemId == null ? null : other.inventoryItemId.copy();
    }

    @Override
    public InventoryItemDelegateCriteria copy() {
        return new InventoryItemDelegateCriteria(this);
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

    public ZonedDateTimeFilter getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(ZonedDateTimeFilter effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimalFilter getQuantityOnHandDiff() {
        return quantityOnHandDiff;
    }

    public void setQuantityOnHandDiff(BigDecimalFilter quantityOnHandDiff) {
        this.quantityOnHandDiff = quantityOnHandDiff;
    }

    public BigDecimalFilter getAvailableToPromiseDiff() {
        return availableToPromiseDiff;
    }

    public void setAvailableToPromiseDiff(BigDecimalFilter availableToPromiseDiff) {
        this.availableToPromiseDiff = availableToPromiseDiff;
    }

    public BigDecimalFilter getAccountingQuantityDiff() {
        return accountingQuantityDiff;
    }

    public void setAccountingQuantityDiff(BigDecimalFilter accountingQuantityDiff) {
        this.accountingQuantityDiff = accountingQuantityDiff;
    }

    public BigDecimalFilter getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimalFilter unitCost) {
        this.unitCost = unitCost;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(LongFilter invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
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

    public LongFilter getItemIssuanceId() {
        return itemIssuanceId;
    }

    public void setItemIssuanceId(LongFilter itemIssuanceId) {
        this.itemIssuanceId = itemIssuanceId;
    }

    public LongFilter getInventoryTransferId() {
        return inventoryTransferId;
    }

    public void setInventoryTransferId(LongFilter inventoryTransferId) {
        this.inventoryTransferId = inventoryTransferId;
    }

    public LongFilter getInventoryItemVarianceId() {
        return inventoryItemVarianceId;
    }

    public void setInventoryItemVarianceId(LongFilter inventoryItemVarianceId) {
        this.inventoryItemVarianceId = inventoryItemVarianceId;
    }

    public LongFilter getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(LongFilter inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryItemDelegateCriteria that = (InventoryItemDelegateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(effectiveDate, that.effectiveDate) &&
            Objects.equals(quantityOnHandDiff, that.quantityOnHandDiff) &&
            Objects.equals(availableToPromiseDiff, that.availableToPromiseDiff) &&
            Objects.equals(accountingQuantityDiff, that.accountingQuantityDiff) &&
            Objects.equals(unitCost, that.unitCost) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(invoiceItemId, that.invoiceItemId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(orderItemId, that.orderItemId) &&
            Objects.equals(itemIssuanceId, that.itemIssuanceId) &&
            Objects.equals(inventoryTransferId, that.inventoryTransferId) &&
            Objects.equals(inventoryItemVarianceId, that.inventoryItemVarianceId) &&
            Objects.equals(inventoryItemId, that.inventoryItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        effectiveDate,
        quantityOnHandDiff,
        availableToPromiseDiff,
        accountingQuantityDiff,
        unitCost,
        remarks,
        invoiceId,
        invoiceItemId,
        orderId,
        orderItemId,
        itemIssuanceId,
        inventoryTransferId,
        inventoryItemVarianceId,
        inventoryItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItemDelegateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (effectiveDate != null ? "effectiveDate=" + effectiveDate + ", " : "") +
                (quantityOnHandDiff != null ? "quantityOnHandDiff=" + quantityOnHandDiff + ", " : "") +
                (availableToPromiseDiff != null ? "availableToPromiseDiff=" + availableToPromiseDiff + ", " : "") +
                (accountingQuantityDiff != null ? "accountingQuantityDiff=" + accountingQuantityDiff + ", " : "") +
                (unitCost != null ? "unitCost=" + unitCost + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (invoiceItemId != null ? "invoiceItemId=" + invoiceItemId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (orderItemId != null ? "orderItemId=" + orderItemId + ", " : "") +
                (itemIssuanceId != null ? "itemIssuanceId=" + itemIssuanceId + ", " : "") +
                (inventoryTransferId != null ? "inventoryTransferId=" + inventoryTransferId + ", " : "") +
                (inventoryItemVarianceId != null ? "inventoryItemVarianceId=" + inventoryItemVarianceId + ", " : "") +
                (inventoryItemId != null ? "inventoryItemId=" + inventoryItemId + ", " : "") +
            "}";
    }

}
