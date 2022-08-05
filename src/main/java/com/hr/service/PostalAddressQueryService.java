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

import com.hr.domain.PostalAddress;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PostalAddressRepository;
import com.hr.service.dto.PostalAddressCriteria;

/**
 * Service for executing complex queries for {@link PostalAddress} entities in the database.
 * The main input is a {@link PostalAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostalAddress} or a {@link Page} of {@link PostalAddress} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostalAddressQueryService extends QueryService<PostalAddress> {

    private final Logger log = LoggerFactory.getLogger(PostalAddressQueryService.class);

    private final PostalAddressRepository postalAddressRepository;

    public PostalAddressQueryService(PostalAddressRepository postalAddressRepository) {
        this.postalAddressRepository = postalAddressRepository;
    }

    /**
     * Return a {@link List} of {@link PostalAddress} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostalAddress> findByCriteria(PostalAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostalAddress> specification = createSpecification(criteria);
        return postalAddressRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PostalAddress} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostalAddress> findByCriteria(PostalAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostalAddress> specification = createSpecification(criteria);
        return postalAddressRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostalAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostalAddress> specification = createSpecification(criteria);
        return postalAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link PostalAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PostalAddress> createSpecification(PostalAddressCriteria criteria) {
        Specification<PostalAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostalAddress_.id));
            }
            if (criteria.getToName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToName(), PostalAddress_.toName));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), PostalAddress_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), PostalAddress_.address2));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), PostalAddress_.city));
            }
            if (criteria.getLandmark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandmark(), PostalAddress_.landmark));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), PostalAddress_.postalCode));
            }
            if (criteria.getIsDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDefault(), PostalAddress_.isDefault));
            }
            if (criteria.getCustomAddressType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomAddressType(), PostalAddress_.customAddressType));
            }
            if (criteria.getStateStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateStr(), PostalAddress_.stateStr));
            }
            if (criteria.getCountryStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryStr(), PostalAddress_.countryStr));
            }
            if (criteria.getIsIndianAddress() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIndianAddress(), PostalAddress_.isIndianAddress));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), PostalAddress_.note));
            }
            if (criteria.getDirections() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirections(), PostalAddress_.directions));
            }
            if (criteria.getStateId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateId(),
                    root -> root.join(PostalAddress_.state, JoinType.LEFT).get(Geo_.id)));
            }
            if (criteria.getPincodeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPincodeId(),
                    root -> root.join(PostalAddress_.pincode, JoinType.LEFT).get(Geo_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(PostalAddress_.country, JoinType.LEFT).get(Geo_.id)));
            }
            if (criteria.getContactMechId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechId(),
                    root -> root.join(PostalAddress_.contactMech, JoinType.LEFT).get(ContactMech_.id)));
            }
            if (criteria.getGeoPointId() != null) {
                specification = specification.and(buildSpecification(criteria.getGeoPointId(),
                    root -> root.join(PostalAddress_.geoPoint, JoinType.LEFT).get(GeoPoint_.id)));
            }
        }
        return specification;
    }
}
