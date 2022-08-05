package com.hr.web.rest;

import com.hr.domain.ProductStoreUserGroup;
import com.hr.service.ProductStoreUserGroupService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ProductStoreUserGroupCriteria;
import com.hr.service.ProductStoreUserGroupQueryService;

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
 * REST controller for managing {@link com.hr.domain.ProductStoreUserGroup}.
 */
@RestController
@RequestMapping("/api")
public class ProductStoreUserGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProductStoreUserGroupResource.class);

    private static final String ENTITY_NAME = "productStoreUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStoreUserGroupService productStoreUserGroupService;

    private final ProductStoreUserGroupQueryService productStoreUserGroupQueryService;

    public ProductStoreUserGroupResource(ProductStoreUserGroupService productStoreUserGroupService, ProductStoreUserGroupQueryService productStoreUserGroupQueryService) {
        this.productStoreUserGroupService = productStoreUserGroupService;
        this.productStoreUserGroupQueryService = productStoreUserGroupQueryService;
    }

    /**
     * {@code POST  /product-store-user-groups} : Create a new productStoreUserGroup.
     *
     * @param productStoreUserGroup the productStoreUserGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStoreUserGroup, or with status {@code 400 (Bad Request)} if the productStoreUserGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-store-user-groups")
    public ResponseEntity<ProductStoreUserGroup> createProductStoreUserGroup(@Valid @RequestBody ProductStoreUserGroup productStoreUserGroup) throws URISyntaxException {
        log.debug("REST request to save ProductStoreUserGroup : {}", productStoreUserGroup);
        if (productStoreUserGroup.getId() != null) {
            throw new BadRequestAlertException("A new productStoreUserGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStoreUserGroup result = productStoreUserGroupService.save(productStoreUserGroup);
        return ResponseEntity.created(new URI("/api/product-store-user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-store-user-groups} : Updates an existing productStoreUserGroup.
     *
     * @param productStoreUserGroup the productStoreUserGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStoreUserGroup,
     * or with status {@code 400 (Bad Request)} if the productStoreUserGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStoreUserGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-store-user-groups")
    public ResponseEntity<ProductStoreUserGroup> updateProductStoreUserGroup(@Valid @RequestBody ProductStoreUserGroup productStoreUserGroup) throws URISyntaxException {
        log.debug("REST request to update ProductStoreUserGroup : {}", productStoreUserGroup);
        if (productStoreUserGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStoreUserGroup result = productStoreUserGroupService.save(productStoreUserGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productStoreUserGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-store-user-groups} : get all the productStoreUserGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStoreUserGroups in body.
     */
    @GetMapping("/product-store-user-groups")
    public ResponseEntity<List<ProductStoreUserGroup>> getAllProductStoreUserGroups(ProductStoreUserGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductStoreUserGroups by criteria: {}", criteria);
        Page<ProductStoreUserGroup> page = productStoreUserGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-store-user-groups/count} : count all the productStoreUserGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-store-user-groups/count")
    public ResponseEntity<Long> countProductStoreUserGroups(ProductStoreUserGroupCriteria criteria) {
        log.debug("REST request to count ProductStoreUserGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(productStoreUserGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-store-user-groups/:id} : get the "id" productStoreUserGroup.
     *
     * @param id the id of the productStoreUserGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStoreUserGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-store-user-groups/{id}")
    public ResponseEntity<ProductStoreUserGroup> getProductStoreUserGroup(@PathVariable Long id) {
        log.debug("REST request to get ProductStoreUserGroup : {}", id);
        Optional<ProductStoreUserGroup> productStoreUserGroup = productStoreUserGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStoreUserGroup);
    }

    /**
     * {@code DELETE  /product-store-user-groups/:id} : delete the "id" productStoreUserGroup.
     *
     * @param id the id of the productStoreUserGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-store-user-groups/{id}")
    public ResponseEntity<Void> deleteProductStoreUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete ProductStoreUserGroup : {}", id);
        productStoreUserGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
