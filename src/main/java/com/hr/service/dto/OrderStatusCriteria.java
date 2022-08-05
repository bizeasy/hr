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
 * Criteria class for the {@link com.hr.domain.OrderStatus} entity. This class is used
 * in {@link com.hr.web.rest.OrderStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter statusDateTime;

    private IntegerFilter sequenceNo;

    private LongFilter statusId;

    private LongFilter orderId;

    private LongFilter reasonId;

    public OrderStatusCriteria() {
    }

    public OrderStatusCriteria(OrderStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusDateTime = other.statusDateTime == null ? null : other.statusDateTime.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.reasonId = other.reasonId == null ? null : other.reasonId.copy();
    }

    @Override
    public OrderStatusCriteria copy() {
        return new OrderStatusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(ZonedDateTimeFilter statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getReasonId() {
        return reasonId;
    }

    public void setReasonId(LongFilter reasonId) {
        this.reasonId = reasonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderStatusCriteria that = (OrderStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(statusDateTime, that.statusDateTime) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(reasonId, that.reasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        statusDateTime,
        sequenceNo,
        statusId,
        orderId,
        reasonId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (statusDateTime != null ? "statusDateTime=" + statusDateTime + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (reasonId != null ? "reasonId=" + reasonId + ", " : "") +
            "}";
    }

}
