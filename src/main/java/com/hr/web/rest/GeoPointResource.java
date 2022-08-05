package com.hr.web.rest;

import com.hr.domain.GeoPoint;
import com.hr.service.GeoPointService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.GeoPointCriteria;
import com.hr.service.GeoPointQueryService;

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
 * REST controller for managing {@link com.hr.domain.GeoPoint}.
 */
@RestController
@RequestMapping("/api")
public class GeoPointResource {

    private final Logger log = LoggerFactory.getLogger(GeoPointResource.class);

    private static final String ENTITY_NAME = "geoPoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeoPointService geoPointService;

    private final GeoPointQueryService geoPointQueryService;

    public GeoPointResource(GeoPointService geoPointService, GeoPointQueryService geoPointQueryService) {
        this.geoPointService = geoPointService;
        this.geoPointQueryService = geoPointQueryService;
    }

    /**
     * {@code POST  /geo-points} : Create a new geoPoint.
     *
     * @param geoPoint the geoPoint to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geoPoint, or with status {@code 400 (Bad Request)} if the geoPoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geo-points")
    public ResponseEntity<GeoPoint> createGeoPoint(@RequestBody GeoPoint geoPoint) throws URISyntaxException {
        log.debug("REST request to save GeoPoint : {}", geoPoint);
        if (geoPoint.getId() != null) {
            throw new BadRequestAlertException("A new geoPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeoPoint result = geoPointService.save(geoPoint);
        return ResponseEntity.created(new URI("/api/geo-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geo-points} : Updates an existing geoPoint.
     *
     * @param geoPoint the geoPoint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geoPoint,
     * or with status {@code 400 (Bad Request)} if the geoPoint is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geoPoint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geo-points")
    public ResponseEntity<GeoPoint> updateGeoPoint(@RequestBody GeoPoint geoPoint) throws URISyntaxException {
        log.debug("REST request to update GeoPoint : {}", geoPoint);
        if (geoPoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeoPoint result = geoPointService.save(geoPoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geoPoint.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geo-points} : get all the geoPoints.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geoPoints in body.
     */
    @GetMapping("/geo-points")
    public ResponseEntity<List<GeoPoint>> getAllGeoPoints(GeoPointCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GeoPoints by criteria: {}", criteria);
        Page<GeoPoint> page = geoPointQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geo-points/count} : count all the geoPoints.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geo-points/count")
    public ResponseEntity<Long> countGeoPoints(GeoPointCriteria criteria) {
        log.debug("REST request to count GeoPoints by criteria: {}", criteria);
        return ResponseEntity.ok().body(geoPointQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geo-points/:id} : get the "id" geoPoint.
     *
     * @param id the id of the geoPoint to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geoPoint, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geo-points/{id}")
    public ResponseEntity<GeoPoint> getGeoPoint(@PathVariable Long id) {
        log.debug("REST request to get GeoPoint : {}", id);
        Optional<GeoPoint> geoPoint = geoPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geoPoint);
    }

    /**
     * {@code DELETE  /geo-points/:id} : delete the "id" geoPoint.
     *
     * @param id the id of the geoPoint to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geo-points/{id}")
    public ResponseEntity<Void> deleteGeoPoint(@PathVariable Long id) {
        log.debug("REST request to delete GeoPoint : {}", id);
        geoPointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
