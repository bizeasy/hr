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
 * Criteria class for the {@link com.hr.domain.GeoAssoc} entity. This class is used
 * in {@link com.hr.web.rest.GeoAssocResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /geo-assocs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GeoAssocCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter geoId;

    private LongFilter geoToId;

    private LongFilter geoAssocTypeId;

    public GeoAssocCriteria() {
    }

    public GeoAssocCriteria(GeoAssocCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.geoId = other.geoId == null ? null : other.geoId.copy();
        this.geoToId = other.geoToId == null ? null : other.geoToId.copy();
        this.geoAssocTypeId = other.geoAssocTypeId == null ? null : other.geoAssocTypeId.copy();
    }

    @Override
    public GeoAssocCriteria copy() {
        return new GeoAssocCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getGeoId() {
        return geoId;
    }

    public void setGeoId(LongFilter geoId) {
        this.geoId = geoId;
    }

    public LongFilter getGeoToId() {
        return geoToId;
    }

    public void setGeoToId(LongFilter geoToId) {
        this.geoToId = geoToId;
    }

    public LongFilter getGeoAssocTypeId() {
        return geoAssocTypeId;
    }

    public void setGeoAssocTypeId(LongFilter geoAssocTypeId) {
        this.geoAssocTypeId = geoAssocTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GeoAssocCriteria that = (GeoAssocCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(geoId, that.geoId) &&
            Objects.equals(geoToId, that.geoToId) &&
            Objects.equals(geoAssocTypeId, that.geoAssocTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        geoId,
        geoToId,
        geoAssocTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeoAssocCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (geoId != null ? "geoId=" + geoId + ", " : "") +
                (geoToId != null ? "geoToId=" + geoToId + ", " : "") +
                (geoAssocTypeId != null ? "geoAssocTypeId=" + geoAssocTypeId + ", " : "") +
            "}";
    }

}
