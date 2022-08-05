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

import com.hr.domain.Product;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ProductRepository;
import com.hr.service.dto.ProductCriteria;

/**
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Product} or a {@link Page} of {@link Product} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Return a {@link List} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Product> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Product> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getInternalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalName(), Product_.internalName));
            }
            if (criteria.getBrandName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrandName(), Product_.brandName));
            }
            if (criteria.getVariant() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVariant(), Product_.variant));
            }
            if (criteria.getSku() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSku(), Product_.sku));
            }
            if (criteria.getIntroductionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIntroductionDate(), Product_.introductionDate));
            }
            if (criteria.getReleaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReleaseDate(), Product_.releaseDate));
            }
            if (criteria.getQuantityIncluded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityIncluded(), Product_.quantityIncluded));
            }
            if (criteria.getPiecesIncluded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPiecesIncluded(), Product_.piecesIncluded));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Product_.weight));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Product_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Product_.width));
            }
            if (criteria.getDepth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepth(), Product_.depth));
            }
            if (criteria.getSmallImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSmallImageUrl(), Product_.smallImageUrl));
            }
            if (criteria.getMediumImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMediumImageUrl(), Product_.mediumImageUrl));
            }
            if (criteria.getLargeImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLargeImageUrl(), Product_.largeImageUrl));
            }
            if (criteria.getDetailImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetailImageUrl(), Product_.detailImageUrl));
            }
            if (criteria.getOriginalImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalImageUrl(), Product_.originalImageUrl));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Product_.quantity));
            }
            if (criteria.getCgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgst(), Product_.cgst));
            }
            if (criteria.getIgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgst(), Product_.igst));
            }
            if (criteria.getSgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgst(), Product_.sgst));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getCgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgstPercentage(), Product_.cgstPercentage));
            }
            if (criteria.getIgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgstPercentage(), Product_.igstPercentage));
            }
            if (criteria.getSgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgstPercentage(), Product_.sgstPercentage));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), Product_.totalPrice));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getLongDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongDescription(), Product_.longDescription));
            }
            if (criteria.getIsVirtual() != null) {
                specification = specification.and(buildSpecification(criteria.getIsVirtual(), Product_.isVirtual));
            }
            if (criteria.getIsVariant() != null) {
                specification = specification.and(buildSpecification(criteria.getIsVariant(), Product_.isVariant));
            }
            if (criteria.getRequireInventory() != null) {
                specification = specification.and(buildSpecification(criteria.getRequireInventory(), Product_.requireInventory));
            }
            if (criteria.getReturnable() != null) {
                specification = specification.and(buildSpecification(criteria.getReturnable(), Product_.returnable));
            }
            if (criteria.getIsPopular() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPopular(), Product_.isPopular));
            }
            if (criteria.getChangeControlNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChangeControlNo(), Product_.changeControlNo));
            }
            if (criteria.getRetestDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetestDuration(), Product_.retestDuration));
            }
            if (criteria.getExpiryDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDuration(), Product_.expiryDuration));
            }
            if (criteria.getValidationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidationType(), Product_.validationType));
            }
            if (criteria.getShelfLife() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShelfLife(), Product_.shelfLife));
            }
            if (criteria.getProductTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductTypeId(),
                    root -> root.join(Product_.productType, JoinType.LEFT).get(ProductType_.id)));
            }
            if (criteria.getQuantityUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuantityUomId(),
                    root -> root.join(Product_.quantityUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getWeightUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getWeightUomId(),
                    root -> root.join(Product_.weightUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getSizeUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getSizeUomId(),
                    root -> root.join(Product_.sizeUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(Product_.uom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getPrimaryProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrimaryProductCategoryId(),
                    root -> root.join(Product_.primaryProductCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getVirtualProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getVirtualProductId(),
                    root -> root.join(Product_.virtualProduct, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getInventoryItemTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemTypeId(),
                    root -> root.join(Product_.inventoryItemType, JoinType.LEFT).get(InventoryItemType_.id)));
            }
            if (criteria.getTaxSlabId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxSlabId(),
                    root -> root.join(Product_.taxSlab, JoinType.LEFT).get(TaxSlab_.id)));
            }
            if (criteria.getShelfLifeUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getShelfLifeUomId(),
                    root -> root.join(Product_.shelfLifeUom, JoinType.LEFT).get(Uom_.id)));
            }
        }
        return specification;
    }
}
