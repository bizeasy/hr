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

import com.hr.domain.PermissionAuthority;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PermissionAuthorityRepository;
import com.hr.service.dto.PermissionAuthorityCriteria;

/**
 * Service for executing complex queries for {@link PermissionAuthority} entities in the database.
 * The main input is a {@link PermissionAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PermissionAuthority} or a {@link Page} of {@link PermissionAuthority} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermissionAuthorityQueryService extends QueryService<PermissionAuthority> {

    private final Logger log = LoggerFactory.getLogger(PermissionAuthorityQueryService.class);

    private final PermissionAuthorityRepository permissionAuthorityRepository;

    public PermissionAuthorityQueryService(PermissionAuthorityRepository permissionAuthorityRepository) {
        this.permissionAuthorityRepository = permissionAuthorityRepository;
    }

    /**
     * Return a {@link List} of {@link PermissionAuthority} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PermissionAuthority> findByCriteria(PermissionAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PermissionAuthority> specification = createSpecification(criteria);
        return permissionAuthorityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PermissionAuthority} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermissionAuthority> findByCriteria(PermissionAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PermissionAuthority> specification = createSpecification(criteria);
        return permissionAuthorityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermissionAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PermissionAuthority> specification = createSpecification(criteria);
        return permissionAuthorityRepository.count(specification);
    }

    /**
     * Function to convert {@link PermissionAuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PermissionAuthority> createSpecification(PermissionAuthorityCriteria criteria) {
        Specification<PermissionAuthority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PermissionAuthority_.id));
            }
            if (criteria.getAuthority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthority(), PermissionAuthority_.authority));
            }
        }
        return specification;
    }
}
