package com.hr.web.rest;

import com.hr.domain.Geo;
import com.hr.service.GeoService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.GeoCriteria;
import com.hr.service.GeoQueryService;

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
 * REST controller for managing {@link com.hr.domain.Geo}.
 */
@RestController
@RequestMapping("/api")
public class GeoResource {

    private final Logger log = LoggerFactory.getLogger(GeoResource.class);

    private static final String ENTITY_NAME = "geo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeoService geoService;

    private final GeoQueryService geoQueryService;

    public GeoResource(GeoService geoService, GeoQueryService geoQueryService) {
        this.geoService = geoService;
        this.geoQueryService = geoQueryService;
    }

    /**
     * {@code POST  /geos} : Create a new geo.
     *
     * @param geo the geo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geo, or with status {@code 400 (Bad Request)} if the geo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geos")
    public ResponseEntity<Geo> createGeo(@Valid @RequestBody Geo geo) throws URISyntaxException {
        log.debug("REST request to save Geo : {}", geo);
        if (geo.getId() != null) {
            throw new BadRequestAlertException("A new geo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Geo result = geoService.save(geo);
        return ResponseEntity.created(new URI("/api/geos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geos} : Updates an existing geo.
     *
     * @param geo the geo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geo,
     * or with status {@code 400 (Bad Request)} if the geo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geos")
    public ResponseEntity<Geo> updateGeo(@Valid @RequestBody Geo geo) throws URISyntaxException {
        log.debug("REST request to update Geo : {}", geo);
        if (geo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Geo result = geoService.save(geo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geos} : get all the geos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geos in body.
     */
    @GetMapping("/geos")
    public ResponseEntity<List<Geo>> getAllGeos(GeoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Geos by criteria: {}", criteria);
        Page<Geo> page = geoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geos/count} : count all the geos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geos/count")
    public ResponseEntity<Long> countGeos(GeoCriteria criteria) {
        log.debug("REST request to count Geos by criteria: {}", criteria);
        return ResponseEntity.ok().body(geoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geos/:id} : get the "id" geo.
     *
     * @param id the id of the geo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geos/{id}")
    public ResponseEntity<Geo> getGeo(@PathVariable Long id) {
        log.debug("REST request to get Geo : {}", id);
        Optional<Geo> geo = geoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geo);
    }

    /**
     * {@code DELETE  /geos/:id} : delete the "id" geo.
     *
     * @param id the id of the geo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geos/{id}")
    public ResponseEntity<Void> deleteGeo(@PathVariable Long id) {
        log.debug("REST request to delete Geo : {}", id);
        geoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
