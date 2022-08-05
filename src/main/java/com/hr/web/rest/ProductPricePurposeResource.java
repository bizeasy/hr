package com.hr.web.rest;

import com.hr.domain.ProductPricePurpose;
import com.hr.repository.ProductPricePurposeRepository;
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
 * REST controller for managing {@link com.hr.domain.ProductPricePurpose}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductPricePurposeResource {

    private final Logger log = LoggerFactory.getLogger(ProductPricePurposeResource.class);

    private static final String ENTITY_NAME = "productPricePurpose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPricePurposeRepository productPricePurposeRepository;

    public ProductPricePurposeResource(ProductPricePurposeRepository productPricePurposeRepository) {
        this.productPricePurposeRepository = productPricePurposeRepository;
    }

    /**
     * {@code POST  /product-price-purposes} : Create a new productPricePurpose.
     *
     * @param productPricePurpose the productPricePurpose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPricePurpose, or with status {@code 400 (Bad Request)} if the productPricePurpose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-price-purposes")
    public ResponseEntity<ProductPricePurpose> createProductPricePurpose(@Valid @RequestBody ProductPricePurpose productPricePurpose) throws URISyntaxException {
        log.debug("REST request to save ProductPricePurpose : {}", productPricePurpose);
        if (productPricePurpose.getId() != null) {
            throw new BadRequestAlertException("A new productPricePurpose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPricePurpose result = productPricePurposeRepository.save(productPricePurpose);
        return ResponseEntity.created(new URI("/api/product-price-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-price-purposes} : Updates an existing productPricePurpose.
     *
     * @param productPricePurpose the productPricePurpose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPricePurpose,
     * or with status {@code 400 (Bad Request)} if the productPricePurpose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPricePurpose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-price-purposes")
    public ResponseEntity<ProductPricePurpose> updateProductPricePurpose(@Valid @RequestBody ProductPricePurpose productPricePurpose) throws URISyntaxException {
        log.debug("REST request to update ProductPricePurpose : {}", productPricePurpose);
        if (productPricePurpose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPricePurpose result = productPricePurposeRepository.save(productPricePurpose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPricePurpose.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-price-purposes} : get all the productPricePurposes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPricePurposes in body.
     */
    @GetMapping("/product-price-purposes")
    public List<ProductPricePurpose> getAllProductPricePurposes() {
        log.debug("REST request to get all ProductPricePurposes");
        return productPricePurposeRepository.findAll();
    }

    /**
     * {@code GET  /product-price-purposes/:id} : get the "id" productPricePurpose.
     *
     * @param id the id of the productPricePurpose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPricePurpose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-price-purposes/{id}")
    public ResponseEntity<ProductPricePurpose> getProductPricePurpose(@PathVariable Long id) {
        log.debug("REST request to get ProductPricePurpose : {}", id);
        Optional<ProductPricePurpose> productPricePurpose = productPricePurposeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productPricePurpose);
    }

    /**
     * {@code DELETE  /product-price-purposes/:id} : delete the "id" productPricePurpose.
     *
     * @param id the id of the productPricePurpose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-price-purposes/{id}")
    public ResponseEntity<Void> deleteProductPricePurpose(@PathVariable Long id) {
        log.debug("REST request to delete ProductPricePurpose : {}", id);
        productPricePurposeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
