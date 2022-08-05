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

import com.hr.domain.Geo;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.GeoRepository;
import com.hr.service.dto.GeoCriteria;

/**
 * Service for executing complex queries for {@link Geo} entities in the database.
 * The main input is a {@link GeoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Geo} or a {@link Page} of {@link Geo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeoQueryService extends QueryService<Geo> {

    private final Logger log = LoggerFactory.getLogger(GeoQueryService.class);

    private final GeoRepository geoRepository;

    public GeoQueryService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    /**
     * Return a {@link List} of {@link Geo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Geo> findByCriteria(GeoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Geo> specification = createSpecification(criteria);
        return geoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Geo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Geo> findByCriteria(GeoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Geo> specification = createSpecification(criteria);
        return geoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Geo> specification = createSpecification(criteria);
        return geoRepository.count(specification);
    }

    /**
     * Function to convert {@link GeoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Geo> createSpecification(GeoCriteria criteria) {
        Specification<Geo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Geo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Geo_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Geo_.code));
            }
            if (criteria.getAbbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbbreviation(), Geo_.abbreviation));
            }
            if (criteria.getGeoTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoTypeId(),
                    root -> root.join(Geo_.geoType, JoinType.LEFT).get(GeoType_.id)));
            }
        }
        return specification;
    }
}
