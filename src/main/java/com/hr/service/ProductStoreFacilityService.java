package com.hr.service;

import com.hr.domain.ProductStoreFacility;
import com.hr.repository.ProductStoreFacilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductStoreFacility}.
 */
@Service
@Transactional
public class ProductStoreFacilityService {

    private final Logger log = LoggerFactory.getLogger(ProductStoreFacilityService.class);

    private final ProductStoreFacilityRepository productStoreFacilityRepository;

    public ProductStoreFacilityService(ProductStoreFacilityRepository productStoreFacilityRepository) {
        this.productStoreFacilityRepository = productStoreFacilityRepository;
    }

    /**
     * Save a productStoreFacility.
     *
     * @param productStoreFacility the entity to save.
     * @return the persisted entity.
     */
    public ProductStoreFacility save(ProductStoreFacility productStoreFacility) {
        log.debug("Request to save ProductStoreFacility : {}", productStoreFacility);
        return productStoreFacilityRepository.save(productStoreFacility);
    }

    /**
     * Get all the productStoreFacilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreFacility> findAll(Pageable pageable) {
        log.debug("Request to get all ProductStoreFacilities");
        return productStoreFacilityRepository.findAll(pageable);
    }


    /**
     * Get one productStoreFacility by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductStoreFacility> findOne(Long id) {
        log.debug("Request to get ProductStoreFacility : {}", id);
        return productStoreFacilityRepository.findById(id);
    }

    /**
     * Delete the productStoreFacility by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductStoreFacility : {}", id);
        productStoreFacilityRepository.deleteById(id);
    }
}
