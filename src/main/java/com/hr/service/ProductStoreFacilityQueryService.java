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

import com.hr.domain.ProductStoreFacility;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductStoreFacilityRepository;
import com.hr.service.dto.ProductStoreFacilityCriteria;

/**
 * Service for executing complex queries for {@link ProductStoreFacility} entities in the database.
 * The main input is a {@link ProductStoreFacilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductStoreFacility} or a {@link Page} of {@link ProductStoreFacility} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductStoreFacilityQueryService extends QueryService<ProductStoreFacility> {

    private final Logger log = LoggerFactory.getLogger(ProductStoreFacilityQueryService.class);

    private final ProductStoreFacilityRepository productStoreFacilityRepository;

    public ProductStoreFacilityQueryService(ProductStoreFacilityRepository productStoreFacilityRepository) {
        this.productStoreFacilityRepository = productStoreFacilityRepository;
    }

    /**
     * Return a {@link List} of {@link ProductStoreFacility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductStoreFacility> findByCriteria(ProductStoreFacilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductStoreFacility> specification = createSpecification(criteria);
        return productStoreFacilityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductStoreFacility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreFacility> findByCriteria(ProductStoreFacilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductStoreFacility> specification = createSpecification(criteria);
        return productStoreFacilityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductStoreFacilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductStoreFacility> specification = createSpecification(criteria);
        return productStoreFacilityRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductStoreFacilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductStoreFacility> createSpecification(ProductStoreFacilityCriteria criteria) {
        Specification<ProductStoreFacility> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductStoreFacility_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), ProductStoreFacility_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), ProductStoreFacility_.thruDate));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), ProductStoreFacility_.sequenceNo));
            }
            if (criteria.getProductStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductStoreId(),
                    root -> root.join(ProductStoreFacility_.productStore, JoinType.LEFT).get(ProductStore_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(ProductStoreFacility_.facility, JoinType.LEFT).get(Facility_.id)));
            }
        }
        return specification;
    }
}
