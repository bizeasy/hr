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

import com.hr.domain.GeoAssoc;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.GeoAssocRepository;
import com.hr.service.dto.GeoAssocCriteria;

/**
 * Service for executing complex queries for {@link GeoAssoc} entities in the database.
 * The main input is a {@link GeoAssocCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeoAssoc} or a {@link Page} of {@link GeoAssoc} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeoAssocQueryService extends QueryService<GeoAssoc> {

    private final Logger log = LoggerFactory.getLogger(GeoAssocQueryService.class);

    private final GeoAssocRepository geoAssocRepository;

    public GeoAssocQueryService(GeoAssocRepository geoAssocRepository) {
        this.geoAssocRepository = geoAssocRepository;
    }

    /**
     * Return a {@link List} of {@link GeoAssoc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeoAssoc> findByCriteria(GeoAssocCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeoAssoc> specification = createSpecification(criteria);
        return geoAssocRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GeoAssoc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeoAssoc> findByCriteria(GeoAssocCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeoAssoc> specification = createSpecification(criteria);
        return geoAssocRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeoAssocCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GeoAssoc> specification = createSpecification(criteria);
        return geoAssocRepository.count(specification);
    }

    /**
     * Function to convert {@link GeoAssocCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GeoAssoc> createSpecification(GeoAssocCriteria criteria) {
        Specification<GeoAssoc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GeoAssoc_.id));
            }
            if (criteria.getGeoId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoId(),
                    root -> root.join(GeoAssoc_.geo, JoinType.LEFT).get(Geo_.id)));
            }
            if (criteria.getGeoToId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoToId(),
                    root -> root.join(GeoAssoc_.geoTo, JoinType.LEFT).get(Geo_.id)));
            }
            if (criteria.getGeoAssocTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoAssocTypeId(),
                    root -> root.join(GeoAssoc_.geoAssocType, JoinType.LEFT).get(GeoAssocType_.id)));
            }
        }
        return specification;
    }
}
