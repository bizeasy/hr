package com.hr.web.rest;

import com.hr.domain.PayrollPreference;
import com.hr.repository.PayrollPreferenceRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.PayrollPreference}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayrollPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PayrollPreferenceResource.class);

    private static final String ENTITY_NAME = "payrollPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollPreferenceRepository payrollPreferenceRepository;

    public PayrollPreferenceResource(PayrollPreferenceRepository payrollPreferenceRepository) {
        this.payrollPreferenceRepository = payrollPreferenceRepository;
    }

    /**
     * {@code POST  /payroll-preferences} : Create a new payrollPreference.
     *
     * @param payrollPreference the payrollPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollPreference, or with status {@code 400 (Bad Request)} if the payrollPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payroll-preferences")
    public ResponseEntity<PayrollPreference> createPayrollPreference(@RequestBody PayrollPreference payrollPreference) throws URISyntaxException {
        log.debug("REST request to save PayrollPreference : {}", payrollPreference);
        if (payrollPreference.getId() != null) {
            throw new BadRequestAlertException("A new payrollPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollPreference result = payrollPreferenceRepository.save(payrollPreference);
        return ResponseEntity.created(new URI("/api/payroll-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payroll-preferences} : Updates an existing payrollPreference.
     *
     * @param payrollPreference the payrollPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollPreference,
     * or with status {@code 400 (Bad Request)} if the payrollPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payroll-preferences")
    public ResponseEntity<PayrollPreference> updatePayrollPreference(@RequestBody PayrollPreference payrollPreference) throws URISyntaxException {
        log.debug("REST request to update PayrollPreference : {}", payrollPreference);
        if (payrollPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayrollPreference result = payrollPreferenceRepository.save(payrollPreference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollPreference.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payroll-preferences} : get all the payrollPreferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrollPreferences in body.
     */
    @GetMapping("/payroll-preferences")
    public List<PayrollPreference> getAllPayrollPreferences() {
        log.debug("REST request to get all PayrollPreferences");
        return payrollPreferenceRepository.findAll();
    }

    /**
     * {@code GET  /payroll-preferences/:id} : get the "id" payrollPreference.
     *
     * @param id the id of the payrollPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payroll-preferences/{id}")
    public ResponseEntity<PayrollPreference> getPayrollPreference(@PathVariable Long id) {
        log.debug("REST request to get PayrollPreference : {}", id);
        Optional<PayrollPreference> payrollPreference = payrollPreferenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payrollPreference);
    }

    /**
     * {@code DELETE  /payroll-preferences/:id} : delete the "id" payrollPreference.
     *
     * @param id the id of the payrollPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payroll-preferences/{id}")
    public ResponseEntity<Void> deletePayrollPreference(@PathVariable Long id) {
        log.debug("REST request to delete PayrollPreference : {}", id);
        payrollPreferenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
