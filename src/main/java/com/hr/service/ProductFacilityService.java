package com.hr.service;

import com.hr.domain.ProductFacility;
import com.hr.repository.ProductFacilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductFacility}.
 */
@Service
@Transactional
public class ProductFacilityService {

    private final Logger log = LoggerFactory.getLogger(ProductFacilityService.class);

    private final ProductFacilityRepository productFacilityRepository;

    public ProductFacilityService(ProductFacilityRepository productFacilityRepository) {
        this.productFacilityRepository = productFacilityRepository;
    }

    /**
     * Save a productFacility.
     *
     * @param productFacility the entity to save.
     * @return the persisted entity.
     */
    public ProductFacility save(ProductFacility productFacility) {
        log.debug("Request to save ProductFacility : {}", productFacility);
        return productFacilityRepository.save(productFacility);
    }

    /**
     * Get all the productFacilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductFacility> findAll(Pageable pageable) {
        log.debug("Request to get all ProductFacilities");
        return productFacilityRepository.findAll(pageable);
    }


    /**
     * Get one productFacility by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductFacility> findOne(Long id) {
        log.debug("Request to get ProductFacility : {}", id);
        return productFacilityRepository.findById(id);
    }

    /**
     * Delete the productFacility by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductFacility : {}", id);
        productFacilityRepository.deleteById(id);
    }
}
