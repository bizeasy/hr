package com.hr.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.hr.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.hr.domain.Party} entity. This class is used
 * in {@link com.hr.web.rest.PartyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartyCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isOrganisation;

    private StringFilter organisationName;

    private StringFilter organisationShortName;

    private StringFilter salutation;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private GenderFilter gender;

    private LocalDateFilter birthDate;

    private StringFilter primaryPhone;

    private StringFilter primaryEmail;

    private BooleanFilter isTemporaryPassword;

    private StringFilter logoImageUrl;

    private StringFilter profileImageUrl;

    private StringFilter notes;

    private InstantFilter birthdate;

    private InstantFilter dateOfJoining;

    private InstantFilter trainingCompletedDate;

    private InstantFilter jdApprovedOn;

    private StringFilter employeeId;

    private StringFilter tanNo;

    private StringFilter panNo;

    private StringFilter gstNo;

    private StringFilter aadharNo;

    private LongFilter userId;

    private LongFilter partyTypeId;

    private LongFilter statusId;

    public PartyCriteria() {
    }

    public PartyCriteria(PartyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isOrganisation = other.isOrganisation == null ? null : other.isOrganisation.copy();
        this.organisationName = other.organisationName == null ? null : other.organisationName.copy();
        this.organisationShortName = other.organisationShortName == null ? null : other.organisationShortName.copy();
        this.salutation = other.salutation == null ? null : other.salutation.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.primaryPhone = other.primaryPhone == null ? null : other.primaryPhone.copy();
        this.primaryEmail = other.primaryEmail == null ? null : other.primaryEmail.copy();
        this.isTemporaryPassword = other.isTemporaryPassword == null ? null : other.isTemporaryPassword.copy();
        this.logoImageUrl = other.logoImageUrl == null ? null : other.logoImageUrl.copy();
        this.profileImageUrl = other.profileImageUrl == null ? null : other.profileImageUrl.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.dateOfJoining = other.dateOfJoining == null ? null : other.dateOfJoining.copy();
        this.trainingCompletedDate = other.trainingCompletedDate == null ? null : other.trainingCompletedDate.copy();
        this.jdApprovedOn = other.jdApprovedOn == null ? null : other.jdApprovedOn.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.tanNo = other.tanNo == null ? null : other.tanNo.copy();
        this.panNo = other.panNo == null ? null : other.panNo.copy();
        this.gstNo = other.gstNo == null ? null : other.gstNo.copy();
        this.aadharNo = other.aadharNo == null ? null : other.aadharNo.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.partyTypeId = other.partyTypeId == null ? null : other.partyTypeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public PartyCriteria copy() {
        return new PartyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsOrganisation() {
        return isOrganisation;
    }

    public void setIsOrganisation(BooleanFilter isOrganisation) {
        this.isOrganisation = isOrganisation;
    }

    public StringFilter getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(StringFilter organisationName) {
        this.organisationName = organisationName;
    }

    public StringFilter getOrganisationShortName() {
        return organisationShortName;
    }

    public void setOrganisationShortName(StringFilter organisationShortName) {
        this.organisationShortName = organisationShortName;
    }

    public StringFilter getSalutation() {
        return salutation;
    }

    public void setSalutation(StringFilter salutation) {
        this.salutation = salutation;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(StringFilter primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public StringFilter getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(StringFilter primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public BooleanFilter getIsTemporaryPassword() {
        return isTemporaryPassword;
    }

    public void setIsTemporaryPassword(BooleanFilter isTemporaryPassword) {
        this.isTemporaryPassword = isTemporaryPassword;
    }

    public StringFilter getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(StringFilter logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public StringFilter getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(StringFilter profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public InstantFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(InstantFilter birthdate) {
        this.birthdate = birthdate;
    }

    public InstantFilter getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(InstantFilter dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public InstantFilter getTrainingCompletedDate() {
        return trainingCompletedDate;
    }

    public void setTrainingCompletedDate(InstantFilter trainingCompletedDate) {
        this.trainingCompletedDate = trainingCompletedDate;
    }

    public InstantFilter getJdApprovedOn() {
        return jdApprovedOn;
    }

    public void setJdApprovedOn(InstantFilter jdApprovedOn) {
        this.jdApprovedOn = jdApprovedOn;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
    }

    public StringFilter getTanNo() {
        return tanNo;
    }

    public void setTanNo(StringFilter tanNo) {
        this.tanNo = tanNo;
    }

    public StringFilter getPanNo() {
        return panNo;
    }

    public void setPanNo(StringFilter panNo) {
        this.panNo = panNo;
    }

    public StringFilter getGstNo() {
        return gstNo;
    }

    public void setGstNo(StringFilter gstNo) {
        this.gstNo = gstNo;
    }

    public StringFilter getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(StringFilter aadharNo) {
        this.aadharNo = aadharNo;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getPartyTypeId() {
        return partyTypeId;
    }

    public void setPartyTypeId(LongFilter partyTypeId) {
        this.partyTypeId = partyTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartyCriteria that = (PartyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isOrganisation, that.isOrganisation) &&
            Objects.equals(organisationName, that.organisationName) &&
            Objects.equals(organisationShortName, that.organisationShortName) &&
            Objects.equals(salutation, that.salutation) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(primaryPhone, that.primaryPhone) &&
            Objects.equals(primaryEmail, that.primaryEmail) &&
            Objects.equals(isTemporaryPassword, that.isTemporaryPassword) &&
            Objects.equals(logoImageUrl, that.logoImageUrl) &&
            Objects.equals(profileImageUrl, that.profileImageUrl) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(dateOfJoining, that.dateOfJoining) &&
            Objects.equals(trainingCompletedDate, that.trainingCompletedDate) &&
            Objects.equals(jdApprovedOn, that.jdApprovedOn) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(tanNo, that.tanNo) &&
            Objects.equals(panNo, that.panNo) &&
            Objects.equals(gstNo, that.gstNo) &&
            Objects.equals(aadharNo, that.aadharNo) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(partyTypeId, that.partyTypeId) &&
            Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isOrganisation,
        organisationName,
        organisationShortName,
        salutation,
        firstName,
        middleName,
        lastName,
        gender,
        birthDate,
        primaryPhone,
        primaryEmail,
        isTemporaryPassword,
        logoImageUrl,
        profileImageUrl,
        notes,
        birthdate,
        dateOfJoining,
        trainingCompletedDate,
        jdApprovedOn,
        employeeId,
        tanNo,
        panNo,
        gstNo,
        aadharNo,
        userId,
        partyTypeId,
        statusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isOrganisation != null ? "isOrganisation=" + isOrganisation + ", " : "") +
                (organisationName != null ? "organisationName=" + organisationName + ", " : "") +
                (organisationShortName != null ? "organisationShortName=" + organisationShortName + ", " : "") +
                (salutation != null ? "salutation=" + salutation + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (primaryPhone != null ? "primaryPhone=" + primaryPhone + ", " : "") +
                (primaryEmail != null ? "primaryEmail=" + primaryEmail + ", " : "") +
                (isTemporaryPassword != null ? "isTemporaryPassword=" + isTemporaryPassword + ", " : "") +
                (logoImageUrl != null ? "logoImageUrl=" + logoImageUrl + ", " : "") +
                (profileImageUrl != null ? "profileImageUrl=" + profileImageUrl + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (dateOfJoining != null ? "dateOfJoining=" + dateOfJoining + ", " : "") +
                (trainingCompletedDate != null ? "trainingCompletedDate=" + trainingCompletedDate + ", " : "") +
                (jdApprovedOn != null ? "jdApprovedOn=" + jdApprovedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (tanNo != null ? "tanNo=" + tanNo + ", " : "") +
                (panNo != null ? "panNo=" + panNo + ", " : "") +
                (gstNo != null ? "gstNo=" + gstNo + ", " : "") +
                (aadharNo != null ? "aadharNo=" + aadharNo + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (partyTypeId != null ? "partyTypeId=" + partyTypeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
