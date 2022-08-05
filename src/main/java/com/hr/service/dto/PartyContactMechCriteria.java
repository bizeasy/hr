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
 * Criteria class for the {@link com.hr.domain.PartyContactMech} entity. This class is used
 * in {@link com.hr.web.rest.PartyContactMechResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /party-contact-meches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartyContactMechCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private LongFilter partyId;

    private LongFilter contactMechId;

    private LongFilter contactMechPurposeId;

    public PartyContactMechCriteria() {
    }

    public PartyContactMechCriteria(PartyContactMechCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.partyId = other.partyId == null ? null : other.partyId.copy();
        this.contactMechId = other.contactMechId == null ? null : other.contactMechId.copy();
        this.contactMechPurposeId = other.contactMechPurposeId == null ? null : other.contactMechPurposeId.copy();
    }

    @Override
    public PartyContactMechCriteria copy() {
        return new PartyContactMechCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getPartyId() {
        return partyId;
    }

    public void setPartyId(LongFilter partyId) {
        this.partyId = partyId;
    }

    public LongFilter getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(LongFilter contactMechId) {
        this.contactMechId = contactMechId;
    }

    public LongFilter getContactMechPurposeId() {
        return contactMechPurposeId;
    }

    public void setContactMechPurposeId(LongFilter contactMechPurposeId) {
        this.contactMechPurposeId = contactMechPurposeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartyContactMechCriteria that = (PartyContactMechCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(partyId, that.partyId) &&
            Objects.equals(contactMechId, that.contactMechId) &&
            Objects.equals(contactMechPurposeId, that.contactMechPurposeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        partyId,
        contactMechId,
        contactMechPurposeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyContactMechCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (partyId != null ? "partyId=" + partyId + ", " : "") +
                (contactMechId != null ? "contactMechId=" + contactMechId + ", " : "") +
                (contactMechPurposeId != null ? "contactMechPurposeId=" + contactMechPurposeId + ", " : "") +
            "}";
    }

}
