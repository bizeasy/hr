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

import com.hr.domain.GeoPoint;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.GeoPointRepository;
import com.hr.service.dto.GeoPointCriteria;

/**
 * Service for executing complex queries for {@link GeoPoint} entities in the database.
 * The main input is a {@link GeoPointCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeoPoint} or a {@link Page} of {@link GeoPoint} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeoPointQueryService extends QueryService<GeoPoint> {

    private final Logger log = LoggerFactory.getLogger(GeoPointQueryService.class);

    private final GeoPointRepository geoPointRepository;

    public GeoPointQueryService(GeoPointRepository geoPointRepository) {
        this.geoPointRepository = geoPointRepository;
    }

    /**
     * Return a {@link List} of {@link GeoPoint} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeoPoint> findByCriteria(GeoPointCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeoPoint> specification = createSpecification(criteria);
        return geoPointRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GeoPoint} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeoPoint> findByCriteria(GeoPointCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeoPoint> specification = createSpecification(criteria);
        return geoPointRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeoPointCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GeoPoint> specification = createSpecification(criteria);
        return geoPointRepository.count(specification);
    }

    /**
     * Function to convert {@link GeoPointCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GeoPoint> createSpecification(GeoPointCriteria criteria) {
        Specification<GeoPoint> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GeoPoint_.id));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), GeoPoint_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), GeoPoint_.longitude));
            }
        }
        return specification;
    }
}
