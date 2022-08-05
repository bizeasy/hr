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

import com.hr.domain.Party;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PartyRepository;
import com.hr.service.dto.PartyCriteria;

/**
 * Service for executing complex queries for {@link Party} entities in the database.
 * The main input is a {@link PartyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Party} or a {@link Page} of {@link Party} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartyQueryService extends QueryService<Party> {

    private final Logger log = LoggerFactory.getLogger(PartyQueryService.class);

    private final PartyRepository partyRepository;

    public PartyQueryService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * Return a {@link List} of {@link Party} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Party> findByCriteria(PartyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Party> specification = createSpecification(criteria);
        return partyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Party} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Party> findByCriteria(PartyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Party> specification = createSpecification(criteria);
        return partyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Party> specification = createSpecification(criteria);
        return partyRepository.count(specification);
    }

    /**
     * Function to convert {@link PartyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Party> createSpecification(PartyCriteria criteria) {
        Specification<Party> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Party_.id));
            }
            if (criteria.getIsOrganisation() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOrganisation(), Party_.isOrganisation));
            }
            if (criteria.getOrganisationName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganisationName(), Party_.organisationName));
            }
            if (criteria.getOrganisationShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganisationShortName(), Party_.organisationShortName));
            }
            if (criteria.getSalutation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalutation(), Party_.salutation));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Party_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Party_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Party_.lastName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Party_.gender));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Party_.birthDate));
            }
            if (criteria.getPrimaryPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimaryPhone(), Party_.primaryPhone));
            }
            if (criteria.getPrimaryEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimaryEmail(), Party_.primaryEmail));
            }
            if (criteria.getIsTemporaryPassword() != null) {
                specification = specification.and(buildSpecification(criteria.getIsTemporaryPassword(), Party_.isTemporaryPassword));
            }
            if (criteria.getLogoImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogoImageUrl(), Party_.logoImageUrl));
            }
            if (criteria.getProfileImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfileImageUrl(), Party_.profileImageUrl));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Party_.notes));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), Party_.birthdate));
            }
            if (criteria.getDateOfJoining() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfJoining(), Party_.dateOfJoining));
            }
            if (criteria.getTrainingCompletedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrainingCompletedDate(), Party_.trainingCompletedDate));
            }
            if (criteria.getJdApprovedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJdApprovedOn(), Party_.jdApprovedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeId(), Party_.employeeId));
            }
            if (criteria.getTanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTanNo(), Party_.tanNo));
            }
            if (criteria.getPanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanNo(), Party_.panNo));
            }
            if (criteria.getGstNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstNo(), Party_.gstNo));
            }
            if (criteria.getAadharNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNo(), Party_.aadharNo));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Party_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPartyTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyTypeId(),
                    root -> root.join(Party_.partyType, JoinType.LEFT).get(PartyType_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(Party_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
