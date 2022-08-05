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

import com.hr.domain.InventoryTransfer;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.InventoryTransferRepository;
import com.hr.service.dto.InventoryTransferCriteria;

/**
 * Service for executing complex queries for {@link InventoryTransfer} entities in the database.
 * The main input is a {@link InventoryTransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryTransfer} or a {@link Page} of {@link InventoryTransfer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryTransferQueryService extends QueryService<InventoryTransfer> {

    private final Logger log = LoggerFactory.getLogger(InventoryTransferQueryService.class);

    private final InventoryTransferRepository inventoryTransferRepository;

    public InventoryTransferQueryService(InventoryTransferRepository inventoryTransferRepository) {
        this.inventoryTransferRepository = inventoryTransferRepository;
    }

    /**
     * Return a {@link List} of {@link InventoryTransfer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryTransfer> findByCriteria(InventoryTransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryTransfer> specification = createSpecification(criteria);
        return inventoryTransferRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InventoryTransfer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryTransfer> findByCriteria(InventoryTransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryTransfer> specification = createSpecification(criteria);
        return inventoryTransferRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryTransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryTransfer> specification = createSpecification(criteria);
        return inventoryTransferRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryTransferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryTransfer> createSpecification(InventoryTransferCriteria criteria) {
        Specification<InventoryTransfer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryTransfer_.id));
            }
            if (criteria.getSentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSentDate(), InventoryTransfer_.sentDate));
            }
            if (criteria.getReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedDate(), InventoryTransfer_.receivedDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), InventoryTransfer_.comments));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(InventoryTransfer_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(InventoryTransfer_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(InventoryTransfer_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getFacilityToId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityToId(),
                    root -> root.join(InventoryTransfer_.facilityTo, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getIssuanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getIssuanceId(),
                    root -> root.join(InventoryTransfer_.issuance, JoinType.LEFT).get(ItemIssuance_.id)));
            }
        }
        return specification;
    }
}
