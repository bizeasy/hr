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

import com.hr.domain.OrderContactMech;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.OrderContactMechRepository;
import com.hr.service.dto.OrderContactMechCriteria;

/**
 * Service for executing complex queries for {@link OrderContactMech} entities in the database.
 * The main input is a {@link OrderContactMechCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderContactMech} or a {@link Page} of {@link OrderContactMech} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderContactMechQueryService extends QueryService<OrderContactMech> {

    private final Logger log = LoggerFactory.getLogger(OrderContactMechQueryService.class);

    private final OrderContactMechRepository orderContactMechRepository;

    public OrderContactMechQueryService(OrderContactMechRepository orderContactMechRepository) {
        this.orderContactMechRepository = orderContactMechRepository;
    }

    /**
     * Return a {@link List} of {@link OrderContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderContactMech> findByCriteria(OrderContactMechCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderContactMech> specification = createSpecification(criteria);
        return orderContactMechRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OrderContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderContactMech> findByCriteria(OrderContactMechCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderContactMech> specification = createSpecification(criteria);
        return orderContactMechRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderContactMechCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderContactMech> specification = createSpecification(criteria);
        return orderContactMechRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderContactMechCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderContactMech> createSpecification(OrderContactMechCriteria criteria) {
        Specification<OrderContactMech> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderContactMech_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), OrderContactMech_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), OrderContactMech_.thruDate));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(OrderContactMech_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getContactMechId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechId(),
                    root -> root.join(OrderContactMech_.contactMech, JoinType.LEFT).get(ContactMech_.id)));
            }
            if (criteria.getContactMechPurposeId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechPurposeId(),
                    root -> root.join(OrderContactMech_.contactMechPurpose, JoinType.LEFT).get(ContactMechPurpose_.id)));
            }
        }
        return specification;
    }
}
