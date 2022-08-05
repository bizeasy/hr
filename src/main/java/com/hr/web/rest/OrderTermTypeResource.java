package com.hr.web.rest;

import com.hr.domain.OrderTermType;
import com.hr.repository.OrderTermTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.OrderTermType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrderTermTypeResource {

    private final Logger log = LoggerFactory.getLogger(OrderTermTypeResource.class);

    private static final String ENTITY_NAME = "orderTermType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTermTypeRepository orderTermTypeRepository;

    public OrderTermTypeResource(OrderTermTypeRepository orderTermTypeRepository) {
        this.orderTermTypeRepository = orderTermTypeRepository;
    }

    /**
     * {@code POST  /order-term-types} : Create a new orderTermType.
     *
     * @param orderTermType the orderTermType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTermType, or with status {@code 400 (Bad Request)} if the orderTermType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-term-types")
    public ResponseEntity<OrderTermType> createOrderTermType(@Valid @RequestBody OrderTermType orderTermType) throws URISyntaxException {
        log.debug("REST request to save OrderTermType : {}", orderTermType);
        if (orderTermType.getId() != null) {
            throw new BadRequestAlertException("A new orderTermType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTermType result = orderTermTypeRepository.save(orderTermType);
        return ResponseEntity.created(new URI("/api/order-term-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-term-types} : Updates an existing orderTermType.
     *
     * @param orderTermType the orderTermType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTermType,
     * or with status {@code 400 (Bad Request)} if the orderTermType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTermType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-term-types")
    public ResponseEntity<OrderTermType> updateOrderTermType(@Valid @RequestBody OrderTermType orderTermType) throws URISyntaxException {
        log.debug("REST request to update OrderTermType : {}", orderTermType);
        if (orderTermType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTermType result = orderTermTypeRepository.save(orderTermType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTermType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-term-types} : get all the orderTermTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTermTypes in body.
     */
    @GetMapping("/order-term-types")
    public List<OrderTermType> getAllOrderTermTypes() {
        log.debug("REST request to get all OrderTermTypes");
        return orderTermTypeRepository.findAll();
    }

    /**
     * {@code GET  /order-term-types/:id} : get the "id" orderTermType.
     *
     * @param id the id of the orderTermType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTermType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-term-types/{id}")
    public ResponseEntity<OrderTermType> getOrderTermType(@PathVariable Long id) {
        log.debug("REST request to get OrderTermType : {}", id);
        Optional<OrderTermType> orderTermType = orderTermTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderTermType);
    }

    /**
     * {@code DELETE  /order-term-types/:id} : delete the "id" orderTermType.
     *
     * @param id the id of the orderTermType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-term-types/{id}")
    public ResponseEntity<Void> deleteOrderTermType(@PathVariable Long id) {
        log.debug("REST request to delete OrderTermType : {}", id);
        orderTermTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
