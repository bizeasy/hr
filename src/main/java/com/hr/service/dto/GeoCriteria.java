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
 * Criteria class for the {@link com.hr.domain.Geo} entity. This class is used
 * in {@link com.hr.web.rest.GeoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /geos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GeoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter abbreviation;

    private LongFilter geoTypeId;

    public GeoCriteria() {
    }

    public GeoCriteria(GeoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.abbreviation = other.abbreviation == null ? null : other.abbreviation.copy();
        this.geoTypeId = other.geoTypeId == null ? null : other.geoTypeId.copy();
    }

    @Override
    public GeoCriteria copy() {
        return new GeoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(StringFilter abbreviation) {
        this.abbreviation = abbreviation;
    }

    public LongFilter getGeoTypeId() {
        return geoTypeId;
    }

    public void setGeoTypeId(LongFilter geoTypeId) {
        this.geoTypeId = geoTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GeoCriteria that = (GeoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(abbreviation, that.abbreviation) &&
            Objects.equals(geoTypeId, that.geoTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        abbreviation,
        geoTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (abbreviation != null ? "abbreviation=" + abbreviation + ", " : "") +
                (geoTypeId != null ? "geoTypeId=" + geoTypeId + ", " : "") +
            "}";
    }

}
