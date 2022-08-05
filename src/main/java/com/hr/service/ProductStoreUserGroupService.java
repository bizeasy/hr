package com.hr.service;

import com.hr.domain.ProductStoreUserGroup;
import com.hr.repository.ProductStoreUserGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductStoreUserGroup}.
 */
@Service
@Transactional
public class ProductStoreUserGroupService {

    private final Logger log = LoggerFactory.getLogger(ProductStoreUserGroupService.class);

    private final ProductStoreUserGroupRepository productStoreUserGroupRepository;

    public ProductStoreUserGroupService(ProductStoreUserGroupRepository productStoreUserGroupRepository) {
        this.productStoreUserGroupRepository = productStoreUserGroupRepository;
    }

    /**
     * Save a productStoreUserGroup.
     *
     * @param productStoreUserGroup the entity to save.
     * @return the persisted entity.
     */
    public ProductStoreUserGroup save(ProductStoreUserGroup productStoreUserGroup) {
        log.debug("Request to save ProductStoreUserGroup : {}", productStoreUserGroup);
        return productStoreUserGroupRepository.save(productStoreUserGroup);
    }

    /**
     * Get all the productStoreUserGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductStoreUserGroup> findAll(Pageable pageable) {
        log.debug("Request to get all ProductStoreUserGroups");
        return productStoreUserGroupRepository.findAll(pageable);
    }


    /**
     * Get one productStoreUserGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductStoreUserGroup> findOne(Long id) {
        log.debug("Request to get ProductStoreUserGroup : {}", id);
        return productStoreUserGroupRepository.findById(id);
    }

    /**
     * Delete the productStoreUserGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductStoreUserGroup : {}", id);
        productStoreUserGroupRepository.deleteById(id);
    }
}
