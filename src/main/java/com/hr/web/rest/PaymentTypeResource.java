package com.hr.web.rest;

import com.hr.domain.PaymentType;
import com.hr.repository.PaymentTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PaymentType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeResource.class);

    private static final String ENTITY_NAME = "paymentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeResource(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    /**
     * {@code POST  /payment-types} : Create a new paymentType.
     *
     * @param paymentType the paymentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentType, or with status {@code 400 (Bad Request)} if the paymentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-types")
    public ResponseEntity<PaymentType> createPaymentType(@Valid @RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to save PaymentType : {}", paymentType);
        if (paymentType.getId() != null) {
            throw new BadRequestAlertException("A new paymentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentType result = paymentTypeRepository.save(paymentType);
        return ResponseEntity.created(new URI("/api/payment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-types} : Updates an existing paymentType.
     *
     * @param paymentType the paymentType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentType,
     * or with status {@code 400 (Bad Request)} if the paymentType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-types")
    public ResponseEntity<PaymentType> updatePaymentType(@Valid @RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to update PaymentType : {}", paymentType);
        if (paymentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentType result = paymentTypeRepository.save(paymentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-types} : get all the paymentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTypes in body.
     */
    @GetMapping("/payment-types")
    public List<PaymentType> getAllPaymentTypes() {
        log.debug("REST request to get all PaymentTypes");
        return paymentTypeRepository.findAll();
    }

    /**
     * {@code GET  /payment-types/:id} : get the "id" paymentType.
     *
     * @param id the id of the paymentType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-types/{id}")
    public ResponseEntity<PaymentType> getPaymentType(@PathVariable Long id) {
        log.debug("REST request to get PaymentType : {}", id);
        Optional<PaymentType> paymentType = paymentTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentType);
    }

    /**
     * {@code DELETE  /payment-types/:id} : delete the "id" paymentType.
     *
     * @param id the id of the paymentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-types/{id}")
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentType : {}", id);
        paymentTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
