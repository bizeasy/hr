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
 * Criteria class for the {@link com.hr.domain.ProductPrice} entity. This class is used
 * in {@link com.hr.web.rest.ProductPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fromDate;

    private ZonedDateTimeFilter thruDate;

    private BigDecimalFilter price;

    private BigDecimalFilter cgst;

    private BigDecimalFilter igst;

    private BigDecimalFilter sgst;

    private BigDecimalFilter totalPrice;

    private LongFilter productId;

    private LongFilter productPriceTypeId;

    private LongFilter productPricePurposeId;

    private LongFilter currencyUomId;

    public ProductPriceCriteria() {
    }

    public ProductPriceCriteria(ProductPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.thruDate = other.thruDate == null ? null : other.thruDate.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.cgst = other.cgst == null ? null : other.cgst.copy();
        this.igst = other.igst == null ? null : other.igst.copy();
        this.sgst = other.sgst == null ? null : other.sgst.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productPriceTypeId = other.productPriceTypeId == null ? null : other.productPriceTypeId.copy();
        this.productPricePurposeId = other.productPricePurposeId == null ? null : other.productPricePurposeId.copy();
        this.currencyUomId = other.currencyUomId == null ? null : other.currencyUomId.copy();
    }

    @Override
    public ProductPriceCriteria copy() {
        return new ProductPriceCriteria(this);
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

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
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

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductPriceTypeId() {
        return productPriceTypeId;
    }

    public void setProductPriceTypeId(LongFilter productPriceTypeId) {
        this.productPriceTypeId = productPriceTypeId;
    }

    public LongFilter getProductPricePurposeId() {
        return productPricePurposeId;
    }

    public void setProductPricePurposeId(LongFilter productPricePurposeId) {
        this.productPricePurposeId = productPricePurposeId;
    }

    public LongFilter getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(LongFilter currencyUomId) {
        this.currencyUomId = currencyUomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPriceCriteria that = (ProductPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(thruDate, that.thruDate) &&
            Objects.equals(price, that.price) &&
            Objects.equals(cgst, that.cgst) &&
            Objects.equals(igst, that.igst) &&
            Objects.equals(sgst, that.sgst) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productPriceTypeId, that.productPriceTypeId) &&
            Objects.equals(productPricePurposeId, that.productPricePurposeId) &&
            Objects.equals(currencyUomId, that.currencyUomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        thruDate,
        price,
        cgst,
        igst,
        sgst,
        totalPrice,
        productId,
        productPriceTypeId,
        productPricePurposeId,
        currencyUomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (thruDate != null ? "thruDate=" + thruDate + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (cgst != null ? "cgst=" + cgst + ", " : "") +
                (igst != null ? "igst=" + igst + ", " : "") +
                (sgst != null ? "sgst=" + sgst + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (productPriceTypeId != null ? "productPriceTypeId=" + productPriceTypeId + ", " : "") +
                (productPricePurposeId != null ? "productPricePurposeId=" + productPricePurposeId + ", " : "") +
                (currencyUomId != null ? "currencyUomId=" + currencyUomId + ", " : "") +
            "}";
    }

}
