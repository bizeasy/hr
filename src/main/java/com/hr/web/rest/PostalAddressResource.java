package com.hr.web.rest;

import com.hr.domain.PostalAddress;
import com.hr.service.PostalAddressService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.PostalAddressCriteria;
import com.hr.service.PostalAddressQueryService;

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
 * REST controller for managing {@link com.hr.domain.PostalAddress}.
 */
@RestController
@RequestMapping("/api")
public class PostalAddressResource {

    private final Logger log = LoggerFactory.getLogger(PostalAddressResource.class);

    private static final String ENTITY_NAME = "postalAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostalAddressService postalAddressService;

    private final PostalAddressQueryService postalAddressQueryService;

    public PostalAddressResource(PostalAddressService postalAddressService, PostalAddressQueryService postalAddressQueryService) {
        this.postalAddressService = postalAddressService;
        this.postalAddressQueryService = postalAddressQueryService;
    }

    /**
     * {@code POST  /postal-addresses} : Create a new postalAddress.
     *
     * @param postalAddress the postalAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postalAddress, or with status {@code 400 (Bad Request)} if the postalAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/postal-addresses")
    public ResponseEntity<PostalAddress> createPostalAddress(@Valid @RequestBody PostalAddress postalAddress) throws URISyntaxException {
        log.debug("REST request to save PostalAddress : {}", postalAddress);
        if (postalAddress.getId() != null) {
            throw new BadRequestAlertException("A new postalAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostalAddress result = postalAddressService.save(postalAddress);
        return ResponseEntity.created(new URI("/api/postal-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /postal-addresses} : Updates an existing postalAddress.
     *
     * @param postalAddress the postalAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postalAddress,
     * or with status {@code 400 (Bad Request)} if the postalAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postalAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/postal-addresses")
    public ResponseEntity<PostalAddress> updatePostalAddress(@Valid @RequestBody PostalAddress postalAddress) throws URISyntaxException {
        log.debug("REST request to update PostalAddress : {}", postalAddress);
        if (postalAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostalAddress result = postalAddressService.save(postalAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postalAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /postal-addresses} : get all the postalAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postalAddresses in body.
     */
    @GetMapping("/postal-addresses")
    public ResponseEntity<List<PostalAddress>> getAllPostalAddresses(PostalAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PostalAddresses by criteria: {}", criteria);
        Page<PostalAddress> page = postalAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /postal-addresses/count} : count all the postalAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/postal-addresses/count")
    public ResponseEntity<Long> countPostalAddresses(PostalAddressCriteria criteria) {
        log.debug("REST request to count PostalAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(postalAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /postal-addresses/:id} : get the "id" postalAddress.
     *
     * @param id the id of the postalAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postalAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/postal-addresses/{id}")
    public ResponseEntity<PostalAddress> getPostalAddress(@PathVariable Long id) {
        log.debug("REST request to get PostalAddress : {}", id);
        Optional<PostalAddress> postalAddress = postalAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postalAddress);
    }

    /**
     * {@code DELETE  /postal-addresses/:id} : delete the "id" postalAddress.
     *
     * @param id the id of the postalAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/postal-addresses/{id}")
    public ResponseEntity<Void> deletePostalAddress(@PathVariable Long id) {
        log.debug("REST request to delete PostalAddress : {}", id);
        postalAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
