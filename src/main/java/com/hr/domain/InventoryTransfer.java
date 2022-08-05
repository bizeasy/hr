package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A InventoryTransfer.
 */
@Entity
@Table(name = "inventory_transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sent_date")
    private ZonedDateTime sentDate;

    @Column(name = "received_date")
    private ZonedDateTime receivedDate;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryTransfers", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryTransfers", allowSetters = true)
    private InventoryItem inventoryItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryTransfers", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryTransfers", allowSetters = true)
    private Facility facilityTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryTransfers", allowSetters = true)
    private ItemIssuance issuance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSentDate() {
        return sentDate;
    }

    public InventoryTransfer sentDate(ZonedDateTime sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public void setSentDate(ZonedDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public ZonedDateTime getReceivedDate() {
        return receivedDate;
    }

    public InventoryTransfer receivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    public void setReceivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getComments() {
        return comments;
    }

    public InventoryTransfer comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Status getStatus() {
        return status;
    }

    public InventoryTransfer status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public InventoryTransfer inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Facility getFacility() {
        return facility;
    }

    public InventoryTransfer facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Facility getFacilityTo() {
        return facilityTo;
    }

    public InventoryTransfer facilityTo(Facility facility) {
        this.facilityTo = facility;
        return this;
    }

    public void setFacilityTo(Facility facility) {
        this.facilityTo = facility;
    }

    public ItemIssuance getIssuance() {
        return issuance;
    }

    public InventoryTransfer issuance(ItemIssuance itemIssuance) {
        this.issuance = itemIssuance;
        return this;
    }

    public void setIssuance(ItemIssuance itemIssuance) {
        this.issuance = itemIssuance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryTransfer)) {
            return false;
        }
        return id != null && id.equals(((InventoryTransfer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryTransfer{" +
            "id=" + getId() +
            ", sentDate='" + getSentDate() + "'" +
            ", receivedDate='" + getReceivedDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
