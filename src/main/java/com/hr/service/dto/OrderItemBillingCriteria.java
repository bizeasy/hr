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
 * Criteria class for the {@link com.hr.domain.OrderItemBilling} entity. This class is used
 * in {@link com.hr.web.rest.OrderItemBillingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-item-billings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderItemBillingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantity;

    private BigDecimalFilter amount;

    private LongFilter orderItemId;

    private LongFilter invoiceItemId;

    public OrderItemBillingCriteria() {
    }

    public OrderItemBillingCriteria(OrderItemBillingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.orderItemId = other.orderItemId == null ? null : other.orderItemId.copy();
        this.invoiceItemId = other.invoiceItemId == null ? null : other.invoiceItemId.copy();
    }

    @Override
    public OrderItemBillingCriteria copy() {
        return new OrderItemBillingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(LongFilter orderItemId) {
        this.orderItemId = orderItemId;
    }

    public LongFilter getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(LongFilter invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderItemBillingCriteria that = (OrderItemBillingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(orderItemId, that.orderItemId) &&
            Objects.equals(invoiceItemId, that.invoiceItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity,
        amount,
        orderItemId,
        invoiceItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemBillingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (orderItemId != null ? "orderItemId=" + orderItemId + ", " : "") +
                (invoiceItemId != null ? "invoiceItemId=" + invoiceItemId + ", " : "") +
            "}";
    }

}
