package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PublicHolidays.
 */
@Entity
@Table(name = "public_holidays")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PublicHolidays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Column(name = "no_of_holidays")
    private Integer noOfHolidays;

    @ManyToOne
    @JsonIgnoreProperties(value = "publicHolidays", allowSetters = true)
    private HolidayType type;

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

    public PublicHolidays name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public PublicHolidays fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PublicHolidays thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Integer getNoOfHolidays() {
        return noOfHolidays;
    }

    public PublicHolidays noOfHolidays(Integer noOfHolidays) {
        this.noOfHolidays = noOfHolidays;
        return this;
    }

    public void setNoOfHolidays(Integer noOfHolidays) {
        this.noOfHolidays = noOfHolidays;
    }

    public HolidayType getType() {
        return type;
    }

    public PublicHolidays type(HolidayType holidayType) {
        this.type = holidayType;
        return this;
    }

    public void setType(HolidayType holidayType) {
        this.type = holidayType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicHolidays)) {
            return false;
        }
        return id != null && id.equals(((PublicHolidays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicHolidays{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", noOfHolidays=" + getNoOfHolidays() +
            "}";
    }
}
