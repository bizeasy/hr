package com.hr.service;

import com.hr.domain.ItemIssuance;
import com.hr.repository.ItemIssuanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemIssuance}.
 */
@Service
@Transactional
public class ItemIssuanceService {

    private final Logger log = LoggerFactory.getLogger(ItemIssuanceService.class);

    private final ItemIssuanceRepository itemIssuanceRepository;

    public ItemIssuanceService(ItemIssuanceRepository itemIssuanceRepository) {
        this.itemIssuanceRepository = itemIssuanceRepository;
    }

    /**
     * Save a itemIssuance.
     *
     * @param itemIssuance the entity to save.
     * @return the persisted entity.
     */
    public ItemIssuance save(ItemIssuance itemIssuance) {
        log.debug("Request to save ItemIssuance : {}", itemIssuance);
        return itemIssuanceRepository.save(itemIssuance);
    }

    /**
     * Get all the itemIssuances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemIssuance> findAll(Pageable pageable) {
        log.debug("Request to get all ItemIssuances");
        return itemIssuanceRepository.findAll(pageable);
    }


    /**
     * Get one itemIssuance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemIssuance> findOne(Long id) {
        log.debug("Request to get ItemIssuance : {}", id);
        return itemIssuanceRepository.findById(id);
    }

    /**
     * Delete the itemIssuance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemIssuance : {}", id);
        itemIssuanceRepository.deleteById(id);
    }
}
