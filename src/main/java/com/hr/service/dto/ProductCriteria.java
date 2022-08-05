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
import io.github.jhipster.service.filter.DurationFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.hr.domain.Product} entity. This class is used
 * in {@link com.hr.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter internalName;

    private StringFilter brandName;

    private StringFilter variant;

    private StringFilter sku;

    private ZonedDateTimeFilter introductionDate;

    private ZonedDateTimeFilter releaseDate;

    private BigDecimalFilter quantityIncluded;

    private IntegerFilter piecesIncluded;

    private BigDecimalFilter weight;

    private BigDecimalFilter height;

    private BigDecimalFilter width;

    private BigDecimalFilter depth;

    private StringFilter smallImageUrl;

    private StringFilter mediumImageUrl;

    private StringFilter largeImageUrl;

    private StringFilter detailImageUrl;

    private StringFilter originalImageUrl;

    private BigDecimalFilter quantity;

    private BigDecimalFilter cgst;

    private BigDecimalFilter igst;

    private BigDecimalFilter sgst;

    private BigDecimalFilter price;

    private BigDecimalFilter cgstPercentage;

    private BigDecimalFilter igstPercentage;

    private BigDecimalFilter sgstPercentage;

    private BigDecimalFilter totalPrice;

    private StringFilter description;

    private StringFilter longDescription;

    private BooleanFilter isVirtual;

    private BooleanFilter isVariant;

    private BooleanFilter requireInventory;

    private BooleanFilter returnable;

    private BooleanFilter isPopular;

    private StringFilter changeControlNo;

    private DurationFilter retestDuration;

    private DurationFilter expiryDuration;

    private StringFilter validationType;

    private IntegerFilter shelfLife;

    private LongFilter productTypeId;

    private LongFilter quantityUomId;

    private LongFilter weightUomId;

    private LongFilter sizeUomId;

    private LongFilter uomId;

    private LongFilter primaryProductCategoryId;

    private LongFilter virtualProductId;

    private LongFilter inventoryItemTypeId;

    private LongFilter taxSlabId;

    private LongFilter shelfLifeUomId;

    public ProductCriteria() {
    }

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.internalName = other.internalName == null ? null : other.internalName.copy();
        this.brandName = other.brandName == null ? null : other.brandName.copy();
        this.variant = other.variant == null ? null : other.variant.copy();
        this.sku = other.sku == null ? null : other.sku.copy();
        this.introductionDate = other.introductionDate == null ? null : other.introductionDate.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.quantityIncluded = other.quantityIncluded == null ? null : other.quantityIncluded.copy();
        this.piecesIncluded = other.piecesIncluded == null ? null : other.piecesIncluded.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.depth = other.depth == null ? null : other.depth.copy();
        this.smallImageUrl = other.smallImageUrl == null ? null : other.smallImageUrl.copy();
        this.mediumImageUrl = other.mediumImageUrl == null ? null : other.mediumImageUrl.copy();
        this.largeImageUrl = other.largeImageUrl == null ? null : other.largeImageUrl.copy();
        this.detailImageUrl = other.detailImageUrl == null ? null : other.detailImageUrl.copy();
        this.originalImageUrl = other.originalImageUrl == null ? null : other.originalImageUrl.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.cgst = other.cgst == null ? null : other.cgst.copy();
        this.igst = other.igst == null ? null : other.igst.copy();
        this.sgst = other.sgst == null ? null : other.sgst.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.cgstPercentage = other.cgstPercentage == null ? null : other.cgstPercentage.copy();
        this.igstPercentage = other.igstPercentage == null ? null : other.igstPercentage.copy();
        this.sgstPercentage = other.sgstPercentage == null ? null : other.sgstPercentage.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.longDescription = other.longDescription == null ? null : other.longDescription.copy();
        this.isVirtual = other.isVirtual == null ? null : other.isVirtual.copy();
        this.isVariant = other.isVariant == null ? null : other.isVariant.copy();
        this.requireInventory = other.requireInventory == null ? null : other.requireInventory.copy();
        this.returnable = other.returnable == null ? null : other.returnable.copy();
        this.isPopular = other.isPopular == null ? null : other.isPopular.copy();
        this.changeControlNo = other.changeControlNo == null ? null : other.changeControlNo.copy();
        this.retestDuration = other.retestDuration == null ? null : other.retestDuration.copy();
        this.expiryDuration = other.expiryDuration == null ? null : other.expiryDuration.copy();
        this.validationType = other.validationType == null ? null : other.validationType.copy();
        this.shelfLife = other.shelfLife == null ? null : other.shelfLife.copy();
        this.productTypeId = other.productTypeId == null ? null : other.productTypeId.copy();
        this.quantityUomId = other.quantityUomId == null ? null : other.quantityUomId.copy();
        this.weightUomId = other.weightUomId == null ? null : other.weightUomId.copy();
        this.sizeUomId = other.sizeUomId == null ? null : other.sizeUomId.copy();
        this.uomId = other.uomId == null ? null : other.uomId.copy();
        this.primaryProductCategoryId = other.primaryProductCategoryId == null ? null : other.primaryProductCategoryId.copy();
        this.virtualProductId = other.virtualProductId == null ? null : other.virtualProductId.copy();
        this.inventoryItemTypeId = other.inventoryItemTypeId == null ? null : other.inventoryItemTypeId.copy();
        this.taxSlabId = other.taxSlabId == null ? null : other.taxSlabId.copy();
        this.shelfLifeUomId = other.shelfLifeUomId == null ? null : other.shelfLifeUomId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getInternalName() {
        return internalName;
    }

    public void setInternalName(StringFilter internalName) {
        this.internalName = internalName;
    }

    public StringFilter getBrandName() {
        return brandName;
    }

    public void setBrandName(StringFilter brandName) {
        this.brandName = brandName;
    }

    public StringFilter getVariant() {
        return variant;
    }

    public void setVariant(StringFilter variant) {
        this.variant = variant;
    }

    public StringFilter getSku() {
        return sku;
    }

    public void setSku(StringFilter sku) {
        this.sku = sku;
    }

    public ZonedDateTimeFilter getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(ZonedDateTimeFilter introductionDate) {
        this.introductionDate = introductionDate;
    }

    public ZonedDateTimeFilter getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(ZonedDateTimeFilter releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimalFilter getQuantityIncluded() {
        return quantityIncluded;
    }

    public void setQuantityIncluded(BigDecimalFilter quantityIncluded) {
        this.quantityIncluded = quantityIncluded;
    }

    public IntegerFilter getPiecesIncluded() {
        return piecesIncluded;
    }

    public void setPiecesIncluded(IntegerFilter piecesIncluded) {
        this.piecesIncluded = piecesIncluded;
    }

    public BigDecimalFilter getWeight() {
        return weight;
    }

    public void setWeight(BigDecimalFilter weight) {
        this.weight = weight;
    }

    public BigDecimalFilter getHeight() {
        return height;
    }

    public void setHeight(BigDecimalFilter height) {
        this.height = height;
    }

    public BigDecimalFilter getWidth() {
        return width;
    }

    public void setWidth(BigDecimalFilter width) {
        this.width = width;
    }

    public BigDecimalFilter getDepth() {
        return depth;
    }

    public void setDepth(BigDecimalFilter depth) {
        this.depth = depth;
    }

    public StringFilter getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(StringFilter smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public StringFilter getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(StringFilter mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public StringFilter getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(StringFilter largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public StringFilter getDetailImageUrl() {
        return detailImageUrl;
    }

    public void setDetailImageUrl(StringFilter detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }

    public StringFilter getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(StringFilter originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
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

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public BigDecimalFilter getCgstPercentage() {
        return cgstPercentage;
    }

    public void setCgstPercentage(BigDecimalFilter cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
    }

    public BigDecimalFilter getIgstPercentage() {
        return igstPercentage;
    }

    public void setIgstPercentage(BigDecimalFilter igstPercentage) {
        this.igstPercentage = igstPercentage;
    }

    public BigDecimalFilter getSgstPercentage() {
        return sgstPercentage;
    }

    public void setSgstPercentage(BigDecimalFilter sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(StringFilter longDescription) {
        this.longDescription = longDescription;
    }

    public BooleanFilter getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(BooleanFilter isVirtual) {
        this.isVirtual = isVirtual;
    }

    public BooleanFilter getIsVariant() {
        return isVariant;
    }

    public void setIsVariant(BooleanFilter isVariant) {
        this.isVariant = isVariant;
    }

    public BooleanFilter getRequireInventory() {
        return requireInventory;
    }

    public void setRequireInventory(BooleanFilter requireInventory) {
        this.requireInventory = requireInventory;
    }

    public BooleanFilter getReturnable() {
        return returnable;
    }

    public void setReturnable(BooleanFilter returnable) {
        this.returnable = returnable;
    }

    public BooleanFilter getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(BooleanFilter isPopular) {
        this.isPopular = isPopular;
    }

    public StringFilter getChangeControlNo() {
        return changeControlNo;
    }

    public void setChangeControlNo(StringFilter changeControlNo) {
        this.changeControlNo = changeControlNo;
    }

    public DurationFilter getRetestDuration() {
        return retestDuration;
    }

    public void setRetestDuration(DurationFilter retestDuration) {
        this.retestDuration = retestDuration;
    }

    public DurationFilter getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(DurationFilter expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public StringFilter getValidationType() {
        return validationType;
    }

    public void setValidationType(StringFilter validationType) {
        this.validationType = validationType;
    }

    public IntegerFilter getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(IntegerFilter shelfLife) {
        this.shelfLife = shelfLife;
    }

    public LongFilter getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(LongFilter productTypeId) {
        this.productTypeId = productTypeId;
    }

    public LongFilter getQuantityUomId() {
        return quantityUomId;
    }

    public void setQuantityUomId(LongFilter quantityUomId) {
        this.quantityUomId = quantityUomId;
    }

    public LongFilter getWeightUomId() {
        return weightUomId;
    }

    public void setWeightUomId(LongFilter weightUomId) {
        this.weightUomId = weightUomId;
    }

    public LongFilter getSizeUomId() {
        return sizeUomId;
    }

    public void setSizeUomId(LongFilter sizeUomId) {
        this.sizeUomId = sizeUomId;
    }

    public LongFilter getUomId() {
        return uomId;
    }

    public void setUomId(LongFilter uomId) {
        this.uomId = uomId;
    }

    public LongFilter getPrimaryProductCategoryId() {
        return primaryProductCategoryId;
    }

    public void setPrimaryProductCategoryId(LongFilter primaryProductCategoryId) {
        this.primaryProductCategoryId = primaryProductCategoryId;
    }

    public LongFilter getVirtualProductId() {
        return virtualProductId;
    }

    public void setVirtualProductId(LongFilter virtualProductId) {
        this.virtualProductId = virtualProductId;
    }

    public LongFilter getInventoryItemTypeId() {
        return inventoryItemTypeId;
    }

    public void setInventoryItemTypeId(LongFilter inventoryItemTypeId) {
        this.inventoryItemTypeId = inventoryItemTypeId;
    }

    public LongFilter getTaxSlabId() {
        return taxSlabId;
    }

    public void setTaxSlabId(LongFilter taxSlabId) {
        this.taxSlabId = taxSlabId;
    }

    public LongFilter getShelfLifeUomId() {
        return shelfLifeUomId;
    }

    public void setShelfLifeUomId(LongFilter shelfLifeUomId) {
        this.shelfLifeUomId = shelfLifeUomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(internalName, that.internalName) &&
            Objects.equals(brandName, that.brandName) &&
            Objects.equals(variant, that.variant) &&
            Objects.equals(sku, that.sku) &&
            Objects.equals(introductionDate, that.introductionDate) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(quantityIncluded, that.quantityIncluded) &&
            Objects.equals(piecesIncluded, that.piecesIncluded) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(depth, that.depth) &&
            Objects.equals(smallImageUrl, that.smallImageUrl) &&
            Objects.equals(mediumImageUrl, that.mediumImageUrl) &&
            Objects.equals(largeImageUrl, that.largeImageUrl) &&
            Objects.equals(detailImageUrl, that.detailImageUrl) &&
            Objects.equals(originalImageUrl, that.originalImageUrl) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(cgst, that.cgst) &&
            Objects.equals(igst, that.igst) &&
            Objects.equals(sgst, that.sgst) &&
            Objects.equals(price, that.price) &&
            Objects.equals(cgstPercentage, that.cgstPercentage) &&
            Objects.equals(igstPercentage, that.igstPercentage) &&
            Objects.equals(sgstPercentage, that.sgstPercentage) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(description, that.description) &&
            Objects.equals(longDescription, that.longDescription) &&
            Objects.equals(isVirtual, that.isVirtual) &&
            Objects.equals(isVariant, that.isVariant) &&
            Objects.equals(requireInventory, that.requireInventory) &&
            Objects.equals(returnable, that.returnable) &&
            Objects.equals(isPopular, that.isPopular) &&
            Objects.equals(changeControlNo, that.changeControlNo) &&
            Objects.equals(retestDuration, that.retestDuration) &&
            Objects.equals(expiryDuration, that.expiryDuration) &&
            Objects.equals(validationType, that.validationType) &&
            Objects.equals(shelfLife, that.shelfLife) &&
            Objects.equals(productTypeId, that.productTypeId) &&
            Objects.equals(quantityUomId, that.quantityUomId) &&
            Objects.equals(weightUomId, that.weightUomId) &&
            Objects.equals(sizeUomId, that.sizeUomId) &&
            Objects.equals(uomId, that.uomId) &&
            Objects.equals(primaryProductCategoryId, that.primaryProductCategoryId) &&
            Objects.equals(virtualProductId, that.virtualProductId) &&
            Objects.equals(inventoryItemTypeId, that.inventoryItemTypeId) &&
            Objects.equals(taxSlabId, that.taxSlabId) &&
            Objects.equals(shelfLifeUomId, that.shelfLifeUomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        internalName,
        brandName,
        variant,
        sku,
        introductionDate,
        releaseDate,
        quantityIncluded,
        piecesIncluded,
        weight,
        height,
        width,
        depth,
        smallImageUrl,
        mediumImageUrl,
        largeImageUrl,
        detailImageUrl,
        originalImageUrl,
        quantity,
        cgst,
        igst,
        sgst,
        price,
        cgstPercentage,
        igstPercentage,
        sgstPercentage,
        totalPrice,
        description,
        longDescription,
        isVirtual,
        isVariant,
        requireInventory,
        returnable,
        isPopular,
        changeControlNo,
        retestDuration,
        expiryDuration,
        validationType,
        shelfLife,
        productTypeId,
        quantityUomId,
        weightUomId,
        sizeUomId,
        uomId,
        primaryProductCategoryId,
        virtualProductId,
        inventoryItemTypeId,
        taxSlabId,
        shelfLifeUomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (internalName != null ? "internalName=" + internalName + ", " : "") +
                (brandName != null ? "brandName=" + brandName + ", " : "") +
                (variant != null ? "variant=" + variant + ", " : "") +
                (sku != null ? "sku=" + sku + ", " : "") +
                (introductionDate != null ? "introductionDate=" + introductionDate + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (quantityIncluded != null ? "quantityIncluded=" + quantityIncluded + ", " : "") +
                (piecesIncluded != null ? "piecesIncluded=" + piecesIncluded + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (width != null ? "width=" + width + ", " : "") +
                (depth != null ? "depth=" + depth + ", " : "") +
                (smallImageUrl != null ? "smallImageUrl=" + smallImageUrl + ", " : "") +
                (mediumImageUrl != null ? "mediumImageUrl=" + mediumImageUrl + ", " : "") +
                (largeImageUrl != null ? "largeImageUrl=" + largeImageUrl + ", " : "") +
                (detailImageUrl != null ? "detailImageUrl=" + detailImageUrl + ", " : "") +
                (originalImageUrl != null ? "originalImageUrl=" + originalImageUrl + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (cgst != null ? "cgst=" + cgst + ", " : "") +
                (igst != null ? "igst=" + igst + ", " : "") +
                (sgst != null ? "sgst=" + sgst + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (cgstPercentage != null ? "cgstPercentage=" + cgstPercentage + ", " : "") +
                (igstPercentage != null ? "igstPercentage=" + igstPercentage + ", " : "") +
                (sgstPercentage != null ? "sgstPercentage=" + sgstPercentage + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (longDescription != null ? "longDescription=" + longDescription + ", " : "") +
                (isVirtual != null ? "isVirtual=" + isVirtual + ", " : "") +
                (isVariant != null ? "isVariant=" + isVariant + ", " : "") +
                (requireInventory != null ? "requireInventory=" + requireInventory + ", " : "") +
                (returnable != null ? "returnable=" + returnable + ", " : "") +
                (isPopular != null ? "isPopular=" + isPopular + ", " : "") +
                (changeControlNo != null ? "changeControlNo=" + changeControlNo + ", " : "") +
                (retestDuration != null ? "retestDuration=" + retestDuration + ", " : "") +
                (expiryDuration != null ? "expiryDuration=" + expiryDuration + ", " : "") +
                (validationType != null ? "validationType=" + validationType + ", " : "") +
                (shelfLife != null ? "shelfLife=" + shelfLife + ", " : "") +
                (productTypeId != null ? "productTypeId=" + productTypeId + ", " : "") +
                (quantityUomId != null ? "quantityUomId=" + quantityUomId + ", " : "") +
                (weightUomId != null ? "weightUomId=" + weightUomId + ", " : "") +
                (sizeUomId != null ? "sizeUomId=" + sizeUomId + ", " : "") +
                (uomId != null ? "uomId=" + uomId + ", " : "") +
                (primaryProductCategoryId != null ? "primaryProductCategoryId=" + primaryProductCategoryId + ", " : "") +
                (virtualProductId != null ? "virtualProductId=" + virtualProductId + ", " : "") +
                (inventoryItemTypeId != null ? "inventoryItemTypeId=" + inventoryItemTypeId + ", " : "") +
                (taxSlabId != null ? "taxSlabId=" + taxSlabId + ", " : "") +
                (shelfLifeUomId != null ? "shelfLifeUomId=" + shelfLifeUomId + ", " : "") +
            "}";
    }

}
