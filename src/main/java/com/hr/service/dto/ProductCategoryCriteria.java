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
 * Criteria class for the {@link com.hr.domain.ProductCategory} entity. This class is used
 * in {@link com.hr.web.rest.ProductCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter longDescription;

    private StringFilter attribute;

    private IntegerFilter sequenceNo;

    private StringFilter imageUrl;

    private StringFilter altImageUrl;

    private LongFilter productCategoryTypeId;

    private LongFilter parentId;

    public ProductCategoryCriteria() {
    }

    public ProductCategoryCriteria(ProductCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.longDescription = other.longDescription == null ? null : other.longDescription.copy();
        this.attribute = other.attribute == null ? null : other.attribute.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.altImageUrl = other.altImageUrl == null ? null : other.altImageUrl.copy();
        this.productCategoryTypeId = other.productCategoryTypeId == null ? null : other.productCategoryTypeId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public ProductCategoryCriteria copy() {
        return new ProductCategoryCriteria(this);
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

    public StringFilter getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(StringFilter longDescription) {
        this.longDescription = longDescription;
    }

    public StringFilter getAttribute() {
        return attribute;
    }

    public void setAttribute(StringFilter attribute) {
        this.attribute = attribute;
    }

    public IntegerFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(IntegerFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getAltImageUrl() {
        return altImageUrl;
    }

    public void setAltImageUrl(StringFilter altImageUrl) {
        this.altImageUrl = altImageUrl;
    }

    public LongFilter getProductCategoryTypeId() {
        return productCategoryTypeId;
    }

    public void setProductCategoryTypeId(LongFilter productCategoryTypeId) {
        this.productCategoryTypeId = productCategoryTypeId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCategoryCriteria that = (ProductCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(longDescription, that.longDescription) &&
            Objects.equals(attribute, that.attribute) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(altImageUrl, that.altImageUrl) &&
            Objects.equals(productCategoryTypeId, that.productCategoryTypeId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        longDescription,
        attribute,
        sequenceNo,
        imageUrl,
        altImageUrl,
        productCategoryTypeId,
        parentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (longDescription != null ? "longDescription=" + longDescription + ", " : "") +
                (attribute != null ? "attribute=" + attribute + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (altImageUrl != null ? "altImageUrl=" + altImageUrl + ", " : "") +
                (productCategoryTypeId != null ? "productCategoryTypeId=" + productCategoryTypeId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
