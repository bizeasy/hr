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

import com.hr.domain.Invoice;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.InvoiceRepository;
import com.hr.service.dto.InvoiceCriteria;

/**
 * Service for executing complex queries for {@link Invoice} entities in the database.
 * The main input is a {@link InvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Invoice} or a {@link Page} of {@link Invoice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceQueryService extends QueryService<Invoice> {

    private final Logger log = LoggerFactory.getLogger(InvoiceQueryService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Return a {@link List} of {@link Invoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Invoice> findByCriteria(InvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Invoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Invoice> findByCriteria(InvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoice> createSpecification(InvoiceCriteria criteria) {
        Specification<Invoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoice_.id));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Invoice_.invoiceDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Invoice_.dueDate));
            }
            if (criteria.getPaidDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidDate(), Invoice_.paidDate));
            }
            if (criteria.getInvoiceMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceMessage(), Invoice_.invoiceMessage));
            }
            if (criteria.getReferenceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceNumber(), Invoice_.referenceNumber));
            }
            if (criteria.getInvoiceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceTypeId(),
                    root -> root.join(Invoice_.invoiceType, JoinType.LEFT).get(InvoiceType_.id)));
            }
            if (criteria.getPartyIdFromId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyIdFromId(),
                    root -> root.join(Invoice_.partyIdFrom, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getPartyIdToId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyIdToId(),
                    root -> root.join(Invoice_.partyIdTo, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleTypeId(),
                    root -> root.join(Invoice_.roleType, JoinType.LEFT).get(RoleType_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(Invoice_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getContactMechId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechId(),
                    root -> root.join(Invoice_.contactMech, JoinType.LEFT).get(ContactMech_.id)));
            }
        }
        return specification;
    }
}
