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

/**
 * Criteria class for the {@link com.hr.domain.InventoryItemVariance} entity. This class is used
 * in {@link com.hr.web.rest.InventoryItemVarianceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-item-variances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryItemVarianceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter varianceReason;

    private BigDecimalFilter atpVar;

    private BigDecimalFilter qohVar;

    private StringFilter comments;

    private LongFilter inventoryItemId;

    private LongFilter reasonId;

    public InventoryItemVarianceCriteria() {
    }

    public InventoryItemVarianceCriteria(InventoryItemVarianceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.varianceReason = other.varianceReason == null ? null : other.varianceReason.copy();
        this.atpVar = other.atpVar == null ? null : other.atpVar.copy();
        this.qohVar = other.qohVar == null ? null : other.qohVar.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.inventoryItemId = other.inventoryItemId == null ? null : other.inventoryItemId.copy();
        this.reasonId = other.reasonId == null ? null : other.reasonId.copy();
    }

    @Override
    public InventoryItemVarianceCriteria copy() {
        return new InventoryItemVarianceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVarianceReason() {
        return varianceReason;
    }

    public void setVarianceReason(StringFilter varianceReason) {
        this.varianceReason = varianceReason;
    }

    public BigDecimalFilter getAtpVar() {
        return atpVar;
    }

    public void setAtpVar(BigDecimalFilter atpVar) {
        this.atpVar = atpVar;
    }

    public BigDecimalFilter getQohVar() {
        return qohVar;
    }

    public void setQohVar(BigDecimalFilter qohVar) {
        this.qohVar = qohVar;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LongFilter getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(LongFilter inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public LongFilter getReasonId() {
        return reasonId;
    }

    public void setReasonId(LongFilter reasonId) {
        this.reasonId = reasonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryItemVarianceCriteria that = (InventoryItemVarianceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(varianceReason, that.varianceReason) &&
            Objects.equals(atpVar, that.atpVar) &&
            Objects.equals(qohVar, that.qohVar) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(inventoryItemId, that.inventoryItemId) &&
            Objects.equals(reasonId, that.reasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        varianceReason,
        atpVar,
        qohVar,
        comments,
        inventoryItemId,
        reasonId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryItemVarianceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (varianceReason != null ? "varianceReason=" + varianceReason + ", " : "") +
                (atpVar != null ? "atpVar=" + atpVar + ", " : "") +
                (qohVar != null ? "qohVar=" + qohVar + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (inventoryItemId != null ? "inventoryItemId=" + inventoryItemId + ", " : "") +
                (reasonId != null ? "reasonId=" + reasonId + ", " : "") +
            "}";
    }

}
