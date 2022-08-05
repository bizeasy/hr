package com.hr.web.rest;

import com.hr.domain.ContactMechPurpose;
import com.hr.repository.ContactMechPurposeRepository;
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
 * REST controller for managing {@link com.hr.domain.ContactMechPurpose}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactMechPurposeResource {

    private final Logger log = LoggerFactory.getLogger(ContactMechPurposeResource.class);

    private static final String ENTITY_NAME = "contactMechPurpose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactMechPurposeRepository contactMechPurposeRepository;

    public ContactMechPurposeResource(ContactMechPurposeRepository contactMechPurposeRepository) {
        this.contactMechPurposeRepository = contactMechPurposeRepository;
    }

    /**
     * {@code POST  /contact-mech-purposes} : Create a new contactMechPurpose.
     *
     * @param contactMechPurpose the contactMechPurpose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactMechPurpose, or with status {@code 400 (Bad Request)} if the contactMechPurpose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-mech-purposes")
    public ResponseEntity<ContactMechPurpose> createContactMechPurpose(@Valid @RequestBody ContactMechPurpose contactMechPurpose) throws URISyntaxException {
        log.debug("REST request to save ContactMechPurpose : {}", contactMechPurpose);
        if (contactMechPurpose.getId() != null) {
            throw new BadRequestAlertException("A new contactMechPurpose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactMechPurpose result = contactMechPurposeRepository.save(contactMechPurpose);
        return ResponseEntity.created(new URI("/api/contact-mech-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-mech-purposes} : Updates an existing contactMechPurpose.
     *
     * @param contactMechPurpose the contactMechPurpose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactMechPurpose,
     * or with status {@code 400 (Bad Request)} if the contactMechPurpose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactMechPurpose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-mech-purposes")
    public ResponseEntity<ContactMechPurpose> updateContactMechPurpose(@Valid @RequestBody ContactMechPurpose contactMechPurpose) throws URISyntaxException {
        log.debug("REST request to update ContactMechPurpose : {}", contactMechPurpose);
        if (contactMechPurpose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactMechPurpose result = contactMechPurposeRepository.save(contactMechPurpose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactMechPurpose.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-mech-purposes} : get all the contactMechPurposes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactMechPurposes in body.
     */
    @GetMapping("/contact-mech-purposes")
    public List<ContactMechPurpose> getAllContactMechPurposes() {
        log.debug("REST request to get all ContactMechPurposes");
        return contactMechPurposeRepository.findAll();
    }

    /**
     * {@code GET  /contact-mech-purposes/:id} : get the "id" contactMechPurpose.
     *
     * @param id the id of the contactMechPurpose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactMechPurpose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-mech-purposes/{id}")
    public ResponseEntity<ContactMechPurpose> getContactMechPurpose(@PathVariable Long id) {
        log.debug("REST request to get ContactMechPurpose : {}", id);
        Optional<ContactMechPurpose> contactMechPurpose = contactMechPurposeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactMechPurpose);
    }

    /**
     * {@code DELETE  /contact-mech-purposes/:id} : delete the "id" contactMechPurpose.
     *
     * @param id the id of the contactMechPurpose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-mech-purposes/{id}")
    public ResponseEntity<Void> deleteContactMechPurpose(@PathVariable Long id) {
        log.debug("REST request to delete ContactMechPurpose : {}", id);
        contactMechPurposeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
