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
 * A InventoryItemDelegate.
 */
@Entity
@Table(name = "inventory_item_delegate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryItemDelegate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "effective_date")
    private ZonedDateTime effectiveDate;

    @Column(name = "quantity_on_hand_diff", precision = 21, scale = 2)
    private BigDecimal quantityOnHandDiff;

    @Column(name = "available_to_promise_diff", precision = 21, scale = 2)
    private BigDecimal availableToPromiseDiff;

    @Column(name = "accounting_quantity_diff", precision = 21, scale = 2)
    private BigDecimal accountingQuantityDiff;

    @Column(name = "unit_cost", precision = 21, scale = 2)
    private BigDecimal unitCost;

    @Size(max = 255)
    @Column(name = "remarks", length = 255)
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private InvoiceItem invoiceItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private OrderItem orderItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private ItemIssuance itemIssuance;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private InventoryTransfer inventoryTransfer;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private InventoryItemVariance inventoryItemVariance;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemDelegates", allowSetters = true)
    private InventoryItem inventoryItem;

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

    public InventoryItemDelegate sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public ZonedDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public InventoryItemDelegate effectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getQuantityOnHandDiff() {
        return quantityOnHandDiff;
    }

    public InventoryItemDelegate quantityOnHandDiff(BigDecimal quantityOnHandDiff) {
        this.quantityOnHandDiff = quantityOnHandDiff;
        return this;
    }

    public void setQuantityOnHandDiff(BigDecimal quantityOnHandDiff) {
        this.quantityOnHandDiff = quantityOnHandDiff;
    }

    public BigDecimal getAvailableToPromiseDiff() {
        return availableToPromiseDiff;
    }

    public InventoryItemDelegate availableToPromiseDiff(BigDecimal availableToPromiseDiff) {
        this.availableToPromiseDiff = availableToPromiseDiff;
        return this;
    }

    public void setAvailableToPromiseDiff(BigDecimal availableToPromiseDiff) {
        this.availableToPromiseDiff = availableToPromiseDiff;
    }

    public BigDecimal getAccountingQuantityDiff() {
        return accountingQuantityDiff;
    }

    public InventoryItemDelegate accountingQuantityDiff(BigDecimal accountingQuantityDiff) {
        this.accountingQuantityDiff = accountingQuantityDiff;
        return this;
    }

    public void setAccountingQuantityDiff(BigDecimal accountingQuantityDiff) {
        this.accountingQuantityDiff = accountingQuantityDiff;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public InventoryItemDelegate unitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        return this;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getRemarks() {
        return remarks;
    }

    public InventoryItemDelegate remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InventoryItemDelegate invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public InventoryItemDelegate invoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
        return this;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public Order getOrder() {
        return order;
    }

    public InventoryItemDelegate order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public InventoryItemDelegate orderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public ItemIssuance getItemIssuance() {
        return itemIssuance;
    }

    public InventoryItemDelegate itemIssuance(ItemIssuance itemIssuance) {
        this.itemIssuance = itemIssuance;
        return this;
    }

    public void setItemIssuance(ItemIssuance itemIssuance) {
        this.itemIssuance = itemIssuance;
    }

    public InventoryTransfer getInventoryTransfer() {
        return inventoryTransfer;
    }

    public InventoryItemDelegate inventoryTransfer(InventoryTransfer inventoryTransfer) {
        this.inventoryTransfer = inventoryTransfer;
        return this;
    }

    public void setInventoryTransfer(InventoryTransfer inventoryTransfer) {
        this.inventoryTransfer = inventoryTransfer;
    }

    public InventoryItemVariance getInventoryItemVariance() {
        return inventoryItemVariance;
    }

    public InventoryItemDelegate inventoryItemVariance(InventoryItemVariance inventoryItemVariance) {
        this.inventoryItemVariance = inventoryItemVariance;
        return this;
    }

    public void setInventoryItemVariance(InventoryItemVariance inventoryItemVariance) {
        this.inventoryItemVariance = inventoryItemVariance;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public InventoryItemDelegate inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryItemDelegate)) {
            return false;
        }
        return id != null && id.equals(((InventoryItemDelegate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItemDelegate{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", quantityOnHandDiff=" + getQuantityOnHandDiff() +
            ", availableToPromiseDiff=" + getAvailableToPromiseDiff() +
            ", accountingQuantityDiff=" + getAccountingQuantityDiff() +
            ", unitCost=" + getUnitCost() +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
