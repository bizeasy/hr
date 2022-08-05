package com.hr.service;

import com.hr.domain.GeoAssoc;
import com.hr.repository.GeoAssocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GeoAssoc}.
 */
@Service
@Transactional
public class GeoAssocService {

    private final Logger log = LoggerFactory.getLogger(GeoAssocService.class);

    private final GeoAssocRepository geoAssocRepository;

    public GeoAssocService(GeoAssocRepository geoAssocRepository) {
        this.geoAssocRepository = geoAssocRepository;
    }

    /**
     * Save a geoAssoc.
     *
     * @param geoAssoc the entity to save.
     * @return the persisted entity.
     */
    public GeoAssoc save(GeoAssoc geoAssoc) {
        log.debug("Request to save GeoAssoc : {}", geoAssoc);
        return geoAssocRepository.save(geoAssoc);
    }

    /**
     * Get all the geoAssocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GeoAssoc> findAll(Pageable pageable) {
        log.debug("Request to get all GeoAssocs");
        return geoAssocRepository.findAll(pageable);
    }


    /**
     * Get one geoAssoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeoAssoc> findOne(Long id) {
        log.debug("Request to get GeoAssoc : {}", id);
        return geoAssocRepository.findById(id);
    }

    /**
     * Delete the geoAssoc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeoAssoc : {}", id);
        geoAssocRepository.deleteById(id);
    }
}
