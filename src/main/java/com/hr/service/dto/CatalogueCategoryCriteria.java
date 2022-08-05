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
 * Criteria class for the {@link com.hr.domain.CatalogueCategory} entity. This class is used
 * in {@link com.hr.web.rest.CatalogueCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /catalogue-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatalogueCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sequenceNo;

    private LongFilter catalogueId;

    private LongFilter productCategoryId;

    private LongFilter catalogueCategoryTypeId;

    public CatalogueCategoryCriteria() {
    }

    public CatalogueCategoryCriteria(CatalogueCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.catalogueId = other.catalogueId == null ? null : other.catalogueId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.catalogueCategoryTypeId = other.catalogueCategoryTypeId == null ? null : other.catalogueCategoryTypeId.copy();
    }

    @Override
    public CatalogueCategoryCriteria copy() {
        return new CatalogueCategoryCriteria(this);
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

    public LongFilter getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(LongFilter catalogueId) {
        this.catalogueId = catalogueId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getCatalogueCategoryTypeId() {
        return catalogueCategoryTypeId;
    }

    public void setCatalogueCategoryTypeId(LongFilter catalogueCategoryTypeId) {
        this.catalogueCategoryTypeId = catalogueCategoryTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatalogueCategoryCriteria that = (CatalogueCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(catalogueId, that.catalogueId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(catalogueCategoryTypeId, that.catalogueCategoryTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        catalogueId,
        productCategoryId,
        catalogueCategoryTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (catalogueId != null ? "catalogueId=" + catalogueId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (catalogueCategoryTypeId != null ? "catalogueCategoryTypeId=" + catalogueCategoryTypeId + ", " : "") +
            "}";
    }

}
