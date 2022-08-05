package com.hr.web.rest;

import com.hr.domain.ProductCategoryMember;
import com.hr.service.ProductCategoryMemberService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductCategoryMemberCriteria;
import com.hr.service.ProductCategoryMemberQueryService;

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
 * REST controller for managing {@link com.hr.domain.ProductCategoryMember}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryMemberResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryMemberResource.class);

    private static final String ENTITY_NAME = "productCategoryMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryMemberService productCategoryMemberService;

    private final ProductCategoryMemberQueryService productCategoryMemberQueryService;

    public ProductCategoryMemberResource(ProductCategoryMemberService productCategoryMemberService, ProductCategoryMemberQueryService productCategoryMemberQueryService) {
        this.productCategoryMemberService = productCategoryMemberService;
        this.productCategoryMemberQueryService = productCategoryMemberQueryService;
    }

    /**
     * {@code POST  /product-category-members} : Create a new productCategoryMember.
     *
     * @param productCategoryMember the productCategoryMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryMember, or with status {@code 400 (Bad Request)} if the productCategoryMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-members")
    public ResponseEntity<ProductCategoryMember> createProductCategoryMember(@RequestBody ProductCategoryMember productCategoryMember) throws URISyntaxException {
        log.debug("REST request to save ProductCategoryMember : {}", productCategoryMember);
        if (productCategoryMember.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryMember result = productCategoryMemberService.save(productCategoryMember);
        return ResponseEntity.created(new URI("/api/product-category-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-members} : Updates an existing productCategoryMember.
     *
     * @param productCategoryMember the productCategoryMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryMember,
     * or with status {@code 400 (Bad Request)} if the productCategoryMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-members")
    public ResponseEntity<ProductCategoryMember> updateProductCategoryMember(@RequestBody ProductCategoryMember productCategoryMember) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryMember : {}", productCategoryMember);
        if (productCategoryMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCategoryMember result = productCategoryMemberService.save(productCategoryMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-category-members} : get all the productCategoryMembers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryMembers in body.
     */
    @GetMapping("/product-category-members")
    public ResponseEntity<List<ProductCategoryMember>> getAllProductCategoryMembers(ProductCategoryMemberCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductCategoryMembers by criteria: {}", criteria);
        Page<ProductCategoryMember> page = productCategoryMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-category-members/count} : count all the productCategoryMembers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-category-members/count")
    public ResponseEntity<Long> countProductCategoryMembers(ProductCategoryMemberCriteria criteria) {
        log.debug("REST request to count ProductCategoryMembers by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCategoryMemberQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-category-members/:id} : get the "id" productCategoryMember.
     *
     * @param id the id of the productCategoryMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-members/{id}")
    public ResponseEntity<ProductCategoryMember> getProductCategoryMember(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryMember : {}", id);
        Optional<ProductCategoryMember> productCategoryMember = productCategoryMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryMember);
    }

    /**
     * {@code DELETE  /product-category-members/:id} : delete the "id" productCategoryMember.
     *
     * @param id the id of the productCategoryMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-members/{id}")
    public ResponseEntity<Void> deleteProductCategoryMember(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryMember : {}", id);
        productCategoryMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
