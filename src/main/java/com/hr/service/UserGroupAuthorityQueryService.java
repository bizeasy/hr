package com.hr.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hr.domain.UserGroupAuthority;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.UserGroupAuthorityRepository;
import com.hr.service.dto.UserGroupAuthorityCriteria;

/**
 * Service for executing complex queries for {@link UserGroupAuthority} entities in the database.
 * The main input is a {@link UserGroupAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserGroupAuthority} or a {@link Page} of {@link UserGroupAuthority} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserGroupAuthorityQueryService extends QueryService<UserGroupAuthority> {

    private final Logger log = LoggerFactory.getLogger(UserGroupAuthorityQueryService.class);

    private final UserGroupAuthorityRepository userGroupAuthorityRepository;

    public UserGroupAuthorityQueryService(UserGroupAuthorityRepository userGroupAuthorityRepository) {
        this.userGroupAuthorityRepository = userGroupAuthorityRepository;
    }

    /**
     * Return a {@link List} of {@link UserGroupAuthority} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGroupAuthority> findByCriteria(UserGroupAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserGroupAuthority> specification = createSpecification(criteria);
        return userGroupAuthorityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserGroupAuthority} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGroupAuthority> findByCriteria(UserGroupAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserGroupAuthority> specification = createSpecification(criteria);
        return userGroupAuthorityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserGroupAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserGroupAuthority> specification = createSpecification(criteria);
        return userGroupAuthorityRepository.count(specification);
    }

    /**
     * Function to convert {@link UserGroupAuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserGroupAuthority> createSpecification(UserGroupAuthorityCriteria criteria) {
        Specification<UserGroupAuthority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserGroupAuthority_.id));
            }
            if (criteria.getAuthority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthority(), UserGroupAuthority_.authority));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(UserGroupAuthority_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
        }
        return specification;
    }
}
