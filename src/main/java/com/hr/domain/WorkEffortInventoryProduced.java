package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WorkEffortInventoryProduced.
 */
@Entity
@Table(name = "work_effort_inventory_produced")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEffortInventoryProduced implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortInventoryProduceds", allowSetters = true)
    private WorkEffort workEffort;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortInventoryProduceds", allowSetters = true)
    private InventoryItem inventoryItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortInventoryProduceds", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public WorkEffortInventoryProduced quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public WorkEffortInventoryProduced workEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
        return this;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public WorkEffortInventoryProduced inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Status getStatus() {
        return status;
    }

    public WorkEffortInventoryProduced status(Status status) {
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
        if (!(o instanceof WorkEffortInventoryProduced)) {
            return false;
        }
        return id != null && id.equals(((WorkEffortInventoryProduced) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortInventoryProduced{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
