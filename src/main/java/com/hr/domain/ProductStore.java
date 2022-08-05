package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductStore.
 */
@Entity
@Table(name = "product_store")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "comments")
    private String comments;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStores", allowSetters = true)
    private ProductStoreType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStores", allowSetters = true)
    private ProductStore parent;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStores", allowSetters = true)
    private Party owner;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStores", allowSetters = true)
    private PostalAddress postalAddress;

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

    public ProductStore name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public ProductStore title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ProductStore subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductStore imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComments() {
        return comments;
    }

    public ProductStore comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCode() {
        return code;
    }

    public ProductStore code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProductStoreType getType() {
        return type;
    }

    public ProductStore type(ProductStoreType productStoreType) {
        this.type = productStoreType;
        return this;
    }

    public void setType(ProductStoreType productStoreType) {
        this.type = productStoreType;
    }

    public ProductStore getParent() {
        return parent;
    }

    public ProductStore parent(ProductStore productStore) {
        this.parent = productStore;
        return this;
    }

    public void setParent(ProductStore productStore) {
        this.parent = productStore;
    }

    public Party getOwner() {
        return owner;
    }

    public ProductStore owner(Party party) {
        this.owner = party;
        return this;
    }

    public void setOwner(Party party) {
        this.owner = party;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public ProductStore postalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStore)) {
            return false;
        }
        return id != null && id.equals(((ProductStore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStore{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", subtitle='" + getSubtitle() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", comments='" + getComments() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
