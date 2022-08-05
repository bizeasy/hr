package com.hr.service;

import com.hr.domain.OrderContactMech;
import com.hr.repository.OrderContactMechRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderContactMech}.
 */
@Service
@Transactional
public class OrderContactMechService {

    private final Logger log = LoggerFactory.getLogger(OrderContactMechService.class);

    private final OrderContactMechRepository orderContactMechRepository;

    public OrderContactMechService(OrderContactMechRepository orderContactMechRepository) {
        this.orderContactMechRepository = orderContactMechRepository;
    }

    /**
     * Save a orderContactMech.
     *
     * @param orderContactMech the entity to save.
     * @return the persisted entity.
     */
    public OrderContactMech save(OrderContactMech orderContactMech) {
        log.debug("Request to save OrderContactMech : {}", orderContactMech);
        return orderContactMechRepository.save(orderContactMech);
    }

    /**
     * Get all the orderContactMeches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderContactMech> findAll(Pageable pageable) {
        log.debug("Request to get all OrderContactMeches");
        return orderContactMechRepository.findAll(pageable);
    }


    /**
     * Get one orderContactMech by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderContactMech> findOne(Long id) {
        log.debug("Request to get OrderContactMech : {}", id);
        return orderContactMechRepository.findById(id);
    }

    /**
     * Delete the orderContactMech by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderContactMech : {}", id);
        orderContactMechRepository.deleteById(id);
    }
}
