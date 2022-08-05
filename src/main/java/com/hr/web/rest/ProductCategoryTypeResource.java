package com.hr.web.rest;

import com.hr.domain.ProductCategoryType;
import com.hr.repository.ProductCategoryTypeRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.ProductCategoryType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductCategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryTypeResource.class);

    private static final String ENTITY_NAME = "productCategoryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryTypeRepository productCategoryTypeRepository;

    public ProductCategoryTypeResource(ProductCategoryTypeRepository productCategoryTypeRepository) {
        this.productCategoryTypeRepository = productCategoryTypeRepository;
    }

    /**
     * {@code POST  /product-category-types} : Create a new productCategoryType.
     *
     * @param productCategoryType the productCategoryType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryType, or with status {@code 400 (Bad Request)} if the productCategoryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-types")
    public ResponseEntity<ProductCategoryType> createProductCategoryType(@Valid @RequestBody ProductCategoryType productCategoryType) throws URISyntaxException {
        log.debug("REST request to save ProductCategoryType : {}", productCategoryType);
        if (productCategoryType.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryType result = productCategoryTypeRepository.save(productCategoryType);
        return ResponseEntity.created(new URI("/api/product-category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-types} : Updates an existing productCategoryType.
     *
     * @param productCategoryType the productCategoryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryType,
     * or with status {@code 400 (Bad Request)} if the productCategoryType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-types")
    public ResponseEntity<ProductCategoryType> updateProductCategoryType(@Valid @RequestBody ProductCategoryType productCategoryType) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryType : {}", productCategoryType);
        if (productCategoryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCategoryType result = productCategoryTypeRepository.save(productCategoryType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-category-types} : get all the productCategoryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryTypes in body.
     */
    @GetMapping("/product-category-types")
    public List<ProductCategoryType> getAllProductCategoryTypes() {
        log.debug("REST request to get all ProductCategoryTypes");
        return productCategoryTypeRepository.findAll();
    }

    /**
     * {@code GET  /product-category-types/:id} : get the "id" productCategoryType.
     *
     * @param id the id of the productCategoryType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-types/{id}")
    public ResponseEntity<ProductCategoryType> getProductCategoryType(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryType : {}", id);
        Optional<ProductCategoryType> productCategoryType = productCategoryTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productCategoryType);
    }

    /**
     * {@code DELETE  /product-category-types/:id} : delete the "id" productCategoryType.
     *
     * @param id the id of the productCategoryType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-types/{id}")
    public ResponseEntity<Void> deleteProductCategoryType(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryType : {}", id);
        productCategoryTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
