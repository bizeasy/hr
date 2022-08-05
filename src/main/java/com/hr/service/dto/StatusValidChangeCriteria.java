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
 * Criteria class for the {@link com.hr.domain.StatusValidChange} entity. This class is used
 * in {@link com.hr.web.rest.StatusValidChangeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /status-valid-changes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatusValidChangeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transitionName;

    private LongFilter statusId;

    private LongFilter statusToId;

    public StatusValidChangeCriteria() {
    }

    public StatusValidChangeCriteria(StatusValidChangeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transitionName = other.transitionName == null ? null : other.transitionName.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.statusToId = other.statusToId == null ? null : other.statusToId.copy();
    }

    @Override
    public StatusValidChangeCriteria copy() {
        return new StatusValidChangeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(StringFilter transitionName) {
        this.transitionName = transitionName;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getStatusToId() {
        return statusToId;
    }

    public void setStatusToId(LongFilter statusToId) {
        this.statusToId = statusToId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StatusValidChangeCriteria that = (StatusValidChangeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transitionName, that.transitionName) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(statusToId, that.statusToId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transitionName,
        statusId,
        statusToId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusValidChangeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transitionName != null ? "transitionName=" + transitionName + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (statusToId != null ? "statusToId=" + statusToId + ", " : "") +
            "}";
    }

}
