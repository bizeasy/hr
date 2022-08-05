package com.hr.web.rest;

import com.hr.domain.KeywordType;
import com.hr.repository.KeywordTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.KeywordType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KeywordTypeResource {

    private final Logger log = LoggerFactory.getLogger(KeywordTypeResource.class);

    private static final String ENTITY_NAME = "keywordType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeywordTypeRepository keywordTypeRepository;

    public KeywordTypeResource(KeywordTypeRepository keywordTypeRepository) {
        this.keywordTypeRepository = keywordTypeRepository;
    }

    /**
     * {@code POST  /keyword-types} : Create a new keywordType.
     *
     * @param keywordType the keywordType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keywordType, or with status {@code 400 (Bad Request)} if the keywordType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/keyword-types")
    public ResponseEntity<KeywordType> createKeywordType(@Valid @RequestBody KeywordType keywordType) throws URISyntaxException {
        log.debug("REST request to save KeywordType : {}", keywordType);
        if (keywordType.getId() != null) {
            throw new BadRequestAlertException("A new keywordType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeywordType result = keywordTypeRepository.save(keywordType);
        return ResponseEntity.created(new URI("/api/keyword-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /keyword-types} : Updates an existing keywordType.
     *
     * @param keywordType the keywordType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keywordType,
     * or with status {@code 400 (Bad Request)} if the keywordType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keywordType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/keyword-types")
    public ResponseEntity<KeywordType> updateKeywordType(@Valid @RequestBody KeywordType keywordType) throws URISyntaxException {
        log.debug("REST request to update KeywordType : {}", keywordType);
        if (keywordType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeywordType result = keywordTypeRepository.save(keywordType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keywordType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /keyword-types} : get all the keywordTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keywordTypes in body.
     */
    @GetMapping("/keyword-types")
    public List<KeywordType> getAllKeywordTypes() {
        log.debug("REST request to get all KeywordTypes");
        return keywordTypeRepository.findAll();
    }

    /**
     * {@code GET  /keyword-types/:id} : get the "id" keywordType.
     *
     * @param id the id of the keywordType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keywordType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/keyword-types/{id}")
    public ResponseEntity<KeywordType> getKeywordType(@PathVariable Long id) {
        log.debug("REST request to get KeywordType : {}", id);
        Optional<KeywordType> keywordType = keywordTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(keywordType);
    }

    /**
     * {@code DELETE  /keyword-types/:id} : delete the "id" keywordType.
     *
     * @param id the id of the keywordType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/keyword-types/{id}")
    public ResponseEntity<Void> deleteKeywordType(@PathVariable Long id) {
        log.debug("REST request to delete KeywordType : {}", id);
        keywordTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
