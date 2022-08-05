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
 * Criteria class for the {@link com.hr.domain.InvoiceItem} entity. This class is used
 * in {@link com.hr.web.rest.InvoiceItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private BigDecimalFilter quantity;

    private BigDecimalFilter amount;

    private LongFilter invoiceId;

    private LongFilter invoiceItemTypeId;

    private LongFilter fromInventoryItemId;

    private LongFilter productId;

    private LongFilter taxAuthRateId;

    public InvoiceItemCriteria() {
    }

    public InvoiceItemCriteria(InvoiceItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.invoiceItemTypeId = other.invoiceItemTypeId == null ? null : other.invoiceItemTypeId.copy();
        this.fromInventoryItemId = other.fromInventoryItemId == null ? null : other.fromInventoryItemId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.taxAuthRateId = other.taxAuthRateId == null ? null : other.taxAuthRateId.copy();
    }

    @Override
    public InvoiceItemCriteria copy() {
        return new InvoiceItemCriteria(this);
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

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getInvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setInvoiceItemTypeId(LongFilter invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
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
        final InvoiceItemCriteria that = (InvoiceItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(invoiceItemTypeId, that.invoiceItemTypeId) &&
            Objects.equals(fromInventoryItemId, that.fromInventoryItemId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(taxAuthRateId, that.taxAuthRateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        quantity,
        amount,
        invoiceId,
        invoiceItemTypeId,
        fromInventoryItemId,
        productId,
        taxAuthRateId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (invoiceItemTypeId != null ? "invoiceItemTypeId=" + invoiceItemTypeId + ", " : "") +
                (fromInventoryItemId != null ? "fromInventoryItemId=" + fromInventoryItemId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (taxAuthRateId != null ? "taxAuthRateId=" + taxAuthRateId + ", " : "") +
            "}";
    }

}
