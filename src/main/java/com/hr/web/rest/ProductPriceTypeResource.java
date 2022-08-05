package com.hr.web.rest;

import com.hr.domain.ProductPriceType;
import com.hr.repository.ProductPriceTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.ProductPriceType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductPriceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductPriceTypeResource.class);

    private static final String ENTITY_NAME = "productPriceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPriceTypeRepository productPriceTypeRepository;

    public ProductPriceTypeResource(ProductPriceTypeRepository productPriceTypeRepository) {
        this.productPriceTypeRepository = productPriceTypeRepository;
    }

    /**
     * {@code POST  /product-price-types} : Create a new productPriceType.
     *
     * @param productPriceType the productPriceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPriceType, or with status {@code 400 (Bad Request)} if the productPriceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-price-types")
    public ResponseEntity<ProductPriceType> createProductPriceType(@Valid @RequestBody ProductPriceType productPriceType) throws URISyntaxException {
        log.debug("REST request to save ProductPriceType : {}", productPriceType);
        if (productPriceType.getId() != null) {
            throw new BadRequestAlertException("A new productPriceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPriceType result = productPriceTypeRepository.save(productPriceType);
        return ResponseEntity.created(new URI("/api/product-price-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-price-types} : Updates an existing productPriceType.
     *
     * @param productPriceType the productPriceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPriceType,
     * or with status {@code 400 (Bad Request)} if the productPriceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPriceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-price-types")
    public ResponseEntity<ProductPriceType> updateProductPriceType(@Valid @RequestBody ProductPriceType productPriceType) throws URISyntaxException {
        log.debug("REST request to update ProductPriceType : {}", productPriceType);
        if (productPriceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPriceType result = productPriceTypeRepository.save(productPriceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPriceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-price-types} : get all the productPriceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPriceTypes in body.
     */
    @GetMapping("/product-price-types")
    public List<ProductPriceType> getAllProductPriceTypes() {
        log.debug("REST request to get all ProductPriceTypes");
        return productPriceTypeRepository.findAll();
    }

    /**
     * {@code GET  /product-price-types/:id} : get the "id" productPriceType.
     *
     * @param id the id of the productPriceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPriceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-price-types/{id}")
    public ResponseEntity<ProductPriceType> getProductPriceType(@PathVariable Long id) {
        log.debug("REST request to get ProductPriceType : {}", id);
        Optional<ProductPriceType> productPriceType = productPriceTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productPriceType);
    }

    /**
     * {@code DELETE  /product-price-types/:id} : delete the "id" productPriceType.
     *
     * @param id the id of the productPriceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-price-types/{id}")
    public ResponseEntity<Void> deleteProductPriceType(@PathVariable Long id) {
        log.debug("REST request to delete ProductPriceType : {}", id);
        productPriceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
