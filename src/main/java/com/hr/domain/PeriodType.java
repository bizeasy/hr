package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PeriodType.
 */
@Entity
@Table(name = "period_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PeriodType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 25)
    @Column(name = "name", length = 25, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "period_length")
    private Integer periodLength;

    @ManyToOne
    @JsonIgnoreProperties(value = "periodTypes", allowSetters = true)
    private Uom uom;

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

    public PeriodType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PeriodType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPeriodLength() {
        return periodLength;
    }

    public PeriodType periodLength(Integer periodLength) {
        this.periodLength = periodLength;
        return this;
    }

    public void setPeriodLength(Integer periodLength) {
        this.periodLength = periodLength;
    }

    public Uom getUom() {
        return uom;
    }

    public PeriodType uom(Uom uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodType)) {
            return false;
        }
        return id != null && id.equals(((PeriodType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", periodLength=" + getPeriodLength() +
            "}";
    }
}
