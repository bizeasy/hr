package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ProductFacility.
 */
@Entity
@Table(name = "product_facility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "minimum_stock", precision = 21, scale = 2)
    private BigDecimal minimumStock;

    @Column(name = "reorder_qty", precision = 21, scale = 2)
    private BigDecimal reorderQty;

    @Column(name = "days_to_ship")
    private Integer daysToShip;

    @Column(name = "last_inventory_count", precision = 21, scale = 2)
    private BigDecimal lastInventoryCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "productFacilities", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "productFacilities", allowSetters = true)
    private Facility facility;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinimumStock() {
        return minimumStock;
    }

    public ProductFacility minimumStock(BigDecimal minimumStock) {
        this.minimumStock = minimumStock;
        return this;
    }

    public void setMinimumStock(BigDecimal minimumStock) {
        this.minimumStock = minimumStock;
    }

    public BigDecimal getReorderQty() {
        return reorderQty;
    }

    public ProductFacility reorderQty(BigDecimal reorderQty) {
        this.reorderQty = reorderQty;
        return this;
    }

    public void setReorderQty(BigDecimal reorderQty) {
        this.reorderQty = reorderQty;
    }

    public Integer getDaysToShip() {
        return daysToShip;
    }

    public ProductFacility daysToShip(Integer daysToShip) {
        this.daysToShip = daysToShip;
        return this;
    }

    public void setDaysToShip(Integer daysToShip) {
        this.daysToShip = daysToShip;
    }

    public BigDecimal getLastInventoryCount() {
        return lastInventoryCount;
    }

    public ProductFacility lastInventoryCount(BigDecimal lastInventoryCount) {
        this.lastInventoryCount = lastInventoryCount;
        return this;
    }

    public void setLastInventoryCount(BigDecimal lastInventoryCount) {
        this.lastInventoryCount = lastInventoryCount;
    }

    public Product getProduct() {
        return product;
    }

    public ProductFacility product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Facility getFacility() {
        return facility;
    }

    public ProductFacility facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductFacility)) {
            return false;
        }
        return id != null && id.equals(((ProductFacility) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductFacility{" +
            "id=" + getId() +
            ", minimumStock=" + getMinimumStock() +
            ", reorderQty=" + getReorderQty() +
            ", daysToShip=" + getDaysToShip() +
            ", lastInventoryCount=" + getLastInventoryCount() +
            "}";
    }
}
