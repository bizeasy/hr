package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A JobInterview.
 */
@Entity
@Table(name = "job_interview")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobInterview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "interview_date")
    private Instant interviewDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private Party interviewee;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private Party interviewer;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private JobInterviewType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private JobRequisition jobRequisition;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private InterviewGrade gradeSecured;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobInterviews", allowSetters = true)
    private InterviewResult result;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public JobInterview remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getInterviewDate() {
        return interviewDate;
    }

    public JobInterview interviewDate(Instant interviewDate) {
        this.interviewDate = interviewDate;
        return this;
    }

    public void setInterviewDate(Instant interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Party getInterviewee() {
        return interviewee;
    }

    public JobInterview interviewee(Party party) {
        this.interviewee = party;
        return this;
    }

    public void setInterviewee(Party party) {
        this.interviewee = party;
    }

    public Party getInterviewer() {
        return interviewer;
    }

    public JobInterview interviewer(Party party) {
        this.interviewer = party;
        return this;
    }

    public void setInterviewer(Party party) {
        this.interviewer = party;
    }

    public JobInterviewType getType() {
        return type;
    }

    public JobInterview type(JobInterviewType jobInterviewType) {
        this.type = jobInterviewType;
        return this;
    }

    public void setType(JobInterviewType jobInterviewType) {
        this.type = jobInterviewType;
    }

    public JobRequisition getJobRequisition() {
        return jobRequisition;
    }

    public JobInterview jobRequisition(JobRequisition jobRequisition) {
        this.jobRequisition = jobRequisition;
        return this;
    }

    public void setJobRequisition(JobRequisition jobRequisition) {
        this.jobRequisition = jobRequisition;
    }

    public InterviewGrade getGradeSecured() {
        return gradeSecured;
    }

    public JobInterview gradeSecured(InterviewGrade interviewGrade) {
        this.gradeSecured = interviewGrade;
        return this;
    }

    public void setGradeSecured(InterviewGrade interviewGrade) {
        this.gradeSecured = interviewGrade;
    }

    public InterviewResult getResult() {
        return result;
    }

    public JobInterview result(InterviewResult interviewResult) {
        this.result = interviewResult;
        return this;
    }

    public void setResult(InterviewResult interviewResult) {
        this.result = interviewResult;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobInterview)) {
            return false;
        }
        return id != null && id.equals(((JobInterview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobInterview{" +
            "id=" + getId() +
            ", remarks='" + getRemarks() + "'" +
            ", interviewDate='" + getInterviewDate() + "'" +
            "}";
    }
}
