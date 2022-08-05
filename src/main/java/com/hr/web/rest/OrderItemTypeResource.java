package com.hr.web.rest;

import com.hr.domain.OrderItemType;
import com.hr.repository.OrderItemTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.OrderItemType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemTypeResource.class);

    private static final String ENTITY_NAME = "orderItemType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderItemTypeRepository orderItemTypeRepository;

    public OrderItemTypeResource(OrderItemTypeRepository orderItemTypeRepository) {
        this.orderItemTypeRepository = orderItemTypeRepository;
    }

    /**
     * {@code POST  /order-item-types} : Create a new orderItemType.
     *
     * @param orderItemType the orderItemType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItemType, or with status {@code 400 (Bad Request)} if the orderItemType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-item-types")
    public ResponseEntity<OrderItemType> createOrderItemType(@Valid @RequestBody OrderItemType orderItemType) throws URISyntaxException {
        log.debug("REST request to save OrderItemType : {}", orderItemType);
        if (orderItemType.getId() != null) {
            throw new BadRequestAlertException("A new orderItemType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItemType result = orderItemTypeRepository.save(orderItemType);
        return ResponseEntity.created(new URI("/api/order-item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-item-types} : Updates an existing orderItemType.
     *
     * @param orderItemType the orderItemType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItemType,
     * or with status {@code 400 (Bad Request)} if the orderItemType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderItemType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-item-types")
    public ResponseEntity<OrderItemType> updateOrderItemType(@Valid @RequestBody OrderItemType orderItemType) throws URISyntaxException {
        log.debug("REST request to update OrderItemType : {}", orderItemType);
        if (orderItemType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItemType result = orderItemTypeRepository.save(orderItemType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderItemType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-item-types} : get all the orderItemTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderItemTypes in body.
     */
    @GetMapping("/order-item-types")
    public List<OrderItemType> getAllOrderItemTypes() {
        log.debug("REST request to get all OrderItemTypes");
        return orderItemTypeRepository.findAll();
    }

    /**
     * {@code GET  /order-item-types/:id} : get the "id" orderItemType.
     *
     * @param id the id of the orderItemType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItemType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-item-types/{id}")
    public ResponseEntity<OrderItemType> getOrderItemType(@PathVariable Long id) {
        log.debug("REST request to get OrderItemType : {}", id);
        Optional<OrderItemType> orderItemType = orderItemTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderItemType);
    }

    /**
     * {@code DELETE  /order-item-types/:id} : delete the "id" orderItemType.
     *
     * @param id the id of the orderItemType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-item-types/{id}")
    public ResponseEntity<Void> deleteOrderItemType(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemType : {}", id);
        orderItemTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
