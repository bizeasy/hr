package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "invoice_date")
    private ZonedDateTime invoiceDate;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @Column(name = "paid_date")
    private ZonedDateTime paidDate;

    @Size(max = 255)
    @Column(name = "invoice_message", length = 255)
    private String invoiceMessage;

    @Size(max = 60)
    @Column(name = "reference_number", length = 60)
    private String referenceNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private InvoiceType invoiceType;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private Party partyIdFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private Party partyIdTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private RoleType roleType;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private ContactMech contactMech;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(ZonedDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(ZonedDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Invoice dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getPaidDate() {
        return paidDate;
    }

    public Invoice paidDate(ZonedDateTime paidDate) {
        this.paidDate = paidDate;
        return this;
    }

    public void setPaidDate(ZonedDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public String getInvoiceMessage() {
        return invoiceMessage;
    }

    public Invoice invoiceMessage(String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
        return this;
    }

    public void setInvoiceMessage(String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Invoice referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public Invoice invoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Party getPartyIdFrom() {
        return partyIdFrom;
    }

    public Invoice partyIdFrom(Party party) {
        this.partyIdFrom = party;
        return this;
    }

    public void setPartyIdFrom(Party party) {
        this.partyIdFrom = party;
    }

    public Party getPartyIdTo() {
        return partyIdTo;
    }

    public Invoice partyIdTo(Party party) {
        this.partyIdTo = party;
        return this;
    }

    public void setPartyIdTo(Party party) {
        this.partyIdTo = party;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public Invoice roleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Status getStatus() {
        return status;
    }

    public Invoice status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public Invoice contactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
        return this;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", paidDate='" + getPaidDate() + "'" +
            ", invoiceMessage='" + getInvoiceMessage() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            "}";
    }
}
