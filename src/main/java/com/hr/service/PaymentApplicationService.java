package com.hr.service;

import com.hr.domain.PaymentApplication;
import com.hr.repository.PaymentApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaymentApplication}.
 */
@Service
@Transactional
public class PaymentApplicationService {

    private final Logger log = LoggerFactory.getLogger(PaymentApplicationService.class);

    private final PaymentApplicationRepository paymentApplicationRepository;

    public PaymentApplicationService(PaymentApplicationRepository paymentApplicationRepository) {
        this.paymentApplicationRepository = paymentApplicationRepository;
    }

    /**
     * Save a paymentApplication.
     *
     * @param paymentApplication the entity to save.
     * @return the persisted entity.
     */
    public PaymentApplication save(PaymentApplication paymentApplication) {
        log.debug("Request to save PaymentApplication : {}", paymentApplication);
        return paymentApplicationRepository.save(paymentApplication);
    }

    /**
     * Get all the paymentApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentApplication> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentApplications");
        return paymentApplicationRepository.findAll(pageable);
    }


    /**
     * Get one paymentApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentApplication> findOne(Long id) {
        log.debug("Request to get PaymentApplication : {}", id);
        return paymentApplicationRepository.findById(id);
    }

    /**
     * Delete the paymentApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentApplication : {}", id);
        paymentApplicationRepository.deleteById(id);
    }
}
