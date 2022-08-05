package com.hr.web.rest;

import com.hr.domain.ContactMech;
import com.hr.service.ContactMechService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ContactMechCriteria;
import com.hr.service.ContactMechQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.ContactMech}.
 */
@RestController
@RequestMapping("/api")
public class ContactMechResource {

    private final Logger log = LoggerFactory.getLogger(ContactMechResource.class);

    private static final String ENTITY_NAME = "contactMech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactMechService contactMechService;

    private final ContactMechQueryService contactMechQueryService;

    public ContactMechResource(ContactMechService contactMechService, ContactMechQueryService contactMechQueryService) {
        this.contactMechService = contactMechService;
        this.contactMechQueryService = contactMechQueryService;
    }

    /**
     * {@code POST  /contact-meches} : Create a new contactMech.
     *
     * @param contactMech the contactMech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactMech, or with status {@code 400 (Bad Request)} if the contactMech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-meches")
    public ResponseEntity<ContactMech> createContactMech(@Valid @RequestBody ContactMech contactMech) throws URISyntaxException {
        log.debug("REST request to save ContactMech : {}", contactMech);
        if (contactMech.getId() != null) {
            throw new BadRequestAlertException("A new contactMech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactMech result = contactMechService.save(contactMech);
        return ResponseEntity.created(new URI("/api/contact-meches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-meches} : Updates an existing contactMech.
     *
     * @param contactMech the contactMech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactMech,
     * or with status {@code 400 (Bad Request)} if the contactMech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactMech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-meches")
    public ResponseEntity<ContactMech> updateContactMech(@Valid @RequestBody ContactMech contactMech) throws URISyntaxException {
        log.debug("REST request to update ContactMech : {}", contactMech);
        if (contactMech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactMech result = contactMechService.save(contactMech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactMech.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-meches} : get all the contactMeches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactMeches in body.
     */
    @GetMapping("/contact-meches")
    public ResponseEntity<List<ContactMech>> getAllContactMeches(ContactMechCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContactMeches by criteria: {}", criteria);
        Page<ContactMech> page = contactMechQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-meches/count} : count all the contactMeches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-meches/count")
    public ResponseEntity<Long> countContactMeches(ContactMechCriteria criteria) {
        log.debug("REST request to count ContactMeches by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactMechQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-meches/:id} : get the "id" contactMech.
     *
     * @param id the id of the contactMech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactMech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-meches/{id}")
    public ResponseEntity<ContactMech> getContactMech(@PathVariable Long id) {
        log.debug("REST request to get ContactMech : {}", id);
        Optional<ContactMech> contactMech = contactMechService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactMech);
    }

    /**
     * {@code DELETE  /contact-meches/:id} : delete the "id" contactMech.
     *
     * @param id the id of the contactMech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-meches/{id}")
    public ResponseEntity<Void> deleteContactMech(@PathVariable Long id) {
        log.debug("REST request to delete ContactMech : {}", id);
        contactMechService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
