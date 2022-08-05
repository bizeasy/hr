package com.hr.web.rest;

import com.hr.domain.OrderTerm;
import com.hr.repository.OrderTermRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.OrderTerm}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderTermResource {

    private final Logger log = LoggerFactory.getLogger(OrderTermResource.class);

    private static final String ENTITY_NAME = "orderTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTermRepository orderTermRepository;

    public OrderTermResource(OrderTermRepository orderTermRepository) {
        this.orderTermRepository = orderTermRepository;
    }

    /**
     * {@code POST  /order-terms} : Create a new orderTerm.
     *
     * @param orderTerm the orderTerm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTerm, or with status {@code 400 (Bad Request)} if the orderTerm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-terms")
    public ResponseEntity<OrderTerm> createOrderTerm(@RequestBody OrderTerm orderTerm) throws URISyntaxException {
        log.debug("REST request to save OrderTerm : {}", orderTerm);
        if (orderTerm.getId() != null) {
            throw new BadRequestAlertException("A new orderTerm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTerm result = orderTermRepository.save(orderTerm);
        return ResponseEntity.created(new URI("/api/order-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-terms} : Updates an existing orderTerm.
     *
     * @param orderTerm the orderTerm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTerm,
     * or with status {@code 400 (Bad Request)} if the orderTerm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTerm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-terms")
    public ResponseEntity<OrderTerm> updateOrderTerm(@RequestBody OrderTerm orderTerm) throws URISyntaxException {
        log.debug("REST request to update OrderTerm : {}", orderTerm);
        if (orderTerm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTerm result = orderTermRepository.save(orderTerm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTerm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-terms} : get all the orderTerms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTerms in body.
     */
    @GetMapping("/order-terms")
    public List<OrderTerm> getAllOrderTerms() {
        log.debug("REST request to get all OrderTerms");
        return orderTermRepository.findAll();
    }

    /**
     * {@code GET  /order-terms/:id} : get the "id" orderTerm.
     *
     * @param id the id of the orderTerm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTerm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-terms/{id}")
    public ResponseEntity<OrderTerm> getOrderTerm(@PathVariable Long id) {
        log.debug("REST request to get OrderTerm : {}", id);
        Optional<OrderTerm> orderTerm = orderTermRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderTerm);
    }

    /**
     * {@code DELETE  /order-terms/:id} : delete the "id" orderTerm.
     *
     * @param id the id of the orderTerm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-terms/{id}")
    public ResponseEntity<Void> deleteOrderTerm(@PathVariable Long id) {
        log.debug("REST request to delete OrderTerm : {}", id);
        orderTermRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
