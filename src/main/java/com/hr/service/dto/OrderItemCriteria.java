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
 * Criteria class for the {@link com.hr.domain.OrderItem} entity. This class is used
 * in {@link com.hr.web.rest.OrderItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private BigDecimalFilter quantity;

    private BigDecimalFilter cancelQuantity;

    private BigDecimalFilter selectedAmount;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter unitListPrice;

    private BigDecimalFilter cgst;

    private BigDecimalFilter igst;

    private BigDecimalFilter sgst;

    private BigDecimalFilter cgstPercentage;

    private BigDecimalFilter igstPercentage;

    private BigDecimalFilter sgstPercentage;

    private BigDecimalFilter totalPrice;

    private BooleanFilter isModifiedPrice;

    private ZonedDateTimeFilter estimatedShipDate;

    private ZonedDateTimeFilter estimatedDeliveryDate;

    private ZonedDateTimeFilter shipBeforeDate;

    private ZonedDateTimeFilter shipAfterDate;

    private LongFilter orderId;

    private LongFilter orderItemTypeId;

    private LongFilter fromInventoryItemId;

    private LongFilter productId;

    private LongFilter supplierProductId;

    private LongFilter statusId;

    private LongFilter taxAuthRateId;

    public OrderItemCriteria() {
    }

    public OrderItemCriteria(OrderItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.cancelQuantity = other.cancelQuantity == null ? null : other.cancelQuantity.copy();
        this.selectedAmount = other.selectedAmount == null ? null : other.selectedAmount.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.unitListPrice = other.unitListPrice == null ? null : other.unitListPrice.copy();
        this.cgst = other.cgst == null ? null : other.cgst.copy();
        this.igst = other.igst == null ? null : other.igst.copy();
        this.sgst = other.sgst == null ? null : other.sgst.copy();
        this.cgstPercentage = other.cgstPercentage == null ? null : other.cgstPercentage.copy();
        this.igstPercentage = other.igstPercentage == null ? null : other.igstPercentage.copy();
        this.sgstPercentage = other.sgstPercentage == null ? null : other.sgstPercentage.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.isModifiedPrice = other.isModifiedPrice == null ? null : other.isModifiedPrice.copy();
        this.estimatedShipDate = other.estimatedShipDate == null ? null : other.estimatedShipDate.copy();
        this.estimatedDeliveryDate = other.estimatedDeliveryDate == null ? null : other.estimatedDeliveryDate.copy();
        this.shipBeforeDate = other.shipBeforeDate == null ? null : other.shipBeforeDate.copy();
        this.shipAfterDate = other.shipAfterDate == null ? null : other.shipAfterDate.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.orderItemTypeId = other.orderItemTypeId == null ? null : other.orderItemTypeId.copy();
        this.fromInventoryItemId = other.fromInventoryItemId == null ? null : other.fromInventoryItemId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.supplierProductId = other.supplierProductId == null ? null : other.supplierProductId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.taxAuthRateId = other.taxAuthRateId == null ? null : other.taxAuthRateId.copy();
    }

    @Override
    public OrderItemCriteria copy() {
        return new OrderItemCriteria(this);
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

    public BigDecimalFilter getSelectedAmount() {
        return selectedAmount;
    }

    public void setSelectedAmount(BigDecimalFilter selectedAmount) {
        this.selectedAmount = selectedAmount;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getUnitListPrice() {
        return unitListPrice;
    }

    public void setUnitListPrice(BigDecimalFilter unitListPrice) {
        this.unitListPrice = unitListPrice;
    }

    public BigDecimalFilter getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimalFilter cgst) {
        this.cgst = cgst;
    }

    public BigDecimalFilter getIgst() {
        return igst;
    }

    public void setIgst(BigDecimalFilter igst) {
        this.igst = igst;
    }

    public BigDecimalFilter getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimalFilter sgst) {
        this.sgst = sgst;
    }

    public BigDecimalFilter getCgstPercentage() {
        return cgstPercentage;
    }

    public void setCgstPercentage(BigDecimalFilter cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
    }

    public BigDecimalFilter getIgstPercentage() {
        return igstPercentage;
    }

    public void setIgstPercentage(BigDecimalFilter igstPercentage) {
        this.igstPercentage = igstPercentage;
    }

    public BigDecimalFilter getSgstPercentage() {
        return sgstPercentage;
    }

    public void setSgstPercentage(BigDecimalFilter sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BooleanFilter getIsModifiedPrice() {
        return isModifiedPrice;
    }

    public void setIsModifiedPrice(BooleanFilter isModifiedPrice) {
        this.isModifiedPrice = isModifiedPrice;
    }

    public ZonedDateTimeFilter getEstimatedShipDate() {
        return estimatedShipDate;
    }

    public void setEstimatedShipDate(ZonedDateTimeFilter estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }

    public ZonedDateTimeFilter getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(ZonedDateTimeFilter estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public ZonedDateTimeFilter getShipBeforeDate() {
        return shipBeforeDate;
    }

    public void setShipBeforeDate(ZonedDateTimeFilter shipBeforeDate) {
        this.shipBeforeDate = shipBeforeDate;
    }

    public ZonedDateTimeFilter getShipAfterDate() {
        return shipAfterDate;
    }

    public void setShipAfterDate(ZonedDateTimeFilter shipAfterDate) {
        this.shipAfterDate = shipAfterDate;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getOrderItemTypeId() {
        return orderItemTypeId;
    }

    public void setOrderItemTypeId(LongFilter orderItemTypeId) {
        this.orderItemTypeId = orderItemTypeId;
    }

    public LongFilter getFromInventoryItemId() {
        return fromInventoryItemId;
    }

    public void setFromInventoryItemId(LongFilter fromInventoryItemId) {
        this.fromInventoryItemId = fromInventoryItemId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(LongFilter supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getTaxAuthRateId() {
        return taxAuthRateId;
    }

    public void setTaxAuthRateId(LongFilter taxAuthRateId) {
        this.taxAuthRateId = taxAuthRateId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderItemCriteria that = (OrderItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(cancelQuantity, that.cancelQuantity) &&
            Objects.equals(selectedAmount, that.selectedAmount) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(unitListPrice, that.unitListPrice) &&
            Objects.equals(cgst, that.cgst) &&
            Objects.equals(igst, that.igst) &&
            Objects.equals(sgst, that.sgst) &&
            Objects.equals(cgstPercentage, that.cgstPercentage) &&
            Objects.equals(igstPercentage, that.igstPercentage) &&
            Objects.equals(sgstPercentage, that.sgstPercentage) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(isModifiedPrice, that.isModifiedPrice) &&
            Objects.equals(estimatedShipDate, that.estimatedShipDate) &&
            Objects.equals(estimatedDeliveryDate, that.estimatedDeliveryDate) &&
            Objects.equals(shipBeforeDate, that.shipBeforeDate) &&
            Objects.equals(shipAfterDate, that.shipAfterDate) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(orderItemTypeId, that.orderItemTypeId) &&
            Objects.equals(fromInventoryItemId, that.fromInventoryItemId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(supplierProductId, that.supplierProductId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(taxAuthRateId, that.taxAuthRateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        quantity,
        cancelQuantity,
        selectedAmount,
        unitPrice,
        unitListPrice,
        cgst,
        igst,
        sgst,
        cgstPercentage,
        igstPercentage,
        sgstPercentage,
        totalPrice,
        isModifiedPrice,
        estimatedShipDate,
        estimatedDeliveryDate,
        shipBeforeDate,
        shipAfterDate,
        orderId,
        orderItemTypeId,
        fromInventoryItemId,
        productId,
        supplierProductId,
        statusId,
        taxAuthRateId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (cancelQuantity != null ? "cancelQuantity=" + cancelQuantity + ", " : "") +
                (selectedAmount != null ? "selectedAmount=" + selectedAmount + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (unitListPrice != null ? "unitListPrice=" + unitListPrice + ", " : "") +
                (cgst != null ? "cgst=" + cgst + ", " : "") +
                (igst != null ? "igst=" + igst + ", " : "") +
                (sgst != null ? "sgst=" + sgst + ", " : "") +
                (cgstPercentage != null ? "cgstPercentage=" + cgstPercentage + ", " : "") +
                (igstPercentage != null ? "igstPercentage=" + igstPercentage + ", " : "") +
                (sgstPercentage != null ? "sgstPercentage=" + sgstPercentage + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (isModifiedPrice != null ? "isModifiedPrice=" + isModifiedPrice + ", " : "") +
                (estimatedShipDate != null ? "estimatedShipDate=" + estimatedShipDate + ", " : "") +
                (estimatedDeliveryDate != null ? "estimatedDeliveryDate=" + estimatedDeliveryDate + ", " : "") +
                (shipBeforeDate != null ? "shipBeforeDate=" + shipBeforeDate + ", " : "") +
                (shipAfterDate != null ? "shipAfterDate=" + shipAfterDate + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (orderItemTypeId != null ? "orderItemTypeId=" + orderItemTypeId + ", " : "") +
                (fromInventoryItemId != null ? "fromInventoryItemId=" + fromInventoryItemId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (supplierProductId != null ? "supplierProductId=" + supplierProductId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (taxAuthRateId != null ? "taxAuthRateId=" + taxAuthRateId + ", " : "") +
            "}";
    }

}
