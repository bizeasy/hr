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

import com.hr.domain.Facility;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.FacilityRepository;
import com.hr.service.dto.FacilityCriteria;

/**
 * Service for executing complex queries for {@link Facility} entities in the database.
 * The main input is a {@link FacilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Facility} or a {@link Page} of {@link Facility} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacilityQueryService extends QueryService<Facility> {

    private final Logger log = LoggerFactory.getLogger(FacilityQueryService.class);

    private final FacilityRepository facilityRepository;

    public FacilityQueryService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    /**
     * Return a {@link List} of {@link Facility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Facility> findByCriteria(FacilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Facility> specification = createSpecification(criteria);
        return facilityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Facility} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Facility> findByCriteria(FacilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Facility> specification = createSpecification(criteria);
        return facilityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Facility> specification = createSpecification(criteria);
        return facilityRepository.count(specification);
    }

    /**
     * Function to convert {@link FacilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Facility> createSpecification(FacilityCriteria criteria) {
        Specification<Facility> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Facility_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Facility_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Facility_.description));
            }
            if (criteria.getFacilityCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacilityCode(), Facility_.facilityCode));
            }
            if (criteria.getFacilitySize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFacilitySize(), Facility_.facilitySize));
            }
            if (criteria.getCostCenterCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCostCenterCode(), Facility_.costCenterCode));
            }
            if (criteria.getFacilityTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityTypeId(),
                    root -> root.join(Facility_.facilityType, JoinType.LEFT).get(FacilityType_.id)));
            }
            if (criteria.getParentFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentFacilityId(),
                    root -> root.join(Facility_.parentFacility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getOwnerPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerPartyId(),
                    root -> root.join(Facility_.ownerParty, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getFacilityGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityGroupId(),
                    root -> root.join(Facility_.facilityGroup, JoinType.LEFT).get(FacilityGroup_.id)));
            }
            if (criteria.getSizeUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getSizeUomId(),
                    root -> root.join(Facility_.sizeUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getGeoPointId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoPointId(),
                    root -> root.join(Facility_.geoPoint, JoinType.LEFT).get(GeoPoint_.id)));
            }
            if (criteria.getInventoryItemTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemTypeId(),
                    root -> root.join(Facility_.inventoryItemType, JoinType.LEFT).get(InventoryItemType_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(Facility_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getUsageStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsageStatusId(),
                    root -> root.join(Facility_.usageStatus, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getReviewedById() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewedById(),
                    root -> root.join(Facility_.reviewedBy, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getApprovedById() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovedById(),
                    root -> root.join(Facility_.approvedBy, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
