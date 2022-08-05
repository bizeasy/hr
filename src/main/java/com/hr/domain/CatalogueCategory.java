package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CatalogueCategory.
 */
@Entity
@Table(name = "catalogue_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatalogueCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @ManyToOne
    @JsonIgnoreProperties(value = "catalogueCategories", allowSetters = true)
    private Catalogue catalogue;

    @ManyToOne
    @JsonIgnoreProperties(value = "catalogueCategories", allowSetters = true)
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "catalogueCategories", allowSetters = true)
    private CatalogueCategoryType catalogueCategoryType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public CatalogueCategory sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public CatalogueCategory catalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
        return this;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public CatalogueCategory productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public CatalogueCategoryType getCatalogueCategoryType() {
        return catalogueCategoryType;
    }

    public CatalogueCategory catalogueCategoryType(CatalogueCategoryType catalogueCategoryType) {
        this.catalogueCategoryType = catalogueCategoryType;
        return this;
    }

    public void setCatalogueCategoryType(CatalogueCategoryType catalogueCategoryType) {
        this.catalogueCategoryType = catalogueCategoryType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogueCategory)) {
            return false;
        }
        return id != null && id.equals(((CatalogueCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueCategory{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            "}";
    }
}
