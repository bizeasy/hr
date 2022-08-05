package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductKeyword.
 */
@Entity
@Table(name = "product_keyword")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductKeyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "keyword", length = 60)
    private String keyword;

    @Column(name = "relevancy_weight")
    private Integer relevancyWeight;

    @ManyToOne
    @JsonIgnoreProperties(value = "productKeywords", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "productKeywords", allowSetters = true)
    private KeywordType keywordType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public ProductKeyword keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getRelevancyWeight() {
        return relevancyWeight;
    }

    public ProductKeyword relevancyWeight(Integer relevancyWeight) {
        this.relevancyWeight = relevancyWeight;
        return this;
    }

    public void setRelevancyWeight(Integer relevancyWeight) {
        this.relevancyWeight = relevancyWeight;
    }

    public Product getProduct() {
        return product;
    }

    public ProductKeyword product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public KeywordType getKeywordType() {
        return keywordType;
    }

    public ProductKeyword keywordType(KeywordType keywordType) {
        this.keywordType = keywordType;
        return this;
    }

    public void setKeywordType(KeywordType keywordType) {
        this.keywordType = keywordType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductKeyword)) {
            return false;
        }
        return id != null && id.equals(((ProductKeyword) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductKeyword{" +
            "id=" + getId() +
            ", keyword='" + getKeyword() + "'" +
            ", relevancyWeight=" + getRelevancyWeight() +
            "}";
    }
}
