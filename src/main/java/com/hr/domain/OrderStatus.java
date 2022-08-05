package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A OrderStatus.
 */
@Entity
@Table(name = "order_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status_date_time")
    private ZonedDateTime statusDateTime;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderStatuses", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderStatuses", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderStatuses", allowSetters = true)
    private Reason reason;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStatusDateTime() {
        return statusDateTime;
    }

    public OrderStatus statusDateTime(ZonedDateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
        return this;
    }

    public void setStatusDateTime(ZonedDateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public OrderStatus sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Status getStatus() {
        return status;
    }

    public OrderStatus status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public OrderStatus order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Reason getReason() {
        return reason;
    }

    public OrderStatus reason(Reason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStatus)) {
            return false;
        }
        return id != null && id.equals(((OrderStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderStatus{" +
            "id=" + getId() +
            ", statusDateTime='" + getStatusDateTime() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            "}";
    }
}
