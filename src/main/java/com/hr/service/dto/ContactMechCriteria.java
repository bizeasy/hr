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
 * Criteria class for the {@link com.hr.domain.ContactMech} entity. This class is used
 * in {@link com.hr.web.rest.ContactMechResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contact-meches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContactMechCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter infoString;

    private LongFilter contactMechTypeId;

    public ContactMechCriteria() {
    }

    public ContactMechCriteria(ContactMechCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.infoString = other.infoString == null ? null : other.infoString.copy();
        this.contactMechTypeId = other.contactMechTypeId == null ? null : other.contactMechTypeId.copy();
    }

    @Override
    public ContactMechCriteria copy() {
        return new ContactMechCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInfoString() {
        return infoString;
    }

    public void setInfoString(StringFilter infoString) {
        this.infoString = infoString;
    }

    public LongFilter getContactMechTypeId() {
        return contactMechTypeId;
    }

    public void setContactMechTypeId(LongFilter contactMechTypeId) {
        this.contactMechTypeId = contactMechTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContactMechCriteria that = (ContactMechCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(infoString, that.infoString) &&
            Objects.equals(contactMechTypeId, that.contactMechTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        infoString,
        contactMechTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactMechCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (infoString != null ? "infoString=" + infoString + ", " : "") +
                (contactMechTypeId != null ? "contactMechTypeId=" + contactMechTypeId + ", " : "") +
            "}";
    }

}
