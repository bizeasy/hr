package com.hr.web.rest;

import com.hr.domain.PayGrade;
import com.hr.repository.PayGradeRepository;
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
 * REST controller for managing {@link com.hr.domain.PayGrade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayGradeResource {

    private final Logger log = LoggerFactory.getLogger(PayGradeResource.class);

    private static final String ENTITY_NAME = "payGrade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayGradeRepository payGradeRepository;

    public PayGradeResource(PayGradeRepository payGradeRepository) {
        this.payGradeRepository = payGradeRepository;
    }

    /**
     * {@code POST  /pay-grades} : Create a new payGrade.
     *
     * @param payGrade the payGrade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payGrade, or with status {@code 400 (Bad Request)} if the payGrade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pay-grades")
    public ResponseEntity<PayGrade> createPayGrade(@Valid @RequestBody PayGrade payGrade) throws URISyntaxException {
        log.debug("REST request to save PayGrade : {}", payGrade);
        if (payGrade.getId() != null) {
            throw new BadRequestAlertException("A new payGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayGrade result = payGradeRepository.save(payGrade);
        return ResponseEntity.created(new URI("/api/pay-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pay-grades} : Updates an existing payGrade.
     *
     * @param payGrade the payGrade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payGrade,
     * or with status {@code 400 (Bad Request)} if the payGrade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payGrade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pay-grades")
    public ResponseEntity<PayGrade> updatePayGrade(@Valid @RequestBody PayGrade payGrade) throws URISyntaxException {
        log.debug("REST request to update PayGrade : {}", payGrade);
        if (payGrade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayGrade result = payGradeRepository.save(payGrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payGrade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pay-grades} : get all the payGrades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payGrades in body.
     */
    @GetMapping("/pay-grades")
    public List<PayGrade> getAllPayGrades() {
        log.debug("REST request to get all PayGrades");
        return payGradeRepository.findAll();
    }

    /**
     * {@code GET  /pay-grades/:id} : get the "id" payGrade.
     *
     * @param id the id of the payGrade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payGrade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pay-grades/{id}")
    public ResponseEntity<PayGrade> getPayGrade(@PathVariable Long id) {
        log.debug("REST request to get PayGrade : {}", id);
        Optional<PayGrade> payGrade = payGradeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payGrade);
    }

    /**
     * {@code DELETE  /pay-grades/:id} : delete the "id" payGrade.
     *
     * @param id the id of the payGrade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pay-grades/{id}")
    public ResponseEntity<Void> deletePayGrade(@PathVariable Long id) {
        log.debug("REST request to delete PayGrade : {}", id);
        payGradeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
