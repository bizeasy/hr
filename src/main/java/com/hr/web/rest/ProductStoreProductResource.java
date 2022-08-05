package com.hr.web.rest;

import com.hr.domain.ProductStoreProduct;
import com.hr.repository.ProductStoreProductRepository;
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
 * REST controller for managing {@link com.hr.domain.ProductStoreProduct}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductStoreProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreProductResource.class);

    private static final String ENTITY_NAME = "productStoreProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreProductRepository productStoreProductRepository;

    public ProductStoreProductResource(ProductStoreProductRepository productStoreProductRepository) {
        this.productStoreProductRepository = productStoreProductRepository;
    }

    /**
     * {@code POST  /product-store-products} : Create a new productStoreProduct.
     *
     * @param productStoreProduct the productStoreProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreProduct, or with status {@code 400 (Bad Request)} if the productStoreProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-store-products")
    public ResponseEntity<ProductStoreProduct> createProductStoreProduct(@Valid @RequestBody ProductStoreProduct productStoreProduct) throws URISyntaxException {
        log.debug("REST request to save ProductStoreProduct : {}", productStoreProduct);
        if (productStoreProduct.getId() != null) {
            throw new BadRequestAlertException("A new productStoreProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreProduct result = productStoreProductRepository.save(productStoreProduct);
        return ResponseEntity.created(new URI("/api/product-store-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-store-products} : Updates an existing productStoreProduct.
     *
     * @param productStoreProduct the productStoreProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreProduct,
     * or with status {@code 400 (Bad Request)} if the productStoreProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-store-products")
    public ResponseEntity<ProductStoreProduct> updateProductStoreProduct(@Valid @RequestBody ProductStoreProduct productStoreProduct) throws URISyntaxException {
        log.debug("REST request to update ProductStoreProduct : {}", productStoreProduct);
        if (productStoreProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStoreProduct result = productStoreProductRepository.save(productStoreProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreProduct.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-store-products} : get all the productStoreProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStoreProducts in body.
     */
    @GetMapping("/product-store-products")
    public List<ProductStoreProduct> getAllProductStoreProducts() {
        log.debug("REST request to get all ProductStoreProducts");
        return productStoreProductRepository.findAll();
    }

    /**
     * {@code GET  /product-store-products/:id} : get the "id" productStoreProduct.
     *
     * @param id the id of the productStoreProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-store-products/{id}")
    public ResponseEntity<ProductStoreProduct> getProductStoreProduct(@PathVariable Long id) {
        log.debug("REST request to get ProductStoreProduct : {}", id);
        Optional<ProductStoreProduct> productStoreProduct = productStoreProductRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productStoreProduct);
    }

    /**
     * {@code DELETE  /product-store-products/:id} : delete the "id" productStoreProduct.
     *
     * @param id the id of the productStoreProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-store-products/{id}")
    public ResponseEntity<Void> deleteProductStoreProduct(@PathVariable Long id) {
        log.debug("REST request to delete ProductStoreProduct : {}", id);
        productStoreProductRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
