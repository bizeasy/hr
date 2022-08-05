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
 * A ItemIssuance.
 */
@Entity
@Table(name = "item_issuance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemIssuance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 255)
    @Column(name = "message", length = 255)
    private String message;

    @Column(name = "issued_date")
    private ZonedDateTime issuedDate;

    @Size(max = 60)
    @Column(name = "issued_by", length = 60)
    private String issuedBy;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "cancel_quantity", precision = 21, scale = 2)
    private BigDecimal cancelQuantity;

    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @Column(name = "thru_date")
    private ZonedDateTime thruDate;

    @Size(max = 20)
    @Column(name = "equipment_id", length = 20)
    private String equipmentId;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private OrderItem orderItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private InventoryItem inventoryItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private User issuedByUserLogin;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private Reason varianceReason;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemIssuances", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public ItemIssuance message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ItemIssuance issuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public ItemIssuance issuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
        return this;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public ItemIssuance quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCancelQuantity() {
        return cancelQuantity;
    }

    public ItemIssuance cancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
        return this;
    }

    public void setCancelQuantity(BigDecimal cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public ItemIssuance fromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getThruDate() {
        return thruDate;
    }

    public ItemIssuance thruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(ZonedDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public ItemIssuance equipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
        return this;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Order getOrder() {
        return order;
    }

    public ItemIssuance order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public ItemIssuance orderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public ItemIssuance inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public User getIssuedByUserLogin() {
        return issuedByUserLogin;
    }

    public ItemIssuance issuedByUserLogin(User user) {
        this.issuedByUserLogin = user;
        return this;
    }

    public void setIssuedByUserLogin(User user) {
        this.issuedByUserLogin = user;
    }

    public Reason getVarianceReason() {
        return varianceReason;
    }

    public ItemIssuance varianceReason(Reason reason) {
        this.varianceReason = reason;
        return this;
    }

    public void setVarianceReason(Reason reason) {
        this.varianceReason = reason;
    }

    public Facility getFacility() {
        return facility;
    }

    public ItemIssuance facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Status getStatus() {
        return status;
    }

    public ItemIssuance status(Status status) {
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
        if (!(o instanceof ItemIssuance)) {
            return false;
        }
        return id != null && id.equals(((ItemIssuance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemIssuance{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", issuedBy='" + getIssuedBy() + "'" +
            ", quantity=" + getQuantity() +
            ", cancelQuantity=" + getCancelQuantity() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", equipmentId='" + getEquipmentId() + "'" +
            "}";
    }
}
