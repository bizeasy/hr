package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A WorkEffort.
 */
@Entity
@Table(name = "work_effort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEffort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60, unique = true)
    private String name;

    @Size(max = 60)
    @Column(name = "description", length = 60)
    private String description;

    @Size(max = 60)
    @Column(name = "code", length = 60)
    private String code;

    @Column(name = "batch_size")
    private Double batchSize;

    @Column(name = "min_yield_range")
    private Double minYieldRange;

    @Column(name = "max_yield_range")
    private Double maxYieldRange;

    @Column(name = "percent_complete")
    private Double percentComplete;

    @Size(max = 25)
    @Column(name = "validation_type", length = 25)
    private String validationType;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @Column(name = "output_qty")
    private Double outputQty;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private Product ksm;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private WorkEffortType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private WorkEffortPurpose purpose;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private Uom shelfListUom;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEfforts", allowSetters = true)
    private ProductStore productStore;

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

    public WorkEffort name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public WorkEffort description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public WorkEffort code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getBatchSize() {
        return batchSize;
    }

    public WorkEffort batchSize(Double batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public void setBatchSize(Double batchSize) {
        this.batchSize = batchSize;
    }

    public Double getMinYieldRange() {
        return minYieldRange;
    }

    public WorkEffort minYieldRange(Double minYieldRange) {
        this.minYieldRange = minYieldRange;
        return this;
    }

    public void setMinYieldRange(Double minYieldRange) {
        this.minYieldRange = minYieldRange;
    }

    public Double getMaxYieldRange() {
        return maxYieldRange;
    }

    public WorkEffort maxYieldRange(Double maxYieldRange) {
        this.maxYieldRange = maxYieldRange;
        return this;
    }

    public void setMaxYieldRange(Double maxYieldRange) {
        this.maxYieldRange = maxYieldRange;
    }

    public Double getPercentComplete() {
        return percentComplete;
    }

    public WorkEffort percentComplete(Double percentComplete) {
        this.percentComplete = percentComplete;
        return this;
    }

    public void setPercentComplete(Double percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getValidationType() {
        return validationType;
    }

    public WorkEffort validationType(String validationType) {
        this.validationType = validationType;
        return this;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public WorkEffort shelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
        return this;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Double getOutputQty() {
        return outputQty;
    }

    public WorkEffort outputQty(Double outputQty) {
        this.outputQty = outputQty;
        return this;
    }

    public void setOutputQty(Double outputQty) {
        this.outputQty = outputQty;
    }

    public Product getProduct() {
        return product;
    }

    public WorkEffort product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getKsm() {
        return ksm;
    }

    public WorkEffort ksm(Product product) {
        this.ksm = product;
        return this;
    }

    public void setKsm(Product product) {
        this.ksm = product;
    }

    public WorkEffortType getType() {
        return type;
    }

    public WorkEffort type(WorkEffortType workEffortType) {
        this.type = workEffortType;
        return this;
    }

    public void setType(WorkEffortType workEffortType) {
        this.type = workEffortType;
    }

    public WorkEffortPurpose getPurpose() {
        return purpose;
    }

    public WorkEffort purpose(WorkEffortPurpose workEffortPurpose) {
        this.purpose = workEffortPurpose;
        return this;
    }

    public void setPurpose(WorkEffortPurpose workEffortPurpose) {
        this.purpose = workEffortPurpose;
    }

    public Status getStatus() {
        return status;
    }

    public WorkEffort status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Facility getFacility() {
        return facility;
    }

    public WorkEffort facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Uom getShelfListUom() {
        return shelfListUom;
    }

    public WorkEffort shelfListUom(Uom uom) {
        this.shelfListUom = uom;
        return this;
    }

    public void setShelfListUom(Uom uom) {
        this.shelfListUom = uom;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public WorkEffort productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    public WorkEffort productStore(ProductStore productStore) {
        this.productStore = productStore;
        return this;
    }

    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEffort)) {
            return false;
        }
        return id != null && id.equals(((WorkEffort) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffort{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            ", batchSize=" + getBatchSize() +
            ", minYieldRange=" + getMinYieldRange() +
            ", maxYieldRange=" + getMaxYieldRange() +
            ", percentComplete=" + getPercentComplete() +
            ", validationType='" + getValidationType() + "'" +
            ", shelfLife=" + getShelfLife() +
            ", outputQty=" + getOutputQty() +
            "}";
    }
}
