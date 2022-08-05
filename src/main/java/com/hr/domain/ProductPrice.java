package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A ProductPrice.
 */
@Entity
@Table(name = "product_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "thru_date")
    private ZonedDateTime thruDate;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "cgst", precision = 21, scale = 2)
    private BigDecimal cgst;

    @Column(name = "igst", precision = 21, scale = 2)
    private BigDecimal igst;

    @Column(name = "sgst", precision = 21, scale = 2)
    private BigDecimal sgst;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne
    @JsonIgnoreProperties(value = "productPrices", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "productPrices", allowSetters = true)
    private ProductPriceType productPriceType;

    @ManyToOne
    @JsonIgnoreProperties(value = "productPrices", allowSetters = true)
    private ProductPricePurpose productPricePurpose;

    @ManyToOne
    @JsonIgnoreProperties(value = "productPrices", allowSetters = true)
    private Uom currencyUom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public ProductPrice fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public ProductPrice thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public ProductPrice cgst(BigDecimal cgst) {
        this.cgst = cgst;
        return this;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public ProductPrice igst(BigDecimal igst) {
        this.igst = igst;
        return this;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public ProductPrice sgst(BigDecimal sgst) {
        this.sgst = sgst;
        return this;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ProductPrice totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public ProductPrice product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductPriceType getProductPriceType() {
        return productPriceType;
    }

    public ProductPrice productPriceType(ProductPriceType productPriceType) {
        this.productPriceType = productPriceType;
        return this;
    }

    public void setProductPriceType(ProductPriceType productPriceType) {
        this.productPriceType = productPriceType;
    }

    public ProductPricePurpose getProductPricePurpose() {
        return productPricePurpose;
    }

    public ProductPrice productPricePurpose(ProductPricePurpose productPricePurpose) {
        this.productPricePurpose = productPricePurpose;
        return this;
    }

    public void setProductPricePurpose(ProductPricePurpose productPricePurpose) {
        this.productPricePurpose = productPricePurpose;
    }

    public Uom getCurrencyUom() {
        return currencyUom;
    }

    public ProductPrice currencyUom(Uom uom) {
        this.currencyUom = uom;
        return this;
    }

    public void setCurrencyUom(Uom uom) {
        this.currencyUom = uom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice)) {
            return false;
        }
        return id != null && id.equals(((ProductPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductPrice{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", price=" + getPrice() +
            ", cgst=" + getCgst() +
            ", igst=" + getIgst() +
            ", sgst=" + getSgst() +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
