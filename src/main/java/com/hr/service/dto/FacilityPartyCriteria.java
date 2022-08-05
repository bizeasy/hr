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
 * Criteria class for the {@link com.hr.domain.FacilityParty} entity. This class is used
 * in {@link com.hr.web.rest.FacilityPartyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /facility-parties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FacilityPartyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private LongFilter facilityId;

    private LongFilter partyId;

    private LongFilter roleTypeId;

    public FacilityPartyCriteria() {
    }

    public FacilityPartyCriteria(FacilityPartyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.partyId = other.partyId == null ? null : other.partyId.copy();
        this.roleTypeId = other.roleTypeId == null ? null : other.roleTypeId.copy();
    }

    @Override
    public FacilityPartyCriteria copy() {
        return new FacilityPartyCriteria(this);
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

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getPartyId() {
        return partyId;
    }

    public void setPartyId(LongFilter partyId) {
        this.partyId = partyId;
    }

    public LongFilter getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(LongFilter roleTypeId) {
        this.roleTypeId = roleTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FacilityPartyCriteria that = (FacilityPartyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(partyId, that.partyId) &&
            Objects.equals(roleTypeId, that.roleTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        facilityId,
        partyId,
        roleTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityPartyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (partyId != null ? "partyId=" + partyId + ", " : "") +
                (roleTypeId != null ? "roleTypeId=" + roleTypeId + ", " : "") +
            "}";
    }

}
