package com.hr.web.rest;

import com.hr.domain.PaymentMethodType;
import com.hr.repository.PaymentMethodTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PaymentMethodType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentMethodTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodTypeResource.class);

    private static final String ENTITY_NAME = "paymentMethodType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentMethodTypeRepository paymentMethodTypeRepository;

    public PaymentMethodTypeResource(PaymentMethodTypeRepository paymentMethodTypeRepository) {
        this.paymentMethodTypeRepository = paymentMethodTypeRepository;
    }

    /**
     * {@code POST  /payment-method-types} : Create a new paymentMethodType.
     *
     * @param paymentMethodType the paymentMethodType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentMethodType, or with status {@code 400 (Bad Request)} if the paymentMethodType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-method-types")
    public ResponseEntity<PaymentMethodType> createPaymentMethodType(@Valid @RequestBody PaymentMethodType paymentMethodType) throws URISyntaxException {
        log.debug("REST request to save PaymentMethodType : {}", paymentMethodType);
        if (paymentMethodType.getId() != null) {
            throw new BadRequestAlertException("A new paymentMethodType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentMethodType result = paymentMethodTypeRepository.save(paymentMethodType);
        return ResponseEntity.created(new URI("/api/payment-method-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-method-types} : Updates an existing paymentMethodType.
     *
     * @param paymentMethodType the paymentMethodType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethodType,
     * or with status {@code 400 (Bad Request)} if the paymentMethodType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethodType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-method-types")
    public ResponseEntity<PaymentMethodType> updatePaymentMethodType(@Valid @RequestBody PaymentMethodType paymentMethodType) throws URISyntaxException {
        log.debug("REST request to update PaymentMethodType : {}", paymentMethodType);
        if (paymentMethodType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentMethodType result = paymentMethodTypeRepository.save(paymentMethodType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentMethodType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-method-types} : get all the paymentMethodTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentMethodTypes in body.
     */
    @GetMapping("/payment-method-types")
    public List<PaymentMethodType> getAllPaymentMethodTypes() {
        log.debug("REST request to get all PaymentMethodTypes");
        return paymentMethodTypeRepository.findAll();
    }

    /**
     * {@code GET  /payment-method-types/:id} : get the "id" paymentMethodType.
     *
     * @param id the id of the paymentMethodType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentMethodType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-method-types/{id}")
    public ResponseEntity<PaymentMethodType> getPaymentMethodType(@PathVariable Long id) {
        log.debug("REST request to get PaymentMethodType : {}", id);
        Optional<PaymentMethodType> paymentMethodType = paymentMethodTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentMethodType);
    }

    /**
     * {@code DELETE  /payment-method-types/:id} : delete the "id" paymentMethodType.
     *
     * @param id the id of the paymentMethodType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-method-types/{id}")
    public ResponseEntity<Void> deletePaymentMethodType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMethodType : {}", id);
        paymentMethodTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
