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

import com.hr.domain.StatusValidChange;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.StatusValidChangeRepository;
import com.hr.service.dto.StatusValidChangeCriteria;

/**
 * Service for executing complex queries for {@link StatusValidChange} entities in the database.
 * The main input is a {@link StatusValidChangeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusValidChange} or a {@link Page} of {@link StatusValidChange} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusValidChangeQueryService extends QueryService<StatusValidChange> {

    private final Logger log = LoggerFactory.getLogger(StatusValidChangeQueryService.class);

    private final StatusValidChangeRepository statusValidChangeRepository;

    public StatusValidChangeQueryService(StatusValidChangeRepository statusValidChangeRepository) {
        this.statusValidChangeRepository = statusValidChangeRepository;
    }

    /**
     * Return a {@link List} of {@link StatusValidChange} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusValidChange> findByCriteria(StatusValidChangeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StatusValidChange> specification = createSpecification(criteria);
        return statusValidChangeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StatusValidChange} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusValidChange> findByCriteria(StatusValidChangeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatusValidChange> specification = createSpecification(criteria);
        return statusValidChangeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusValidChangeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StatusValidChange> specification = createSpecification(criteria);
        return statusValidChangeRepository.count(specification);
    }

    /**
     * Function to convert {@link StatusValidChangeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatusValidChange> createSpecification(StatusValidChangeCriteria criteria) {
        Specification<StatusValidChange> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatusValidChange_.id));
            }
            if (criteria.getTransitionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransitionName(), StatusValidChange_.transitionName));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(StatusValidChange_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getStatusToId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusToId(),
                    root -> root.join(StatusValidChange_.statusTo, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
