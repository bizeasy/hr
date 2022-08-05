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
 * Criteria class for the {@link com.hr.domain.InventoryTransfer} entity. This class is used
 * in {@link com.hr.web.rest.InventoryTransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryTransferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter sentDate;

    private ZonedDateTimeFilter receivedDate;

    private StringFilter comments;

    private LongFilter statusId;

    private LongFilter inventoryItemId;

    private LongFilter facilityId;

    private LongFilter facilityToId;

    private LongFilter issuanceId;

    public InventoryTransferCriteria() {
    }

    public InventoryTransferCriteria(InventoryTransferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sentDate = other.sentDate == null ? null : other.sentDate.copy();
        this.receivedDate = other.receivedDate == null ? null : other.receivedDate.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.inventoryItemId = other.inventoryItemId == null ? null : other.inventoryItemId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.facilityToId = other.facilityToId == null ? null : other.facilityToId.copy();
        this.issuanceId = other.issuanceId == null ? null : other.issuanceId.copy();
    }

    @Override
    public InventoryTransferCriteria copy() {
        return new InventoryTransferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getSentDate() {
        return sentDate;
    }

    public void setSentDate(ZonedDateTimeFilter sentDate) {
        this.sentDate = sentDate;
    }

    public ZonedDateTimeFilter getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(ZonedDateTimeFilter receivedDate) {
        this.receivedDate = receivedDate;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(LongFilter inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getFacilityToId() {
        return facilityToId;
    }

    public void setFacilityToId(LongFilter facilityToId) {
        this.facilityToId = facilityToId;
    }

    public LongFilter getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(LongFilter issuanceId) {
        this.issuanceId = issuanceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryTransferCriteria that = (InventoryTransferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sentDate, that.sentDate) &&
            Objects.equals(receivedDate, that.receivedDate) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(inventoryItemId, that.inventoryItemId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(facilityToId, that.facilityToId) &&
            Objects.equals(issuanceId, that.issuanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sentDate,
        receivedDate,
        comments,
        statusId,
        inventoryItemId,
        facilityId,
        facilityToId,
        issuanceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryTransferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sentDate != null ? "sentDate=" + sentDate + ", " : "") +
                (receivedDate != null ? "receivedDate=" + receivedDate + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (inventoryItemId != null ? "inventoryItemId=" + inventoryItemId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (facilityToId != null ? "facilityToId=" + facilityToId + ", " : "") +
                (issuanceId != null ? "issuanceId=" + issuanceId + ", " : "") +
            "}";
    }

}
