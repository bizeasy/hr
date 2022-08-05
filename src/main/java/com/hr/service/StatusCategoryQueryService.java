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

import com.hr.domain.StatusCategory;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.StatusCategoryRepository;
import com.hr.service.dto.StatusCategoryCriteria;

/**
 * Service for executing complex queries for {@link StatusCategory} entities in the database.
 * The main input is a {@link StatusCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusCategory} or a {@link Page} of {@link StatusCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusCategoryQueryService extends QueryService<StatusCategory> {

    private final Logger log = LoggerFactory.getLogger(StatusCategoryQueryService.class);

    private final StatusCategoryRepository statusCategoryRepository;

    public StatusCategoryQueryService(StatusCategoryRepository statusCategoryRepository) {
        this.statusCategoryRepository = statusCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link StatusCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusCategory> findByCriteria(StatusCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StatusCategory> specification = createSpecification(criteria);
        return statusCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StatusCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusCategory> findByCriteria(StatusCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatusCategory> specification = createSpecification(criteria);
        return statusCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StatusCategory> specification = createSpecification(criteria);
        return statusCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link StatusCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatusCategory> createSpecification(StatusCategoryCriteria criteria) {
        Specification<StatusCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatusCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StatusCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), StatusCategory_.description));
            }
        }
        return specification;
    }
}
