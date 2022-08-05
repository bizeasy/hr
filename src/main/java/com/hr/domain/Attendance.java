package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "punch_in")
    private Instant punchIn;

    @Column(name = "punch_out")
    private Instant punchOut;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Party employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPunchIn() {
        return punchIn;
    }

    public Attendance punchIn(Instant punchIn) {
        this.punchIn = punchIn;
        return this;
    }

    public void setPunchIn(Instant punchIn) {
        this.punchIn = punchIn;
    }

    public Instant getPunchOut() {
        return punchOut;
    }

    public Attendance punchOut(Instant punchOut) {
        this.punchOut = punchOut;
        return this;
    }

    public void setPunchOut(Instant punchOut) {
        this.punchOut = punchOut;
    }

    public Party getEmployee() {
        return employee;
    }

    public Attendance employee(Party party) {
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
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", punchIn='" + getPunchIn() + "'" +
            ", punchOut='" + getPunchOut() + "'" +
            "}";
    }
}
