package com.hr.web.rest;

import com.hr.domain.ProductStoreFacility;
import com.hr.service.ProductStoreFacilityService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductStoreFacilityCriteria;
import com.hr.service.ProductStoreFacilityQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.ProductStoreFacility}.
 */
@RestController
@RequestMapping("/api")
public class ProductStoreFacilityResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreFacilityResource.class);

    private static final String ENTITY_NAME = "productStoreFacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreFacilityService productStoreFacilityService;

    private final ProductStoreFacilityQueryService productStoreFacilityQueryService;

    public ProductStoreFacilityResource(ProductStoreFacilityService productStoreFacilityService, ProductStoreFacilityQueryService productStoreFacilityQueryService) {
        this.productStoreFacilityService = productStoreFacilityService;
        this.productStoreFacilityQueryService = productStoreFacilityQueryService;
    }

    /**
     * {@code POST  /product-store-facilities} : Create a new productStoreFacility.
     *
     * @param productStoreFacility the productStoreFacility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreFacility, or with status {@code 400 (Bad Request)} if the productStoreFacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-store-facilities")
    public ResponseEntity<ProductStoreFacility> createProductStoreFacility(@Valid @RequestBody ProductStoreFacility productStoreFacility) throws URISyntaxException {
        log.debug("REST request to save ProductStoreFacility : {}", productStoreFacility);
        if (productStoreFacility.getId() != null) {
            throw new BadRequestAlertException("A new productStoreFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreFacility result = productStoreFacilityService.save(productStoreFacility);
        return ResponseEntity.created(new URI("/api/product-store-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-store-facilities} : Updates an existing productStoreFacility.
     *
     * @param productStoreFacility the productStoreFacility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreFacility,
     * or with status {@code 400 (Bad Request)} if the productStoreFacility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreFacility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-store-facilities")
    public ResponseEntity<ProductStoreFacility> updateProductStoreFacility(@Valid @RequestBody ProductStoreFacility productStoreFacility) throws URISyntaxException {
        log.debug("REST request to update ProductStoreFacility : {}", productStoreFacility);
        if (productStoreFacility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStoreFacility result = productStoreFacilityService.save(productStoreFacility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreFacility.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-store-facilities} : get all the productStoreFacilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStoreFacilities in body.
     */
    @GetMapping("/product-store-facilities")
    public ResponseEntity<List<ProductStoreFacility>> getAllProductStoreFacilities(ProductStoreFacilityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductStoreFacilities by criteria: {}", criteria);
        Page<ProductStoreFacility> page = productStoreFacilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-store-facilities/count} : count all the productStoreFacilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-store-facilities/count")
    public ResponseEntity<Long> countProductStoreFacilities(ProductStoreFacilityCriteria criteria) {
        log.debug("REST request to count ProductStoreFacilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(productStoreFacilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-store-facilities/:id} : get the "id" productStoreFacility.
     *
     * @param id the id of the productStoreFacility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreFacility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-store-facilities/{id}")
    public ResponseEntity<ProductStoreFacility> getProductStoreFacility(@PathVariable Long id) {
        log.debug("REST request to get ProductStoreFacility : {}", id);
        Optional<ProductStoreFacility> productStoreFacility = productStoreFacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStoreFacility);
    }

    /**
     * {@code DELETE  /product-store-facilities/:id} : delete the "id" productStoreFacility.
     *
     * @param id the id of the productStoreFacility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-store-facilities/{id}")
    public ResponseEntity<Void> deleteProductStoreFacility(@PathVariable Long id) {
        log.debug("REST request to delete ProductStoreFacility : {}", id);
        productStoreFacilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
