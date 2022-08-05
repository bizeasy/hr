package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A SupplierProduct.
 */
@Entity
@Table(name = "supplier_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplierProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "thru_date")
    private ZonedDateTime thruDate;

    @Column(name = "min_order_qty", precision = 21, scale = 2)
    private BigDecimal minOrderQty;

    @Column(name = "last_price", precision = 21, scale = 2)
    private BigDecimal lastPrice;

    @Column(name = "shipping_price", precision = 21, scale = 2)
    private BigDecimal shippingPrice;

    @Column(name = "supplier_product_id")
    private String supplierProductId;

    @Column(name = "lead_time_days")
    private Integer leadTimeDays;

    @Column(name = "cgst", precision = 21, scale = 2)
    private BigDecimal cgst;

    @Column(name = "igst", precision = 21, scale = 2)
    private BigDecimal igst;

    @Column(name = "sgst", precision = 21, scale = 2)
    private BigDecimal sgst;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "supplier_product_name")
    private String supplierProductName;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @ManyToOne
    @JsonIgnoreProperties(value = "supplierProducts", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "supplierProducts", allowSetters = true)
    private Party supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "supplierProducts", allowSetters = true)
    private Uom quantityUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "supplierProducts", allowSetters = true)
    private Uom currencyUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "supplierProducts", allowSetters = true)
    private Party manufacturer;

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

    public SupplierProduct fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public SupplierProduct thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public BigDecimal getMinOrderQty() {
        return minOrderQty;
    }

    public SupplierProduct minOrderQty(BigDecimal minOrderQty) {
        this.minOrderQty = minOrderQty;
        return this;
    }

    public void setMinOrderQty(BigDecimal minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public SupplierProduct lastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public SupplierProduct shippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
        return this;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getSupplierProductId() {
        return supplierProductId;
    }

    public SupplierProduct supplierProductId(String supplierProductId) {
        this.supplierProductId = supplierProductId;
        return this;
    }

    public void setSupplierProductId(String supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public SupplierProduct leadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
        return this;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public SupplierProduct cgst(BigDecimal cgst) {
        this.cgst = cgst;
        return this;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public SupplierProduct igst(BigDecimal igst) {
        this.igst = igst;
        return this;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public SupplierProduct sgst(BigDecimal sgst) {
        this.sgst = sgst;
        return this;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public SupplierProduct totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComments() {
        return comments;
    }

    public SupplierProduct comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSupplierProductName() {
        return supplierProductName;
    }

    public SupplierProduct supplierProductName(String supplierProductName) {
        this.supplierProductName = supplierProductName;
        return this;
    }

    public void setSupplierProductName(String supplierProductName) {
        this.supplierProductName = supplierProductName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public SupplierProduct manufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
        return this;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Product getProduct() {
        return product;
    }

    public SupplierProduct product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Party getSupplier() {
        return supplier;
    }

    public SupplierProduct supplier(Party party) {
        this.supplier = party;
        return this;
    }

    public void setSupplier(Party party) {
        this.supplier = party;
    }

    public Uom getQuantityUom() {
        return quantityUom;
    }

    public SupplierProduct quantityUom(Uom uom) {
        this.quantityUom = uom;
        return this;
    }

    public void setQuantityUom(Uom uom) {
        this.quantityUom = uom;
    }

    public Uom getCurrencyUom() {
        return currencyUom;
    }

    public SupplierProduct currencyUom(Uom uom) {
        this.currencyUom = uom;
        return this;
    }

    public void setCurrencyUom(Uom uom) {
        this.currencyUom = uom;
    }

    public Party getManufacturer() {
        return manufacturer;
    }

    public SupplierProduct manufacturer(Party party) {
        this.manufacturer = party;
        return this;
    }

    public void setManufacturer(Party party) {
        this.manufacturer = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierProduct)) {
            return false;
        }
        return id != null && id.equals(((SupplierProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierProduct{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", minOrderQty=" + getMinOrderQty() +
            ", lastPrice=" + getLastPrice() +
            ", shippingPrice=" + getShippingPrice() +
            ", supplierProductId='" + getSupplierProductId() + "'" +
            ", leadTimeDays=" + getLeadTimeDays() +
            ", cgst=" + getCgst() +
            ", igst=" + getIgst() +
            ", sgst=" + getSgst() +
            ", totalPrice=" + getTotalPrice() +
            ", comments='" + getComments() + "'" +
            ", supplierProductName='" + getSupplierProductName() + "'" +
            ", manufacturerName='" + getManufacturerName() + "'" +
            "}";
    }
}
