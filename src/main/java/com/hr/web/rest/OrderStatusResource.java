package com.hr.web.rest;

import com.hr.domain.OrderStatus;
import com.hr.service.OrderStatusService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.OrderStatusCriteria;
import com.hr.service.OrderStatusQueryService;

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
 * REST controller for managing {@link com.hr.domain.OrderStatus}.
 */
@RestController
@RequestMapping("/api")
public class OrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(OrderStatusResource.class);

    private static final String ENTITY_NAME = "orderStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderStatusService orderStatusService;

    private final OrderStatusQueryService orderStatusQueryService;

    public OrderStatusResource(OrderStatusService orderStatusService, OrderStatusQueryService orderStatusQueryService) {
        this.orderStatusService = orderStatusService;
        this.orderStatusQueryService = orderStatusQueryService;
    }

    /**
     * {@code POST  /order-statuses} : Create a new orderStatus.
     *
     * @param orderStatus the orderStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderStatus, or with status {@code 400 (Bad Request)} if the orderStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-statuses")
    public ResponseEntity<OrderStatus> createOrderStatus(@RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to save OrderStatus : {}", orderStatus);
        if (orderStatus.getId() != null) {
            throw new BadRequestAlertException("A new orderStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderStatus result = orderStatusService.save(orderStatus);
        return ResponseEntity.created(new URI("/api/order-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-statuses} : Updates an existing orderStatus.
     *
     * @param orderStatus the orderStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderStatus,
     * or with status {@code 400 (Bad Request)} if the orderStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-statuses")
    public ResponseEntity<OrderStatus> updateOrderStatus(@RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to update OrderStatus : {}", orderStatus);
        if (orderStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderStatus result = orderStatusService.save(orderStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-statuses} : get all the orderStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderStatuses in body.
     */
    @GetMapping("/order-statuses")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses(OrderStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderStatuses by criteria: {}", criteria);
        Page<OrderStatus> page = orderStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-statuses/count} : count all the orderStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-statuses/count")
    public ResponseEntity<Long> countOrderStatuses(OrderStatusCriteria criteria) {
        log.debug("REST request to count OrderStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-statuses/:id} : get the "id" orderStatus.
     *
     * @param id the id of the orderStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-statuses/{id}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        log.debug("REST request to get OrderStatus : {}", id);
        Optional<OrderStatus> orderStatus = orderStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderStatus);
    }

    /**
     * {@code DELETE  /order-statuses/:id} : delete the "id" orderStatus.
     *
     * @param id the id of the orderStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-statuses/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        log.debug("REST request to delete OrderStatus : {}", id);
        orderStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
