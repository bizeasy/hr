package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProductStoreProduct.
 */
@Entity
@Table(name = "product_store_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductStoreProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "thru_date")
    private Instant thruDate;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productStoreProducts", allowSetters = true)
    private ProductStore productStore;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productStoreProducts", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public ProductStoreProduct fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getThruDate() {
        return thruDate;
    }

    public ProductStoreProduct thruDate(Instant thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(Instant thruDate) {
        this.thruDate = thruDate;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public ProductStoreProduct sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    public ProductStoreProduct productStore(ProductStore productStore) {
        this.productStore = productStore;
        return this;
    }

    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }

    public Product getProduct() {
        return product;
    }

    public ProductStoreProduct product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStoreProduct)) {
            return false;
        }
        return id != null && id.equals(((ProductStoreProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreProduct{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            "}";
    }
}
