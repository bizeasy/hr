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
 * Criteria class for the {@link com.hr.domain.Invoice} entity. This class is used
 * in {@link com.hr.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter invoiceDate;

    private ZonedDateTimeFilter dueDate;

    private ZonedDateTimeFilter paidDate;

    private StringFilter invoiceMessage;

    private StringFilter referenceNumber;

    private LongFilter invoiceTypeId;

    private LongFilter partyIdFromId;

    private LongFilter partyIdToId;

    private LongFilter roleTypeId;

    private LongFilter statusId;

    private LongFilter contactMechId;

    public InvoiceCriteria() {
    }

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.paidDate = other.paidDate == null ? null : other.paidDate.copy();
        this.invoiceMessage = other.invoiceMessage == null ? null : other.invoiceMessage.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.invoiceTypeId = other.invoiceTypeId == null ? null : other.invoiceTypeId.copy();
        this.partyIdFromId = other.partyIdFromId == null ? null : other.partyIdFromId.copy();
        this.partyIdToId = other.partyIdToId == null ? null : other.partyIdToId.copy();
        this.roleTypeId = other.roleTypeId == null ? null : other.roleTypeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.contactMechId = other.contactMechId == null ? null : other.contactMechId.copy();
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(ZonedDateTimeFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public ZonedDateTimeFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTimeFilter dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTimeFilter getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(ZonedDateTimeFilter paidDate) {
        this.paidDate = paidDate;
    }

    public StringFilter getInvoiceMessage() {
        return invoiceMessage;
    }

    public void setInvoiceMessage(StringFilter invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }

    public StringFilter getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(StringFilter referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LongFilter getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(LongFilter invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public LongFilter getPartyIdFromId() {
        return partyIdFromId;
    }

    public void setPartyIdFromId(LongFilter partyIdFromId) {
        this.partyIdFromId = partyIdFromId;
    }

    public LongFilter getPartyIdToId() {
        return partyIdToId;
    }

    public void setPartyIdToId(LongFilter partyIdToId) {
        this.partyIdToId = partyIdToId;
    }

    public LongFilter getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(LongFilter roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(LongFilter contactMechId) {
        this.contactMechId = contactMechId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(paidDate, that.paidDate) &&
            Objects.equals(invoiceMessage, that.invoiceMessage) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(invoiceTypeId, that.invoiceTypeId) &&
            Objects.equals(partyIdFromId, that.partyIdFromId) &&
            Objects.equals(partyIdToId, that.partyIdToId) &&
            Objects.equals(roleTypeId, that.roleTypeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(contactMechId, that.contactMechId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceDate,
        dueDate,
        paidDate,
        invoiceMessage,
        referenceNumber,
        invoiceTypeId,
        partyIdFromId,
        partyIdToId,
        roleTypeId,
        statusId,
        contactMechId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (paidDate != null ? "paidDate=" + paidDate + ", " : "") +
                (invoiceMessage != null ? "invoiceMessage=" + invoiceMessage + ", " : "") +
                (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
                (invoiceTypeId != null ? "invoiceTypeId=" + invoiceTypeId + ", " : "") +
                (partyIdFromId != null ? "partyIdFromId=" + partyIdFromId + ", " : "") +
                (partyIdToId != null ? "partyIdToId=" + partyIdToId + ", " : "") +
                (roleTypeId != null ? "roleTypeId=" + roleTypeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (contactMechId != null ? "contactMechId=" + contactMechId + ", " : "") +
            "}";
    }

}
