package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.RateType;
import com.hr.repository.RateTypeRepository;

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
 * Integration tests for the {@link RateTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RateTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RateTypeRepository rateTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRateTypeMockMvc;

    private RateType rateType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateType createEntity(EntityManager em) {
        RateType rateType = new RateType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rateType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateType createUpdatedEntity(EntityManager em) {
        RateType rateType = new RateType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return rateType;
    }

    @BeforeEach
    public void initTest() {
        rateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateType() throws Exception {
        int databaseSizeBeforeCreate = rateTypeRepository.findAll().size();
        // Create the RateType
        restRateTypeMockMvc.perform(post("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateType)))
            .andExpect(status().isCreated());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RateType testRateType = rateTypeList.get(rateTypeList.size() - 1);
        assertThat(testRateType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRateType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateTypeRepository.findAll().size();

        // Create the RateType with an existing ID
        rateType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateTypeMockMvc.perform(post("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateType)))
            .andExpect(status().isBadRequest());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRateTypes() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        // Get all the rateTypeList
        restRateTypeMockMvc.perform(get("/api/rate-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        // Get the rateType
        restRateTypeMockMvc.perform(get("/api/rate-types/{id}", rateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rateType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingRateType() throws Exception {
        // Get the rateType
        restRateTypeMockMvc.perform(get("/api/rate-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        int databaseSizeBeforeUpdate = rateTypeRepository.findAll().size();

        // Update the rateType
        RateType updatedRateType = rateTypeRepository.findById(rateType.getId()).get();
        // Disconnect from session so that the updates on updatedRateType are not directly saved in db
        em.detach(updatedRateType);
        updatedRateType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRateTypeMockMvc.perform(put("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRateType)))
            .andExpect(status().isOk());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeUpdate);
        RateType testRateType = rateTypeList.get(rateTypeList.size() - 1);
        assertThat(testRateType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRateType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRateType() throws Exception {
        int databaseSizeBeforeUpdate = rateTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRateTypeMockMvc.perform(put("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateType)))
            .andExpect(status().isBadRequest());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        int databaseSizeBeforeDelete = rateTypeRepository.findAll().size();

        // Delete the rateType
        restRateTypeMockMvc.perform(delete("/api/rate-types/{id}", rateType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
