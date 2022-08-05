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
 * Criteria class for the {@link com.hr.domain.Uom} entity. This class is used
 * in {@link com.hr.web.rest.UomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /uoms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter sequenceNo;

    private StringFilter abbreviation;

    private LongFilter uomTypeId;

    public UomCriteria() {
    }

    public UomCriteria(UomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.abbreviation = other.abbreviation == null ? null : other.abbreviation.copy();
        this.uomTypeId = other.uomTypeId == null ? null : other.uomTypeId.copy();
    }

    @Override
    public UomCriteria copy() {
        return new UomCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public StringFilter getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(StringFilter abbreviation) {
        this.abbreviation = abbreviation;
    }

    public LongFilter getUomTypeId() {
        return uomTypeId;
    }

    public void setUomTypeId(LongFilter uomTypeId) {
        this.uomTypeId = uomTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UomCriteria that = (UomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(abbreviation, that.abbreviation) &&
            Objects.equals(uomTypeId, that.uomTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        sequenceNo,
        abbreviation,
        uomTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (abbreviation != null ? "abbreviation=" + abbreviation + ", " : "") +
                (uomTypeId != null ? "uomTypeId=" + uomTypeId + ", " : "") +
            "}";
    }

}
