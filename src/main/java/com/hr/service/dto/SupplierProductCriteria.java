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
 * Criteria class for the {@link com.hr.domain.SupplierProduct} entity. This class is used
 * in {@link com.hr.web.rest.SupplierProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private BigDecimalFilter minOrderQty;

    private BigDecimalFilter lastPrice;

    private BigDecimalFilter shippingPrice;

    private StringFilter supplierProductId;

    private IntegerFilter leadTimeDays;

    private BigDecimalFilter cgst;

    private BigDecimalFilter igst;

    private BigDecimalFilter sgst;

    private BigDecimalFilter totalPrice;

    private StringFilter comments;

    private StringFilter supplierProductName;

    private StringFilter manufacturerName;

    private LongFilter productId;

    private LongFilter supplierId;

    private LongFilter quantityUomId;

    private LongFilter currencyUomId;

    private LongFilter manufacturerId;

    public SupplierProductCriteria() {
    }

    public SupplierProductCriteria(SupplierProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.minOrderQty = other.minOrderQty == null ? null : other.minOrderQty.copy();
        this.lastPrice = other.lastPrice == null ? null : other.lastPrice.copy();
        this.shippingPrice = other.shippingPrice == null ? null : other.shippingPrice.copy();
        this.supplierProductId = other.supplierProductId == null ? null : other.supplierProductId.copy();
        this.leadTimeDays = other.leadTimeDays == null ? null : other.leadTimeDays.copy();
        this.cgst = other.cgst == null ? null : other.cgst.copy();
        this.igst = other.igst == null ? null : other.igst.copy();
        this.sgst = other.sgst == null ? null : other.sgst.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.supplierProductName = other.supplierProductName == null ? null : other.supplierProductName.copy();
        this.manufacturerName = other.manufacturerName == null ? null : other.manufacturerName.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.quantityUomId = other.quantityUomId == null ? null : other.quantityUomId.copy();
        this.currencyUomId = other.currencyUomId == null ? null : other.currencyUomId.copy();
        this.manufacturerId = other.manufacturerId == null ? null : other.manufacturerId.copy();
    }

    @Override
    public SupplierProductCriteria copy() {
        return new SupplierProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(ZonedDateTimeFilter fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTimeFilter getThruDate() {
        return thruDate;
    }

    public void setThruDate(ZonedDateTimeFilter thruDate) {
        this.thruDate = thruDate;
    }

    public BigDecimalFilter getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(BigDecimalFilter minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public BigDecimalFilter getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimalFilter lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimalFilter getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimalFilter shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public StringFilter getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(StringFilter supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    public IntegerFilter getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(IntegerFilter leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public BigDecimalFilter getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimalFilter cgst) {
        this.cgst = cgst;
    }

    public BigDecimalFilter getIgst() {
        return igst;
    }

    public void setIgst(BigDecimalFilter igst) {
        this.igst = igst;
    }

    public BigDecimalFilter getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimalFilter sgst) {
        this.sgst = sgst;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getSupplierProductName() {
        return supplierProductName;
    }

    public void setSupplierProductName(StringFilter supplierProductName) {
        this.supplierProductName = supplierProductName;
    }

    public StringFilter getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(StringFilter manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    public LongFilter getQuantityUomId() {
        return quantityUomId;
    }

    public void setQuantityUomId(LongFilter quantityUomId) {
        this.quantityUomId = quantityUomId;
    }

    public LongFilter getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(LongFilter currencyUomId) {
        this.currencyUomId = currencyUomId;
    }

    public LongFilter getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(LongFilter manufacturerId) {
        this.manufacturerId = manufacturerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierProductCriteria that = (SupplierProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(minOrderQty, that.minOrderQty) &&
            Objects.equals(lastPrice, that.lastPrice) &&
            Objects.equals(shippingPrice, that.shippingPrice) &&
            Objects.equals(supplierProductId, that.supplierProductId) &&
            Objects.equals(leadTimeDays, that.leadTimeDays) &&
            Objects.equals(cgst, that.cgst) &&
            Objects.equals(igst, that.igst) &&
            Objects.equals(sgst, that.sgst) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(supplierProductName, that.supplierProductName) &&
            Objects.equals(manufacturerName, that.manufacturerName) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(quantityUomId, that.quantityUomId) &&
            Objects.equals(currencyUomId, that.currencyUomId) &&
            Objects.equals(manufacturerId, that.manufacturerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        minOrderQty,
        lastPrice,
        shippingPrice,
        supplierProductId,
        leadTimeDays,
        cgst,
        igst,
        sgst,
        totalPrice,
        comments,
        supplierProductName,
        manufacturerName,
        productId,
        supplierId,
        quantityUomId,
        currencyUomId,
        manufacturerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (minOrderQty != null ? "minOrderQty=" + minOrderQty + ", " : "") +
                (lastPrice != null ? "lastPrice=" + lastPrice + ", " : "") +
                (shippingPrice != null ? "shippingPrice=" + shippingPrice + ", " : "") +
                (supplierProductId != null ? "supplierProductId=" + supplierProductId + ", " : "") +
                (leadTimeDays != null ? "leadTimeDays=" + leadTimeDays + ", " : "") +
                (cgst != null ? "cgst=" + cgst + ", " : "") +
                (igst != null ? "igst=" + igst + ", " : "") +
                (sgst != null ? "sgst=" + sgst + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (supplierProductName != null ? "supplierProductName=" + supplierProductName + ", " : "") +
                (manufacturerName != null ? "manufacturerName=" + manufacturerName + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (quantityUomId != null ? "quantityUomId=" + quantityUomId + ", " : "") +
                (currencyUomId != null ? "currencyUomId=" + currencyUomId + ", " : "") +
                (manufacturerId != null ? "manufacturerId=" + manufacturerId + ", " : "") +
            "}";
    }

}
