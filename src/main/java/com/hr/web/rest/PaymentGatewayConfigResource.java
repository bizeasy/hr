package com.hr.web.rest;

import com.hr.domain.PaymentGatewayConfig;
import com.hr.repository.PaymentGatewayConfigRepository;
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
 * REST controller for managing {@link com.hr.domain.PaymentGatewayConfig}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentGatewayConfigResource {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayConfigResource.class);

    private static final String ENTITY_NAME = "paymentGatewayConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentGatewayConfigRepository paymentGatewayConfigRepository;

    public PaymentGatewayConfigResource(PaymentGatewayConfigRepository paymentGatewayConfigRepository) {
        this.paymentGatewayConfigRepository = paymentGatewayConfigRepository;
    }

    /**
     * {@code POST  /payment-gateway-configs} : Create a new paymentGatewayConfig.
     *
     * @param paymentGatewayConfig the paymentGatewayConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentGatewayConfig, or with status {@code 400 (Bad Request)} if the paymentGatewayConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-gateway-configs")
    public ResponseEntity<PaymentGatewayConfig> createPaymentGatewayConfig(@Valid @RequestBody PaymentGatewayConfig paymentGatewayConfig) throws URISyntaxException {
        log.debug("REST request to save PaymentGatewayConfig : {}", paymentGatewayConfig);
        if (paymentGatewayConfig.getId() != null) {
            throw new BadRequestAlertException("A new paymentGatewayConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentGatewayConfig result = paymentGatewayConfigRepository.save(paymentGatewayConfig);
        return ResponseEntity.created(new URI("/api/payment-gateway-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-gateway-configs} : Updates an existing paymentGatewayConfig.
     *
     * @param paymentGatewayConfig the paymentGatewayConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGatewayConfig,
     * or with status {@code 400 (Bad Request)} if the paymentGatewayConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentGatewayConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-gateway-configs")
    public ResponseEntity<PaymentGatewayConfig> updatePaymentGatewayConfig(@Valid @RequestBody PaymentGatewayConfig paymentGatewayConfig) throws URISyntaxException {
        log.debug("REST request to update PaymentGatewayConfig : {}", paymentGatewayConfig);
        if (paymentGatewayConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentGatewayConfig result = paymentGatewayConfigRepository.save(paymentGatewayConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGatewayConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-gateway-configs} : get all the paymentGatewayConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentGatewayConfigs in body.
     */
    @GetMapping("/payment-gateway-configs")
    public List<PaymentGatewayConfig> getAllPaymentGatewayConfigs() {
        log.debug("REST request to get all PaymentGatewayConfigs");
        return paymentGatewayConfigRepository.findAll();
    }

    /**
     * {@code GET  /payment-gateway-configs/:id} : get the "id" paymentGatewayConfig.
     *
     * @param id the id of the paymentGatewayConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentGatewayConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-gateway-configs/{id}")
    public ResponseEntity<PaymentGatewayConfig> getPaymentGatewayConfig(@PathVariable Long id) {
        log.debug("REST request to get PaymentGatewayConfig : {}", id);
        Optional<PaymentGatewayConfig> paymentGatewayConfig = paymentGatewayConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentGatewayConfig);
    }

    /**
     * {@code DELETE  /payment-gateway-configs/:id} : delete the "id" paymentGatewayConfig.
     *
     * @param id the id of the paymentGatewayConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-gateway-configs/{id}")
    public ResponseEntity<Void> deletePaymentGatewayConfig(@PathVariable Long id) {
        log.debug("REST request to delete PaymentGatewayConfig : {}", id);
        paymentGatewayConfigRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
