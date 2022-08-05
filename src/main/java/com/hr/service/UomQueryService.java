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

import com.hr.domain.Uom;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.UomRepository;
import com.hr.service.dto.UomCriteria;

/**
 * Service for executing complex queries for {@link Uom} entities in the database.
 * The main input is a {@link UomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Uom} or a {@link Page} of {@link Uom} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UomQueryService extends QueryService<Uom> {

    private final Logger log = LoggerFactory.getLogger(UomQueryService.class);

    private final UomRepository uomRepository;

    public UomQueryService(UomRepository uomRepository) {
        this.uomRepository = uomRepository;
    }

    /**
     * Return a {@link List} of {@link Uom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Uom> findByCriteria(UomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Uom> specification = createSpecification(criteria);
        return uomRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Uom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Uom> findByCriteria(UomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Uom> specification = createSpecification(criteria);
        return uomRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Uom> specification = createSpecification(criteria);
        return uomRepository.count(specification);
    }

    /**
     * Function to convert {@link UomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Uom> createSpecification(UomCriteria criteria) {
        Specification<Uom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Uom_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Uom_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Uom_.description));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), Uom_.sequenceNo));
            }
            if (criteria.getAbbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbbreviation(), Uom_.abbreviation));
            }
            if (criteria.getUomTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomTypeId(),
                    root -> root.join(Uom_.uomType, JoinType.LEFT).get(UomType_.id)));
            }
        }
        return specification;
    }
}
