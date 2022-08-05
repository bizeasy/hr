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
 * Criteria class for the {@link com.hr.domain.PaymentApplication} entity. This class is used
 * in {@link com.hr.web.rest.PaymentApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amountApplied;

    private LongFilter paymentId;

    private LongFilter invoiceId;

    private LongFilter invoiceItemId;

    private LongFilter orderId;

    private LongFilter orderItemId;

    private LongFilter toPaymentId;

    public PaymentApplicationCriteria() {
    }

    public PaymentApplicationCriteria(PaymentApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amountApplied = other.amountApplied == null ? null : other.amountApplied.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.invoiceItemId = other.invoiceItemId == null ? null : other.invoiceItemId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.orderItemId = other.orderItemId == null ? null : other.orderItemId.copy();
        this.toPaymentId = other.toPaymentId == null ? null : other.toPaymentId.copy();
    }

    @Override
    public PaymentApplicationCriteria copy() {
        return new PaymentApplicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getAmountApplied() {
        return amountApplied;
    }

    public void setAmountApplied(DoubleFilter amountApplied) {
        this.amountApplied = amountApplied;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
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

    public LongFilter getToPaymentId() {
        return toPaymentId;
    }

    public void setToPaymentId(LongFilter toPaymentId) {
        this.toPaymentId = toPaymentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentApplicationCriteria that = (PaymentApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amountApplied, that.amountApplied) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(invoiceItemId, that.invoiceItemId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(orderItemId, that.orderItemId) &&
            Objects.equals(toPaymentId, that.toPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amountApplied,
        paymentId,
        invoiceId,
        invoiceItemId,
        orderId,
        orderItemId,
        toPaymentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amountApplied != null ? "amountApplied=" + amountApplied + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (invoiceItemId != null ? "invoiceItemId=" + invoiceItemId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (orderItemId != null ? "orderItemId=" + orderItemId + ", " : "") +
                (toPaymentId != null ? "toPaymentId=" + toPaymentId + ", " : "") +
            "}";
    }

}
