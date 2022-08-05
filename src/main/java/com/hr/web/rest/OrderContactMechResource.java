package com.hr.web.rest;

import com.hr.domain.OrderContactMech;
import com.hr.service.OrderContactMechService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.OrderContactMechCriteria;
import com.hr.service.OrderContactMechQueryService;

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
 * REST controller for managing {@link com.hr.domain.OrderContactMech}.
 */
@RestController
@RequestMapping("/api")
public class OrderContactMechResource {

    private final Logger log = LoggerFactory.getLogger(OrderContactMechResource.class);

    private static final String ENTITY_NAME = "orderContactMech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderContactMechService orderContactMechService;

    private final OrderContactMechQueryService orderContactMechQueryService;

    public OrderContactMechResource(OrderContactMechService orderContactMechService, OrderContactMechQueryService orderContactMechQueryService) {
        this.orderContactMechService = orderContactMechService;
        this.orderContactMechQueryService = orderContactMechQueryService;
    }

    /**
     * {@code POST  /order-contact-meches} : Create a new orderContactMech.
     *
     * @param orderContactMech the orderContactMech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderContactMech, or with status {@code 400 (Bad Request)} if the orderContactMech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-contact-meches")
    public ResponseEntity<OrderContactMech> createOrderContactMech(@RequestBody OrderContactMech orderContactMech) throws URISyntaxException {
        log.debug("REST request to save OrderContactMech : {}", orderContactMech);
        if (orderContactMech.getId() != null) {
            throw new BadRequestAlertException("A new orderContactMech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderContactMech result = orderContactMechService.save(orderContactMech);
        return ResponseEntity.created(new URI("/api/order-contact-meches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-contact-meches} : Updates an existing orderContactMech.
     *
     * @param orderContactMech the orderContactMech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderContactMech,
     * or with status {@code 400 (Bad Request)} if the orderContactMech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderContactMech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-contact-meches")
    public ResponseEntity<OrderContactMech> updateOrderContactMech(@RequestBody OrderContactMech orderContactMech) throws URISyntaxException {
        log.debug("REST request to update OrderContactMech : {}", orderContactMech);
        if (orderContactMech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderContactMech result = orderContactMechService.save(orderContactMech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderContactMech.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-contact-meches} : get all the orderContactMeches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderContactMeches in body.
     */
    @GetMapping("/order-contact-meches")
    public ResponseEntity<List<OrderContactMech>> getAllOrderContactMeches(OrderContactMechCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderContactMeches by criteria: {}", criteria);
        Page<OrderContactMech> page = orderContactMechQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-contact-meches/count} : count all the orderContactMeches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-contact-meches/count")
    public ResponseEntity<Long> countOrderContactMeches(OrderContactMechCriteria criteria) {
        log.debug("REST request to count OrderContactMeches by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderContactMechQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-contact-meches/:id} : get the "id" orderContactMech.
     *
     * @param id the id of the orderContactMech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderContactMech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-contact-meches/{id}")
    public ResponseEntity<OrderContactMech> getOrderContactMech(@PathVariable Long id) {
        log.debug("REST request to get OrderContactMech : {}", id);
        Optional<OrderContactMech> orderContactMech = orderContactMechService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderContactMech);
    }

    /**
     * {@code DELETE  /order-contact-meches/:id} : delete the "id" orderContactMech.
     *
     * @param id the id of the orderContactMech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-contact-meches/{id}")
    public ResponseEntity<Void> deleteOrderContactMech(@PathVariable Long id) {
        log.debug("REST request to delete OrderContactMech : {}", id);
        orderContactMechService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
