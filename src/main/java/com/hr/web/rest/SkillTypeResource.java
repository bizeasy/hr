package com.hr.web.rest;

import com.hr.domain.SkillType;
import com.hr.repository.SkillTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.SkillType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillTypeResource {

    private final Logger log = LoggerFactory.getLogger(SkillTypeResource.class);

    private static final String ENTITY_NAME = "skillType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillTypeRepository skillTypeRepository;

    public SkillTypeResource(SkillTypeRepository skillTypeRepository) {
        this.skillTypeRepository = skillTypeRepository;
    }

    /**
     * {@code POST  /skill-types} : Create a new skillType.
     *
     * @param skillType the skillType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillType, or with status {@code 400 (Bad Request)} if the skillType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-types")
    public ResponseEntity<SkillType> createSkillType(@Valid @RequestBody SkillType skillType) throws URISyntaxException {
        log.debug("REST request to save SkillType : {}", skillType);
        if (skillType.getId() != null) {
            throw new BadRequestAlertException("A new skillType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillType result = skillTypeRepository.save(skillType);
        return ResponseEntity.created(new URI("/api/skill-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-types} : Updates an existing skillType.
     *
     * @param skillType the skillType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillType,
     * or with status {@code 400 (Bad Request)} if the skillType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-types")
    public ResponseEntity<SkillType> updateSkillType(@Valid @RequestBody SkillType skillType) throws URISyntaxException {
        log.debug("REST request to update SkillType : {}", skillType);
        if (skillType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillType result = skillTypeRepository.save(skillType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-types} : get all the skillTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillTypes in body.
     */
    @GetMapping("/skill-types")
    public List<SkillType> getAllSkillTypes() {
        log.debug("REST request to get all SkillTypes");
        return skillTypeRepository.findAll();
    }

    /**
     * {@code GET  /skill-types/:id} : get the "id" skillType.
     *
     * @param id the id of the skillType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-types/{id}")
    public ResponseEntity<SkillType> getSkillType(@PathVariable Long id) {
        log.debug("REST request to get SkillType : {}", id);
        Optional<SkillType> skillType = skillTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skillType);
    }

    /**
     * {@code DELETE  /skill-types/:id} : delete the "id" skillType.
     *
     * @param id the id of the skillType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-types/{id}")
    public ResponseEntity<Void> deleteSkillType(@PathVariable Long id) {
        log.debug("REST request to delete SkillType : {}", id);
        skillTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
