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
 * Criteria class for the {@link com.hr.domain.Order} entity. This class is used
 * in {@link com.hr.web.rest.OrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter externalId;

    private ZonedDateTimeFilter orderDate;

    private IntegerFilter priority;

    private ZonedDateTimeFilter entryDate;

    private BooleanFilter isRushOrder;

    private BooleanFilter needsInventoryIssuance;

    private BigDecimalFilter remainingSubTotal;

    private BigDecimalFilter grandTotal;

    private BooleanFilter hasRateContract;

    private BooleanFilter gotQuoteFromVendors;

    private StringFilter vendorReason;

    private ZonedDateTimeFilter expectedDeliveryDate;

    private StringFilter insuranceResp;

    private StringFilter transportResp;

    private StringFilter unloadingResp;

    private LongFilter orderTypeId;

    private LongFilter salesChannelId;

    private LongFilter partyId;

    private LongFilter statusId;

    public OrderCriteria() {
    }

    public OrderCriteria(OrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.externalId = other.externalId == null ? null : other.externalId.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.entryDate = other.entryDate == null ? null : other.entryDate.copy();
        this.isRushOrder = other.isRushOrder == null ? null : other.isRushOrder.copy();
        this.needsInventoryIssuance = other.needsInventoryIssuance == null ? null : other.needsInventoryIssuance.copy();
        this.remainingSubTotal = other.remainingSubTotal == null ? null : other.remainingSubTotal.copy();
        this.grandTotal = other.grandTotal == null ? null : other.grandTotal.copy();
        this.hasRateContract = other.hasRateContract == null ? null : other.hasRateContract.copy();
        this.gotQuoteFromVendors = other.gotQuoteFromVendors == null ? null : other.gotQuoteFromVendors.copy();
        this.vendorReason = other.vendorReason == null ? null : other.vendorReason.copy();
        this.expectedDeliveryDate = other.expectedDeliveryDate == null ? null : other.expectedDeliveryDate.copy();
        this.insuranceResp = other.insuranceResp == null ? null : other.insuranceResp.copy();
        this.transportResp = other.transportResp == null ? null : other.transportResp.copy();
        this.unloadingResp = other.unloadingResp == null ? null : other.unloadingResp.copy();
        this.orderTypeId = other.orderTypeId == null ? null : other.orderTypeId.copy();
        this.salesChannelId = other.salesChannelId == null ? null : other.salesChannelId.copy();
        this.partyId = other.partyId == null ? null : other.partyId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public OrderCriteria copy() {
        return new OrderCriteria(this);
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

    public StringFilter getExternalId() {
        return externalId;
    }

    public void setExternalId(StringFilter externalId) {
        this.externalId = externalId;
    }

    public ZonedDateTimeFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTimeFilter orderDate) {
        this.orderDate = orderDate;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public ZonedDateTimeFilter getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTimeFilter entryDate) {
        this.entryDate = entryDate;
    }

    public BooleanFilter getIsRushOrder() {
        return isRushOrder;
    }

    public void setIsRushOrder(BooleanFilter isRushOrder) {
        this.isRushOrder = isRushOrder;
    }

    public BooleanFilter getNeedsInventoryIssuance() {
        return needsInventoryIssuance;
    }

    public void setNeedsInventoryIssuance(BooleanFilter needsInventoryIssuance) {
        this.needsInventoryIssuance = needsInventoryIssuance;
    }

    public BigDecimalFilter getRemainingSubTotal() {
        return remainingSubTotal;
    }

    public void setRemainingSubTotal(BigDecimalFilter remainingSubTotal) {
        this.remainingSubTotal = remainingSubTotal;
    }

    public BigDecimalFilter getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimalFilter grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BooleanFilter getHasRateContract() {
        return hasRateContract;
    }

    public void setHasRateContract(BooleanFilter hasRateContract) {
        this.hasRateContract = hasRateContract;
    }

    public BooleanFilter getGotQuoteFromVendors() {
        return gotQuoteFromVendors;
    }

    public void setGotQuoteFromVendors(BooleanFilter gotQuoteFromVendors) {
        this.gotQuoteFromVendors = gotQuoteFromVendors;
    }

    public StringFilter getVendorReason() {
        return vendorReason;
    }

    public void setVendorReason(StringFilter vendorReason) {
        this.vendorReason = vendorReason;
    }

    public ZonedDateTimeFilter getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(ZonedDateTimeFilter expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public StringFilter getInsuranceResp() {
        return insuranceResp;
    }

    public void setInsuranceResp(StringFilter insuranceResp) {
        this.insuranceResp = insuranceResp;
    }

    public StringFilter getTransportResp() {
        return transportResp;
    }

    public void setTransportResp(StringFilter transportResp) {
        this.transportResp = transportResp;
    }

    public StringFilter getUnloadingResp() {
        return unloadingResp;
    }

    public void setUnloadingResp(StringFilter unloadingResp) {
        this.unloadingResp = unloadingResp;
    }

    public LongFilter getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(LongFilter orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public LongFilter getSalesChannelId() {
        return salesChannelId;
    }

    public void setSalesChannelId(LongFilter salesChannelId) {
        this.salesChannelId = salesChannelId;
    }

    public LongFilter getPartyId() {
        return partyId;
    }

    public void setPartyId(LongFilter partyId) {
        this.partyId = partyId;
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
        final OrderCriteria that = (OrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(externalId, that.externalId) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(entryDate, that.entryDate) &&
            Objects.equals(isRushOrder, that.isRushOrder) &&
            Objects.equals(needsInventoryIssuance, that.needsInventoryIssuance) &&
            Objects.equals(remainingSubTotal, that.remainingSubTotal) &&
            Objects.equals(grandTotal, that.grandTotal) &&
            Objects.equals(hasRateContract, that.hasRateContract) &&
            Objects.equals(gotQuoteFromVendors, that.gotQuoteFromVendors) &&
            Objects.equals(vendorReason, that.vendorReason) &&
            Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate) &&
            Objects.equals(insuranceResp, that.insuranceResp) &&
            Objects.equals(transportResp, that.transportResp) &&
            Objects.equals(unloadingResp, that.unloadingResp) &&
            Objects.equals(orderTypeId, that.orderTypeId) &&
            Objects.equals(salesChannelId, that.salesChannelId) &&
            Objects.equals(partyId, that.partyId) &&
            Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        externalId,
        orderDate,
        priority,
        entryDate,
        isRushOrder,
        needsInventoryIssuance,
        remainingSubTotal,
        grandTotal,
        hasRateContract,
        gotQuoteFromVendors,
        vendorReason,
        expectedDeliveryDate,
        insuranceResp,
        transportResp,
        unloadingResp,
        orderTypeId,
        salesChannelId,
        partyId,
        statusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (externalId != null ? "externalId=" + externalId + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (entryDate != null ? "entryDate=" + entryDate + ", " : "") +
                (isRushOrder != null ? "isRushOrder=" + isRushOrder + ", " : "") +
                (needsInventoryIssuance != null ? "needsInventoryIssuance=" + needsInventoryIssuance + ", " : "") +
                (remainingSubTotal != null ? "remainingSubTotal=" + remainingSubTotal + ", " : "") +
                (grandTotal != null ? "grandTotal=" + grandTotal + ", " : "") +
                (hasRateContract != null ? "hasRateContract=" + hasRateContract + ", " : "") +
                (gotQuoteFromVendors != null ? "gotQuoteFromVendors=" + gotQuoteFromVendors + ", " : "") +
                (vendorReason != null ? "vendorReason=" + vendorReason + ", " : "") +
                (expectedDeliveryDate != null ? "expectedDeliveryDate=" + expectedDeliveryDate + ", " : "") +
                (insuranceResp != null ? "insuranceResp=" + insuranceResp + ", " : "") +
                (transportResp != null ? "transportResp=" + transportResp + ", " : "") +
                (unloadingResp != null ? "unloadingResp=" + unloadingResp + ", " : "") +
                (orderTypeId != null ? "orderTypeId=" + orderTypeId + ", " : "") +
                (salesChannelId != null ? "salesChannelId=" + salesChannelId + ", " : "") +
                (partyId != null ? "partyId=" + partyId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
