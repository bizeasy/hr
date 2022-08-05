package com.hr.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.hr.domain.Payment} entity. This class is used
 * in {@link com.hr.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter effectiveDate;

    private InstantFilter paymentDate;

    private StringFilter paymentRefNumber;

    private BigDecimalFilter amount;

    private StringFilter paymentStatus;

    private StringFilter mihpayId;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter productInfo;

    private StringFilter txnId;

    private BigDecimalFilter actualAmount;

    private LongFilter paymentTypeId;

    private LongFilter paymentMethodTypeId;

    private LongFilter statusId;

    private LongFilter paymentMethodId;

    private LongFilter paymentGatewayResponseId;

    private LongFilter partyIdFromId;

    private LongFilter partyIdToId;

    private LongFilter roleTypeId;

    private LongFilter currencyUomId;

    public PaymentCriteria() {
    }

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.effectiveDate = other.effectiveDate == null ? null : other.effectiveDate.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.paymentRefNumber = other.paymentRefNumber == null ? null : other.paymentRefNumber.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.mihpayId = other.mihpayId == null ? null : other.mihpayId.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.productInfo = other.productInfo == null ? null : other.productInfo.copy();
        this.txnId = other.txnId == null ? null : other.txnId.copy();
        this.actualAmount = other.actualAmount == null ? null : other.actualAmount.copy();
        this.paymentTypeId = other.paymentTypeId == null ? null : other.paymentTypeId.copy();
        this.paymentMethodTypeId = other.paymentMethodTypeId == null ? null : other.paymentMethodTypeId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.paymentMethodId = other.paymentMethodId == null ? null : other.paymentMethodId.copy();
        this.paymentGatewayResponseId = other.paymentGatewayResponseId == null ? null : other.paymentGatewayResponseId.copy();
        this.partyIdFromId = other.partyIdFromId == null ? null : other.partyIdFromId.copy();
        this.partyIdToId = other.partyIdToId == null ? null : other.partyIdToId.copy();
        this.roleTypeId = other.roleTypeId == null ? null : other.roleTypeId.copy();
        this.currencyUomId = other.currencyUomId == null ? null : other.currencyUomId.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(InstantFilter effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public InstantFilter getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(InstantFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StringFilter getPaymentRefNumber() {
        return paymentRefNumber;
    }

    public void setPaymentRefNumber(StringFilter paymentRefNumber) {
        this.paymentRefNumber = paymentRefNumber;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(StringFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StringFilter getMihpayId() {
        return mihpayId;
    }

    public void setMihpayId(StringFilter mihpayId) {
        this.mihpayId = mihpayId;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(StringFilter productInfo) {
        this.productInfo = productInfo;
    }

    public StringFilter getTxnId() {
        return txnId;
    }

    public void setTxnId(StringFilter txnId) {
        this.txnId = txnId;
    }

    public BigDecimalFilter getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimalFilter actualAmount) {
        this.actualAmount = actualAmount;
    }

    public LongFilter getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(LongFilter paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public LongFilter getPaymentMethodTypeId() {
        return paymentMethodTypeId;
    }

    public void setPaymentMethodTypeId(LongFilter paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(LongFilter paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public LongFilter getPaymentGatewayResponseId() {
        return paymentGatewayResponseId;
    }

    public void setPaymentGatewayResponseId(LongFilter paymentGatewayResponseId) {
        this.paymentGatewayResponseId = paymentGatewayResponseId;
    }

    public LongFilter getPartyIdFromId() {
        return partyIdFromId;
    }

    public void setPartyIdFromId(LongFilter partyIdFromId) {
        this.partyIdFromId = partyIdFromId;
    }

    public LongFilter getPartyIdToId() {
        return partyIdToId;
    }

    public void setPartyIdToId(LongFilter partyIdToId) {
        this.partyIdToId = partyIdToId;
    }

    public LongFilter getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(LongFilter roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public LongFilter getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(LongFilter currencyUomId) {
        this.currencyUomId = currencyUomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(effectiveDate, that.effectiveDate) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(paymentRefNumber, that.paymentRefNumber) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(mihpayId, that.mihpayId) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(productInfo, that.productInfo) &&
            Objects.equals(txnId, that.txnId) &&
            Objects.equals(actualAmount, that.actualAmount) &&
            Objects.equals(paymentTypeId, that.paymentTypeId) &&
            Objects.equals(paymentMethodTypeId, that.paymentMethodTypeId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(paymentMethodId, that.paymentMethodId) &&
            Objects.equals(paymentGatewayResponseId, that.paymentGatewayResponseId) &&
            Objects.equals(partyIdFromId, that.partyIdFromId) &&
            Objects.equals(partyIdToId, that.partyIdToId) &&
            Objects.equals(roleTypeId, that.roleTypeId) &&
            Objects.equals(currencyUomId, that.currencyUomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        effectiveDate,
        paymentDate,
        paymentRefNumber,
        amount,
        paymentStatus,
        mihpayId,
        email,
        phone,
        productInfo,
        txnId,
        actualAmount,
        paymentTypeId,
        paymentMethodTypeId,
        statusId,
        paymentMethodId,
        paymentGatewayResponseId,
        partyIdFromId,
        partyIdToId,
        roleTypeId,
        currencyUomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (effectiveDate != null ? "effectiveDate=" + effectiveDate + ", " : "") +
                (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
                (paymentRefNumber != null ? "paymentRefNumber=" + paymentRefNumber + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (mihpayId != null ? "mihpayId=" + mihpayId + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (productInfo != null ? "productInfo=" + productInfo + ", " : "") +
                (txnId != null ? "txnId=" + txnId + ", " : "") +
                (actualAmount != null ? "actualAmount=" + actualAmount + ", " : "") +
                (paymentTypeId != null ? "paymentTypeId=" + paymentTypeId + ", " : "") +
                (paymentMethodTypeId != null ? "paymentMethodTypeId=" + paymentMethodTypeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (paymentMethodId != null ? "paymentMethodId=" + paymentMethodId + ", " : "") +
                (paymentGatewayResponseId != null ? "paymentGatewayResponseId=" + paymentGatewayResponseId + ", " : "") +
                (partyIdFromId != null ? "partyIdFromId=" + partyIdFromId + ", " : "") +
                (partyIdToId != null ? "partyIdToId=" + partyIdToId + ", " : "") +
                (roleTypeId != null ? "roleTypeId=" + roleTypeId + ", " : "") +
                (currencyUomId != null ? "currencyUomId=" + currencyUomId + ", " : "") +
            "}";
    }

}
