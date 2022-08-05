package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FacilityUsageLog.
 */
@Entity
@Table(name = "facility_usage_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FacilityUsageLog implements Serializable {

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
    @JsonIgnoreProperties(value = "facilityUsageLogs", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityUsageLogs", allowSetters = true)
    private User cleanedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityUsageLogs", allowSetters = true)
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

    public FacilityUsageLog fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public FacilityUsageLog toTime(Instant toTime) {
        this.toTime = toTime;
        return this;
    }

    public void setToTime(Instant toTime) {
        this.toTime = toTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public FacilityUsageLog remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityUsageLog facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public User getCleanedBy() {
        return cleanedBy;
    }

    public FacilityUsageLog cleanedBy(User user) {
        this.cleanedBy = user;
        return this;
    }

    public void setCleanedBy(User user) {
        this.cleanedBy = user;
    }

    public User getCheckedBy() {
        return checkedBy;
    }

    public FacilityUsageLog checkedBy(User user) {
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
        if (!(o instanceof FacilityUsageLog)) {
            return false;
        }
        return id != null && id.equals(((FacilityUsageLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityUsageLog{" +
            "id=" + getId() +
            ", fromTime='" + getFromTime() + "'" +
            ", toTime='" + getToTime() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
