package com.hr.service;

import com.hr.domain.GeoPoint;
import com.hr.repository.GeoPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GeoPoint}.
 */
@Service
@Transactional
public class GeoPointService {

    private final Logger log = LoggerFactory.getLogger(GeoPointService.class);

    private final GeoPointRepository geoPointRepository;

    public GeoPointService(GeoPointRepository geoPointRepository) {
        this.geoPointRepository = geoPointRepository;
    }

    /**
     * Save a geoPoint.
     *
     * @param geoPoint the entity to save.
     * @return the persisted entity.
     */
    public GeoPoint save(GeoPoint geoPoint) {
        log.debug("Request to save GeoPoint : {}", geoPoint);
        return geoPointRepository.save(geoPoint);
    }

    /**
     * Get all the geoPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GeoPoint> findAll(Pageable pageable) {
        log.debug("Request to get all GeoPoints");
        return geoPointRepository.findAll(pageable);
    }


    /**
     * Get one geoPoint by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeoPoint> findOne(Long id) {
        log.debug("Request to get GeoPoint : {}", id);
        return geoPointRepository.findById(id);
    }

    /**
     * Delete the geoPoint by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeoPoint : {}", id);
        geoPointRepository.deleteById(id);
    }
}
