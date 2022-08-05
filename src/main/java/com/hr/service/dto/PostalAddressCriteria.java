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
 * Criteria class for the {@link com.hr.domain.PostalAddress} entity. This class is used
 * in {@link com.hr.web.rest.PostalAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /postal-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostalAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter toName;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter city;

    private StringFilter landmark;

    private StringFilter postalCode;

    private BooleanFilter isDefault;

    private StringFilter customAddressType;

    private StringFilter stateStr;

    private StringFilter countryStr;

    private BooleanFilter isIndianAddress;

    private StringFilter note;

    private StringFilter directions;

    private LongFilter stateId;

    private LongFilter pincodeId;

    private LongFilter countryId;

    private LongFilter contactMechId;

    private LongFilter geoPointId;

    public PostalAddressCriteria() {
    }

    public PostalAddressCriteria(PostalAddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.toName = other.toName == null ? null : other.toName.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.landmark = other.landmark == null ? null : other.landmark.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.isDefault = other.isDefault == null ? null : other.isDefault.copy();
        this.customAddressType = other.customAddressType == null ? null : other.customAddressType.copy();
        this.stateStr = other.stateStr == null ? null : other.stateStr.copy();
        this.countryStr = other.countryStr == null ? null : other.countryStr.copy();
        this.isIndianAddress = other.isIndianAddress == null ? null : other.isIndianAddress.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.directions = other.directions == null ? null : other.directions.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.pincodeId = other.pincodeId == null ? null : other.pincodeId.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.contactMechId = other.contactMechId == null ? null : other.contactMechId.copy();
        this.geoPointId = other.geoPointId == null ? null : other.geoPointId.copy();
    }

    @Override
    public PostalAddressCriteria copy() {
        return new PostalAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getToName() {
        return toName;
    }

    public void setToName(StringFilter toName) {
        this.toName = toName;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getLandmark() {
        return landmark;
    }

    public void setLandmark(StringFilter landmark) {
        this.landmark = landmark;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public BooleanFilter getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(BooleanFilter isDefault) {
        this.isDefault = isDefault;
    }

    public StringFilter getCustomAddressType() {
        return customAddressType;
    }

    public void setCustomAddressType(StringFilter customAddressType) {
        this.customAddressType = customAddressType;
    }

    public StringFilter getStateStr() {
        return stateStr;
    }

    public void setStateStr(StringFilter stateStr) {
        this.stateStr = stateStr;
    }

    public StringFilter getCountryStr() {
        return countryStr;
    }

    public void setCountryStr(StringFilter countryStr) {
        this.countryStr = countryStr;
    }

    public BooleanFilter getIsIndianAddress() {
        return isIndianAddress;
    }

    public void setIsIndianAddress(BooleanFilter isIndianAddress) {
        this.isIndianAddress = isIndianAddress;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getDirections() {
        return directions;
    }

    public void setDirections(StringFilter directions) {
        this.directions = directions;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(LongFilter pincodeId) {
        this.pincodeId = pincodeId;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(LongFilter contactMechId) {
        this.contactMechId = contactMechId;
    }

    public LongFilter getGeoPointId() {
        return geoPointId;
    }

    public void setGeoPointId(LongFilter geoPointId) {
        this.geoPointId = geoPointId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostalAddressCriteria that = (PostalAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(toName, that.toName) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(city, that.city) &&
            Objects.equals(landmark, that.landmark) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(isDefault, that.isDefault) &&
            Objects.equals(customAddressType, that.customAddressType) &&
            Objects.equals(stateStr, that.stateStr) &&
            Objects.equals(countryStr, that.countryStr) &&
            Objects.equals(isIndianAddress, that.isIndianAddress) &&
            Objects.equals(note, that.note) &&
            Objects.equals(directions, that.directions) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(pincodeId, that.pincodeId) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(contactMechId, that.contactMechId) &&
            Objects.equals(geoPointId, that.geoPointId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        toName,
        address1,
        address2,
        city,
        landmark,
        postalCode,
        isDefault,
        customAddressType,
        stateStr,
        countryStr,
        isIndianAddress,
        note,
        directions,
        stateId,
        pincodeId,
        countryId,
        contactMechId,
        geoPointId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostalAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (toName != null ? "toName=" + toName + ", " : "") +
                (address1 != null ? "address1=" + address1 + ", " : "") +
                (address2 != null ? "address2=" + address2 + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (landmark != null ? "landmark=" + landmark + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (isDefault != null ? "isDefault=" + isDefault + ", " : "") +
                (customAddressType != null ? "customAddressType=" + customAddressType + ", " : "") +
                (stateStr != null ? "stateStr=" + stateStr + ", " : "") +
                (countryStr != null ? "countryStr=" + countryStr + ", " : "") +
                (isIndianAddress != null ? "isIndianAddress=" + isIndianAddress + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (directions != null ? "directions=" + directions + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (pincodeId != null ? "pincodeId=" + pincodeId + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (contactMechId != null ? "contactMechId=" + contactMechId + ", " : "") +
                (geoPointId != null ? "geoPointId=" + geoPointId + ", " : "") +
            "}";
    }

}
