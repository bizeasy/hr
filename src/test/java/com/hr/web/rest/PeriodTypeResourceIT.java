package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PeriodType;
import com.hr.repository.PeriodTypeRepository;

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
 * Integration tests for the {@link PeriodTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeriodTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIOD_LENGTH = 1;
    private static final Integer UPDATED_PERIOD_LENGTH = 2;

    @Autowired
    private PeriodTypeRepository periodTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodTypeMockMvc;

    private PeriodType periodType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodType createEntity(EntityManager em) {
        PeriodType periodType = new PeriodType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .periodLength(DEFAULT_PERIOD_LENGTH);
        return periodType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodType createUpdatedEntity(EntityManager em) {
        PeriodType periodType = new PeriodType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .periodLength(UPDATED_PERIOD_LENGTH);
        return periodType;
    }

    @BeforeEach
    public void initTest() {
        periodType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodType() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();
        // Create the PeriodType
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isCreated());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPeriodType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPeriodType.getPeriodLength()).isEqualTo(DEFAULT_PERIOD_LENGTH);
    }

    @Test
    @Transactional
    public void createPeriodTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();

        // Create the PeriodType with an existing ID
        periodType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeriodTypes() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get all the periodTypeList
        restPeriodTypeMockMvc.perform(get("/api/period-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].periodLength").value(hasItem(DEFAULT_PERIOD_LENGTH)));
    }
    
    @Test
    @Transactional
    public void getPeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", periodType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.periodLength").value(DEFAULT_PERIOD_LENGTH));
    }
    @Test
    @Transactional
    public void getNonExistingPeriodType() throws Exception {
        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // Update the periodType
        PeriodType updatedPeriodType = periodTypeRepository.findById(periodType.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodType are not directly saved in db
        em.detach(updatedPeriodType);
        updatedPeriodType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .periodLength(UPDATED_PERIOD_LENGTH);

        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodType)))
            .andExpect(status().isOk());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPeriodType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPeriodType.getPeriodLength()).isEqualTo(UPDATED_PERIOD_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodType() throws Exception {
        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodType)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        int databaseSizeBeforeDelete = periodTypeRepository.findAll().size();

        // Delete the periodType
        restPeriodTypeMockMvc.perform(delete("/api/period-types/{id}", periodType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
