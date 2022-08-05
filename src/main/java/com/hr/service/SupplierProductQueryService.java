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

import com.hr.domain.SupplierProduct;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.SupplierProductRepository;
import com.hr.service.dto.SupplierProductCriteria;

/**
 * Service for executing complex queries for {@link SupplierProduct} entities in the database.
 * The main input is a {@link SupplierProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierProduct} or a {@link Page} of {@link SupplierProduct} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierProductQueryService extends QueryService<SupplierProduct> {

    private final Logger log = LoggerFactory.getLogger(SupplierProductQueryService.class);

    private final SupplierProductRepository supplierProductRepository;

    public SupplierProductQueryService(SupplierProductRepository supplierProductRepository) {
        this.supplierProductRepository = supplierProductRepository;
    }

    /**
     * Return a {@link List} of {@link SupplierProduct} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierProduct> findByCriteria(SupplierProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierProduct> specification = createSpecification(criteria);
        return supplierProductRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SupplierProduct} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierProduct> findByCriteria(SupplierProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierProduct> specification = createSpecification(criteria);
        return supplierProductRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierProduct> specification = createSpecification(criteria);
        return supplierProductRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierProduct> createSpecification(SupplierProductCriteria criteria) {
        Specification<SupplierProduct> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierProduct_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), SupplierProduct_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), SupplierProduct_.thruDate));
            }
            if (criteria.getMinOrderQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinOrderQty(), SupplierProduct_.minOrderQty));
            }
            if (criteria.getLastPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPrice(), SupplierProduct_.lastPrice));
            }
            if (criteria.getShippingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShippingPrice(), SupplierProduct_.shippingPrice));
            }
            if (criteria.getSupplierProductId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierProductId(), SupplierProduct_.supplierProductId));
            }
            if (criteria.getLeadTimeDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeadTimeDays(), SupplierProduct_.leadTimeDays));
            }
            if (criteria.getCgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgst(), SupplierProduct_.cgst));
            }
            if (criteria.getIgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgst(), SupplierProduct_.igst));
            }
            if (criteria.getSgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgst(), SupplierProduct_.sgst));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), SupplierProduct_.totalPrice));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), SupplierProduct_.comments));
            }
            if (criteria.getSupplierProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierProductName(), SupplierProduct_.supplierProductName));
            }
            if (criteria.getManufacturerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManufacturerName(), SupplierProduct_.manufacturerName));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(SupplierProduct_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(SupplierProduct_.supplier, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getQuantityUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuantityUomId(),
                    root -> root.join(SupplierProduct_.quantityUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getCurrencyUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyUomId(),
                    root -> root.join(SupplierProduct_.currencyUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getManufacturerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManufacturerId(),
                    root -> root.join(SupplierProduct_.manufacturer, JoinType.LEFT).get(Party_.id)));
            }
        }
        return specification;
    }
}
