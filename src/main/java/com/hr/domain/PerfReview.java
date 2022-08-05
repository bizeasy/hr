package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PerfReview.
 */
@Entity
@Table(name = "perf_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PerfReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviews", allowSetters = true)
    private Party employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviews", allowSetters = true)
    private Party manager;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviews", allowSetters = true)
    private EmplPosition emplPosition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public PerfReview fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PerfReview thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getComments() {
        return comments;
    }

    public PerfReview comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Party getEmployee() {
        return employee;
    }

    public PerfReview employee(Party party) {
        this.employee = party;
        return this;
    }

    public void setEmployee(Party party) {
        this.employee = party;
    }

    public Party getManager() {
        return manager;
    }

    public PerfReview manager(Party party) {
        this.manager = party;
        return this;
    }

    public void setManager(Party party) {
        this.manager = party;
    }

    public EmplPosition getEmplPosition() {
        return emplPosition;
    }

    public PerfReview emplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
        return this;
    }

    public void setEmplPosition(EmplPosition emplPosition) {
        this.emplPosition = emplPosition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfReview)) {
            return false;
        }
        return id != null && id.equals(((PerfReview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfReview{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
