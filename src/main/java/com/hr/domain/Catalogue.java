package com.hr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Catalogue.
 */
@Entity
@Table(name = "catalogue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60, unique = true)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "mobile_image_path")
    private String mobileImagePath;

    @Column(name = "alt_image_1")
    private String altImage1;

    @Column(name = "alt_image_2")
    private String altImage2;

    @Column(name = "alt_image_3")
    private String altImage3;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Catalogue name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Catalogue description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public Catalogue sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Catalogue imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMobileImagePath() {
        return mobileImagePath;
    }

    public Catalogue mobileImagePath(String mobileImagePath) {
        this.mobileImagePath = mobileImagePath;
        return this;
    }

    public void setMobileImagePath(String mobileImagePath) {
        this.mobileImagePath = mobileImagePath;
    }

    public String getAltImage1() {
        return altImage1;
    }

    public Catalogue altImage1(String altImage1) {
        this.altImage1 = altImage1;
        return this;
    }

    public void setAltImage1(String altImage1) {
        this.altImage1 = altImage1;
    }

    public String getAltImage2() {
        return altImage2;
    }

    public Catalogue altImage2(String altImage2) {
        this.altImage2 = altImage2;
        return this;
    }

    public void setAltImage2(String altImage2) {
        this.altImage2 = altImage2;
    }

    public String getAltImage3() {
        return altImage3;
    }

    public Catalogue altImage3(String altImage3) {
        this.altImage3 = altImage3;
        return this;
    }

    public void setAltImage3(String altImage3) {
        this.altImage3 = altImage3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogue)) {
            return false;
        }
        return id != null && id.equals(((Catalogue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            ", imagePath='" + getImagePath() + "'" +
            ", mobileImagePath='" + getMobileImagePath() + "'" +
            ", altImage1='" + getAltImage1() + "'" +
            ", altImage2='" + getAltImage2() + "'" +
            ", altImage3='" + getAltImage3() + "'" +
            "}";
    }
}
