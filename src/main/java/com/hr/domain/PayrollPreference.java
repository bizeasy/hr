package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A PayrollPreference.
 */
@Entity
@Table(name = "payroll_preference")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayrollPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "thru_date")
    private Instant thruDate;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "percentage", precision = 21, scale = 2)
    private BigDecimal percentage;

    @Column(name = "flat_amount", precision = 21, scale = 2)
    private BigDecimal flatAmount;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch")
    private String branch;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrollPreferences", allowSetters = true)
    private Party employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrollPreferences", allowSetters = true)
    private DeductionType deductionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrollPreferences", allowSetters = true)
    private PaymentMethodType paymentMethodType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrollPreferences", allowSetters = true)
    private PeriodType periodType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public PayrollPreference fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getThruDate() {
        return thruDate;
    }

    public PayrollPreference thruDate(Instant thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(Instant thruDate) {
        this.thruDate = thruDate;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public PayrollPreference sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public PayrollPreference percentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getFlatAmount() {
        return flatAmount;
    }

    public PayrollPreference flatAmount(BigDecimal flatAmount) {
        this.flatAmount = flatAmount;
        return this;
    }

    public void setFlatAmount(BigDecimal flatAmount) {
        this.flatAmount = flatAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PayrollPreference accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public PayrollPreference bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public PayrollPreference ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public PayrollPreference branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Party getEmployee() {
        return employee;
    }

    public PayrollPreference employee(Party party) {
        this.employee = party;
        return this;
    }

    public void setEmployee(Party party) {
        this.employee = party;
    }

    public DeductionType getDeductionType() {
        return deductionType;
    }

    public PayrollPreference deductionType(DeductionType deductionType) {
        this.deductionType = deductionType;
        return this;
    }

    public void setDeductionType(DeductionType deductionType) {
        this.deductionType = deductionType;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public PayrollPreference paymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        return this;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public PayrollPreference periodType(PeriodType periodType) {
        this.periodType = periodType;
        return this;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayrollPreference)) {
            return false;
        }
        return id != null && id.equals(((PayrollPreference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollPreference{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            ", percentage=" + getPercentage() +
            ", flatAmount=" + getFlatAmount() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", branch='" + getBranch() + "'" +
            "}";
    }
}
