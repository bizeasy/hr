package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.Duration;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 60)
    @Column(name = "internal_name", length = 60)
    private String internalName;

    @Size(max = 60)
    @Column(name = "brand_name", length = 60)
    private String brandName;

    @Size(max = 60)
    @Column(name = "variant", length = 60)
    private String variant;

    @Size(max = 60)
    @Column(name = "sku", length = 60)
    private String sku;

    @Column(name = "introduction_date")
    private ZonedDateTime introductionDate;

    @Column(name = "release_date")
    private ZonedDateTime releaseDate;

    @Column(name = "quantity_included", precision = 21, scale = 2)
    private BigDecimal quantityIncluded;

    @Column(name = "pieces_included")
    private Integer piecesIncluded;

    @Column(name = "weight", precision = 21, scale = 2)
    private BigDecimal weight;

    @Column(name = "height", precision = 21, scale = 2)
    private BigDecimal height;

    @Column(name = "width", precision = 21, scale = 2)
    private BigDecimal width;

    @Column(name = "depth", precision = 21, scale = 2)
    private BigDecimal depth;

    @Size(max = 2000)
    @Column(name = "small_image_url", length = 2000)
    private String smallImageUrl;

    @Size(max = 2000)
    @Column(name = "medium_image_url", length = 2000)
    private String mediumImageUrl;

    @Size(max = 2000)
    @Column(name = "large_image_url", length = 2000)
    private String largeImageUrl;

    @Size(max = 2000)
    @Column(name = "detail_image_url", length = 2000)
    private String detailImageUrl;

    @Size(max = 2000)
    @Column(name = "original_image_url", length = 2000)
    private String originalImageUrl;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "cgst", precision = 21, scale = 2)
    private BigDecimal cgst;

    @Column(name = "igst", precision = 21, scale = 2)
    private BigDecimal igst;

    @Column(name = "sgst", precision = 21, scale = 2)
    private BigDecimal sgst;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "cgst_percentage", precision = 21, scale = 2)
    private BigDecimal cgstPercentage;

    @Column(name = "igst_percentage", precision = 21, scale = 2)
    private BigDecimal igstPercentage;

    @Column(name = "sgst_percentage", precision = 21, scale = 2)
    private BigDecimal sgstPercentage;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "long_description")
    private String longDescription;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "info")
    private String info;

    @Column(name = "is_virtual")
    private Boolean isVirtual;

    @Column(name = "is_variant")
    private Boolean isVariant;

    @Column(name = "require_inventory")
    private Boolean requireInventory;

    @Column(name = "returnable")
    private Boolean returnable;

    @Column(name = "is_popular")
    private Boolean isPopular;

    @Size(max = 20)
    @Column(name = "change_control_no", length = 20)
    private String changeControlNo;

    @Column(name = "retest_duration")
    private Duration retestDuration;

    @Column(name = "expiry_duration")
    private Duration expiryDuration;

    @Size(max = 20)
    @Column(name = "validation_type", length = 20)
    private String validationType;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductType productType;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Uom quantityUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Uom weightUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Uom sizeUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Uom uom;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductCategory primaryProductCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Product virtualProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private InventoryItemType inventoryItemType;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private TaxSlab taxSlab;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Uom shelfLifeUom;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInternalName() {
        return internalName;
    }

    public Product internalName(String internalName) {
        this.internalName = internalName;
        return this;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getBrandName() {
        return brandName;
    }

    public Product brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getVariant() {
        return variant;
    }

    public Product variant(String variant) {
        this.variant = variant;
        return this;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSku() {
        return sku;
    }

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public ZonedDateTime getIntroductionDate() {
        return introductionDate;
    }

    public Product introductionDate(ZonedDateTime introductionDate) {
        this.introductionDate = introductionDate;
        return this;
    }

    public void setIntroductionDate(ZonedDateTime introductionDate) {
        this.introductionDate = introductionDate;
    }

    public ZonedDateTime getReleaseDate() {
        return releaseDate;
    }

    public Product releaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getQuantityIncluded() {
        return quantityIncluded;
    }

    public Product quantityIncluded(BigDecimal quantityIncluded) {
        this.quantityIncluded = quantityIncluded;
        return this;
    }

    public void setQuantityIncluded(BigDecimal quantityIncluded) {
        this.quantityIncluded = quantityIncluded;
    }

    public Integer getPiecesIncluded() {
        return piecesIncluded;
    }

    public Product piecesIncluded(Integer piecesIncluded) {
        this.piecesIncluded = piecesIncluded;
        return this;
    }

    public void setPiecesIncluded(Integer piecesIncluded) {
        this.piecesIncluded = piecesIncluded;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Product weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public Product height(BigDecimal height) {
        this.height = height;
        return this;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public Product width(BigDecimal width) {
        this.width = width;
        return this;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getDepth() {
        return depth;
    }

    public Product depth(BigDecimal depth) {
        this.depth = depth;
        return this;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public Product smallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
        return this;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public Product mediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
        return this;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public Product largeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
        return this;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    public Product detailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
        return this;
    }

    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public Product originalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
        return this;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Product quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public Product cgst(BigDecimal cgst) {
        this.cgst = cgst;
        return this;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public Product igst(BigDecimal igst) {
        this.igst = igst;
        return this;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public Product sgst(BigDecimal sgst) {
        this.sgst = sgst;
        return this;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCgstPercentage() {
        return cgstPercentage;
    }

    public Product cgstPercentage(BigDecimal cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
        return this;
    }

    public void setCgstPercentage(BigDecimal cgstPercentage) {
        this.cgstPercentage = cgstPercentage;
    }

    public BigDecimal getIgstPercentage() {
        return igstPercentage;
    }

    public Product igstPercentage(BigDecimal igstPercentage) {
        this.igstPercentage = igstPercentage;
        return this;
    }

    public void setIgstPercentage(BigDecimal igstPercentage) {
        this.igstPercentage = igstPercentage;
    }

    public BigDecimal getSgstPercentage() {
        return sgstPercentage;
    }

    public Product sgstPercentage(BigDecimal sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
        return this;
    }

    public void setSgstPercentage(BigDecimal sgstPercentage) {
        this.sgstPercentage = sgstPercentage;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Product totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Product longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getInfo() {
        return info;
    }

    public Product info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean isIsVirtual() {
        return isVirtual;
    }

    public Product isVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
        return this;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Boolean isIsVariant() {
        return isVariant;
    }

    public Product isVariant(Boolean isVariant) {
        this.isVariant = isVariant;
        return this;
    }

    public void setIsVariant(Boolean isVariant) {
        this.isVariant = isVariant;
    }

    public Boolean isRequireInventory() {
        return requireInventory;
    }

    public Product requireInventory(Boolean requireInventory) {
        this.requireInventory = requireInventory;
        return this;
    }

    public void setRequireInventory(Boolean requireInventory) {
        this.requireInventory = requireInventory;
    }

    public Boolean isReturnable() {
        return returnable;
    }

    public Product returnable(Boolean returnable) {
        this.returnable = returnable;
        return this;
    }

    public void setReturnable(Boolean returnable) {
        this.returnable = returnable;
    }

    public Boolean isIsPopular() {
        return isPopular;
    }

    public Product isPopular(Boolean isPopular) {
        this.isPopular = isPopular;
        return this;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    public String getChangeControlNo() {
        return changeControlNo;
    }

    public Product changeControlNo(String changeControlNo) {
        this.changeControlNo = changeControlNo;
        return this;
    }

    public void setChangeControlNo(String changeControlNo) {
        this.changeControlNo = changeControlNo;
    }

    public Duration getRetestDuration() {
        return retestDuration;
    }

    public Product retestDuration(Duration retestDuration) {
        this.retestDuration = retestDuration;
        return this;
    }

    public void setRetestDuration(Duration retestDuration) {
        this.retestDuration = retestDuration;
    }

    public Duration getExpiryDuration() {
        return expiryDuration;
    }

    public Product expiryDuration(Duration expiryDuration) {
        this.expiryDuration = expiryDuration;
        return this;
    }

    public void setExpiryDuration(Duration expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public String getValidationType() {
        return validationType;
    }

    public Product validationType(String validationType) {
        this.validationType = validationType;
        return this;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public Product shelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
        return this;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Uom getQuantityUom() {
        return quantityUom;
    }

    public Product quantityUom(Uom uom) {
        this.quantityUom = uom;
        return this;
    }

    public void setQuantityUom(Uom uom) {
        this.quantityUom = uom;
    }

    public Uom getWeightUom() {
        return weightUom;
    }

    public Product weightUom(Uom uom) {
        this.weightUom = uom;
        return this;
    }

    public void setWeightUom(Uom uom) {
        this.weightUom = uom;
    }

    public Uom getSizeUom() {
        return sizeUom;
    }

    public Product sizeUom(Uom uom) {
        this.sizeUom = uom;
        return this;
    }

    public void setSizeUom(Uom uom) {
        this.sizeUom = uom;
    }

    public Uom getUom() {
        return uom;
    }

    public Product uom(Uom uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public ProductCategory getPrimaryProductCategory() {
        return primaryProductCategory;
    }

    public Product primaryProductCategory(ProductCategory productCategory) {
        this.primaryProductCategory = productCategory;
        return this;
    }

    public void setPrimaryProductCategory(ProductCategory productCategory) {
        this.primaryProductCategory = productCategory;
    }

    public Product getVirtualProduct() {
        return virtualProduct;
    }

    public Product virtualProduct(Product product) {
        this.virtualProduct = product;
        return this;
    }

    public void setVirtualProduct(Product product) {
        this.virtualProduct = product;
    }

    public InventoryItemType getInventoryItemType() {
        return inventoryItemType;
    }

    public Product inventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
        return this;
    }

    public void setInventoryItemType(InventoryItemType inventoryItemType) {
        this.inventoryItemType = inventoryItemType;
    }

    public TaxSlab getTaxSlab() {
        return taxSlab;
    }

    public Product taxSlab(TaxSlab taxSlab) {
        this.taxSlab = taxSlab;
        return this;
    }

    public void setTaxSlab(TaxSlab taxSlab) {
        this.taxSlab = taxSlab;
    }

    public Uom getShelfLifeUom() {
        return shelfLifeUom;
    }

    public Product shelfLifeUom(Uom uom) {
        this.shelfLifeUom = uom;
        return this;
    }

    public void setShelfLifeUom(Uom uom) {
        this.shelfLifeUom = uom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", internalName='" + getInternalName() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", variant='" + getVariant() + "'" +
            ", sku='" + getSku() + "'" +
            ", introductionDate='" + getIntroductionDate() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", quantityIncluded=" + getQuantityIncluded() +
            ", piecesIncluded=" + getPiecesIncluded() +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", depth=" + getDepth() +
            ", smallImageUrl='" + getSmallImageUrl() + "'" +
            ", mediumImageUrl='" + getMediumImageUrl() + "'" +
            ", largeImageUrl='" + getLargeImageUrl() + "'" +
            ", detailImageUrl='" + getDetailImageUrl() + "'" +
            ", originalImageUrl='" + getOriginalImageUrl() + "'" +
            ", quantity=" + getQuantity() +
            ", cgst=" + getCgst() +
            ", igst=" + getIgst() +
            ", sgst=" + getSgst() +
            ", price=" + getPrice() +
            ", cgstPercentage=" + getCgstPercentage() +
            ", igstPercentage=" + getIgstPercentage() +
            ", sgstPercentage=" + getSgstPercentage() +
            ", totalPrice=" + getTotalPrice() +
            ", description='" + getDescription() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", info='" + getInfo() + "'" +
            ", isVirtual='" + isIsVirtual() + "'" +
            ", isVariant='" + isIsVariant() + "'" +
            ", requireInventory='" + isRequireInventory() + "'" +
            ", returnable='" + isReturnable() + "'" +
            ", isPopular='" + isIsPopular() + "'" +
            ", changeControlNo='" + getChangeControlNo() + "'" +
            ", retestDuration='" + getRetestDuration() + "'" +
            ", expiryDuration='" + getExpiryDuration() + "'" +
            ", validationType='" + getValidationType() + "'" +
            ", shelfLife=" + getShelfLife() +
            "}";
    }
}
