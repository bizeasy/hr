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

import com.hr.domain.ProductStore;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductStoreRepository;
import com.hr.service.dto.ProductStoreCriteria;

/**
 * Service for executing complex queries for {@link ProductStore} entities in the database.
 * The main input is a {@link ProductStoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductStore} or a {@link Page} of {@link ProductStore} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductStoreQueryService extends QueryService<ProductStore> {

    private final Logger log = LoggerFactory.getLogger(ProductStoreQueryService.class);

    private final ProductStoreRepository productStoreRepository;

    public ProductStoreQueryService(ProductStoreRepository productStoreRepository) {
        this.productStoreRepository = productStoreRepository;
    }

    /**
     * Return a {@link List} of {@link ProductStore} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductStore> findByCriteria(ProductStoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductStore> specification = createSpecification(criteria);
        return productStoreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductStore} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStore> findByCriteria(ProductStoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductStore> specification = createSpecification(criteria);
        return productStoreRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductStoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductStore> specification = createSpecification(criteria);
        return productStoreRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductStoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductStore> createSpecification(ProductStoreCriteria criteria) {
        Specification<ProductStore> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductStore_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductStore_.name));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ProductStore_.title));
            }
            if (criteria.getSubtitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubtitle(), ProductStore_.subtitle));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), ProductStore_.imageUrl));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ProductStore_.comments));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProductStore_.code));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(ProductStore_.type, JoinType.LEFT).get(ProductStoreType_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ProductStore_.parent, JoinType.LEFT).get(ProductStore_.id)));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(ProductStore_.owner, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getPostalAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostalAddressId(),
                    root -> root.join(ProductStore_.postalAddress, JoinType.LEFT).get(PostalAddress_.id)));
            }
        }
        return specification;
    }
}
