package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A EmplPositionTypeRate.
 */
@Entity
@Table(name = "empl_position_type_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmplPositionTypeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rate_amount", precision = 21, scale = 2)
    private BigDecimal rateAmount;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionTypeRates", allowSetters = true)
    private RateType rateType;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionTypeRates", allowSetters = true)
    private EmplPositionType emplPositionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "emplPositionTypeRates", allowSetters = true)
    private PayGrade payGrade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRateAmount() {
        return rateAmount;
    }

    public EmplPositionTypeRate rateAmount(BigDecimal rateAmount) {
        this.rateAmount = rateAmount;
        return this;
    }

    public void setRateAmount(BigDecimal rateAmount) {
        this.rateAmount = rateAmount;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public EmplPositionTypeRate fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public EmplPositionTypeRate thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public RateType getRateType() {
        return rateType;
    }

    public EmplPositionTypeRate rateType(RateType rateType) {
        this.rateType = rateType;
        return this;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public EmplPositionType getEmplPositionType() {
        return emplPositionType;
    }

    public EmplPositionTypeRate emplPositionType(EmplPositionType emplPositionType) {
        this.emplPositionType = emplPositionType;
        return this;
    }

    public void setEmplPositionType(EmplPositionType emplPositionType) {
        this.emplPositionType = emplPositionType;
    }

    public PayGrade getPayGrade() {
        return payGrade;
    }

    public EmplPositionTypeRate payGrade(PayGrade payGrade) {
        this.payGrade = payGrade;
        return this;
    }

    public void setPayGrade(PayGrade payGrade) {
        this.payGrade = payGrade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmplPositionTypeRate)) {
            return false;
        }
        return id != null && id.equals(((EmplPositionTypeRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmplPositionTypeRate{" +
            "id=" + getId() +
            ", rateAmount=" + getRateAmount() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
