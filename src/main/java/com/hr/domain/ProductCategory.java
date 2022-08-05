package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory implements Serializable {

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

    @Column(name = "long_description")
    private String longDescription;

    @Size(max = 25)
    @Column(name = "attribute", length = 25)
    private String attribute;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "alt_image_url")
    private String altImageUrl;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "info")
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategories", allowSetters = true)
    private ProductCategoryType productCategoryType;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategories", allowSetters = true)
    private ProductCategory parent;

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

    public ProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public ProductCategory longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getAttribute() {
        return attribute;
    }

    public ProductCategory attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public ProductCategory sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductCategory imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltImageUrl() {
        return altImageUrl;
    }

    public ProductCategory altImageUrl(String altImageUrl) {
        this.altImageUrl = altImageUrl;
        return this;
    }

    public void setAltImageUrl(String altImageUrl) {
        this.altImageUrl = altImageUrl;
    }

    public String getInfo() {
        return info;
    }

    public ProductCategory info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ProductCategoryType getProductCategoryType() {
        return productCategoryType;
    }

    public ProductCategory productCategoryType(ProductCategoryType productCategoryType) {
        this.productCategoryType = productCategoryType;
        return this;
    }

    public void setProductCategoryType(ProductCategoryType productCategoryType) {
        this.productCategoryType = productCategoryType;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public ProductCategory parent(ProductCategory productCategory) {
        this.parent = productCategory;
        return this;
    }

    public void setParent(ProductCategory productCategory) {
        this.parent = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", attribute='" + getAttribute() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", altImageUrl='" + getAltImageUrl() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
