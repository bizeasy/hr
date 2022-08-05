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
 * Criteria class for the {@link com.hr.domain.PartyRole} entity. This class is used
 * in {@link com.hr.web.rest.PartyRoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /party-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartyRoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter partyId;

    private LongFilter roleTypeId;

    public PartyRoleCriteria() {
    }

    public PartyRoleCriteria(PartyRoleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.partyId = other.partyId == null ? null : other.partyId.copy();
        this.roleTypeId = other.roleTypeId == null ? null : other.roleTypeId.copy();
    }

    @Override
    public PartyRoleCriteria copy() {
        return new PartyRoleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPartyId() {
        return partyId;
    }

    public void setPartyId(LongFilter partyId) {
        this.partyId = partyId;
    }

    public LongFilter getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(LongFilter roleTypeId) {
        this.roleTypeId = roleTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartyRoleCriteria that = (PartyRoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(partyId, that.partyId) &&
            Objects.equals(roleTypeId, that.roleTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        partyId,
        roleTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (partyId != null ? "partyId=" + partyId + ", " : "") +
                (roleTypeId != null ? "roleTypeId=" + roleTypeId + ", " : "") +
            "}";
    }

}
