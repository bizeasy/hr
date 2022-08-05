package com.hr.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.hr.domain.InventoryItem} entity. This class is used
 * in {@link com.hr.web.rest.InventoryItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter receivedDate;

    private ZonedDateTimeFilter manufacturedDate;

    private ZonedDateTimeFilter expiryDate;

    private ZonedDateTimeFilter retestDate;

    private StringFilter containerId;

    private StringFilter batchNo;

    private StringFilter mfgBatchNo;

    private StringFilter lotNoStr;

    private StringFilter binNumber;

    private StringFilter comments;

    private BigDecimalFilter quantityOnHandTotal;

    private BigDecimalFilter availableToPromiseTotal;

    private BigDecimalFilter accountingQuantityTotal;

    private BigDecimalFilter oldQuantityOnHand;

    private BigDecimalFilter oldAvailableToPromise;

    private StringFilter serialNumber;

    private StringFilter softIdentifier;

    private StringFilter activationNumber;

    private ZonedDateTimeFilter activationValidTrue;

    private BigDecimalFilter unitCost;

    private LongFilter inventoryItemTypeId;

    private LongFilter productId;

    private LongFilter supplierId;

    private LongFilter ownerPartyId;

    private LongFilter statusId;

    private LongFilter facilityId;

    private LongFilter uomId;

    private LongFilter currencyUomId;

    private LongFilter lotId;

    public InventoryItemCriteria() {
    }

    public InventoryItemCriteria(InventoryItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.receivedDate = other.receivedDate == null ? null : other.receivedDate.copy();
        this.manufacturedDate = other.manufacturedDate == null ? null : other.manufacturedDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.retestDate = other.retestDate == null ? null : other.retestDate.copy();
        this.containerId = other.containerId == null ? null : other.containerId.copy();
        this.batchNo = other.batchNo == null ? null : other.batchNo.copy();
        this.mfgBatchNo = other.mfgBatchNo == null ? null : other.mfgBatchNo.copy();
        this.lotNoStr = other.lotNoStr == null ? null : other.lotNoStr.copy();
        this.binNumber = other.binNumber == null ? null : other.binNumber.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.quantityOnHandTotal = other.quantityOnHandTotal == null ? null : other.quantityOnHandTotal.copy();
        this.availableToPromiseTotal = other.availableToPromiseTotal == null ? null : other.availableToPromiseTotal.copy();
        this.accountingQuantityTotal = other.accountingQuantityTotal == null ? null : other.accountingQuantityTotal.copy();
        this.oldQuantityOnHand = other.oldQuantityOnHand == null ? null : other.oldQuantityOnHand.copy();
        this.oldAvailableToPromise = other.oldAvailableToPromise == null ? null : other.oldAvailableToPromise.copy();
        this.serialNumber = other.serialNumber == null ? null : other.serialNumber.copy();
        this.softIdentifier = other.softIdentifier == null ? null : other.softIdentifier.copy();
        this.activationNumber = other.activationNumber == null ? null : other.activationNumber.copy();
        this.activationValidTrue = other.activationValidTrue == null ? null : other.activationValidTrue.copy();
        this.unitCost = other.unitCost == null ? null : other.unitCost.copy();
        this.inventoryItemTypeId = other.inventoryItemTypeId == null ? null : other.inventoryItemTypeId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.ownerPartyId = other.ownerPartyId == null ? null : other.ownerPartyId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.currencyUomId = other.currencyUomId == null ? null : other.currencyUomId.copy();
        this.lotId = other.lotId == null ? null : other.lotId.copy();
    }

    @Override
    public InventoryItemCriteria copy() {
        return new InventoryItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(ZonedDateTimeFilter receivedDate) {
        this.receivedDate = receivedDate;
    }

    public ZonedDateTimeFilter getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(ZonedDateTimeFilter manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public ZonedDateTimeFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTimeFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTimeFilter getRetestDate() {
        return retestDate;
    }

    public void setRetestDate(ZonedDateTimeFilter retestDate) {
        this.retestDate = retestDate;
    }

    public StringFilter getContainerId() {
        return containerId;
    }

    public void setContainerId(StringFilter containerId) {
        this.containerId = containerId;
    }

    public StringFilter getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(StringFilter batchNo) {
        this.batchNo = batchNo;
    }

    public StringFilter getMfgBatchNo() {
        return mfgBatchNo;
    }

    public void setMfgBatchNo(StringFilter mfgBatchNo) {
        this.mfgBatchNo = mfgBatchNo;
    }

    public StringFilter getLotNoStr() {
        return lotNoStr;
    }

    public void setLotNoStr(StringFilter lotNoStr) {
        this.lotNoStr = lotNoStr;
    }

    public StringFilter getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(StringFilter binNumber) {
        this.binNumber = binNumber;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public BigDecimalFilter getQuantityOnHandTotal() {
        return quantityOnHandTotal;
    }

    public void setQuantityOnHandTotal(BigDecimalFilter quantityOnHandTotal) {
        this.quantityOnHandTotal = quantityOnHandTotal;
    }

    public BigDecimalFilter getAvailableToPromiseTotal() {
        return availableToPromiseTotal;
    }

    public void setAvailableToPromiseTotal(BigDecimalFilter availableToPromiseTotal) {
        this.availableToPromiseTotal = availableToPromiseTotal;
    }

    public BigDecimalFilter getAccountingQuantityTotal() {
        return accountingQuantityTotal;
    }

    public void setAccountingQuantityTotal(BigDecimalFilter accountingQuantityTotal) {
        this.accountingQuantityTotal = accountingQuantityTotal;
    }

    public BigDecimalFilter getOldQuantityOnHand() {
        return oldQuantityOnHand;
    }

    public void setOldQuantityOnHand(BigDecimalFilter oldQuantityOnHand) {
        this.oldQuantityOnHand = oldQuantityOnHand;
    }

    public BigDecimalFilter getOldAvailableToPromise() {
        return oldAvailableToPromise;
    }

    public void setOldAvailableToPromise(BigDecimalFilter oldAvailableToPromise) {
        this.oldAvailableToPromise = oldAvailableToPromise;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public StringFilter getSoftIdentifier() {
        return softIdentifier;
    }

    public void setSoftIdentifier(StringFilter softIdentifier) {
        this.softIdentifier = softIdentifier;
    }

    public StringFilter getActivationNumber() {
        return activationNumber;
    }

    public void setActivationNumber(StringFilter activationNumber) {
        this.activationNumber = activationNumber;
    }

    public ZonedDateTimeFilter getActivationValidTrue() {
        return activationValidTrue;
    }

    public void setActivationValidTrue(ZonedDateTimeFilter activationValidTrue) {
        this.activationValidTrue = activationValidTrue;
    }

    public BigDecimalFilter getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimalFilter unitCost) {
        this.unitCost = unitCost;
    }

    public LongFilter getInventoryItemTypeId() {
        return inventoryItemTypeId;
    }

    public void setInventoryItemTypeId(LongFilter inventoryItemTypeId) {
        this.inventoryItemTypeId = inventoryItemTypeId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getOwnerPartyId() {
        return ownerPartyId;
    }

    public void setOwnerPartyId(LongFilter ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
    }

    public LongFilter getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(LongFilter currencyUomId) {
        this.currencyUomId = currencyUomId;
    }

    public LongFilter getLotId() {
        return lotId;
    }

    public void setLotId(LongFilter lotId) {
        this.lotId = lotId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryItemCriteria that = (InventoryItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(receivedDate, that.receivedDate) &&
            Objects.equals(manufacturedDate, that.manufacturedDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(retestDate, that.retestDate) &&
            Objects.equals(containerId, that.containerId) &&
            Objects.equals(batchNo, that.batchNo) &&
            Objects.equals(mfgBatchNo, that.mfgBatchNo) &&
            Objects.equals(lotNoStr, that.lotNoStr) &&
            Objects.equals(binNumber, that.binNumber) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(quantityOnHandTotal, that.quantityOnHandTotal) &&
            Objects.equals(availableToPromiseTotal, that.availableToPromiseTotal) &&
            Objects.equals(accountingQuantityTotal, that.accountingQuantityTotal) &&
            Objects.equals(oldQuantityOnHand, that.oldQuantityOnHand) &&
            Objects.equals(oldAvailableToPromise, that.oldAvailableToPromise) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(softIdentifier, that.softIdentifier) &&
            Objects.equals(activationNumber, that.activationNumber) &&
            Objects.equals(activationValidTrue, that.activationValidTrue) &&
            Objects.equals(unitCost, that.unitCost) &&
            Objects.equals(inventoryItemTypeId, that.inventoryItemTypeId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(ownerPartyId, that.ownerPartyId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(currencyUomId, that.currencyUomId) &&
            Objects.equals(lotId, that.lotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        receivedDate,
        manufacturedDate,
        expiryDate,
        retestDate,
        containerId,
        batchNo,
        mfgBatchNo,
        lotNoStr,
        binNumber,
        comments,
        quantityOnHandTotal,
        availableToPromiseTotal,
        accountingQuantityTotal,
        oldQuantityOnHand,
        oldAvailableToPromise,
        serialNumber,
        softIdentifier,
        activationNumber,
        activationValidTrue,
        unitCost,
        inventoryItemTypeId,
        productId,
        supplierId,
        ownerPartyId,
        statusId,
        facilityId,
        uomId,
        currencyUomId,
        lotId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (receivedDate != null ? "receivedDate=" + receivedDate + ", " : "") +
                (manufacturedDate != null ? "manufacturedDate=" + manufacturedDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (retestDate != null ? "retestDate=" + retestDate + ", " : "") +
                (containerId != null ? "containerId=" + containerId + ", " : "") +
                (batchNo != null ? "batchNo=" + batchNo + ", " : "") +
                (mfgBatchNo != null ? "mfgBatchNo=" + mfgBatchNo + ", " : "") +
                (lotNoStr != null ? "lotNoStr=" + lotNoStr + ", " : "") +
                (binNumber != null ? "binNumber=" + binNumber + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (quantityOnHandTotal != null ? "quantityOnHandTotal=" + quantityOnHandTotal + ", " : "") +
                (availableToPromiseTotal != null ? "availableToPromiseTotal=" + availableToPromiseTotal + ", " : "") +
                (accountingQuantityTotal != null ? "accountingQuantityTotal=" + accountingQuantityTotal + ", " : "") +
                (oldQuantityOnHand != null ? "oldQuantityOnHand=" + oldQuantityOnHand + ", " : "") +
                (oldAvailableToPromise != null ? "oldAvailableToPromise=" + oldAvailableToPromise + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (softIdentifier != null ? "softIdentifier=" + softIdentifier + ", " : "") +
                (activationNumber != null ? "activationNumber=" + activationNumber + ", " : "") +
                (activationValidTrue != null ? "activationValidTrue=" + activationValidTrue + ", " : "") +
                (unitCost != null ? "unitCost=" + unitCost + ", " : "") +
                (inventoryItemTypeId != null ? "inventoryItemTypeId=" + inventoryItemTypeId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (ownerPartyId != null ? "ownerPartyId=" + ownerPartyId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (currencyUomId != null ? "currencyUomId=" + currencyUomId + ", " : "") +
                (lotId != null ? "lotId=" + lotId + ", " : "") +
            "}";
    }

}
