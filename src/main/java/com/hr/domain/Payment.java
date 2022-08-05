package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "effective_date")
    private Instant effectiveDate;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "payment_ref_number")
    private String paymentRefNumber;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "mihpay_id")
    private String mihpayId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "product_info")
    private String productInfo;

    @Column(name = "txn_id")
    private String txnId;

    @Column(name = "actual_amount", precision = 21, scale = 2)
    private BigDecimal actualAmount;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private PaymentType paymentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private PaymentMethodType paymentMethodType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private PaymentGatewayResponse paymentGatewayResponse;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Party partyIdFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Party partyIdTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private RoleType roleType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Uom currencyUom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public Payment effectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public Payment paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentRefNumber() {
        return paymentRefNumber;
    }

    public Payment paymentRefNumber(String paymentRefNumber) {
        this.paymentRefNumber = paymentRefNumber;
        return this;
    }

    public void setPaymentRefNumber(String paymentRefNumber) {
        this.paymentRefNumber = paymentRefNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Payment amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Payment paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMihpayId() {
        return mihpayId;
    }

    public Payment mihpayId(String mihpayId) {
        this.mihpayId = mihpayId;
        return this;
    }

    public void setMihpayId(String mihpayId) {
        this.mihpayId = mihpayId;
    }

    public String getEmail() {
        return email;
    }

    public Payment email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Payment phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public Payment productInfo(String productInfo) {
        this.productInfo = productInfo;
        return this;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getTxnId() {
        return txnId;
    }

    public Payment txnId(String txnId) {
        this.txnId = txnId;
        return this;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public Payment actualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
        return this;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Payment paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public Payment paymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        return this;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Status getStatus() {
        return status;
    }

    public Payment status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Payment paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentGatewayResponse getPaymentGatewayResponse() {
        return paymentGatewayResponse;
    }

    public Payment paymentGatewayResponse(PaymentGatewayResponse paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
        return this;
    }

    public void setPaymentGatewayResponse(PaymentGatewayResponse paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
    }

    public Party getPartyIdFrom() {
        return partyIdFrom;
    }

    public Payment partyIdFrom(Party party) {
        this.partyIdFrom = party;
        return this;
    }

    public void setPartyIdFrom(Party party) {
        this.partyIdFrom = party;
    }

    public Party getPartyIdTo() {
        return partyIdTo;
    }

    public Payment partyIdTo(Party party) {
        this.partyIdTo = party;
        return this;
    }

    public void setPartyIdTo(Party party) {
        this.partyIdTo = party;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public Payment roleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Uom getCurrencyUom() {
        return currencyUom;
    }

    public Payment currencyUom(Uom uom) {
        this.currencyUom = uom;
        return this;
    }

    public void setCurrencyUom(Uom uom) {
        this.currencyUom = uom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentRefNumber='" + getPaymentRefNumber() + "'" +
            ", amount=" + getAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", mihpayId='" + getMihpayId() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", productInfo='" + getProductInfo() + "'" +
            ", txnId='" + getTxnId() + "'" +
            ", actualAmount=" + getActualAmount() +
            "}";
    }
}
