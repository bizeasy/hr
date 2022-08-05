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

import com.hr.domain.Reason;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ReasonRepository;
import com.hr.service.dto.ReasonCriteria;

/**
 * Service for executing complex queries for {@link Reason} entities in the database.
 * The main input is a {@link ReasonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Reason} or a {@link Page} of {@link Reason} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReasonQueryService extends QueryService<Reason> {

    private final Logger log = LoggerFactory.getLogger(ReasonQueryService.class);

    private final ReasonRepository reasonRepository;

    public ReasonQueryService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    /**
     * Return a {@link List} of {@link Reason} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Reason> findByCriteria(ReasonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reason> specification = createSpecification(criteria);
        return reasonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Reason} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Reason> findByCriteria(ReasonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reason> specification = createSpecification(criteria);
        return reasonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReasonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reason> specification = createSpecification(criteria);
        return reasonRepository.count(specification);
    }

    /**
     * Function to convert {@link ReasonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reason> createSpecification(ReasonCriteria criteria) {
        Specification<Reason> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reason_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Reason_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Reason_.description));
            }
            if (criteria.getReasonTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getReasonTypeId(),
                    root -> root.join(Reason_.reasonType, JoinType.LEFT).get(ReasonType_.id)));
            }
        }
        return specification;
    }
}
