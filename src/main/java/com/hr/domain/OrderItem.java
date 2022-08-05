package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "cancel_quantity", precision = 21, scale = 2)
    private BigDecimal cancelQuantity;

    @Column(name = "selected_amount", precision = 21, scale = 2)
    private BigDecimal selectedAmount;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "unit_list_price", precision = 21, scale = 2)
    private BigDecimal unitListPrice;

    @Column(name = "cgst", precision = 21, scale = 2)
    private BigDecimal cgst;

    @Column(name = "igst", precision = 21, scale = 2)
    private BigDecimal igst;

    @Column(name = "sgst", precision = 21, scale = 2)
    private BigDecimal sgst;

    @Column(name = "cgst_percentage", precision = 21, scale = 2)
    private BigDecimal cgstPercentage;

    @Column(name = "igst_percentage", precision = 21, scale = 2)
    private BigDecimal igstPercentage;

    @Column(name = "sgst_percentage", precision = 21, scale = 2)
    private BigDecimal sgstPercentage;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "is_modified_price")
    private Boolean isModifiedPrice;

    @Column(name = "estimated_ship_date")
    private ZonedDateTime estimatedShipDate;

    @Column(name = "estimated_delivery_date")
    private ZonedDateTime estimatedDeliveryDate;

    @Column(name = "ship_before_date")
    private ZonedDateTime shipBeforeDate;

    @Column(name = "ship_after_date")
    private ZonedDateTime shipAfterDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private OrderItemType orderItemType;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private InventoryItem fromInventoryItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private SupplierProduct supplierProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private TaxAuthorityRateType taxAuthRate;

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

    public OrderItem sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public OrderItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCancelQuantity() {
        return cancelQuantity;
    }

    public OrderItem cancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
        return this;
    }

    public void setCancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
    }

    public BigDecimal getSelectedAmount() {
        return selectedAmount;
    }

    public OrderItem selectedAmount(BigDecimal selectedAmount) {
        this.selectedAmount = selectedAmount;
        return this;
    }

    public void setSelectedAmount(BigDecimal selectedAmount) {
        this.selectedAmount = selectedAmount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public OrderItem unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitListPrice() {
        return unitListPrice;
    }

    public OrderItem unitListPrice(BigDecimal unitListPrice) {
        this.unitListPrice = unitListPrice;
        return this;
    }

    public void setUnitListPrice(BigDecimal unitListPrice) {
        this.unitListPrice = unitListPrice;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public OrderItem cgst(BigDecimal cgst) {
        this.cgst = cgst;
        return this;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public OrderItem igst(BigDecimal igst) {
        this.igst = igst;
        return this;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public OrderItem sgst(BigDecimal sgst) {
        this.sgst = sgst;
        return this;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getCgstPercentage() {
        return cgstPercentage;
    }

    public OrderItem cgstPercentage(BigDecimal cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
        return this;
    }

    public void setCgstPercentage(BigDecimal cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
    }

    public BigDecimal getIgstPercentage() {
        return igstPercentage;
    }

    public OrderItem igstPercentage(BigDecimal igstPercentage) {
        this.igstPercentage = igstPercentage;
        return this;
    }

    public void setIgstPercentage(BigDecimal igstPercentage) {
        this.igstPercentage = igstPercentage;
    }

    public BigDecimal getSgstPercentage() {
        return sgstPercentage;
    }

    public OrderItem sgstPercentage(BigDecimal sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
        return this;
    }

    public void setSgstPercentage(BigDecimal sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderItem totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean isIsModifiedPrice() {
        return isModifiedPrice;
    }

    public OrderItem isModifiedPrice(Boolean isModifiedPrice) {
        this.isModifiedPrice = isModifiedPrice;
        return this;
    }

    public void setIsModifiedPrice(Boolean isModifiedPrice) {
        this.isModifiedPrice = isModifiedPrice;
    }

    public ZonedDateTime getEstimatedShipDate() {
        return estimatedShipDate;
    }

    public OrderItem estimatedShipDate(ZonedDateTime estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
        return this;
    }

    public void setEstimatedShipDate(ZonedDateTime estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }

    public ZonedDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public OrderItem estimatedDeliveryDate(ZonedDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        return this;
    }

    public void setEstimatedDeliveryDate(ZonedDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public ZonedDateTime getShipBeforeDate() {
        return shipBeforeDate;
    }

    public OrderItem shipBeforeDate(ZonedDateTime shipBeforeDate) {
        this.shipBeforeDate = shipBeforeDate;
        return this;
    }

    public void setShipBeforeDate(ZonedDateTime shipBeforeDate) {
        this.shipBeforeDate = shipBeforeDate;
    }

    public ZonedDateTime getShipAfterDate() {
        return shipAfterDate;
    }

    public OrderItem shipAfterDate(ZonedDateTime shipAfterDate) {
        this.shipAfterDate = shipAfterDate;
        return this;
    }

    public void setShipAfterDate(ZonedDateTime shipAfterDate) {
        this.shipAfterDate = shipAfterDate;
    }

    public Order getOrder() {
        return order;
    }

    public OrderItem order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItemType getOrderItemType() {
        return orderItemType;
    }

    public OrderItem orderItemType(OrderItemType orderItemType) {
        this.orderItemType = orderItemType;
        return this;
    }

    public void setOrderItemType(OrderItemType orderItemType) {
        this.orderItemType = orderItemType;
    }

    public InventoryItem getFromInventoryItem() {
        return fromInventoryItem;
    }

    public OrderItem fromInventoryItem(InventoryItem inventoryItem) {
        this.fromInventoryItem = inventoryItem;
        return this;
    }

    public void setFromInventoryItem(InventoryItem inventoryItem) {
        this.fromInventoryItem = inventoryItem;
    }

    public Product getProduct() {
        return product;
    }

    public OrderItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SupplierProduct getSupplierProduct() {
        return supplierProduct;
    }

    public OrderItem supplierProduct(SupplierProduct supplierProduct) {
        this.supplierProduct = supplierProduct;
        return this;
    }

    public void setSupplierProduct(SupplierProduct supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public Status getStatus() {
        return status;
    }

    public OrderItem status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaxAuthorityRateType getTaxAuthRate() {
        return taxAuthRate;
    }

    public OrderItem taxAuthRate(TaxAuthorityRateType taxAuthorityRateType) {
        this.taxAuthRate = taxAuthorityRateType;
        return this;
    }

    public void setTaxAuthRate(TaxAuthorityRateType taxAuthorityRateType) {
        this.taxAuthRate = taxAuthorityRateType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", quantity=" + getQuantity() +
            ", cancelQuantity=" + getCancelQuantity() +
            ", selectedAmount=" + getSelectedAmount() +
            ", unitPrice=" + getUnitPrice() +
            ", unitListPrice=" + getUnitListPrice() +
            ", cgst=" + getCgst() +
            ", igst=" + getIgst() +
            ", sgst=" + getSgst() +
            ", cgstPercentage=" + getCgstPercentage() +
            ", igstPercentage=" + getIgstPercentage() +
            ", sgstPercentage=" + getSgstPercentage() +
            ", totalPrice=" + getTotalPrice() +
            ", isModifiedPrice='" + isIsModifiedPrice() + "'" +
            ", estimatedShipDate='" + getEstimatedShipDate() + "'" +
            ", estimatedDeliveryDate='" + getEstimatedDeliveryDate() + "'" +
            ", shipBeforeDate='" + getShipBeforeDate() + "'" +
            ", shipAfterDate='" + getShipAfterDate() + "'" +
            "}";
    }
}
