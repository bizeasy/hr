package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PerfReviewItem.
 */
@Entity
@Table(name = "perf_review_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PerfReviewItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviewItems", allowSetters = true)
    private PerfReview perfReview;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviewItems", allowSetters = true)
    private PerfRatingType perfRatingType;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviewItems", allowSetters = true)
    private PerfReviewItemType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "perfReviewItems", allowSetters = true)
    private Party employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public PerfReviewItem sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getComments() {
        return comments;
    }

    public PerfReviewItem comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PerfReview getPerfReview() {
        return perfReview;
    }

    public PerfReviewItem perfReview(PerfReview perfReview) {
        this.perfReview = perfReview;
        return this;
    }

    public void setPerfReview(PerfReview perfReview) {
        this.perfReview = perfReview;
    }

    public PerfRatingType getPerfRatingType() {
        return perfRatingType;
    }

    public PerfReviewItem perfRatingType(PerfRatingType perfRatingType) {
        this.perfRatingType = perfRatingType;
        return this;
    }

    public void setPerfRatingType(PerfRatingType perfRatingType) {
        this.perfRatingType = perfRatingType;
    }

    public PerfReviewItemType getType() {
        return type;
    }

    public PerfReviewItem type(PerfReviewItemType perfReviewItemType) {
        this.type = perfReviewItemType;
        return this;
    }

    public void setType(PerfReviewItemType perfReviewItemType) {
        this.type = perfReviewItemType;
    }

    public Party getEmployee() {
        return employee;
    }

    public PerfReviewItem employee(Party party) {
        this.employee = party;
        return this;
    }

    public void setEmployee(Party party) {
        this.employee = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfReviewItem)) {
            return false;
        }
        return id != null && id.equals(((PerfReviewItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfReviewItem{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
