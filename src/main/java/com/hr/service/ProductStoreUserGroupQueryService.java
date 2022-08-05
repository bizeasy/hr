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

import com.hr.domain.ProductStoreUserGroup;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductStoreUserGroupRepository;
import com.hr.service.dto.ProductStoreUserGroupCriteria;

/**
 * Service for executing complex queries for {@link ProductStoreUserGroup} entities in the database.
 * The main input is a {@link ProductStoreUserGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductStoreUserGroup} or a {@link Page} of {@link ProductStoreUserGroup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductStoreUserGroupQueryService extends QueryService<ProductStoreUserGroup> {

    private final Logger log = LoggerFactory.getLogger(ProductStoreUserGroupQueryService.class);

    private final ProductStoreUserGroupRepository productStoreUserGroupRepository;

    public ProductStoreUserGroupQueryService(ProductStoreUserGroupRepository productStoreUserGroupRepository) {
        this.productStoreUserGroupRepository = productStoreUserGroupRepository;
    }

    /**
     * Return a {@link List} of {@link ProductStoreUserGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductStoreUserGroup> findByCriteria(ProductStoreUserGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductStoreUserGroup> specification = createSpecification(criteria);
        return productStoreUserGroupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductStoreUserGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreUserGroup> findByCriteria(ProductStoreUserGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductStoreUserGroup> specification = createSpecification(criteria);
        return productStoreUserGroupRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductStoreUserGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductStoreUserGroup> specification = createSpecification(criteria);
        return productStoreUserGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductStoreUserGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductStoreUserGroup> createSpecification(ProductStoreUserGroupCriteria criteria) {
        Specification<ProductStoreUserGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductStoreUserGroup_.id));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(ProductStoreUserGroup_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ProductStoreUserGroup_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(ProductStoreUserGroup_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getProductStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductStoreId(),
                    root -> root.join(ProductStoreUserGroup_.productStore, JoinType.LEFT).get(ProductStore_.id)));
            }
        }
        return specification;
    }
}
