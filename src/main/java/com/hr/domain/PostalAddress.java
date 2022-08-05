package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PostalAddress.
 */
@Entity
@Table(name = "postal_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PostalAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "to_name", length = 100)
    private String toName;

    @Size(max = 200)
    @Column(name = "address_1", length = 200)
    private String address1;

    @Size(max = 200)
    @Column(name = "address_2", length = 200)
    private String address2;

    @Size(max = 60)
    @Column(name = "city", length = 60)
    private String city;

    @Size(max = 60)
    @Column(name = "landmark", length = 60)
    private String landmark;

    @Size(max = 10)
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Size(max = 25)
    @Column(name = "custom_address_type", length = 25)
    private String customAddressType;

    @Size(max = 40)
    @Column(name = "state_str", length = 40)
    private String stateStr;

    @Size(max = 40)
    @Column(name = "country_str", length = 40)
    private String countryStr;

    @Column(name = "is_indian_address")
    private Boolean isIndianAddress;

    @Size(max = 255)
    @Column(name = "note", length = 255)
    private String note;

    @Size(max = 255)
    @Column(name = "directions", length = 255)
    private String directions;

    @ManyToOne
    @JsonIgnoreProperties(value = "postalAddresses", allowSetters = true)
    private Geo state;

    @ManyToOne
    @JsonIgnoreProperties(value = "postalAddresses", allowSetters = true)
    private Geo pincode;

    @ManyToOne
    @JsonIgnoreProperties(value = "postalAddresses", allowSetters = true)
    private Geo country;

    @ManyToOne
    @JsonIgnoreProperties(value = "postalAddresses", allowSetters = true)
    private ContactMech contactMech;

    @ManyToOne
    @JsonIgnoreProperties(value = "postalAddresses", allowSetters = true)
    private GeoPoint geoPoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToName() {
        return toName;
    }

    public PostalAddress toName(String toName) {
        this.toName = toName;
        return this;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getAddress1() {
        return address1;
    }

    public PostalAddress address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public PostalAddress address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public PostalAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLandmark() {
        return landmark;
    }

    public PostalAddress landmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public PostalAddress postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public PostalAddress isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getCustomAddressType() {
        return customAddressType;
    }

    public PostalAddress customAddressType(String customAddressType) {
        this.customAddressType = customAddressType;
        return this;
    }

    public void setCustomAddressType(String customAddressType) {
        this.customAddressType = customAddressType;
    }

    public String getStateStr() {
        return stateStr;
    }

    public PostalAddress stateStr(String stateStr) {
        this.stateStr = stateStr;
        return this;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getCountryStr() {
        return countryStr;
    }

    public PostalAddress countryStr(String countryStr) {
        this.countryStr = countryStr;
        return this;
    }

    public void setCountryStr(String countryStr) {
        this.countryStr = countryStr;
    }

    public Boolean isIsIndianAddress() {
        return isIndianAddress;
    }

    public PostalAddress isIndianAddress(Boolean isIndianAddress) {
        this.isIndianAddress = isIndianAddress;
        return this;
    }

    public void setIsIndianAddress(Boolean isIndianAddress) {
        this.isIndianAddress = isIndianAddress;
    }

    public String getNote() {
        return note;
    }

    public PostalAddress note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDirections() {
        return directions;
    }

    public PostalAddress directions(String directions) {
        this.directions = directions;
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Geo getState() {
        return state;
    }

    public PostalAddress state(Geo geo) {
        this.state = geo;
        return this;
    }

    public void setState(Geo geo) {
        this.state = geo;
    }

    public Geo getPincode() {
        return pincode;
    }

    public PostalAddress pincode(Geo geo) {
        this.pincode = geo;
        return this;
    }

    public void setPincode(Geo geo) {
        this.pincode = geo;
    }

    public Geo getCountry() {
        return country;
    }

    public PostalAddress country(Geo geo) {
        this.country = geo;
        return this;
    }

    public void setCountry(Geo geo) {
        this.country = geo;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public PostalAddress contactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
        return this;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public PostalAddress geoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
        return this;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostalAddress)) {
            return false;
        }
        return id != null && id.equals(((PostalAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostalAddress{" +
            "id=" + getId() +
            ", toName='" + getToName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", isDefault='" + isIsDefault() + "'" +
            ", customAddressType='" + getCustomAddressType() + "'" +
            ", stateStr='" + getStateStr() + "'" +
            ", countryStr='" + getCountryStr() + "'" +
            ", isIndianAddress='" + isIsIndianAddress() + "'" +
            ", note='" + getNote() + "'" +
            ", directions='" + getDirections() + "'" +
            "}";
    }
}
