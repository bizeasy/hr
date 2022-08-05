package com.hr.web.rest;

import com.hr.domain.ProductPrice;
import com.hr.service.ProductPriceService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductPriceCriteria;
import com.hr.service.ProductPriceQueryService;

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
 * REST controller for managing {@link com.hr.domain.ProductPrice}.
 */
@RestController
@RequestMapping("/api")
public class ProductPriceResource {

    private final Logger log = LoggerFactory.getLogger(ProductPriceResource.class);

    private static final String ENTITY_NAME = "productPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPriceService productPriceService;

    private final ProductPriceQueryService productPriceQueryService;

    public ProductPriceResource(ProductPriceService productPriceService, ProductPriceQueryService productPriceQueryService) {
        this.productPriceService = productPriceService;
        this.productPriceQueryService = productPriceQueryService;
    }

    /**
     * {@code POST  /product-prices} : Create a new productPrice.
     *
     * @param productPrice the productPrice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPrice, or with status {@code 400 (Bad Request)} if the productPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-prices")
    public ResponseEntity<ProductPrice> createProductPrice(@RequestBody ProductPrice productPrice) throws URISyntaxException {
        log.debug("REST request to save ProductPrice : {}", productPrice);
        if (productPrice.getId() != null) {
            throw new BadRequestAlertException("A new productPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPrice result = productPriceService.save(productPrice);
        return ResponseEntity.created(new URI("/api/product-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-prices} : Updates an existing productPrice.
     *
     * @param productPrice the productPrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPrice,
     * or with status {@code 400 (Bad Request)} if the productPrice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-prices")
    public ResponseEntity<ProductPrice> updateProductPrice(@RequestBody ProductPrice productPrice) throws URISyntaxException {
        log.debug("REST request to update ProductPrice : {}", productPrice);
        if (productPrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPrice result = productPriceService.save(productPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPrice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-prices} : get all the productPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPrices in body.
     */
    @GetMapping("/product-prices")
    public ResponseEntity<List<ProductPrice>> getAllProductPrices(ProductPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductPrices by criteria: {}", criteria);
        Page<ProductPrice> page = productPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-prices/count} : count all the productPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-prices/count")
    public ResponseEntity<Long> countProductPrices(ProductPriceCriteria criteria) {
        log.debug("REST request to count ProductPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(productPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-prices/:id} : get the "id" productPrice.
     *
     * @param id the id of the productPrice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPrice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-prices/{id}")
    public ResponseEntity<ProductPrice> getProductPrice(@PathVariable Long id) {
        log.debug("REST request to get ProductPrice : {}", id);
        Optional<ProductPrice> productPrice = productPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPrice);
    }

    /**
     * {@code DELETE  /product-prices/:id} : delete the "id" productPrice.
     *
     * @param id the id of the productPrice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-prices/{id}")
    public ResponseEntity<Void> deleteProductPrice(@PathVariable Long id) {
        log.debug("REST request to delete ProductPrice : {}", id);
        productPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
