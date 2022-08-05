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

import com.hr.domain.Payment;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PaymentRepository;
import com.hr.service.dto.PaymentCriteria;

/**
 * Service for executing complex queries for {@link Payment} entities in the database.
 * The main input is a {@link PaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Payment} or a {@link Page} of {@link Payment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryService extends QueryService<Payment> {

    private final Logger log = LoggerFactory.getLogger(PaymentQueryService.class);

    private final PaymentRepository paymentRepository;

    public PaymentQueryService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Return a {@link List} of {@link Payment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Payment> findByCriteria(PaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Payment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Payment> findByCriteria(PaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Payment> createSpecification(PaymentCriteria criteria) {
        Specification<Payment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Payment_.id));
            }
            if (criteria.getEffectiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectiveDate(), Payment_.effectiveDate));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), Payment_.paymentDate));
            }
            if (criteria.getPaymentRefNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentRefNumber(), Payment_.paymentRefNumber));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Payment_.amount));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentStatus(), Payment_.paymentStatus));
            }
            if (criteria.getMihpayId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMihpayId(), Payment_.mihpayId));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Payment_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Payment_.phone));
            }
            if (criteria.getProductInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductInfo(), Payment_.productInfo));
            }
            if (criteria.getTxnId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTxnId(), Payment_.txnId));
            }
            if (criteria.getActualAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualAmount(), Payment_.actualAmount));
            }
            if (criteria.getPaymentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentTypeId(),
                    root -> root.join(Payment_.paymentType, JoinType.LEFT).get(PaymentType_.id)));
            }
            if (criteria.getPaymentMethodTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethodTypeId(),
                    root -> root.join(Payment_.paymentMethodType, JoinType.LEFT).get(PaymentMethodType_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(Payment_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getPaymentMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethodId(),
                    root -> root.join(Payment_.paymentMethod, JoinType.LEFT).get(PaymentMethod_.id)));
            }
            if (criteria.getPaymentGatewayResponseId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentGatewayResponseId(),
                    root -> root.join(Payment_.paymentGatewayResponse, JoinType.LEFT).get(PaymentGatewayResponse_.id)));
            }
            if (criteria.getPartyIdFromId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyIdFromId(),
                    root -> root.join(Payment_.partyIdFrom, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getPartyIdToId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyIdToId(),
                    root -> root.join(Payment_.partyIdTo, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleTypeId(),
                    root -> root.join(Payment_.roleType, JoinType.LEFT).get(RoleType_.id)));
            }
            if (criteria.getCurrencyUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyUomId(),
                    root -> root.join(Payment_.currencyUom, JoinType.LEFT).get(Uom_.id)));
            }
        }
        return specification;
    }
}
