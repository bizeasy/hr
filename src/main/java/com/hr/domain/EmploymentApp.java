package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EmploymentApp.
 */
@Entity
@Table(name = "employment_app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmploymentApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "application_date")
    private Instant applicationDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private EmplPosition emplPosition;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private EmploymentAppSourceType source;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private Party applyingParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private Party referredByParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private Party approverParty;

    @ManyToOne
    @JsonIgnoreProperties(value = "employmentApps", allowSetters = true)
    private JobRequisition jobRequisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public EmploymentApp applicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public EmplPosition getEmplPosition() {
        return emplPosition;
    }

    public EmploymentApp emplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
        return this;
    }

    public void setEmplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
    }

    public Status getStatus() {
        return status;
    }

    public EmploymentApp status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EmploymentAppSourceType getSource() {
        return source;
    }

    public EmploymentApp source(EmploymentAppSourceType employmentAppSourceType) {
        this.source = employmentAppSourceType;
        return this;
    }

    public void setSource(EmploymentAppSourceType employmentAppSourceType) {
        this.source = employmentAppSourceType;
    }

    public Party getApplyingParty() {
        return applyingParty;
    }

    public EmploymentApp applyingParty(Party party) {
        this.applyingParty = party;
        return this;
    }

    public void setApplyingParty(Party party) {
        this.applyingParty = party;
    }

    public Party getReferredByParty() {
        return referredByParty;
    }

    public EmploymentApp referredByParty(Party party) {
        this.referredByParty = party;
        return this;
    }

    public void setReferredByParty(Party party) {
        this.referredByParty = party;
    }

    public Party getApproverParty() {
        return approverParty;
    }

    public EmploymentApp approverParty(Party party) {
        this.approverParty = party;
        return this;
    }

    public void setApproverParty(Party party) {
        this.approverParty = party;
    }

    public JobRequisition getJobRequisition() {
        return jobRequisition;
    }

    public EmploymentApp jobRequisition(JobRequisition jobRequisition) {
        this.jobRequisition = jobRequisition;
        return this;
    }

    public void setJobRequisition(JobRequisition jobRequisition) {
        this.jobRequisition = jobRequisition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmploymentApp)) {
            return false;
        }
        return id != null && id.equals(((EmploymentApp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmploymentApp{" +
            "id=" + getId() +
            ", applicationDate='" + getApplicationDate() + "'" +
            "}";
    }
}
