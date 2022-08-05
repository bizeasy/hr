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

import com.hr.domain.UserGroupMember;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.UserGroupMemberRepository;
import com.hr.service.dto.UserGroupMemberCriteria;

/**
 * Service for executing complex queries for {@link UserGroupMember} entities in the database.
 * The main input is a {@link UserGroupMemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserGroupMember} or a {@link Page} of {@link UserGroupMember} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserGroupMemberQueryService extends QueryService<UserGroupMember> {

    private final Logger log = LoggerFactory.getLogger(UserGroupMemberQueryService.class);

    private final UserGroupMemberRepository userGroupMemberRepository;

    public UserGroupMemberQueryService(UserGroupMemberRepository userGroupMemberRepository) {
        this.userGroupMemberRepository = userGroupMemberRepository;
    }

    /**
     * Return a {@link List} of {@link UserGroupMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGroupMember> findByCriteria(UserGroupMemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserGroupMember> specification = createSpecification(criteria);
        return userGroupMemberRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserGroupMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGroupMember> findByCriteria(UserGroupMemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserGroupMember> specification = createSpecification(criteria);
        return userGroupMemberRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserGroupMemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserGroupMember> specification = createSpecification(criteria);
        return userGroupMemberRepository.count(specification);
    }

    /**
     * Function to convert {@link UserGroupMemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserGroupMember> createSpecification(UserGroupMemberCriteria criteria) {
        Specification<UserGroupMember> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserGroupMember_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), UserGroupMember_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), UserGroupMember_.thruDate));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(UserGroupMember_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserGroupMember_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
