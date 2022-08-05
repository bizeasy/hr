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

import com.hr.domain.PartyRole;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PartyRoleRepository;
import com.hr.service.dto.PartyRoleCriteria;

/**
 * Service for executing complex queries for {@link PartyRole} entities in the database.
 * The main input is a {@link PartyRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartyRole} or a {@link Page} of {@link PartyRole} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartyRoleQueryService extends QueryService<PartyRole> {

    private final Logger log = LoggerFactory.getLogger(PartyRoleQueryService.class);

    private final PartyRoleRepository partyRoleRepository;

    public PartyRoleQueryService(PartyRoleRepository partyRoleRepository) {
        this.partyRoleRepository = partyRoleRepository;
    }

    /**
     * Return a {@link List} of {@link PartyRole} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartyRole> findByCriteria(PartyRoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartyRole> specification = createSpecification(criteria);
        return partyRoleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartyRole} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyRole> findByCriteria(PartyRoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartyRole> specification = createSpecification(criteria);
        return partyRoleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartyRoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartyRole> specification = createSpecification(criteria);
        return partyRoleRepository.count(specification);
    }

    /**
     * Function to convert {@link PartyRoleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartyRole> createSpecification(PartyRoleCriteria criteria) {
        Specification<PartyRole> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartyRole_.id));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(PartyRole_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleTypeId(),
                    root -> root.join(PartyRole_.roleType, JoinType.LEFT).get(RoleType_.id)));
            }
        }
        return specification;
    }
}
