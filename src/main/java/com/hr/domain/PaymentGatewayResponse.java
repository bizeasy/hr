package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PaymentGatewayResponse.
 */
@Entity
@Table(name = "payment_gateway_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentGatewayResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Size(max = 25)
    @Column(name = "reference_number", length = 25)
    private String referenceNumber;

    @Column(name = "gateway_message")
    private String gatewayMessage;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentGatewayResponses", allowSetters = true)
    private PaymentMethodType paymentMethodType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentGatewayResponse amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public PaymentGatewayResponse referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public PaymentGatewayResponse gatewayMessage(String gatewayMessage) {
        this.gatewayMessage = gatewayMessage;
        return this;
    }

    public void setGatewayMessage(String gatewayMessage) {
        this.gatewayMessage = gatewayMessage;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public PaymentGatewayResponse paymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        return this;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentGatewayResponse)) {
            return false;
        }
        return id != null && id.equals(((PaymentGatewayResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGatewayResponse{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", gatewayMessage='" + getGatewayMessage() + "'" +
            "}";
    }
}
