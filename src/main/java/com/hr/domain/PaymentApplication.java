package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PaymentApplication.
 */
@Entity
@Table(name = "payment_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount_applied")
    private Double amountApplied;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private Payment payment;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private InvoiceItem invoiceItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private OrderItem orderItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentApplications", allowSetters = true)
    private Payment toPayment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountApplied() {
        return amountApplied;
    }

    public PaymentApplication amountApplied(Double amountApplied) {
        this.amountApplied = amountApplied;
        return this;
    }

    public void setAmountApplied(Double amountApplied) {
        this.amountApplied = amountApplied;
    }

    public Payment getPayment() {
        return payment;
    }

    public PaymentApplication payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public PaymentApplication invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public PaymentApplication invoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
        return this;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public Order getOrder() {
        return order;
    }

    public PaymentApplication order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public PaymentApplication orderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Payment getToPayment() {
        return toPayment;
    }

    public PaymentApplication toPayment(Payment payment) {
        this.toPayment = payment;
        return this;
    }

    public void setToPayment(Payment payment) {
        this.toPayment = payment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentApplication)) {
            return false;
        }
        return id != null && id.equals(((PaymentApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentApplication{" +
            "id=" + getId() +
            ", amountApplied=" + getAmountApplied() +
            "}";
    }
}
