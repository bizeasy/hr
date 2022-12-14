package com.hr.web.rest;

import com.hr.domain.SupplierProduct;
import com.hr.service.SupplierProductService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.SupplierProductCriteria;
import com.hr.service.SupplierProductQueryService;

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
 * REST controller for managing {@link com.hr.domain.SupplierProduct}.
 */
@RestController
@RequestMapping("/api")
public class SupplierProductResource {

    private final Logger log = LoggerFactory.getLogger(SupplierProductResource.class);

    private static final String ENTITY_NAME = "supplierProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierProductService supplierProductService;

    private final SupplierProductQueryService supplierProductQueryService;

    public SupplierProductResource(SupplierProductService supplierProductService, SupplierProductQueryService supplierProductQueryService) {
        this.supplierProductService = supplierProductService;
        this.supplierProductQueryService = supplierProductQueryService;
    }

    /**
     * {@code POST  /supplier-products} : Create a new supplierProduct.
     *
     * @param supplierProduct the supplierProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierProduct, or with status {@code 400 (Bad Request)} if the supplierProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-products")
    public ResponseEntity<SupplierProduct> createSupplierProduct(@Valid @RequestBody SupplierProduct supplierProduct) throws URISyntaxException {
        log.debug("REST request to save SupplierProduct : {}", supplierProduct);
        if (supplierProduct.getId() != null) {
            throw new BadRequestAlertException("A new supplierProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierProduct result = supplierProductService.save(supplierProduct);
        return ResponseEntity.created(new URI("/api/supplier-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-products} : Updates an existing supplierProduct.
     *
     * @param supplierProduct the supplierProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierProduct,
     * or with status {@code 400 (Bad Request)} if the supplierProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-products")
    public ResponseEntity<SupplierProduct> updateSupplierProduct(@Valid @RequestBody SupplierProduct supplierProduct) throws URISyntaxException {
        log.debug("REST request to update SupplierProduct : {}", supplierProduct);
        if (supplierProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierProduct result = supplierProductService.save(supplierProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierProduct.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-products} : get all the supplierProducts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierProducts in body.
     */
    @GetMapping("/supplier-products")
    public ResponseEntity<List<SupplierProduct>> getAllSupplierProducts(SupplierProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplierProducts by criteria: {}", criteria);
        Page<SupplierProduct> page = supplierProductQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supplier-products/count} : count all the supplierProducts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supplier-products/count")
    public ResponseEntity<Long> countSupplierProducts(SupplierProductCriteria criteria) {
        log.debug("REST request to count SupplierProducts by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierProductQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-products/:id} : get the "id" supplierProduct.
     *
     * @param id the id of the supplierProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-products/{id}")
    public ResponseEntity<SupplierProduct> getSupplierProduct(@PathVariable Long id) {
        log.debug("REST request to get SupplierProduct : {}", id);
        Optional<SupplierProduct> supplierProduct = supplierProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierProduct);
    }

    /**
     * {@code DELETE  /supplier-products/:id} : delete the "id" supplierProduct.
     *
     * @param id the id of the supplierProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-products/{id}")
    public ResponseEntity<Void> deleteSupplierProduct(@PathVariable Long id) {
        log.debug("REST request to delete SupplierProduct : {}", id);
        supplierProductService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
