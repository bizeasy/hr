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
 * A Order.
 */
@Entity
@Table(name = "sys_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @Size(max = 25)
    @Column(name = "external_id", length = 25)
    private String externalId;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @Max(value = 10)
    @Column(name = "priority")
    private Integer priority;

    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "is_rush_order")
    private Boolean isRushOrder;

    @Column(name = "needs_inventory_issuance")
    private Boolean needsInventoryIssuance;

    @Column(name = "remaining_sub_total", precision = 21, scale = 2)
    private BigDecimal remainingSubTotal;

    @Column(name = "grand_total", precision = 21, scale = 2)
    private BigDecimal grandTotal;

    @Column(name = "has_rate_contract")
    private Boolean hasRateContract;

    @Column(name = "got_quote_from_vendors")
    private Boolean gotQuoteFromVendors;

    @Column(name = "vendor_reason")
    private String vendorReason;

    @Column(name = "expected_delivery_date")
    private ZonedDateTime expectedDeliveryDate;

    @Column(name = "insurance_resp")
    private String insuranceResp;

    @Column(name = "transport_resp")
    private String transportResp;

    @Column(name = "unloading_resp")
    private String unloadingResp;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private OrderType orderType;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private SalesChannel salesChannel;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Status status;

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

    public Order name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public Order externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public Order orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public Order priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public Order entryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public Boolean isIsRushOrder() {
        return isRushOrder;
    }

    public Order isRushOrder(Boolean isRushOrder) {
        this.isRushOrder = isRushOrder;
        return this;
    }

    public void setIsRushOrder(Boolean isRushOrder) {
        this.isRushOrder = isRushOrder;
    }

    public Boolean isNeedsInventoryIssuance() {
        return needsInventoryIssuance;
    }

    public Order needsInventoryIssuance(Boolean needsInventoryIssuance) {
        this.needsInventoryIssuance = needsInventoryIssuance;
        return this;
    }

    public void setNeedsInventoryIssuance(Boolean needsInventoryIssuance) {
        this.needsInventoryIssuance = needsInventoryIssuance;
    }

    public BigDecimal getRemainingSubTotal() {
        return remainingSubTotal;
    }

    public Order remainingSubTotal(BigDecimal remainingSubTotal) {
        this.remainingSubTotal = remainingSubTotal;
        return this;
    }

    public void setRemainingSubTotal(BigDecimal remainingSubTotal) {
        this.remainingSubTotal = remainingSubTotal;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public Order grandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Boolean isHasRateContract() {
        return hasRateContract;
    }

    public Order hasRateContract(Boolean hasRateContract) {
        this.hasRateContract = hasRateContract;
        return this;
    }

    public void setHasRateContract(Boolean hasRateContract) {
        this.hasRateContract = hasRateContract;
    }

    public Boolean isGotQuoteFromVendors() {
        return gotQuoteFromVendors;
    }

    public Order gotQuoteFromVendors(Boolean gotQuoteFromVendors) {
        this.gotQuoteFromVendors = gotQuoteFromVendors;
        return this;
    }

    public void setGotQuoteFromVendors(Boolean gotQuoteFromVendors) {
        this.gotQuoteFromVendors = gotQuoteFromVendors;
    }

    public String getVendorReason() {
        return vendorReason;
    }

    public Order vendorReason(String vendorReason) {
        this.vendorReason = vendorReason;
        return this;
    }

    public void setVendorReason(String vendorReason) {
        this.vendorReason = vendorReason;
    }

    public ZonedDateTime getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public Order expectedDeliveryDate(ZonedDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(ZonedDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getInsuranceResp() {
        return insuranceResp;
    }

    public Order insuranceResp(String insuranceResp) {
        this.insuranceResp = insuranceResp;
        return this;
    }

    public void setInsuranceResp(String insuranceResp) {
        this.insuranceResp = insuranceResp;
    }

    public String getTransportResp() {
        return transportResp;
    }

    public Order transportResp(String transportResp) {
        this.transportResp = transportResp;
        return this;
    }

    public void setTransportResp(String transportResp) {
        this.transportResp = transportResp;
    }

    public String getUnloadingResp() {
        return unloadingResp;
    }

    public Order unloadingResp(String unloadingResp) {
        this.unloadingResp = unloadingResp;
        return this;
    }

    public void setUnloadingResp(String unloadingResp) {
        this.unloadingResp = unloadingResp;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Order orderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public Order salesChannel(SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
        return this;
    }

    public void setSalesChannel(SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
    }

    public Party getParty() {
        return party;
    }

    public Order party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Status getStatus() {
        return status;
    }

    public Order status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", priority=" + getPriority() +
            ", entryDate='" + getEntryDate() + "'" +
            ", isRushOrder='" + isIsRushOrder() + "'" +
            ", needsInventoryIssuance='" + isNeedsInventoryIssuance() + "'" +
            ", remainingSubTotal=" + getRemainingSubTotal() +
            ", grandTotal=" + getGrandTotal() +
            ", hasRateContract='" + isHasRateContract() + "'" +
            ", gotQuoteFromVendors='" + isGotQuoteFromVendors() + "'" +
            ", vendorReason='" + getVendorReason() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", insuranceResp='" + getInsuranceResp() + "'" +
            ", transportResp='" + getTransportResp() + "'" +
            ", unloadingResp='" + getUnloadingResp() + "'" +
            "}";
    }
}
