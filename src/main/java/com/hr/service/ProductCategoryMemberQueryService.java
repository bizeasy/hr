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

import com.hr.domain.ProductCategoryMember;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductCategoryMemberRepository;
import com.hr.service.dto.ProductCategoryMemberCriteria;

/**
 * Service for executing complex queries for {@link ProductCategoryMember} entities in the database.
 * The main input is a {@link ProductCategoryMemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductCategoryMember} or a {@link Page} of {@link ProductCategoryMember} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryMemberQueryService extends QueryService<ProductCategoryMember> {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryMemberQueryService.class);

    private final ProductCategoryMemberRepository productCategoryMemberRepository;

    public ProductCategoryMemberQueryService(ProductCategoryMemberRepository productCategoryMemberRepository) {
        this.productCategoryMemberRepository = productCategoryMemberRepository;
    }

    /**
     * Return a {@link List} of {@link ProductCategoryMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductCategoryMember> findByCriteria(ProductCategoryMemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductCategoryMember> specification = createSpecification(criteria);
        return productCategoryMemberRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductCategoryMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryMember> findByCriteria(ProductCategoryMemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductCategoryMember> specification = createSpecification(criteria);
        return productCategoryMemberRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCategoryMemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductCategoryMember> specification = createSpecification(criteria);
        return productCategoryMemberRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCategoryMemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductCategoryMember> createSpecification(ProductCategoryMemberCriteria criteria) {
        Specification<ProductCategoryMember> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductCategoryMember_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), ProductCategoryMember_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), ProductCategoryMember_.thruDate));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), ProductCategoryMember_.sequenceNo));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductCategoryMember_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductCategoryMember_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
