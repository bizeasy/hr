package com.hr.web.rest;

import com.hr.domain.ContactMechType;
import com.hr.repository.ContactMechTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.ContactMechType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactMechTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContactMechTypeResource.class);

    private static final String ENTITY_NAME = "contactMechType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactMechTypeRepository contactMechTypeRepository;

    public ContactMechTypeResource(ContactMechTypeRepository contactMechTypeRepository) {
        this.contactMechTypeRepository = contactMechTypeRepository;
    }

    /**
     * {@code POST  /contact-mech-types} : Create a new contactMechType.
     *
     * @param contactMechType the contactMechType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactMechType, or with status {@code 400 (Bad Request)} if the contactMechType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-mech-types")
    public ResponseEntity<ContactMechType> createContactMechType(@Valid @RequestBody ContactMechType contactMechType) throws URISyntaxException {
        log.debug("REST request to save ContactMechType : {}", contactMechType);
        if (contactMechType.getId() != null) {
            throw new BadRequestAlertException("A new contactMechType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactMechType result = contactMechTypeRepository.save(contactMechType);
        return ResponseEntity.created(new URI("/api/contact-mech-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-mech-types} : Updates an existing contactMechType.
     *
     * @param contactMechType the contactMechType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactMechType,
     * or with status {@code 400 (Bad Request)} if the contactMechType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactMechType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-mech-types")
    public ResponseEntity<ContactMechType> updateContactMechType(@Valid @RequestBody ContactMechType contactMechType) throws URISyntaxException {
        log.debug("REST request to update ContactMechType : {}", contactMechType);
        if (contactMechType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactMechType result = contactMechTypeRepository.save(contactMechType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactMechType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-mech-types} : get all the contactMechTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactMechTypes in body.
     */
    @GetMapping("/contact-mech-types")
    public List<ContactMechType> getAllContactMechTypes() {
        log.debug("REST request to get all ContactMechTypes");
        return contactMechTypeRepository.findAll();
    }

    /**
     * {@code GET  /contact-mech-types/:id} : get the "id" contactMechType.
     *
     * @param id the id of the contactMechType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactMechType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-mech-types/{id}")
    public ResponseEntity<ContactMechType> getContactMechType(@PathVariable Long id) {
        log.debug("REST request to get ContactMechType : {}", id);
        Optional<ContactMechType> contactMechType = contactMechTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactMechType);
    }

    /**
     * {@code DELETE  /contact-mech-types/:id} : delete the "id" contactMechType.
     *
     * @param id the id of the contactMechType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-mech-types/{id}")
    public ResponseEntity<Void> deleteContactMechType(@PathVariable Long id) {
        log.debug("REST request to delete ContactMechType : {}", id);
        contactMechTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
