package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A InventoryItemVariance.
 */
@Entity
@Table(name = "inventory_item_variance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryItemVariance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 255)
    @Column(name = "variance_reason", length = 255)
    private String varianceReason;

    @Column(name = "atp_var", precision = 21, scale = 2)
    private BigDecimal atpVar;

    @Column(name = "qoh_var", precision = 21, scale = 2)
    private BigDecimal qohVar;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemVariances", allowSetters = true)
    private InventoryItem inventoryItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItemVariances", allowSetters = true)
    private Reason reason;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVarianceReason() {
        return varianceReason;
    }

    public InventoryItemVariance varianceReason(String varianceReason) {
        this.varianceReason = varianceReason;
        return this;
    }

    public void setVarianceReason(String varianceReason) {
        this.varianceReason = varianceReason;
    }

    public BigDecimal getAtpVar() {
        return atpVar;
    }

    public InventoryItemVariance atpVar(BigDecimal atpVar) {
        this.atpVar = atpVar;
        return this;
    }

    public void setAtpVar(BigDecimal atpVar) {
        this.atpVar = atpVar;
    }

    public BigDecimal getQohVar() {
        return qohVar;
    }

    public InventoryItemVariance qohVar(BigDecimal qohVar) {
        this.qohVar = qohVar;
        return this;
    }

    public void setQohVar(BigDecimal qohVar) {
        this.qohVar = qohVar;
    }

    public String getComments() {
        return comments;
    }

    public InventoryItemVariance comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public InventoryItemVariance inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Reason getReason() {
        return reason;
    }

    public InventoryItemVariance reason(Reason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryItemVariance)) {
            return false;
        }
        return id != null && id.equals(((InventoryItemVariance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItemVariance{" +
            "id=" + getId() +
            ", varianceReason='" + getVarianceReason() + "'" +
            ", atpVar=" + getAtpVar() +
            ", qohVar=" + getQohVar() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
