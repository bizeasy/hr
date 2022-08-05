package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WorkEffortStatus.
 */
@Entity
@Table(name = "work_effort_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEffortStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortStatuses", allowSetters = true)
    private WorkEffort workEffort;

    @ManyToOne
    @JsonIgnoreProperties(value = "workEffortStatuses", allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public WorkEffortStatus reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public WorkEffortStatus workEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
        return this;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }

    public Status getStatus() {
        return status;
    }

    public WorkEffortStatus status(Status status) {
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
        if (!(o instanceof WorkEffortStatus)) {
            return false;
        }
        return id != null && id.equals(((WorkEffortStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffortStatus{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
