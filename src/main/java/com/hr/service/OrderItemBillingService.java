package com.hr.service;

import com.hr.domain.OrderItemBilling;
import com.hr.repository.OrderItemBillingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderItemBilling}.
 */
@Service
@Transactional
public class OrderItemBillingService {

    private final Logger log = LoggerFactory.getLogger(OrderItemBillingService.class);

    private final OrderItemBillingRepository orderItemBillingRepository;

    public OrderItemBillingService(OrderItemBillingRepository orderItemBillingRepository) {
        this.orderItemBillingRepository = orderItemBillingRepository;
    }

    /**
     * Save a orderItemBilling.
     *
     * @param orderItemBilling the entity to save.
     * @return the persisted entity.
     */
    public OrderItemBilling save(OrderItemBilling orderItemBilling) {
        log.debug("Request to save OrderItemBilling : {}", orderItemBilling);
        return orderItemBillingRepository.save(orderItemBilling);
    }

    /**
     * Get all the orderItemBillings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderItemBilling> findAll(Pageable pageable) {
        log.debug("Request to get all OrderItemBillings");
        return orderItemBillingRepository.findAll(pageable);
    }


    /**
     * Get one orderItemBilling by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderItemBilling> findOne(Long id) {
        log.debug("Request to get OrderItemBilling : {}", id);
        return orderItemBillingRepository.findById(id);
    }

    /**
     * Delete the orderItemBilling by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderItemBilling : {}", id);
        orderItemBillingRepository.deleteById(id);
    }
}
