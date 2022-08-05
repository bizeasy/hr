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

import com.hr.domain.CatalogueCategory;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.CatalogueCategoryRepository;
import com.hr.service.dto.CatalogueCategoryCriteria;

/**
 * Service for executing complex queries for {@link CatalogueCategory} entities in the database.
 * The main input is a {@link CatalogueCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatalogueCategory} or a {@link Page} of {@link CatalogueCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatalogueCategoryQueryService extends QueryService<CatalogueCategory> {

    private final Logger log = LoggerFactory.getLogger(CatalogueCategoryQueryService.class);

    private final CatalogueCategoryRepository catalogueCategoryRepository;

    public CatalogueCategoryQueryService(CatalogueCategoryRepository catalogueCategoryRepository) {
        this.catalogueCategoryRepository = catalogueCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link CatalogueCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatalogueCategory> findByCriteria(CatalogueCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatalogueCategory> specification = createSpecification(criteria);
        return catalogueCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatalogueCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatalogueCategory> findByCriteria(CatalogueCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatalogueCategory> specification = createSpecification(criteria);
        return catalogueCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatalogueCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatalogueCategory> specification = createSpecification(criteria);
        return catalogueCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CatalogueCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatalogueCategory> createSpecification(CatalogueCategoryCriteria criteria) {
        Specification<CatalogueCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatalogueCategory_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), CatalogueCategory_.sequenceNo));
            }
            if (criteria.getCatalogueId() != null) {
                specification = specification.and(buildSpecification(criteria.getCatalogueId(),
                    root -> root.join(CatalogueCategory_.catalogue, JoinType.LEFT).get(Catalogue_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(CatalogueCategory_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getCatalogueCategoryTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCatalogueCategoryTypeId(),
                    root -> root.join(CatalogueCategory_.catalogueCategoryType, JoinType.LEFT).get(CatalogueCategoryType_.id)));
            }
        }
        return specification;
    }
}
