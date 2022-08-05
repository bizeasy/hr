package com.hr.web.rest;

import com.hr.domain.PaymentApplication;
import com.hr.service.PaymentApplicationService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.PaymentApplicationCriteria;
import com.hr.service.PaymentApplicationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.PaymentApplication}.
 */
@RestController
@RequestMapping("/api")
public class PaymentApplicationResource {

    private final Logger log = LoggerFactory.getLogger(PaymentApplicationResource.class);

    private static final String ENTITY_NAME = "paymentApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentApplicationService paymentApplicationService;

    private final PaymentApplicationQueryService paymentApplicationQueryService;

    public PaymentApplicationResource(PaymentApplicationService paymentApplicationService, PaymentApplicationQueryService paymentApplicationQueryService) {
        this.paymentApplicationService = paymentApplicationService;
        this.paymentApplicationQueryService = paymentApplicationQueryService;
    }

    /**
     * {@code POST  /payment-applications} : Create a new paymentApplication.
     *
     * @param paymentApplication the paymentApplication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentApplication, or with status {@code 400 (Bad Request)} if the paymentApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-applications")
    public ResponseEntity<PaymentApplication> createPaymentApplication(@RequestBody PaymentApplication paymentApplication) throws URISyntaxException {
        log.debug("REST request to save PaymentApplication : {}", paymentApplication);
        if (paymentApplication.getId() != null) {
            throw new BadRequestAlertException("A new paymentApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentApplication result = paymentApplicationService.save(paymentApplication);
        return ResponseEntity.created(new URI("/api/payment-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-applications} : Updates an existing paymentApplication.
     *
     * @param paymentApplication the paymentApplication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentApplication,
     * or with status {@code 400 (Bad Request)} if the paymentApplication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentApplication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-applications")
    public ResponseEntity<PaymentApplication> updatePaymentApplication(@RequestBody PaymentApplication paymentApplication) throws URISyntaxException {
        log.debug("REST request to update PaymentApplication : {}", paymentApplication);
        if (paymentApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentApplication result = paymentApplicationService.save(paymentApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentApplication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-applications} : get all the paymentApplications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentApplications in body.
     */
    @GetMapping("/payment-applications")
    public ResponseEntity<List<PaymentApplication>> getAllPaymentApplications(PaymentApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentApplications by criteria: {}", criteria);
        Page<PaymentApplication> page = paymentApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-applications/count} : count all the paymentApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-applications/count")
    public ResponseEntity<Long> countPaymentApplications(PaymentApplicationCriteria criteria) {
        log.debug("REST request to count PaymentApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-applications/:id} : get the "id" paymentApplication.
     *
     * @param id the id of the paymentApplication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentApplication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-applications/{id}")
    public ResponseEntity<PaymentApplication> getPaymentApplication(@PathVariable Long id) {
        log.debug("REST request to get PaymentApplication : {}", id);
        Optional<PaymentApplication> paymentApplication = paymentApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentApplication);
    }

    /**
     * {@code DELETE  /payment-applications/:id} : delete the "id" paymentApplication.
     *
     * @param id the id of the paymentApplication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-applications/{id}")
    public ResponseEntity<Void> deletePaymentApplication(@PathVariable Long id) {
        log.debug("REST request to delete PaymentApplication : {}", id);
        paymentApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
