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
 * Criteria class for the {@link com.hr.domain.ProductStoreUserGroup} entity. This class is used
 * in {@link com.hr.web.rest.ProductStoreUserGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-store-user-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductStoreUserGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userGroupId;

    private LongFilter userId;

    private LongFilter partyId;

    private LongFilter productStoreId;

    public ProductStoreUserGroupCriteria() {
    }

    public ProductStoreUserGroupCriteria(ProductStoreUserGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.partyId = other.partyId == null ? null : other.partyId.copy();
        this.productStoreId = other.productStoreId == null ? null : other.productStoreId.copy();
    }

    @Override
    public ProductStoreUserGroupCriteria copy() {
        return new ProductStoreUserGroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getPartyId() {
        return partyId;
    }

    public void setPartyId(LongFilter partyId) {
        this.partyId = partyId;
    }

    public LongFilter getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(LongFilter productStoreId) {
        this.productStoreId = productStoreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductStoreUserGroupCriteria that = (ProductStoreUserGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userGroupId, that.userGroupId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(partyId, that.partyId) &&
            Objects.equals(productStoreId, that.productStoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userGroupId,
        userId,
        partyId,
        productStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreUserGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (partyId != null ? "partyId=" + partyId + ", " : "") +
                (productStoreId != null ? "productStoreId=" + productStoreId + ", " : "") +
            "}";
    }

}
