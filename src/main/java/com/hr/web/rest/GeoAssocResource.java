package com.hr.web.rest;

import com.hr.domain.GeoAssoc;
import com.hr.service.GeoAssocService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.GeoAssocCriteria;
import com.hr.service.GeoAssocQueryService;

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
 * REST controller for managing {@link com.hr.domain.GeoAssoc}.
 */
@RestController
@RequestMapping("/api")
public class GeoAssocResource {

    private final Logger log = LoggerFactory.getLogger(GeoAssocResource.class);

    private static final String ENTITY_NAME = "geoAssoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeoAssocService geoAssocService;

    private final GeoAssocQueryService geoAssocQueryService;

    public GeoAssocResource(GeoAssocService geoAssocService, GeoAssocQueryService geoAssocQueryService) {
        this.geoAssocService = geoAssocService;
        this.geoAssocQueryService = geoAssocQueryService;
    }

    /**
     * {@code POST  /geo-assocs} : Create a new geoAssoc.
     *
     * @param geoAssoc the geoAssoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geoAssoc, or with status {@code 400 (Bad Request)} if the geoAssoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geo-assocs")
    public ResponseEntity<GeoAssoc> createGeoAssoc(@RequestBody GeoAssoc geoAssoc) throws URISyntaxException {
        log.debug("REST request to save GeoAssoc : {}", geoAssoc);
        if (geoAssoc.getId() != null) {
            throw new BadRequestAlertException("A new geoAssoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeoAssoc result = geoAssocService.save(geoAssoc);
        return ResponseEntity.created(new URI("/api/geo-assocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geo-assocs} : Updates an existing geoAssoc.
     *
     * @param geoAssoc the geoAssoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geoAssoc,
     * or with status {@code 400 (Bad Request)} if the geoAssoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geoAssoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geo-assocs")
    public ResponseEntity<GeoAssoc> updateGeoAssoc(@RequestBody GeoAssoc geoAssoc) throws URISyntaxException {
        log.debug("REST request to update GeoAssoc : {}", geoAssoc);
        if (geoAssoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeoAssoc result = geoAssocService.save(geoAssoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geoAssoc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geo-assocs} : get all the geoAssocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geoAssocs in body.
     */
    @GetMapping("/geo-assocs")
    public ResponseEntity<List<GeoAssoc>> getAllGeoAssocs(GeoAssocCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GeoAssocs by criteria: {}", criteria);
        Page<GeoAssoc> page = geoAssocQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geo-assocs/count} : count all the geoAssocs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geo-assocs/count")
    public ResponseEntity<Long> countGeoAssocs(GeoAssocCriteria criteria) {
        log.debug("REST request to count GeoAssocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(geoAssocQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geo-assocs/:id} : get the "id" geoAssoc.
     *
     * @param id the id of the geoAssoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geoAssoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geo-assocs/{id}")
    public ResponseEntity<GeoAssoc> getGeoAssoc(@PathVariable Long id) {
        log.debug("REST request to get GeoAssoc : {}", id);
        Optional<GeoAssoc> geoAssoc = geoAssocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geoAssoc);
    }

    /**
     * {@code DELETE  /geo-assocs/:id} : delete the "id" geoAssoc.
     *
     * @param id the id of the geoAssoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geo-assocs/{id}")
    public ResponseEntity<Void> deleteGeoAssoc(@PathVariable Long id) {
        log.debug("REST request to delete GeoAssoc : {}", id);
        geoAssocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
