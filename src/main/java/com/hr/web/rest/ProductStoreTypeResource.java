package com.hr.web.rest;

import com.hr.domain.ProductStoreType;
import com.hr.repository.ProductStoreTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.ProductStoreType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductStoreTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreTypeResource.class);

    private static final String ENTITY_NAME = "productStoreType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreTypeRepository productStoreTypeRepository;

    public ProductStoreTypeResource(ProductStoreTypeRepository productStoreTypeRepository) {
        this.productStoreTypeRepository = productStoreTypeRepository;
    }

    /**
     * {@code POST  /product-store-types} : Create a new productStoreType.
     *
     * @param productStoreType the productStoreType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreType, or with status {@code 400 (Bad Request)} if the productStoreType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-store-types")
    public ResponseEntity<ProductStoreType> createProductStoreType(@Valid @RequestBody ProductStoreType productStoreType) throws URISyntaxException {
        log.debug("REST request to save ProductStoreType : {}", productStoreType);
        if (productStoreType.getId() != null) {
            throw new BadRequestAlertException("A new productStoreType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreType result = productStoreTypeRepository.save(productStoreType);
        return ResponseEntity.created(new URI("/api/product-store-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-store-types} : Updates an existing productStoreType.
     *
     * @param productStoreType the productStoreType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreType,
     * or with status {@code 400 (Bad Request)} if the productStoreType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-store-types")
    public ResponseEntity<ProductStoreType> updateProductStoreType(@Valid @RequestBody ProductStoreType productStoreType) throws URISyntaxException {
        log.debug("REST request to update ProductStoreType : {}", productStoreType);
        if (productStoreType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStoreType result = productStoreTypeRepository.save(productStoreType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-store-types} : get all the productStoreTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStoreTypes in body.
     */
    @GetMapping("/product-store-types")
    public List<ProductStoreType> getAllProductStoreTypes() {
        log.debug("REST request to get all ProductStoreTypes");
        return productStoreTypeRepository.findAll();
    }

    /**
     * {@code GET  /product-store-types/:id} : get the "id" productStoreType.
     *
     * @param id the id of the productStoreType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-store-types/{id}")
    public ResponseEntity<ProductStoreType> getProductStoreType(@PathVariable Long id) {
        log.debug("REST request to get ProductStoreType : {}", id);
        Optional<ProductStoreType> productStoreType = productStoreTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productStoreType);
    }

    /**
     * {@code DELETE  /product-store-types/:id} : delete the "id" productStoreType.
     *
     * @param id the id of the productStoreType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-store-types/{id}")
    public ResponseEntity<Void> deleteProductStoreType(@PathVariable Long id) {
        log.debug("REST request to delete ProductStoreType : {}", id);
        productStoreTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
