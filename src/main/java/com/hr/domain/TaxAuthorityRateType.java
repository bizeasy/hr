package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TaxAuthorityRateType.
 */
@Entity
@Table(name = "tax_authority_rate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaxAuthorityRateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 25)
    @Column(name = "name", length = 25, unique = true)
    private String name;

    @Size(max = 60)
    @Column(name = "description", length = 60)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "taxAuthorityRateTypes", allowSetters = true)
    private TaxAuthority taxAuthority;

    @ManyToOne
    @JsonIgnoreProperties(value = "taxAuthorityRateTypes", allowSetters = true)
    private TaxSlab taxSlab;

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

    public TaxAuthorityRateType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaxAuthorityRateType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaxAuthority getTaxAuthority() {
        return taxAuthority;
    }

    public TaxAuthorityRateType taxAuthority(TaxAuthority taxAuthority) {
        this.taxAuthority = taxAuthority;
        return this;
    }

    public void setTaxAuthority(TaxAuthority taxAuthority) {
        this.taxAuthority = taxAuthority;
    }

    public TaxSlab getTaxSlab() {
        return taxSlab;
    }

    public TaxAuthorityRateType taxSlab(TaxSlab taxSlab) {
        this.taxSlab = taxSlab;
        return this;
    }

    public void setTaxSlab(TaxSlab taxSlab) {
        this.taxSlab = taxSlab;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxAuthorityRateType)) {
            return false;
        }
        return id != null && id.equals(((TaxAuthorityRateType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxAuthorityRateType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
