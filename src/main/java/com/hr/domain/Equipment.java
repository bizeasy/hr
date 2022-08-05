package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Size(max = 60)
    @Column(name = "equipment_number", length = 60)
    private String equipmentNumber;

    @Column(name = "min_capacity_range")
    private Double minCapacityRange;

    @Column(name = "max_capacity_range")
    private Double maxCapacityRange;

    @Column(name = "min_operational_range")
    private Double minOperationalRange;

    @Column(name = "max_operational_range")
    private Double maxOperationalRange;

    @Column(name = "last_calibrated_date")
    private ZonedDateTime lastCalibratedDate;

    @Column(name = "calibration_due_date")
    private ZonedDateTime calibrationDueDate;

    @Size(max = 60)
    @Column(name = "change_control_no", length = 60)
    private String changeControlNo;

    @ManyToOne
    @JsonIgnoreProperties(value = "equipment", allowSetters = true)
    private EquipmentType equipmentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "equipment", allowSetters = true)
    private Status status;

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

    public Equipment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Equipment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public Equipment equipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
        return this;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Double getMinCapacityRange() {
        return minCapacityRange;
    }

    public Equipment minCapacityRange(Double minCapacityRange) {
        this.minCapacityRange = minCapacityRange;
        return this;
    }

    public void setMinCapacityRange(Double minCapacityRange) {
        this.minCapacityRange = minCapacityRange;
    }

    public Double getMaxCapacityRange() {
        return maxCapacityRange;
    }

    public Equipment maxCapacityRange(Double maxCapacityRange) {
        this.maxCapacityRange = maxCapacityRange;
        return this;
    }

    public void setMaxCapacityRange(Double maxCapacityRange) {
        this.maxCapacityRange = maxCapacityRange;
    }

    public Double getMinOperationalRange() {
        return minOperationalRange;
    }

    public Equipment minOperationalRange(Double minOperationalRange) {
        this.minOperationalRange = minOperationalRange;
        return this;
    }

    public void setMinOperationalRange(Double minOperationalRange) {
        this.minOperationalRange = minOperationalRange;
    }

    public Double getMaxOperationalRange() {
        return maxOperationalRange;
    }

    public Equipment maxOperationalRange(Double maxOperationalRange) {
        this.maxOperationalRange = maxOperationalRange;
        return this;
    }

    public void setMaxOperationalRange(Double maxOperationalRange) {
        this.maxOperationalRange = maxOperationalRange;
    }

    public ZonedDateTime getLastCalibratedDate() {
        return lastCalibratedDate;
    }

    public Equipment lastCalibratedDate(ZonedDateTime lastCalibratedDate) {
        this.lastCalibratedDate = lastCalibratedDate;
        return this;
    }

    public void setLastCalibratedDate(ZonedDateTime lastCalibratedDate) {
        this.lastCalibratedDate = lastCalibratedDate;
    }

    public ZonedDateTime getCalibrationDueDate() {
        return calibrationDueDate;
    }

    public Equipment calibrationDueDate(ZonedDateTime calibrationDueDate) {
        this.calibrationDueDate = calibrationDueDate;
        return this;
    }

    public void setCalibrationDueDate(ZonedDateTime calibrationDueDate) {
        this.calibrationDueDate = calibrationDueDate;
    }

    public String getChangeControlNo() {
        return changeControlNo;
    }

    public Equipment changeControlNo(String changeControlNo) {
        this.changeControlNo = changeControlNo;
        return this;
    }

    public void setChangeControlNo(String changeControlNo) {
        this.changeControlNo = changeControlNo;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public Equipment equipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
        return this;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Status getStatus() {
        return status;
    }

    public Equipment status(Status status) {
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
        if (!(o instanceof Equipment)) {
            return false;
        }
        return id != null && id.equals(((Equipment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", equipmentNumber='" + getEquipmentNumber() + "'" +
            ", minCapacityRange=" + getMinCapacityRange() +
            ", maxCapacityRange=" + getMaxCapacityRange() +
            ", minOperationalRange=" + getMinOperationalRange() +
            ", maxOperationalRange=" + getMaxOperationalRange() +
            ", lastCalibratedDate='" + getLastCalibratedDate() + "'" +
            ", calibrationDueDate='" + getCalibrationDueDate() + "'" +
            ", changeControlNo='" + getChangeControlNo() + "'" +
            "}";
    }
}
