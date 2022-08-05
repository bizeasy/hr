package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductStoreUserGroup.
 */
@Entity
@Table(name = "product_store_user_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductStoreUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStoreUserGroups", allowSetters = true)
    private UserGroup userGroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStoreUserGroups", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "productStoreUserGroups", allowSetters = true)
    private Party party;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productStoreUserGroups", allowSetters = true)
    private ProductStore productStore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public ProductStoreUserGroup userGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        return this;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public User getUser() {
        return user;
    }

    public ProductStoreUserGroup user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Party getParty() {
        return party;
    }

    public ProductStoreUserGroup party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    public ProductStoreUserGroup productStore(ProductStore productStore) {
        this.productStore = productStore;
        return this;
    }

    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStoreUserGroup)) {
            return false;
        }
        return id != null && id.equals(((ProductStoreUserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreUserGroup{" +
            "id=" + getId() +
            "}";
    }
}
