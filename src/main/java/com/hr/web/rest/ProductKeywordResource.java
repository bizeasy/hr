package com.hr.web.rest;

import com.hr.domain.ProductKeyword;
import com.hr.repository.ProductKeywordRepository;
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
 * REST controller for managing {@link com.hr.domain.ProductKeyword}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductKeywordResource {

    private final Logger log = LoggerFactory.getLogger(ProductKeywordResource.class);

    private static final String ENTITY_NAME = "productKeyword";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductKeywordRepository productKeywordRepository;

    public ProductKeywordResource(ProductKeywordRepository productKeywordRepository) {
        this.productKeywordRepository = productKeywordRepository;
    }

    /**
     * {@code POST  /product-keywords} : Create a new productKeyword.
     *
     * @param productKeyword the productKeyword to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productKeyword, or with status {@code 400 (Bad Request)} if the productKeyword has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-keywords")
    public ResponseEntity<ProductKeyword> createProductKeyword(@Valid @RequestBody ProductKeyword productKeyword) throws URISyntaxException {
        log.debug("REST request to save ProductKeyword : {}", productKeyword);
        if (productKeyword.getId() != null) {
            throw new BadRequestAlertException("A new productKeyword cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductKeyword result = productKeywordRepository.save(productKeyword);
        return ResponseEntity.created(new URI("/api/product-keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-keywords} : Updates an existing productKeyword.
     *
     * @param productKeyword the productKeyword to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productKeyword,
     * or with status {@code 400 (Bad Request)} if the productKeyword is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productKeyword couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-keywords")
    public ResponseEntity<ProductKeyword> updateProductKeyword(@Valid @RequestBody ProductKeyword productKeyword) throws URISyntaxException {
        log.debug("REST request to update ProductKeyword : {}", productKeyword);
        if (productKeyword.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductKeyword result = productKeywordRepository.save(productKeyword);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productKeyword.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-keywords} : get all the productKeywords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productKeywords in body.
     */
    @GetMapping("/product-keywords")
    public List<ProductKeyword> getAllProductKeywords() {
        log.debug("REST request to get all ProductKeywords");
        return productKeywordRepository.findAll();
    }

    /**
     * {@code GET  /product-keywords/:id} : get the "id" productKeyword.
     *
     * @param id the id of the productKeyword to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productKeyword, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-keywords/{id}")
    public ResponseEntity<ProductKeyword> getProductKeyword(@PathVariable Long id) {
        log.debug("REST request to get ProductKeyword : {}", id);
        Optional<ProductKeyword> productKeyword = productKeywordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productKeyword);
    }

    /**
     * {@code DELETE  /product-keywords/:id} : delete the "id" productKeyword.
     *
     * @param id the id of the productKeyword to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-keywords/{id}")
    public ResponseEntity<Void> deleteProductKeyword(@PathVariable Long id) {
        log.debug("REST request to delete ProductKeyword : {}", id);
        productKeywordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
