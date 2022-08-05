package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.hr.domain.enumeration.Gender;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_organisation")
    private Boolean isOrganisation;

    @Size(max = 200)
    @Column(name = "organisation_name", length = 200)
    private String organisationName;

    @Size(max = 100)
    @Column(name = "organisation_short_name", length = 100)
    private String organisationShortName;

    @Column(name = "salutation")
    private String salutation;

    @Size(max = 60)
    @Column(name = "first_name", length = 60)
    private String firstName;

    @Size(max = 60)
    @Column(name = "middle_name", length = 60)
    private String middleName;

    @Size(max = 60)
    @Column(name = "last_name", length = 60)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 20)
    @Column(name = "primary_phone", length = 20)
    private String primaryPhone;

    @Size(min = 5, max = 75)
    @Column(name = "primary_email", length = 75)
    private String primaryEmail;

    @Column(name = "is_temporary_password")
    private Boolean isTemporaryPassword;

    @Column(name = "logo_image_url")
    private String logoImageUrl;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Size(max = 255)
    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Column(name = "date_of_joining")
    private Instant dateOfJoining;

    @Column(name = "training_completed_date")
    private Instant trainingCompletedDate;

    @Column(name = "jd_approved_on")
    private Instant jdApprovedOn;

    @Column(name = "employee_id")
    private String employeeId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "auth_string")
    private String authString;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "user_group_string")
    private String userGroupString;

    @Size(max = 25)
    @Column(name = "tan_no", length = 25)
    private String tanNo;

    @Size(max = 25)
    @Column(name = "pan_no", length = 25)
    private String panNo;

    @Size(max = 25)
    @Column(name = "gst_no", length = 25)
    private String gstNo;

    @Size(max = 25)
    @Column(name = "aadhar_no", length = 25)
    private String aadharNo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "parties", allowSetters = true)
    private PartyType partyType;

    @ManyToOne
    @JsonIgnoreProperties(value = "parties", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsOrganisation() {
        return isOrganisation;
    }

    public Party isOrganisation(Boolean isOrganisation) {
        this.isOrganisation = isOrganisation;
        return this;
    }

    public void setIsOrganisation(Boolean isOrganisation) {
        this.isOrganisation = isOrganisation;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public Party organisationName(String organisationName) {
        this.organisationName = organisationName;
        return this;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrganisationShortName() {
        return organisationShortName;
    }

    public Party organisationShortName(String organisationShortName) {
        this.organisationShortName = organisationShortName;
        return this;
    }

    public void setOrganisationShortName(String organisationShortName) {
        this.organisationShortName = organisationShortName;
    }

    public String getSalutation() {
        return salutation;
    }

    public Party salutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public Party firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Party middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Party lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Party gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Party birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public Party primaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
        return this;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public Party primaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Boolean isIsTemporaryPassword() {
        return isTemporaryPassword;
    }

    public Party isTemporaryPassword(Boolean isTemporaryPassword) {
        this.isTemporaryPassword = isTemporaryPassword;
        return this;
    }

    public void setIsTemporaryPassword(Boolean isTemporaryPassword) {
        this.isTemporaryPassword = isTemporaryPassword;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public Party logoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
        return this;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Party profileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        return this;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getNotes() {
        return notes;
    }

    public Party notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public Party birthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public Instant getDateOfJoining() {
        return dateOfJoining;
    }

    public Party dateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
        return this;
    }

    public void setDateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Instant getTrainingCompletedDate() {
        return trainingCompletedDate;
    }

    public Party trainingCompletedDate(Instant trainingCompletedDate) {
        this.trainingCompletedDate = trainingCompletedDate;
        return this;
    }

    public void setTrainingCompletedDate(Instant trainingCompletedDate) {
        this.trainingCompletedDate = trainingCompletedDate;
    }

    public Instant getJdApprovedOn() {
        return jdApprovedOn;
    }

    public Party jdApprovedOn(Instant jdApprovedOn) {
        this.jdApprovedOn = jdApprovedOn;
        return this;
    }

    public void setJdApprovedOn(Instant jdApprovedOn) {
        this.jdApprovedOn = jdApprovedOn;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Party employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAuthString() {
        return authString;
    }

    public Party authString(String authString) {
        this.authString = authString;
        return this;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }

    public String getUserGroupString() {
        return userGroupString;
    }

    public Party userGroupString(String userGroupString) {
        this.userGroupString = userGroupString;
        return this;
    }

    public void setUserGroupString(String userGroupString) {
        this.userGroupString = userGroupString;
    }

    public String getTanNo() {
        return tanNo;
    }

    public Party tanNo(String tanNo) {
        this.tanNo = tanNo;
        return this;
    }

    public void setTanNo(String tanNo) {
        this.tanNo = tanNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public Party panNo(String panNo) {
        this.panNo = panNo;
        return this;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public Party gstNo(String gstNo) {
        this.gstNo = gstNo;
        return this;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public Party aadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
        return this;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public User getUser() {
        return user;
    }

    public Party user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public Party partyType(PartyType partyType) {
        this.partyType = partyType;
        return this;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }

    public Status getStatus() {
        return status;
    }

    public Party status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party)) {
            return false;
        }
        return id != null && id.equals(((Party) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", isOrganisation='" + isIsOrganisation() + "'" +
            ", organisationName='" + getOrganisationName() + "'" +
            ", organisationShortName='" + getOrganisationShortName() + "'" +
            ", salutation='" + getSalutation() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", primaryPhone='" + getPrimaryPhone() + "'" +
            ", primaryEmail='" + getPrimaryEmail() + "'" +
            ", isTemporaryPassword='" + isIsTemporaryPassword() + "'" +
            ", logoImageUrl='" + getLogoImageUrl() + "'" +
            ", profileImageUrl='" + getProfileImageUrl() + "'" +
            ", notes='" + getNotes() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", trainingCompletedDate='" + getTrainingCompletedDate() + "'" +
            ", jdApprovedOn='" + getJdApprovedOn() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", authString='" + getAuthString() + "'" +
            ", userGroupString='" + getUserGroupString() + "'" +
            ", tanNo='" + getTanNo() + "'" +
            ", panNo='" + getPanNo() + "'" +
            ", gstNo='" + getGstNo() + "'" +
            ", aadharNo='" + getAadharNo() + "'" +
            "}";
    }
}
