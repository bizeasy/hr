package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TaxAuthorityRateType;
import com.hr.repository.TaxAuthorityRateTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaxAuthorityRateTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaxAuthorityRateTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TaxAuthorityRateTypeRepository taxAuthorityRateTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxAuthorityRateTypeMockMvc;

    private TaxAuthorityRateType taxAuthorityRateType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxAuthorityRateType createEntity(EntityManager em) {
        TaxAuthorityRateType taxAuthorityRateType = new TaxAuthorityRateType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return taxAuthorityRateType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxAuthorityRateType createUpdatedEntity(EntityManager em) {
        TaxAuthorityRateType taxAuthorityRateType = new TaxAuthorityRateType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return taxAuthorityRateType;
    }

    @BeforeEach
    public void initTest() {
        taxAuthorityRateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxAuthorityRateType() throws Exception {
        int databaseSizeBeforeCreate = taxAuthorityRateTypeRepository.findAll().size();
        // Create the TaxAuthorityRateType
        restTaxAuthorityRateTypeMockMvc.perform(post("/api/tax-authority-rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthorityRateType)))
            .andExpect(status().isCreated());

        // Validate the TaxAuthorityRateType in the database
        List<TaxAuthorityRateType> taxAuthorityRateTypeList = taxAuthorityRateTypeRepository.findAll();
        assertThat(taxAuthorityRateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TaxAuthorityRateType testTaxAuthorityRateType = taxAuthorityRateTypeList.get(taxAuthorityRateTypeList.size() - 1);
        assertThat(testTaxAuthorityRateType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxAuthorityRateType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTaxAuthorityRateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxAuthorityRateTypeRepository.findAll().size();

        // Create the TaxAuthorityRateType with an existing ID
        taxAuthorityRateType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxAuthorityRateTypeMockMvc.perform(post("/api/tax-authority-rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthorityRateType)))
            .andExpect(status().isBadRequest());

        // Validate the TaxAuthorityRateType in the database
        List<TaxAuthorityRateType> taxAuthorityRateTypeList = taxAuthorityRateTypeRepository.findAll();
        assertThat(taxAuthorityRateTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaxAuthorityRateTypes() throws Exception {
        // Initialize the database
        taxAuthorityRateTypeRepository.saveAndFlush(taxAuthorityRateType);

        // Get all the taxAuthorityRateTypeList
        restTaxAuthorityRateTypeMockMvc.perform(get("/api/tax-authority-rate-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxAuthorityRateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTaxAuthorityRateType() throws Exception {
        // Initialize the database
        taxAuthorityRateTypeRepository.saveAndFlush(taxAuthorityRateType);

        // Get the taxAuthorityRateType
        restTaxAuthorityRateTypeMockMvc.perform(get("/api/tax-authority-rate-types/{id}", taxAuthorityRateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxAuthorityRateType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTaxAuthorityRateType() throws Exception {
        // Get the taxAuthorityRateType
        restTaxAuthorityRateTypeMockMvc.perform(get("/api/tax-authority-rate-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxAuthorityRateType() throws Exception {
        // Initialize the database
        taxAuthorityRateTypeRepository.saveAndFlush(taxAuthorityRateType);

        int databaseSizeBeforeUpdate = taxAuthorityRateTypeRepository.findAll().size();

        // Update the taxAuthorityRateType
        TaxAuthorityRateType updatedTaxAuthorityRateType = taxAuthorityRateTypeRepository.findById(taxAuthorityRateType.getId()).get();
        // Disconnect from session so that the updates on updatedTaxAuthorityRateType are not directly saved in db
        em.detach(updatedTaxAuthorityRateType);
        updatedTaxAuthorityRateType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTaxAuthorityRateTypeMockMvc.perform(put("/api/tax-authority-rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxAuthorityRateType)))
            .andExpect(status().isOk());

        // Validate the TaxAuthorityRateType in the database
        List<TaxAuthorityRateType> taxAuthorityRateTypeList = taxAuthorityRateTypeRepository.findAll();
        assertThat(taxAuthorityRateTypeList).hasSize(databaseSizeBeforeUpdate);
        TaxAuthorityRateType testTaxAuthorityRateType = taxAuthorityRateTypeList.get(taxAuthorityRateTypeList.size() - 1);
        assertThat(testTaxAuthorityRateType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxAuthorityRateType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxAuthorityRateType() throws Exception {
        int databaseSizeBeforeUpdate = taxAuthorityRateTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxAuthorityRateTypeMockMvc.perform(put("/api/tax-authority-rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthorityRateType)))
            .andExpect(status().isBadRequest());

        // Validate the TaxAuthorityRateType in the database
        List<TaxAuthorityRateType> taxAuthorityRateTypeList = taxAuthorityRateTypeRepository.findAll();
        assertThat(taxAuthorityRateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxAuthorityRateType() throws Exception {
        // Initialize the database
        taxAuthorityRateTypeRepository.saveAndFlush(taxAuthorityRateType);

        int databaseSizeBeforeDelete = taxAuthorityRateTypeRepository.findAll().size();

        // Delete the taxAuthorityRateType
        restTaxAuthorityRateTypeMockMvc.perform(delete("/api/tax-authority-rate-types/{id}", taxAuthorityRateType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxAuthorityRateType> taxAuthorityRateTypeList = taxAuthorityRateTypeRepository.findAll();
        assertThat(taxAuthorityRateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
