package com.hr.service;

import com.hr.domain.Geo;
import com.hr.repository.GeoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Geo}.
 */
@Service
@Transactional
public class GeoService {

    private final Logger log = LoggerFactory.getLogger(GeoService.class);

    private final GeoRepository geoRepository;

    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    /**
     * Save a geo.
     *
     * @param geo the entity to save.
     * @return the persisted entity.
     */
    public Geo save(Geo geo) {
        log.debug("Request to save Geo : {}", geo);
        return geoRepository.save(geo);
    }

    /**
     * Get all the geos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Geo> findAll(Pageable pageable) {
        log.debug("Request to get all Geos");
        return geoRepository.findAll(pageable);
    }


    /**
     * Get one geo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Geo> findOne(Long id) {
        log.debug("Request to get Geo : {}", id);
        return geoRepository.findById(id);
    }

    /**
     * Delete the geo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Geo : {}", id);
        geoRepository.deleteById(id);
    }
}
