package com.hr.service;

import com.hr.domain.Uom;
import com.hr.repository.UomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Uom}.
 */
@Service
@Transactional
public class UomService {

    private final Logger log = LoggerFactory.getLogger(UomService.class);

    private final UomRepository uomRepository;

    public UomService(UomRepository uomRepository) {
        this.uomRepository = uomRepository;
    }

    /**
     * Save a uom.
     *
     * @param uom the entity to save.
     * @return the persisted entity.
     */
    public Uom save(Uom uom) {
        log.debug("Request to save Uom : {}", uom);
        return uomRepository.save(uom);
    }

    /**
     * Get all the uoms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uom> findAll(Pageable pageable) {
        log.debug("Request to get all Uoms");
        return uomRepository.findAll(pageable);
    }


    /**
     * Get one uom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uom> findOne(Long id) {
        log.debug("Request to get Uom : {}", id);
        return uomRepository.findById(id);
    }

    /**
     * Delete the uom by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uom : {}", id);
        uomRepository.deleteById(id);
    }
}
