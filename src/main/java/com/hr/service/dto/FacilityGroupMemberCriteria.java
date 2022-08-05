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
 * Criteria class for the {@link com.hr.domain.FacilityGroupMember} entity. This class is used
 * in {@link com.hr.web.rest.FacilityGroupMemberResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /facility-group-members?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FacilityGroupMemberCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private IntegerFilter sequenceNo;

    private LongFilter facilityId;

    private LongFilter facilityGroupId;

    public FacilityGroupMemberCriteria() {
    }

    public FacilityGroupMemberCriteria(FacilityGroupMemberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.facilityGroupId = other.facilityGroupId == null ? null : other.facilityGroupId.copy();
    }

    @Override
    public FacilityGroupMemberCriteria copy() {
        return new FacilityGroupMemberCriteria(this);
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

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getFacilityGroupId() {
        return facilityGroupId;
    }

    public void setFacilityGroupId(LongFilter facilityGroupId) {
        this.facilityGroupId = facilityGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FacilityGroupMemberCriteria that = (FacilityGroupMemberCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(facilityGroupId, that.facilityGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        sequenceNo,
        facilityId,
        facilityGroupId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityGroupMemberCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (facilityGroupId != null ? "facilityGroupId=" + facilityGroupId + ", " : "") +
            "}";
    }

}
