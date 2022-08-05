package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrderTerm.
 */
@Entity
@Table(name = "order_term")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderTerm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "term_value", precision = 21, scale = 2)
    private BigDecimal termValue;

    @Column(name = "term_days")
    private Integer termDays;

    @Column(name = "text_value")
    private String textValue;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderTerms", allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderTerms", allowSetters = true)
    private OrderItem item;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderTerms", allowSetters = true)
    private Term term;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderTerms", allowSetters = true)
    private OrderTermType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public OrderTerm sequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getName() {
        return name;
    }

    public OrderTerm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public OrderTerm detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getTermValue() {
        return termValue;
    }

    public OrderTerm termValue(BigDecimal termValue) {
        this.termValue = termValue;
        return this;
    }

    public void setTermValue(BigDecimal termValue) {
        this.termValue = termValue;
    }

    public Integer getTermDays() {
        return termDays;
    }

    public OrderTerm termDays(Integer termDays) {
        this.termDays = termDays;
        return this;
    }

    public void setTermDays(Integer termDays) {
        this.termDays = termDays;
    }

    public String getTextValue() {
        return textValue;
    }

    public OrderTerm textValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Order getOrder() {
        return order;
    }

    public OrderTerm order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getItem() {
        return item;
    }

    public OrderTerm item(OrderItem orderItem) {
        this.item = orderItem;
        return this;
    }

    public void setItem(OrderItem orderItem) {
        this.item = orderItem;
    }

    public Term getTerm() {
        return term;
    }

    public OrderTerm term(Term term) {
        this.term = term;
        return this;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public OrderTermType getType() {
        return type;
    }

    public OrderTerm type(OrderTermType orderTermType) {
        this.type = orderTermType;
        return this;
    }

    public void setType(OrderTermType orderTermType) {
        this.type = orderTermType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTerm)) {
            return false;
        }
        return id != null && id.equals(((OrderTerm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTerm{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", termValue=" + getTermValue() +
            ", termDays=" + getTermDays() +
            ", textValue='" + getTextValue() + "'" +
            "}";
    }
}
