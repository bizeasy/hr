package com.hr.service;

import com.hr.domain.ProductStore;
import com.hr.repository.ProductStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductStore}.
 */
@Service
@Transactional
public class ProductStoreService {

    private final Logger log = LoggerFactory.getLogger(ProductStoreService.class);

    private final ProductStoreRepository productStoreRepository;

    public ProductStoreService(ProductStoreRepository productStoreRepository) {
        this.productStoreRepository = productStoreRepository;
    }

    /**
     * Save a productStore.
     *
     * @param productStore the entity to save.
     * @return the persisted entity.
     */
    public ProductStore save(ProductStore productStore) {
        log.debug("Request to save ProductStore : {}", productStore);
        return productStoreRepository.save(productStore);
    }

    /**
     * Get all the productStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStore> findAll(Pageable pageable) {
        log.debug("Request to get all ProductStores");
        return productStoreRepository.findAll(pageable);
    }


    /**
     * Get one productStore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductStore> findOne(Long id) {
        log.debug("Request to get ProductStore : {}", id);
        return productStoreRepository.findById(id);
    }

    /**
     * Delete the productStore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductStore : {}", id);
        productStoreRepository.deleteById(id);
    }
}
