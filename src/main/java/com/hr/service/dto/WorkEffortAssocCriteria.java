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
 * Criteria class for the {@link com.hr.domain.WorkEffortAssoc} entity. This class is used
 * in {@link com.hr.web.rest.WorkEffortAssocResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-effort-assocs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkEffortAssocCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private LongFilter typeId;

    private LongFilter weIdFromId;

    private LongFilter weIdToId;

    public WorkEffortAssocCriteria() {
    }

    public WorkEffortAssocCriteria(WorkEffortAssocCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.weIdFromId = other.weIdFromId == null ? null : other.weIdFromId.copy();
        this.weIdToId = other.weIdToId == null ? null : other.weIdToId.copy();
    }

    @Override
    public WorkEffortAssocCriteria copy() {
        return new WorkEffortAssocCriteria(this);
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

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getWeIdFromId() {
        return weIdFromId;
    }

    public void setWeIdFromId(LongFilter weIdFromId) {
        this.weIdFromId = weIdFromId;
    }

    public LongFilter getWeIdToId() {
        return weIdToId;
    }

    public void setWeIdToId(LongFilter weIdToId) {
        this.weIdToId = weIdToId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkEffortAssocCriteria that = (WorkEffortAssocCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(weIdFromId, that.weIdFromId) &&
            Objects.equals(weIdToId, that.weIdToId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        fromDate,
        thruDate,
        typeId,
        weIdFromId,
        weIdToId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortAssocCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (weIdFromId != null ? "weIdFromId=" + weIdFromId + ", " : "") +
                (weIdToId != null ? "weIdToId=" + weIdToId + ", " : "") +
            "}";
    }

}
