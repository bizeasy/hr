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

import com.hr.domain.ProductFacility;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductFacilityRepository;
import com.hr.service.dto.ProductFacilityCriteria;

/**
 * Service for executing complex queries for {@link ProductFacility} entities in the database.
 * The main input is a {@link ProductFacilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductFacility} or a {@link Page} of {@link ProductFacility} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductFacilityQueryService extends QueryService<ProductFacility> {

    private final Logger log = LoggerFactory.getLogger(ProductFacilityQueryService.class);

    private final ProductFacilityRepository productFacilityRepository;

    public ProductFacilityQueryService(ProductFacilityRepository productFacilityRepository) {
        this.productFacilityRepository = productFacilityRepository;
    }

    /**
     * Return a {@link List} of {@link ProductFacility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductFacility> findByCriteria(ProductFacilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductFacility> specification = createSpecification(criteria);
        return productFacilityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductFacility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductFacility> findByCriteria(ProductFacilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductFacility> specification = createSpecification(criteria);
        return productFacilityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductFacilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductFacility> specification = createSpecification(criteria);
        return productFacilityRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductFacilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductFacility> createSpecification(ProductFacilityCriteria criteria) {
        Specification<ProductFacility> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductFacility_.id));
            }
            if (criteria.getMinimumStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinimumStock(), ProductFacility_.minimumStock));
            }
            if (criteria.getReorderQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReorderQty(), ProductFacility_.reorderQty));
            }
            if (criteria.getDaysToShip() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDaysToShip(), ProductFacility_.daysToShip));
            }
            if (criteria.getLastInventoryCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastInventoryCount(), ProductFacility_.lastInventoryCount));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductFacility_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(ProductFacility_.facility, JoinType.LEFT).get(Facility_.id)));
            }
        }
        return specification;
    }
}
