package com.hr.web.rest;

import com.hr.domain.ProductFacility;
import com.hr.service.ProductFacilityService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductFacilityCriteria;
import com.hr.service.ProductFacilityQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.ProductFacility}.
 */
@RestController
@RequestMapping("/api")
public class ProductFacilityResource {

    private final Logger log = LoggerFactory.getLogger(ProductFacilityResource.class);

    private static final String ENTITY_NAME = "productFacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductFacilityService productFacilityService;

    private final ProductFacilityQueryService productFacilityQueryService;

    public ProductFacilityResource(ProductFacilityService productFacilityService, ProductFacilityQueryService productFacilityQueryService) {
        this.productFacilityService = productFacilityService;
        this.productFacilityQueryService = productFacilityQueryService;
    }

    /**
     * {@code POST  /product-facilities} : Create a new productFacility.
     *
     * @param productFacility the productFacility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productFacility, or with status {@code 400 (Bad Request)} if the productFacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-facilities")
    public ResponseEntity<ProductFacility> createProductFacility(@RequestBody ProductFacility productFacility) throws URISyntaxException {
        log.debug("REST request to save ProductFacility : {}", productFacility);
        if (productFacility.getId() != null) {
            throw new BadRequestAlertException("A new productFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductFacility result = productFacilityService.save(productFacility);
        return ResponseEntity.created(new URI("/api/product-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-facilities} : Updates an existing productFacility.
     *
     * @param productFacility the productFacility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productFacility,
     * or with status {@code 400 (Bad Request)} if the productFacility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productFacility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-facilities")
    public ResponseEntity<ProductFacility> updateProductFacility(@RequestBody ProductFacility productFacility) throws URISyntaxException {
        log.debug("REST request to update ProductFacility : {}", productFacility);
        if (productFacility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductFacility result = productFacilityService.save(productFacility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productFacility.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-facilities} : get all the productFacilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productFacilities in body.
     */
    @GetMapping("/product-facilities")
    public ResponseEntity<List<ProductFacility>> getAllProductFacilities(ProductFacilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductFacilities by criteria: {}", criteria);
        Page<ProductFacility> page = productFacilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-facilities/count} : count all the productFacilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-facilities/count")
    public ResponseEntity<Long> countProductFacilities(ProductFacilityCriteria criteria) {
        log.debug("REST request to count ProductFacilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(productFacilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-facilities/:id} : get the "id" productFacility.
     *
     * @param id the id of the productFacility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productFacility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-facilities/{id}")
    public ResponseEntity<ProductFacility> getProductFacility(@PathVariable Long id) {
        log.debug("REST request to get ProductFacility : {}", id);
        Optional<ProductFacility> productFacility = productFacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productFacility);
    }

    /**
     * {@code DELETE  /product-facilities/:id} : delete the "id" productFacility.
     *
     * @param id the id of the productFacility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-facilities/{id}")
    public ResponseEntity<Void> deleteProductFacility(@PathVariable Long id) {
        log.debug("REST request to delete ProductFacility : {}", id);
        productFacilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
