package com.hr.service;

import com.hr.domain.SupplierProduct;
import com.hr.repository.SupplierProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SupplierProduct}.
 */
@Service
@Transactional
public class SupplierProductService {

    private final Logger log = LoggerFactory.getLogger(SupplierProductService.class);

    private final SupplierProductRepository supplierProductRepository;

    public SupplierProductService(SupplierProductRepository supplierProductRepository) {
        this.supplierProductRepository = supplierProductRepository;
    }

    /**
     * Save a supplierProduct.
     *
     * @param supplierProduct the entity to save.
     * @return the persisted entity.
     */
    public SupplierProduct save(SupplierProduct supplierProduct) {
        log.debug("Request to save SupplierProduct : {}", supplierProduct);
        return supplierProductRepository.save(supplierProduct);
    }

    /**
     * Get all the supplierProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierProduct> findAll(Pageable pageable) {
        log.debug("Request to get all SupplierProducts");
        return supplierProductRepository.findAll(pageable);
    }


    /**
     * Get one supplierProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupplierProduct> findOne(Long id) {
        log.debug("Request to get SupplierProduct : {}", id);
        return supplierProductRepository.findById(id);
    }

    /**
     * Delete the supplierProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplierProduct : {}", id);
        supplierProductRepository.deleteById(id);
    }
}
