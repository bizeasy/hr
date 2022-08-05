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

import com.hr.domain.InventoryItem;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.InventoryItemRepository;
import com.hr.service.dto.InventoryItemCriteria;

/**
 * Service for executing complex queries for {@link InventoryItem} entities in the database.
 * The main input is a {@link InventoryItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryItem} or a {@link Page} of {@link InventoryItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryItemQueryService extends QueryService<InventoryItem> {

    private final Logger log = LoggerFactory.getLogger(InventoryItemQueryService.class);

    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemQueryService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    /**
     * Return a {@link List} of {@link InventoryItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryItem> findByCriteria(InventoryItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryItem> specification = createSpecification(criteria);
        return inventoryItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InventoryItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItem> findByCriteria(InventoryItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryItem> specification = createSpecification(criteria);
        return inventoryItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryItem> specification = createSpecification(criteria);
        return inventoryItemRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryItem> createSpecification(InventoryItemCriteria criteria) {
        Specification<InventoryItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryItem_.id));
            }
            if (criteria.getReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedDate(), InventoryItem_.receivedDate));
            }
            if (criteria.getManufacturedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getManufacturedDate(), InventoryItem_.manufacturedDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), InventoryItem_.expiryDate));
            }
            if (criteria.getRetestDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetestDate(), InventoryItem_.retestDate));
            }
            if (criteria.getContainerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContainerId(), InventoryItem_.containerId));
            }
            if (criteria.getBatchNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchNo(), InventoryItem_.batchNo));
            }
            if (criteria.getMfgBatchNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMfgBatchNo(), InventoryItem_.mfgBatchNo));
            }
            if (criteria.getLotNoStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLotNoStr(), InventoryItem_.lotNoStr));
            }
            if (criteria.getBinNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBinNumber(), InventoryItem_.binNumber));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), InventoryItem_.comments));
            }
            if (criteria.getQuantityOnHandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnHandTotal(), InventoryItem_.quantityOnHandTotal));
            }
            if (criteria.getAvailableToPromiseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailableToPromiseTotal(), InventoryItem_.availableToPromiseTotal));
            }
            if (criteria.getAccountingQuantityTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountingQuantityTotal(), InventoryItem_.accountingQuantityTotal));
            }
            if (criteria.getOldQuantityOnHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOldQuantityOnHand(), InventoryItem_.oldQuantityOnHand));
            }
            if (criteria.getOldAvailableToPromise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOldAvailableToPromise(), InventoryItem_.oldAvailableToPromise));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), InventoryItem_.serialNumber));
            }
            if (criteria.getSoftIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoftIdentifier(), InventoryItem_.softIdentifier));
            }
            if (criteria.getActivationNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationNumber(), InventoryItem_.activationNumber));
            }
            if (criteria.getActivationValidTrue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivationValidTrue(), InventoryItem_.activationValidTrue));
            }
            if (criteria.getUnitCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitCost(), InventoryItem_.unitCost));
            }
            if (criteria.getInventoryItemTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemTypeId(),
                    root -> root.join(InventoryItem_.inventoryItemType, JoinType.LEFT).get(InventoryItemType_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(InventoryItem_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(InventoryItem_.supplier, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getOwnerPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerPartyId(),
                    root -> root.join(InventoryItem_.ownerParty, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(InventoryItem_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(InventoryItem_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(InventoryItem_.uom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getCurrencyUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyUomId(),
                    root -> root.join(InventoryItem_.currencyUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getLotId() != null) {
                specification = specification.and(buildSpecification(criteria.getLotId(),
                    root -> root.join(InventoryItem_.lot, JoinType.LEFT).get(Lot_.id)));
            }
        }
        return specification;
    }
}
