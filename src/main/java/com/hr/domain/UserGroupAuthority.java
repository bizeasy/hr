package com.hr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserGroupAuthority.
 */
@Entity
@Table(name = "user_group_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserGroupAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userGroupAuthorities", allowSetters = true)
    private UserGroup userGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public UserGroupAuthority authority(String authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public UserGroupAuthority userGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        return this;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroupAuthority)) {
            return false;
        }
        return id != null && id.equals(((UserGroupAuthority) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroupAuthority{" +
            "id=" + getId() +
            ", authority='" + getAuthority() + "'" +
            "}";
    }
}
