package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A GeoAssoc.
 */
@Entity
@Table(name = "geo_assoc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GeoAssoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "geoAssocs", allowSetters = true)
    private Geo geo;

    @ManyToOne
    @JsonIgnoreProperties(value = "geoAssocs", allowSetters = true)
    private Geo geoTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "geoAssocs", allowSetters = true)
    private GeoAssocType geoAssocType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Geo getGeo() {
        return geo;
    }

    public GeoAssoc geo(Geo geo) {
        this.geo = geo;
        return this;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Geo getGeoTo() {
        return geoTo;
    }

    public GeoAssoc geoTo(Geo geo) {
        this.geoTo = geo;
        return this;
    }

    public void setGeoTo(Geo geo) {
        this.geoTo = geo;
    }

    public GeoAssocType getGeoAssocType() {
        return geoAssocType;
    }

    public GeoAssoc geoAssocType(GeoAssocType geoAssocType) {
        this.geoAssocType = geoAssocType;
        return this;
    }

    public void setGeoAssocType(GeoAssocType geoAssocType) {
        this.geoAssocType = geoAssocType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeoAssoc)) {
            return false;
        }
        return id != null && id.equals(((GeoAssoc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeoAssoc{" +
            "id=" + getId() +
            "}";
    }
}
