package com.hr.web.rest;

import com.hr.domain.OrderType;
import com.hr.repository.OrderTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.OrderType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderTypeResource {

    private final Logger log = LoggerFactory.getLogger(OrderTypeResource.class);

    private static final String ENTITY_NAME = "orderType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTypeRepository orderTypeRepository;

    public OrderTypeResource(OrderTypeRepository orderTypeRepository) {
        this.orderTypeRepository = orderTypeRepository;
    }

    /**
     * {@code POST  /order-types} : Create a new orderType.
     *
     * @param orderType the orderType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderType, or with status {@code 400 (Bad Request)} if the orderType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-types")
    public ResponseEntity<OrderType> createOrderType(@Valid @RequestBody OrderType orderType) throws URISyntaxException {
        log.debug("REST request to save OrderType : {}", orderType);
        if (orderType.getId() != null) {
            throw new BadRequestAlertException("A new orderType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderType result = orderTypeRepository.save(orderType);
        return ResponseEntity.created(new URI("/api/order-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-types} : Updates an existing orderType.
     *
     * @param orderType the orderType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderType,
     * or with status {@code 400 (Bad Request)} if the orderType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-types")
    public ResponseEntity<OrderType> updateOrderType(@Valid @RequestBody OrderType orderType) throws URISyntaxException {
        log.debug("REST request to update OrderType : {}", orderType);
        if (orderType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderType result = orderTypeRepository.save(orderType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-types} : get all the orderTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTypes in body.
     */
    @GetMapping("/order-types")
    public List<OrderType> getAllOrderTypes() {
        log.debug("REST request to get all OrderTypes");
        return orderTypeRepository.findAll();
    }

    /**
     * {@code GET  /order-types/:id} : get the "id" orderType.
     *
     * @param id the id of the orderType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-types/{id}")
    public ResponseEntity<OrderType> getOrderType(@PathVariable Long id) {
        log.debug("REST request to get OrderType : {}", id);
        Optional<OrderType> orderType = orderTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderType);
    }

    /**
     * {@code DELETE  /order-types/:id} : delete the "id" orderType.
     *
     * @param id the id of the orderType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-types/{id}")
    public ResponseEntity<Void> deleteOrderType(@PathVariable Long id) {
        log.debug("REST request to delete OrderType : {}", id);
        orderTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
