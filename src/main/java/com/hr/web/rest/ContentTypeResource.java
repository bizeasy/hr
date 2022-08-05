package com.hr.web.rest;

import com.hr.domain.ContentType;
import com.hr.repository.ContentTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.ContentType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContentTypeResource.class);

    private static final String ENTITY_NAME = "contentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentTypeRepository contentTypeRepository;

    public ContentTypeResource(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    /**
     * {@code POST  /content-types} : Create a new contentType.
     *
     * @param contentType the contentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentType, or with status {@code 400 (Bad Request)} if the contentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-types")
    public ResponseEntity<ContentType> createContentType(@Valid @RequestBody ContentType contentType) throws URISyntaxException {
        log.debug("REST request to save ContentType : {}", contentType);
        if (contentType.getId() != null) {
            throw new BadRequestAlertException("A new contentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentType result = contentTypeRepository.save(contentType);
        return ResponseEntity.created(new URI("/api/content-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-types} : Updates an existing contentType.
     *
     * @param contentType the contentType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentType,
     * or with status {@code 400 (Bad Request)} if the contentType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-types")
    public ResponseEntity<ContentType> updateContentType(@Valid @RequestBody ContentType contentType) throws URISyntaxException {
        log.debug("REST request to update ContentType : {}", contentType);
        if (contentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentType result = contentTypeRepository.save(contentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-types} : get all the contentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentTypes in body.
     */
    @GetMapping("/content-types")
    public List<ContentType> getAllContentTypes() {
        log.debug("REST request to get all ContentTypes");
        return contentTypeRepository.findAll();
    }

    /**
     * {@code GET  /content-types/:id} : get the "id" contentType.
     *
     * @param id the id of the contentType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-types/{id}")
    public ResponseEntity<ContentType> getContentType(@PathVariable Long id) {
        log.debug("REST request to get ContentType : {}", id);
        Optional<ContentType> contentType = contentTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contentType);
    }

    /**
     * {@code DELETE  /content-types/:id} : delete the "id" contentType.
     *
     * @param id the id of the contentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-types/{id}")
    public ResponseEntity<Void> deleteContentType(@PathVariable Long id) {
        log.debug("REST request to delete ContentType : {}", id);
        contentTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
