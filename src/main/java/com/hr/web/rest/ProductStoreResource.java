package com.hr.web.rest;

import com.hr.domain.ProductStore;
import com.hr.service.ProductStoreService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductStoreCriteria;
import com.hr.service.ProductStoreQueryService;

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
 * REST controller for managing {@link com.hr.domain.ProductStore}.
 */
@RestController
@RequestMapping("/api")
public class ProductStoreResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreResource.class);

    private static final String ENTITY_NAME = "productStore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreService productStoreService;

    private final ProductStoreQueryService productStoreQueryService;

    public ProductStoreResource(ProductStoreService productStoreService, ProductStoreQueryService productStoreQueryService) {
        this.productStoreService = productStoreService;
        this.productStoreQueryService = productStoreQueryService;
    }

    /**
     * {@code POST  /product-stores} : Create a new productStore.
     *
     * @param productStore the productStore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStore, or with status {@code 400 (Bad Request)} if the productStore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-stores")
    public ResponseEntity<ProductStore> createProductStore(@Valid @RequestBody ProductStore productStore) throws URISyntaxException {
        log.debug("REST request to save ProductStore : {}", productStore);
        if (productStore.getId() != null) {
            throw new BadRequestAlertException("A new productStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStore result = productStoreService.save(productStore);
        return ResponseEntity.created(new URI("/api/product-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-stores} : Updates an existing productStore.
     *
     * @param productStore the productStore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStore,
     * or with status {@code 400 (Bad Request)} if the productStore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-stores")
    public ResponseEntity<ProductStore> updateProductStore(@Valid @RequestBody ProductStore productStore) throws URISyntaxException {
        log.debug("REST request to update ProductStore : {}", productStore);
        if (productStore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStore result = productStoreService.save(productStore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStore.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-stores} : get all the productStores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStores in body.
     */
    @GetMapping("/product-stores")
    public ResponseEntity<List<ProductStore>> getAllProductStores(ProductStoreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductStores by criteria: {}", criteria);
        Page<ProductStore> page = productStoreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-stores/count} : count all the productStores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-stores/count")
    public ResponseEntity<Long> countProductStores(ProductStoreCriteria criteria) {
        log.debug("REST request to count ProductStores by criteria: {}", criteria);
        return ResponseEntity.ok().body(productStoreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-stores/:id} : get the "id" productStore.
     *
     * @param id the id of the productStore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-stores/{id}")
    public ResponseEntity<ProductStore> getProductStore(@PathVariable Long id) {
        log.debug("REST request to get ProductStore : {}", id);
        Optional<ProductStore> productStore = productStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStore);
    }

    /**
     * {@code DELETE  /product-stores/:id} : delete the "id" productStore.
     *
     * @param id the id of the productStore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-stores/{id}")
    public ResponseEntity<Void> deleteProductStore(@PathVariable Long id) {
        log.debug("REST request to delete ProductStore : {}", id);
        productStoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
