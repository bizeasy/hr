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
 * Criteria class for the {@link com.hr.domain.Catalogue} entity. This class is used
 * in {@link com.hr.web.rest.CatalogueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /catalogues?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatalogueCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter sequenceNo;

    private StringFilter imagePath;

    private StringFilter mobileImagePath;

    private StringFilter altImage1;

    private StringFilter altImage2;

    private StringFilter altImage3;

    public CatalogueCriteria() {
    }

    public CatalogueCriteria(CatalogueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.imagePath = other.imagePath == null ? null : other.imagePath.copy();
        this.mobileImagePath = other.mobileImagePath == null ? null : other.mobileImagePath.copy();
        this.altImage1 = other.altImage1 == null ? null : other.altImage1.copy();
        this.altImage2 = other.altImage2 == null ? null : other.altImage2.copy();
        this.altImage3 = other.altImage3 == null ? null : other.altImage3.copy();
    }

    @Override
    public CatalogueCriteria copy() {
        return new CatalogueCriteria(this);
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

    public StringFilter getImagePath() {
        return imagePath;
    }

    public void setImagePath(StringFilter imagePath) {
        this.imagePath = imagePath;
    }

    public StringFilter getMobileImagePath() {
        return mobileImagePath;
    }

    public void setMobileImagePath(StringFilter mobileImagePath) {
        this.mobileImagePath = mobileImagePath;
    }

    public StringFilter getAltImage1() {
        return altImage1;
    }

    public void setAltImage1(StringFilter altImage1) {
        this.altImage1 = altImage1;
    }

    public StringFilter getAltImage2() {
        return altImage2;
    }

    public void setAltImage2(StringFilter altImage2) {
        this.altImage2 = altImage2;
    }

    public StringFilter getAltImage3() {
        return altImage3;
    }

    public void setAltImage3(StringFilter altImage3) {
        this.altImage3 = altImage3;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatalogueCriteria that = (CatalogueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(imagePath, that.imagePath) &&
            Objects.equals(mobileImagePath, that.mobileImagePath) &&
            Objects.equals(altImage1, that.altImage1) &&
            Objects.equals(altImage2, that.altImage2) &&
            Objects.equals(altImage3, that.altImage3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        sequenceNo,
        imagePath,
        mobileImagePath,
        altImage1,
        altImage2,
        altImage3
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (imagePath != null ? "imagePath=" + imagePath + ", " : "") +
                (mobileImagePath != null ? "mobileImagePath=" + mobileImagePath + ", " : "") +
                (altImage1 != null ? "altImage1=" + altImage1 + ", " : "") +
                (altImage2 != null ? "altImage2=" + altImage2 + ", " : "") +
                (altImage3 != null ? "altImage3=" + altImage3 + ", " : "") +
            "}";
    }

}
