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
 * Criteria class for the {@link com.hr.domain.UserGroupAuthority} entity. This class is used
 * in {@link com.hr.web.rest.UserGroupAuthorityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-group-authorities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserGroupAuthorityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter authority;

    private LongFilter userGroupId;

    public UserGroupAuthorityCriteria() {
    }

    public UserGroupAuthorityCriteria(UserGroupAuthorityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.authority = other.authority == null ? null : other.authority.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
    }

    @Override
    public UserGroupAuthorityCriteria copy() {
        return new UserGroupAuthorityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuthority() {
        return authority;
    }

    public void setAuthority(StringFilter authority) {
        this.authority = authority;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserGroupAuthorityCriteria that = (UserGroupAuthorityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(authority, that.authority) &&
            Objects.equals(userGroupId, that.userGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        authority,
        userGroupId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroupAuthorityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (authority != null ? "authority=" + authority + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
            "}";
    }

}
