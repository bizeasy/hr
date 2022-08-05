package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EquipmentUsageLog.
 */
@Entity
@Table(name = "equipment_usage_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EquipmentUsageLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties(value = "equipmentUsageLogs", allowSetters = true)
    private Equipment equipment;

    @ManyToOne
    @JsonIgnoreProperties(value = "equipmentUsageLogs", allowSetters = true)
    private User cleanedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "equipmentUsageLogs", allowSetters = true)
    private User checkedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public EquipmentUsageLog fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public EquipmentUsageLog toTime(Instant toTime) {
        this.toTime = toTime;
        return this;
    }

    public void setToTime(Instant toTime) {
        this.toTime = toTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public EquipmentUsageLog remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public EquipmentUsageLog equipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getCleanedBy() {
        return cleanedBy;
    }

    public EquipmentUsageLog cleanedBy(User user) {
        this.cleanedBy = user;
        return this;
    }

    public void setCleanedBy(User user) {
        this.cleanedBy = user;
    }

    public User getCheckedBy() {
        return checkedBy;
    }

    public EquipmentUsageLog checkedBy(User user) {
        this.checkedBy = user;
        return this;
    }

    public void setCheckedBy(User user) {
        this.checkedBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipmentUsageLog)) {
            return false;
        }
        return id != null && id.equals(((EquipmentUsageLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentUsageLog{" +
            "id=" + getId() +
            ", fromTime='" + getFromTime() + "'" +
            ", toTime='" + getToTime() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
