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

import com.hr.domain.ProductPrice;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductPriceRepository;
import com.hr.service.dto.ProductPriceCriteria;

/**
 * Service for executing complex queries for {@link ProductPrice} entities in the database.
 * The main input is a {@link ProductPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductPrice} or a {@link Page} of {@link ProductPrice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductPriceQueryService extends QueryService<ProductPrice> {

    private final Logger log = LoggerFactory.getLogger(ProductPriceQueryService.class);

    private final ProductPriceRepository productPriceRepository;

    public ProductPriceQueryService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    /**
     * Return a {@link List} of {@link ProductPrice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductPrice> findByCriteria(ProductPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductPrice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductPrice> findByCriteria(ProductPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductPrice> createSpecification(ProductPriceCriteria criteria) {
        Specification<ProductPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductPrice_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), ProductPrice_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), ProductPrice_.thruDate));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ProductPrice_.price));
            }
            if (criteria.getCgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgst(), ProductPrice_.cgst));
            }
            if (criteria.getIgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgst(), ProductPrice_.igst));
            }
            if (criteria.getSgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgst(), ProductPrice_.sgst));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), ProductPrice_.totalPrice));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductPrice_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getProductPriceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductPriceTypeId(),
                    root -> root.join(ProductPrice_.productPriceType, JoinType.LEFT).get(ProductPriceType_.id)));
            }
            if (criteria.getProductPricePurposeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductPricePurposeId(),
                    root -> root.join(ProductPrice_.productPricePurpose, JoinType.LEFT).get(ProductPricePurpose_.id)));
            }
            if (criteria.getCurrencyUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyUomId(),
                    root -> root.join(ProductPrice_.currencyUom, JoinType.LEFT).get(Uom_.id)));
            }
        }
        return specification;
    }
}
