package com.hr.web.rest;

import com.hr.domain.OrderItemBilling;
import com.hr.service.OrderItemBillingService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.OrderItemBillingCriteria;
import com.hr.service.OrderItemBillingQueryService;

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
 * REST controller for managing {@link com.hr.domain.OrderItemBilling}.
 */
@RestController
@RequestMapping("/api")
public class OrderItemBillingResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemBillingResource.class);

    private static final String ENTITY_NAME = "orderItemBilling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderItemBillingService orderItemBillingService;

    private final OrderItemBillingQueryService orderItemBillingQueryService;

    public OrderItemBillingResource(OrderItemBillingService orderItemBillingService, OrderItemBillingQueryService orderItemBillingQueryService) {
        this.orderItemBillingService = orderItemBillingService;
        this.orderItemBillingQueryService = orderItemBillingQueryService;
    }

    /**
     * {@code POST  /order-item-billings} : Create a new orderItemBilling.
     *
     * @param orderItemBilling the orderItemBilling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItemBilling, or with status {@code 400 (Bad Request)} if the orderItemBilling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-item-billings")
    public ResponseEntity<OrderItemBilling> createOrderItemBilling(@RequestBody OrderItemBilling orderItemBilling) throws URISyntaxException {
        log.debug("REST request to save OrderItemBilling : {}", orderItemBilling);
        if (orderItemBilling.getId() != null) {
            throw new BadRequestAlertException("A new orderItemBilling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItemBilling result = orderItemBillingService.save(orderItemBilling);
        return ResponseEntity.created(new URI("/api/order-item-billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-item-billings} : Updates an existing orderItemBilling.
     *
     * @param orderItemBilling the orderItemBilling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItemBilling,
     * or with status {@code 400 (Bad Request)} if the orderItemBilling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderItemBilling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-item-billings")
    public ResponseEntity<OrderItemBilling> updateOrderItemBilling(@RequestBody OrderItemBilling orderItemBilling) throws URISyntaxException {
        log.debug("REST request to update OrderItemBilling : {}", orderItemBilling);
        if (orderItemBilling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItemBilling result = orderItemBillingService.save(orderItemBilling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderItemBilling.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-item-billings} : get all the orderItemBillings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderItemBillings in body.
     */
    @GetMapping("/order-item-billings")
    public ResponseEntity<List<OrderItemBilling>> getAllOrderItemBillings(OrderItemBillingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderItemBillings by criteria: {}", criteria);
        Page<OrderItemBilling> page = orderItemBillingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-item-billings/count} : count all the orderItemBillings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-item-billings/count")
    public ResponseEntity<Long> countOrderItemBillings(OrderItemBillingCriteria criteria) {
        log.debug("REST request to count OrderItemBillings by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderItemBillingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-item-billings/:id} : get the "id" orderItemBilling.
     *
     * @param id the id of the orderItemBilling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItemBilling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-item-billings/{id}")
    public ResponseEntity<OrderItemBilling> getOrderItemBilling(@PathVariable Long id) {
        log.debug("REST request to get OrderItemBilling : {}", id);
        Optional<OrderItemBilling> orderItemBilling = orderItemBillingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderItemBilling);
    }

    /**
     * {@code DELETE  /order-item-billings/:id} : delete the "id" orderItemBilling.
     *
     * @param id the id of the orderItemBilling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-item-billings/{id}")
    public ResponseEntity<Void> deleteOrderItemBilling(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemBilling : {}", id);
        orderItemBillingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
