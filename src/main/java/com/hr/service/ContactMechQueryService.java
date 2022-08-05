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

import com.hr.domain.ContactMech;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ContactMechRepository;
import com.hr.service.dto.ContactMechCriteria;

/**
 * Service for executing complex queries for {@link ContactMech} entities in the database.
 * The main input is a {@link ContactMechCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactMech} or a {@link Page} of {@link ContactMech} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactMechQueryService extends QueryService<ContactMech> {

    private final Logger log = LoggerFactory.getLogger(ContactMechQueryService.class);

    private final ContactMechRepository contactMechRepository;

    public ContactMechQueryService(ContactMechRepository contactMechRepository) {
        this.contactMechRepository = contactMechRepository;
    }

    /**
     * Return a {@link List} of {@link ContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactMech> findByCriteria(ContactMechCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactMech> specification = createSpecification(criteria);
        return contactMechRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactMech> findByCriteria(ContactMechCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactMech> specification = createSpecification(criteria);
        return contactMechRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactMechCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactMech> specification = createSpecification(criteria);
        return contactMechRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactMechCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactMech> createSpecification(ContactMechCriteria criteria) {
        Specification<ContactMech> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactMech_.id));
            }
            if (criteria.getInfoString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoString(), ContactMech_.infoString));
            }
            if (criteria.getContactMechTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechTypeId(),
                    root -> root.join(ContactMech_.contactMechType, JoinType.LEFT).get(ContactMechType_.id)));
            }
        }
        return specification;
    }
}
