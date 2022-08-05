package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Term.
 */
@Entity
@Table(name = "term")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Term implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "name", length = 60, unique = true)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "term_detail")
    private String termDetail;

    @Column(name = "term_value", precision = 21, scale = 2)
    private BigDecimal termValue;

    @Column(name = "term_days")
    private Integer termDays;

    @Column(name = "text_value")
    private String textValue;

    @ManyToOne
    @JsonIgnoreProperties(value = "terms", allowSetters = true)
    private TermType termType;

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

    public Term name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Term description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermDetail() {
        return termDetail;
    }

    public Term termDetail(String termDetail) {
        this.termDetail = termDetail;
        return this;
    }

    public void setTermDetail(String termDetail) {
        this.termDetail = termDetail;
    }

    public BigDecimal getTermValue() {
        return termValue;
    }

    public Term termValue(BigDecimal termValue) {
        this.termValue = termValue;
        return this;
    }

    public void setTermValue(BigDecimal termValue) {
        this.termValue = termValue;
    }

    public Integer getTermDays() {
        return termDays;
    }

    public Term termDays(Integer termDays) {
        this.termDays = termDays;
        return this;
    }

    public void setTermDays(Integer termDays) {
        this.termDays = termDays;
    }

    public String getTextValue() {
        return textValue;
    }

    public Term textValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public TermType getTermType() {
        return termType;
    }

    public Term termType(TermType termType) {
        this.termType = termType;
        return this;
    }

    public void setTermType(TermType termType) {
        this.termType = termType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Term)) {
            return false;
        }
        return id != null && id.equals(((Term) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Term{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", termDetail='" + getTermDetail() + "'" +
            ", termValue=" + getTermValue() +
            ", termDays=" + getTermDays() +
            ", textValue='" + getTextValue() + "'" +
            "}";
    }
}
