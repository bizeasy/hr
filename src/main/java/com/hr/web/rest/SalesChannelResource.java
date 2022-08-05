package com.hr.web.rest;

import com.hr.domain.SalesChannel;
import com.hr.repository.SalesChannelRepository;
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
 * REST controller for managing {@link com.hr.domain.SalesChannel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SalesChannelResource {

    private final Logger log = LoggerFactory.getLogger(SalesChannelResource.class);

    private static final String ENTITY_NAME = "salesChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesChannelRepository salesChannelRepository;

    public SalesChannelResource(SalesChannelRepository salesChannelRepository) {
        this.salesChannelRepository = salesChannelRepository;
    }

    /**
     * {@code POST  /sales-channels} : Create a new salesChannel.
     *
     * @param salesChannel the salesChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesChannel, or with status {@code 400 (Bad Request)} if the salesChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-channels")
    public ResponseEntity<SalesChannel> createSalesChannel(@Valid @RequestBody SalesChannel salesChannel) throws URISyntaxException {
        log.debug("REST request to save SalesChannel : {}", salesChannel);
        if (salesChannel.getId() != null) {
            throw new BadRequestAlertException("A new salesChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesChannel result = salesChannelRepository.save(salesChannel);
        return ResponseEntity.created(new URI("/api/sales-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-channels} : Updates an existing salesChannel.
     *
     * @param salesChannel the salesChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesChannel,
     * or with status {@code 400 (Bad Request)} if the salesChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-channels")
    public ResponseEntity<SalesChannel> updateSalesChannel(@Valid @RequestBody SalesChannel salesChannel) throws URISyntaxException {
        log.debug("REST request to update SalesChannel : {}", salesChannel);
        if (salesChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesChannel result = salesChannelRepository.save(salesChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesChannel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales-channels} : get all the salesChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesChannels in body.
     */
    @GetMapping("/sales-channels")
    public List<SalesChannel> getAllSalesChannels() {
        log.debug("REST request to get all SalesChannels");
        return salesChannelRepository.findAll();
    }

    /**
     * {@code GET  /sales-channels/:id} : get the "id" salesChannel.
     *
     * @param id the id of the salesChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-channels/{id}")
    public ResponseEntity<SalesChannel> getSalesChannel(@PathVariable Long id) {
        log.debug("REST request to get SalesChannel : {}", id);
        Optional<SalesChannel> salesChannel = salesChannelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(salesChannel);
    }

    /**
     * {@code DELETE  /sales-channels/:id} : delete the "id" salesChannel.
     *
     * @param id the id of the salesChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-channels/{id}")
    public ResponseEntity<Void> deleteSalesChannel(@PathVariable Long id) {
        log.debug("REST request to delete SalesChannel : {}", id);
        salesChannelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
