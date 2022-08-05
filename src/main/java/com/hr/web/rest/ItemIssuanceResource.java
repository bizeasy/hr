package com.hr.web.rest;

import com.hr.domain.ItemIssuance;
import com.hr.service.ItemIssuanceService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ItemIssuanceCriteria;
import com.hr.service.ItemIssuanceQueryService;

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
 * REST controller for managing {@link com.hr.domain.ItemIssuance}.
 */
@RestController
@RequestMapping("/api")
public class ItemIssuanceResource {

    private final Logger log = LoggerFactory.getLogger(ItemIssuanceResource.class);

    private static final String ENTITY_NAME = "itemIssuance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemIssuanceService itemIssuanceService;

    private final ItemIssuanceQueryService itemIssuanceQueryService;

    public ItemIssuanceResource(ItemIssuanceService itemIssuanceService, ItemIssuanceQueryService itemIssuanceQueryService) {
        this.itemIssuanceService = itemIssuanceService;
        this.itemIssuanceQueryService = itemIssuanceQueryService;
    }

    /**
     * {@code POST  /item-issuances} : Create a new itemIssuance.
     *
     * @param itemIssuance the itemIssuance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemIssuance, or with status {@code 400 (Bad Request)} if the itemIssuance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-issuances")
    public ResponseEntity<ItemIssuance> createItemIssuance(@Valid @RequestBody ItemIssuance itemIssuance) throws URISyntaxException {
        log.debug("REST request to save ItemIssuance : {}", itemIssuance);
        if (itemIssuance.getId() != null) {
            throw new BadRequestAlertException("A new itemIssuance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemIssuance result = itemIssuanceService.save(itemIssuance);
        return ResponseEntity.created(new URI("/api/item-issuances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-issuances} : Updates an existing itemIssuance.
     *
     * @param itemIssuance the itemIssuance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemIssuance,
     * or with status {@code 400 (Bad Request)} if the itemIssuance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemIssuance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-issuances")
    public ResponseEntity<ItemIssuance> updateItemIssuance(@Valid @RequestBody ItemIssuance itemIssuance) throws URISyntaxException {
        log.debug("REST request to update ItemIssuance : {}", itemIssuance);
        if (itemIssuance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemIssuance result = itemIssuanceService.save(itemIssuance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemIssuance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-issuances} : get all the itemIssuances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemIssuances in body.
     */
    @GetMapping("/item-issuances")
    public ResponseEntity<List<ItemIssuance>> getAllItemIssuances(ItemIssuanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemIssuances by criteria: {}", criteria);
        Page<ItemIssuance> page = itemIssuanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-issuances/count} : count all the itemIssuances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-issuances/count")
    public ResponseEntity<Long> countItemIssuances(ItemIssuanceCriteria criteria) {
        log.debug("REST request to count ItemIssuances by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemIssuanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-issuances/:id} : get the "id" itemIssuance.
     *
     * @param id the id of the itemIssuance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemIssuance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-issuances/{id}")
    public ResponseEntity<ItemIssuance> getItemIssuance(@PathVariable Long id) {
        log.debug("REST request to get ItemIssuance : {}", id);
        Optional<ItemIssuance> itemIssuance = itemIssuanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemIssuance);
    }

    /**
     * {@code DELETE  /item-issuances/:id} : delete the "id" itemIssuance.
     *
     * @param id the id of the itemIssuance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-issuances/{id}")
    public ResponseEntity<Void> deleteItemIssuance(@PathVariable Long id) {
        log.debug("REST request to delete ItemIssuance : {}", id);
        itemIssuanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
