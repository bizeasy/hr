package com.hr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PaymentGatewayConfig.
 */
@Entity
@Table(name = "payment_gateway_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentGatewayConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 25)
    @Column(name = "name", length = 25, unique = true)
    private String name;

    @Column(name = "auth_url")
    private String authUrl;

    @Column(name = "release_url")
    private String releaseUrl;

    @Column(name = "refund_url")
    private String refundUrl;

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

    public PaymentGatewayConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public PaymentGatewayConfig authUrl(String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getReleaseUrl() {
        return releaseUrl;
    }

    public PaymentGatewayConfig releaseUrl(String releaseUrl) {
        this.releaseUrl = releaseUrl;
        return this;
    }

    public void setReleaseUrl(String releaseUrl) {
        this.releaseUrl = releaseUrl;
    }

    public String getRefundUrl() {
        return refundUrl;
    }

    public PaymentGatewayConfig refundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
        return this;
    }

    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentGatewayConfig)) {
            return false;
        }
        return id != null && id.equals(((PaymentGatewayConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGatewayConfig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", authUrl='" + getAuthUrl() + "'" +
            ", releaseUrl='" + getReleaseUrl() + "'" +
            ", refundUrl='" + getRefundUrl() + "'" +
            "}";
    }
}
