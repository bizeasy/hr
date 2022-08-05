package com.hr.service;

import com.hr.domain.ProductCategoryMember;
import com.hr.repository.ProductCategoryMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductCategoryMember}.
 */
@Service
@Transactional
public class ProductCategoryMemberService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryMemberService.class);

    private final ProductCategoryMemberRepository productCategoryMemberRepository;

    public ProductCategoryMemberService(ProductCategoryMemberRepository productCategoryMemberRepository) {
        this.productCategoryMemberRepository = productCategoryMemberRepository;
    }

    /**
     * Save a productCategoryMember.
     *
     * @param productCategoryMember the entity to save.
     * @return the persisted entity.
     */
    public ProductCategoryMember save(ProductCategoryMember productCategoryMember) {
        log.debug("Request to save ProductCategoryMember : {}", productCategoryMember);
        return productCategoryMemberRepository.save(productCategoryMember);
    }

    /**
     * Get all the productCategoryMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryMember> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCategoryMembers");
        return productCategoryMemberRepository.findAll(pageable);
    }


    /**
     * Get one productCategoryMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductCategoryMember> findOne(Long id) {
        log.debug("Request to get ProductCategoryMember : {}", id);
        return productCategoryMemberRepository.findById(id);
    }

    /**
     * Delete the productCategoryMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductCategoryMember : {}", id);
        productCategoryMemberRepository.deleteById(id);
    }
}
