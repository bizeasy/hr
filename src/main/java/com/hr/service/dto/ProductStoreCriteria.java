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
 * Criteria class for the {@link com.hr.domain.ProductStore} entity. This class is used
 * in {@link com.hr.web.rest.ProductStoreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-stores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductStoreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter title;

    private StringFilter subtitle;

    private StringFilter imageUrl;

    private StringFilter comments;

    private StringFilter code;

    private LongFilter typeId;

    private LongFilter parentId;

    private LongFilter ownerId;

    private LongFilter postalAddressId;

    public ProductStoreCriteria() {
    }

    public ProductStoreCriteria(ProductStoreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.subtitle = other.subtitle == null ? null : other.subtitle.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.postalAddressId = other.postalAddressId == null ? null : other.postalAddressId.copy();
    }

    @Override
    public ProductStoreCriteria copy() {
        return new ProductStoreCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(StringFilter subtitle) {
        this.subtitle = subtitle;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getPostalAddressId() {
        return postalAddressId;
    }

    public void setPostalAddressId(LongFilter postalAddressId) {
        this.postalAddressId = postalAddressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductStoreCriteria that = (ProductStoreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(title, that.title) &&
            Objects.equals(subtitle, that.subtitle) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(code, that.code) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(postalAddressId, that.postalAddressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        title,
        subtitle,
        imageUrl,
        comments,
        code,
        typeId,
        parentId,
        ownerId,
        postalAddressId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (subtitle != null ? "subtitle=" + subtitle + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (postalAddressId != null ? "postalAddressId=" + postalAddressId + ", " : "") +
            "}";
    }

}
