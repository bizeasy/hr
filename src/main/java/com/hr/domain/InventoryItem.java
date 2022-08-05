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
 * A InventoryItem.
 */
@Entity
@Table(name = "inventory_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "received_date")
    private ZonedDateTime receivedDate;

    @Column(name = "manufactured_date")
    private ZonedDateTime manufacturedDate;

    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    @Column(name = "retest_date")
    private ZonedDateTime retestDate;

    @Size(max = 60)
    @Column(name = "container_id", length = 60)
    private String containerId;

    @Size(max = 60)
    @Column(name = "batch_no", length = 60)
    private String batchNo;

    @Size(max = 60)
    @Column(name = "mfg_batch_no", length = 60)
    private String mfgBatchNo;

    @Size(max = 60)
    @Column(name = "lot_no_str", length = 60)
    private String lotNoStr;

    @Size(max = 60)
    @Column(name = "bin_number", length = 60)
    private String binNumber;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "quantity_on_hand_total", precision = 21, scale = 2)
    private BigDecimal quantityOnHandTotal;

    @Column(name = "available_to_promise_total", precision = 21, scale = 2)
    private BigDecimal availableToPromiseTotal;

    @Column(name = "accounting_quantity_total", precision = 21, scale = 2)
    private BigDecimal accountingQuantityTotal;

    @Column(name = "old_quantity_on_hand", precision = 21, scale = 2)
    private BigDecimal oldQuantityOnHand;

    @Column(name = "old_available_to_promise", precision = 21, scale = 2)
    private BigDecimal oldAvailableToPromise;

    @Size(max = 255)
    @Column(name = "serial_number", length = 255)
    private String serialNumber;

    @Size(max = 255)
    @Column(name = "soft_identifier", length = 255)
    private String softIdentifier;

    @Size(max = 255)
    @Column(name = "activation_number", length = 255)
    private String activationNumber;

    @Column(name = "activation_valid_true")
    private ZonedDateTime activationValidTrue;

    @Column(name = "unit_cost", precision = 21, scale = 2)
    private BigDecimal unitCost;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private InventoryItemType inventoryItemType;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Party supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Party ownerParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Uom uom;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Uom currencyUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryItems", allowSetters = true)
    private Lot lot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getReceivedDate() {
        return receivedDate;
    }

    public InventoryItem receivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    public void setReceivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public ZonedDateTime getManufacturedDate() {
        return manufacturedDate;
    }

    public InventoryItem manufacturedDate(ZonedDateTime manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
        return this;
    }

    public void setManufacturedDate(ZonedDateTime manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public InventoryItem expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getRetestDate() {
        return retestDate;
    }

    public InventoryItem retestDate(ZonedDateTime retestDate) {
        this.retestDate = retestDate;
        return this;
    }

    public void setRetestDate(ZonedDateTime retestDate) {
        this.retestDate = retestDate;
    }

    public String getContainerId() {
        return containerId;
    }

    public InventoryItem containerId(String containerId) {
        this.containerId = containerId;
        return this;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public InventoryItem batchNo(String batchNo) {
        this.batchNo = batchNo;
        return this;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getMfgBatchNo() {
        return mfgBatchNo;
    }

    public InventoryItem mfgBatchNo(String mfgBatchNo) {
        this.mfgBatchNo = mfgBatchNo;
        return this;
    }

    public void setMfgBatchNo(String mfgBatchNo) {
        this.mfgBatchNo = mfgBatchNo;
    }

    public String getLotNoStr() {
        return lotNoStr;
    }

    public InventoryItem lotNoStr(String lotNoStr) {
        this.lotNoStr = lotNoStr;
        return this;
    }

    public void setLotNoStr(String lotNoStr) {
        this.lotNoStr = lotNoStr;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public InventoryItem binNumber(String binNumber) {
        this.binNumber = binNumber;
        return this;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getComments() {
        return comments;
    }

    public InventoryItem comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getQuantityOnHandTotal() {
        return quantityOnHandTotal;
    }

    public InventoryItem quantityOnHandTotal(BigDecimal quantityOnHandTotal) {
        this.quantityOnHandTotal = quantityOnHandTotal;
        return this;
    }

    public void setQuantityOnHandTotal(BigDecimal quantityOnHandTotal) {
        this.quantityOnHandTotal = quantityOnHandTotal;
    }

    public BigDecimal getAvailableToPromiseTotal() {
        return availableToPromiseTotal;
    }

    public InventoryItem availableToPromiseTotal(BigDecimal availableToPromiseTotal) {
        this.availableToPromiseTotal = availableToPromiseTotal;
        return this;
    }

    public void setAvailableToPromiseTotal(BigDecimal availableToPromiseTotal) {
        this.availableToPromiseTotal = availableToPromiseTotal;
    }

    public BigDecimal getAccountingQuantityTotal() {
        return accountingQuantityTotal;
    }

    public InventoryItem accountingQuantityTotal(BigDecimal accountingQuantityTotal) {
        this.accountingQuantityTotal = accountingQuantityTotal;
        return this;
    }

    public void setAccountingQuantityTotal(BigDecimal accountingQuantityTotal) {
        this.accountingQuantityTotal = accountingQuantityTotal;
    }

    public BigDecimal getOldQuantityOnHand() {
        return oldQuantityOnHand;
    }

    public InventoryItem oldQuantityOnHand(BigDecimal oldQuantityOnHand) {
        this.oldQuantityOnHand = oldQuantityOnHand;
        return this;
    }

    public void setOldQuantityOnHand(BigDecimal oldQuantityOnHand) {
        this.oldQuantityOnHand = oldQuantityOnHand;
    }

    public BigDecimal getOldAvailableToPromise() {
        return oldAvailableToPromise;
    }

    public InventoryItem oldAvailableToPromise(BigDecimal oldAvailableToPromise) {
        this.oldAvailableToPromise = oldAvailableToPromise;
        return this;
    }

    public void setOldAvailableToPromise(BigDecimal oldAvailableToPromise) {
        this.oldAvailableToPromise = oldAvailableToPromise;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public InventoryItem serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSoftIdentifier() {
        return softIdentifier;
    }

    public InventoryItem softIdentifier(String softIdentifier) {
        this.softIdentifier = softIdentifier;
        return this;
    }

    public void setSoftIdentifier(String softIdentifier) {
        this.softIdentifier = softIdentifier;
    }

    public String getActivationNumber() {
        return activationNumber;
    }

    public InventoryItem activationNumber(String activationNumber) {
        this.activationNumber = activationNumber;
        return this;
    }

    public void setActivationNumber(String activationNumber) {
        this.activationNumber = activationNumber;
    }

    public ZonedDateTime getActivationValidTrue() {
        return activationValidTrue;
    }

    public InventoryItem activationValidTrue(ZonedDateTime activationValidTrue) {
        this.activationValidTrue = activationValidTrue;
        return this;
    }

    public void setActivationValidTrue(ZonedDateTime activationValidTrue) {
        this.activationValidTrue = activationValidTrue;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public InventoryItem unitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        return this;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public InventoryItemType getInventoryItemType() {
        return inventoryItemType;
    }

    public InventoryItem inventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
        return this;
    }

    public void setInventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
    }

    public Product getProduct() {
        return product;
    }

    public InventoryItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Party getSupplier() {
        return supplier;
    }

    public InventoryItem supplier(Party party) {
        this.supplier = party;
        return this;
    }

    public void setSupplier(Party party) {
        this.supplier = party;
    }

    public Party getOwnerParty() {
        return ownerParty;
    }

    public InventoryItem ownerParty(Party party) {
        this.ownerParty = party;
        return this;
    }

    public void setOwnerParty(Party party) {
        this.ownerParty = party;
    }

    public Status getStatus() {
        return status;
    }

    public InventoryItem status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Facility getFacility() {
        return facility;
    }

    public InventoryItem facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Uom getUom() {
        return uom;
    }

    public InventoryItem uom(Uom uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public Uom getCurrencyUom() {
        return currencyUom;
    }

    public InventoryItem currencyUom(Uom uom) {
        this.currencyUom = uom;
        return this;
    }

    public void setCurrencyUom(Uom uom) {
        this.currencyUom = uom;
    }

    public Lot getLot() {
        return lot;
    }

    public InventoryItem lot(Lot lot) {
        this.lot = lot;
        return this;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryItem)) {
            return false;
        }
        return id != null && id.equals(((InventoryItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItem{" +
            "id=" + getId() +
            ", receivedDate='" + getReceivedDate() + "'" +
            ", manufacturedDate='" + getManufacturedDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", retestDate='" + getRetestDate() + "'" +
            ", containerId='" + getContainerId() + "'" +
            ", batchNo='" + getBatchNo() + "'" +
            ", mfgBatchNo='" + getMfgBatchNo() + "'" +
            ", lotNoStr='" + getLotNoStr() + "'" +
            ", binNumber='" + getBinNumber() + "'" +
            ", comments='" + getComments() + "'" +
            ", quantityOnHandTotal=" + getQuantityOnHandTotal() +
            ", availableToPromiseTotal=" + getAvailableToPromiseTotal() +
            ", accountingQuantityTotal=" + getAccountingQuantityTotal() +
            ", oldQuantityOnHand=" + getOldQuantityOnHand() +
            ", oldAvailableToPromise=" + getOldAvailableToPromise() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", softIdentifier='" + getSoftIdentifier() + "'" +
            ", activationNumber='" + getActivationNumber() + "'" +
            ", activationValidTrue='" + getActivationValidTrue() + "'" +
            ", unitCost=" + getUnitCost() +
            "}";
    }
}
