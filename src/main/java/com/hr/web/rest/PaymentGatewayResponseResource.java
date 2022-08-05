package com.hr.web.rest;

import com.hr.domain.PaymentGatewayResponse;
import com.hr.repository.PaymentGatewayResponseRepository;
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
 * REST controller for managing {@link com.hr.domain.PaymentGatewayResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentGatewayResponseResource {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayResponseResource.class);

    private static final String ENTITY_NAME = "paymentGatewayResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentGatewayResponseRepository paymentGatewayResponseRepository;

    public PaymentGatewayResponseResource(PaymentGatewayResponseRepository paymentGatewayResponseRepository) {
        this.paymentGatewayResponseRepository = paymentGatewayResponseRepository;
    }

    /**
     * {@code POST  /payment-gateway-responses} : Create a new paymentGatewayResponse.
     *
     * @param paymentGatewayResponse the paymentGatewayResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentGatewayResponse, or with status {@code 400 (Bad Request)} if the paymentGatewayResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-gateway-responses")
    public ResponseEntity<PaymentGatewayResponse> createPaymentGatewayResponse(@Valid @RequestBody PaymentGatewayResponse paymentGatewayResponse) throws URISyntaxException {
        log.debug("REST request to save PaymentGatewayResponse : {}", paymentGatewayResponse);
        if (paymentGatewayResponse.getId() != null) {
            throw new BadRequestAlertException("A new paymentGatewayResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentGatewayResponse result = paymentGatewayResponseRepository.save(paymentGatewayResponse);
        return ResponseEntity.created(new URI("/api/payment-gateway-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-gateway-responses} : Updates an existing paymentGatewayResponse.
     *
     * @param paymentGatewayResponse the paymentGatewayResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGatewayResponse,
     * or with status {@code 400 (Bad Request)} if the paymentGatewayResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentGatewayResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-gateway-responses")
    public ResponseEntity<PaymentGatewayResponse> updatePaymentGatewayResponse(@Valid @RequestBody PaymentGatewayResponse paymentGatewayResponse) throws URISyntaxException {
        log.debug("REST request to update PaymentGatewayResponse : {}", paymentGatewayResponse);
        if (paymentGatewayResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentGatewayResponse result = paymentGatewayResponseRepository.save(paymentGatewayResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGatewayResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-gateway-responses} : get all the paymentGatewayResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentGatewayResponses in body.
     */
    @GetMapping("/payment-gateway-responses")
    public List<PaymentGatewayResponse> getAllPaymentGatewayResponses() {
        log.debug("REST request to get all PaymentGatewayResponses");
        return paymentGatewayResponseRepository.findAll();
    }

    /**
     * {@code GET  /payment-gateway-responses/:id} : get the "id" paymentGatewayResponse.
     *
     * @param id the id of the paymentGatewayResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentGatewayResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-gateway-responses/{id}")
    public ResponseEntity<PaymentGatewayResponse> getPaymentGatewayResponse(@PathVariable Long id) {
        log.debug("REST request to get PaymentGatewayResponse : {}", id);
        Optional<PaymentGatewayResponse> paymentGatewayResponse = paymentGatewayResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentGatewayResponse);
    }

    /**
     * {@code DELETE  /payment-gateway-responses/:id} : delete the "id" paymentGatewayResponse.
     *
     * @param id the id of the paymentGatewayResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-gateway-responses/{id}")
    public ResponseEntity<Void> deletePaymentGatewayResponse(@PathVariable Long id) {
        log.debug("REST request to delete PaymentGatewayResponse : {}", id);
        paymentGatewayResponseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
