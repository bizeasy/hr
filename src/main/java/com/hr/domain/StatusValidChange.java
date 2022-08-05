package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StatusValidChange.
 */
@Entity
@Table(name = "status_valid_change")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StatusValidChange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 60)
    @Column(name = "transition_name", length = 60)
    private String transitionName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "rules")
    private String rules;

    @ManyToOne
    @JsonIgnoreProperties(value = "statusValidChanges", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "statusValidChanges", allowSetters = true)
    private Status statusTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public StatusValidChange transitionName(String transitionName) {
        this.transitionName = transitionName;
        return this;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public String getRules() {
        return rules;
    }

    public StatusValidChange rules(String rules) {
        this.rules = rules;
        return this;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public Status getStatus() {
        return status;
    }

    public StatusValidChange status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatusTo() {
        return statusTo;
    }

    public StatusValidChange statusTo(Status status) {
        this.statusTo = status;
        return this;
    }

    public void setStatusTo(Status status) {
        this.statusTo = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusValidChange)) {
            return false;
        }
        return id != null && id.equals(((StatusValidChange) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusValidChange{" +
            "id=" + getId() +
            ", transitionName='" + getTransitionName() + "'" +
            ", rules='" + getRules() + "'" +
            "}";
    }
}
