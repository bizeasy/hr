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

import com.hr.domain.Catalogue;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.CatalogueRepository;
import com.hr.service.dto.CatalogueCriteria;

/**
 * Service for executing complex queries for {@link Catalogue} entities in the database.
 * The main input is a {@link CatalogueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Catalogue} or a {@link Page} of {@link Catalogue} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatalogueQueryService extends QueryService<Catalogue> {

    private final Logger log = LoggerFactory.getLogger(CatalogueQueryService.class);

    private final CatalogueRepository catalogueRepository;

    public CatalogueQueryService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * Return a {@link List} of {@link Catalogue} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Catalogue> findByCriteria(CatalogueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Catalogue> specification = createSpecification(criteria);
        return catalogueRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Catalogue} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Catalogue> findByCriteria(CatalogueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Catalogue> specification = createSpecification(criteria);
        return catalogueRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatalogueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Catalogue> specification = createSpecification(criteria);
        return catalogueRepository.count(specification);
    }

    /**
     * Function to convert {@link CatalogueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Catalogue> createSpecification(CatalogueCriteria criteria) {
        Specification<Catalogue> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Catalogue_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Catalogue_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Catalogue_.description));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), Catalogue_.sequenceNo));
            }
            if (criteria.getImagePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagePath(), Catalogue_.imagePath));
            }
            if (criteria.getMobileImagePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileImagePath(), Catalogue_.mobileImagePath));
            }
            if (criteria.getAltImage1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltImage1(), Catalogue_.altImage1));
            }
            if (criteria.getAltImage2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltImage2(), Catalogue_.altImage2));
            }
            if (criteria.getAltImage3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltImage3(), Catalogue_.altImage3));
            }
        }
        return specification;
    }
}
