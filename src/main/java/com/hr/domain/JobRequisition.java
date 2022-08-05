package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Duration;

import com.hr.domain.enumeration.Gender;

/**
 * A JobRequisition.
 */
@Entity
@Table(name = "job_requisition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobRequisition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "experience_months")
    private Integer experienceMonths;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Size(max = 60)
    @Column(name = "qualification_str", length = 60)
    private String qualificationStr;

    @Column(name = "no_of_resource")
    private Integer noOfResource;

    @Column(name = "required_on_date")
    private LocalDate requiredOnDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobRequisitions", allowSetters = true)
    private Qualification qualificiation;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobRequisitions", allowSetters = true)
    private SkillType skillType;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobRequisitions", allowSetters = true)
    private Geo jobLocation;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobRequisitions", allowSetters = true)
    private ExamType examType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public JobRequisition duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getAge() {
        return age;
    }

    public JobRequisition age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public JobRequisition gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getExperienceMonths() {
        return experienceMonths;
    }

    public JobRequisition experienceMonths(Integer experienceMonths) {
        this.experienceMonths = experienceMonths;
        return this;
    }

    public void setExperienceMonths(Integer experienceMonths) {
        this.experienceMonths = experienceMonths;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public JobRequisition experienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
        return this;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualificationStr() {
        return qualificationStr;
    }

    public JobRequisition qualificationStr(String qualificationStr) {
        this.qualificationStr = qualificationStr;
        return this;
    }

    public void setQualificationStr(String qualificationStr) {
        this.qualificationStr = qualificationStr;
    }

    public Integer getNoOfResource() {
        return noOfResource;
    }

    public JobRequisition noOfResource(Integer noOfResource) {
        this.noOfResource = noOfResource;
        return this;
    }

    public void setNoOfResource(Integer noOfResource) {
        this.noOfResource = noOfResource;
    }

    public LocalDate getRequiredOnDate() {
        return requiredOnDate;
    }

    public JobRequisition requiredOnDate(LocalDate requiredOnDate) {
        this.requiredOnDate = requiredOnDate;
        return this;
    }

    public void setRequiredOnDate(LocalDate requiredOnDate) {
        this.requiredOnDate = requiredOnDate;
    }

    public Qualification getQualificiation() {
        return qualificiation;
    }

    public JobRequisition qualificiation(Qualification qualification) {
        this.qualificiation = qualification;
        return this;
    }

    public void setQualificiation(Qualification qualification) {
        this.qualificiation = qualification;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public JobRequisition skillType(SkillType skillType) {
        this.skillType = skillType;
        return this;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public Geo getJobLocation() {
        return jobLocation;
    }

    public JobRequisition jobLocation(Geo geo) {
        this.jobLocation = geo;
        return this;
    }

    public void setJobLocation(Geo geo) {
        this.jobLocation = geo;
    }

    public ExamType getExamType() {
        return examType;
    }

    public JobRequisition examType(ExamType examType) {
        this.examType = examType;
        return this;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobRequisition)) {
            return false;
        }
        return id != null && id.equals(((JobRequisition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobRequisition{" +
            "id=" + getId() +
            ", duration='" + getDuration() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", experienceMonths=" + getExperienceMonths() +
            ", experienceYears=" + getExperienceYears() +
            ", qualificationStr='" + getQualificationStr() + "'" +
            ", noOfResource=" + getNoOfResource() +
            ", requiredOnDate='" + getRequiredOnDate() + "'" +
            "}";
    }
}
