package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PerfRatingType;
import com.hr.repository.PerfRatingTypeRepository;

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
 * Integration tests for the {@link PerfRatingTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PerfRatingTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PerfRatingTypeRepository perfRatingTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfRatingTypeMockMvc;

    private PerfRatingType perfRatingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfRatingType createEntity(EntityManager em) {
        PerfRatingType perfRatingType = new PerfRatingType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return perfRatingType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfRatingType createUpdatedEntity(EntityManager em) {
        PerfRatingType perfRatingType = new PerfRatingType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return perfRatingType;
    }

    @BeforeEach
    public void initTest() {
        perfRatingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfRatingType() throws Exception {
        int databaseSizeBeforeCreate = perfRatingTypeRepository.findAll().size();
        // Create the PerfRatingType
        restPerfRatingTypeMockMvc.perform(post("/api/perf-rating-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfRatingType)))
            .andExpect(status().isCreated());

        // Validate the PerfRatingType in the database
        List<PerfRatingType> perfRatingTypeList = perfRatingTypeRepository.findAll();
        assertThat(perfRatingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PerfRatingType testPerfRatingType = perfRatingTypeList.get(perfRatingTypeList.size() - 1);
        assertThat(testPerfRatingType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerfRatingType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPerfRatingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfRatingTypeRepository.findAll().size();

        // Create the PerfRatingType with an existing ID
        perfRatingType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfRatingTypeMockMvc.perform(post("/api/perf-rating-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfRatingType)))
            .andExpect(status().isBadRequest());

        // Validate the PerfRatingType in the database
        List<PerfRatingType> perfRatingTypeList = perfRatingTypeRepository.findAll();
        assertThat(perfRatingTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfRatingTypes() throws Exception {
        // Initialize the database
        perfRatingTypeRepository.saveAndFlush(perfRatingType);

        // Get all the perfRatingTypeList
        restPerfRatingTypeMockMvc.perform(get("/api/perf-rating-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfRatingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPerfRatingType() throws Exception {
        // Initialize the database
        perfRatingTypeRepository.saveAndFlush(perfRatingType);

        // Get the perfRatingType
        restPerfRatingTypeMockMvc.perform(get("/api/perf-rating-types/{id}", perfRatingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfRatingType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPerfRatingType() throws Exception {
        // Get the perfRatingType
        restPerfRatingTypeMockMvc.perform(get("/api/perf-rating-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfRatingType() throws Exception {
        // Initialize the database
        perfRatingTypeRepository.saveAndFlush(perfRatingType);

        int databaseSizeBeforeUpdate = perfRatingTypeRepository.findAll().size();

        // Update the perfRatingType
        PerfRatingType updatedPerfRatingType = perfRatingTypeRepository.findById(perfRatingType.getId()).get();
        // Disconnect from session so that the updates on updatedPerfRatingType are not directly saved in db
        em.detach(updatedPerfRatingType);
        updatedPerfRatingType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPerfRatingTypeMockMvc.perform(put("/api/perf-rating-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfRatingType)))
            .andExpect(status().isOk());

        // Validate the PerfRatingType in the database
        List<PerfRatingType> perfRatingTypeList = perfRatingTypeRepository.findAll();
        assertThat(perfRatingTypeList).hasSize(databaseSizeBeforeUpdate);
        PerfRatingType testPerfRatingType = perfRatingTypeList.get(perfRatingTypeList.size() - 1);
        assertThat(testPerfRatingType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerfRatingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfRatingType() throws Exception {
        int databaseSizeBeforeUpdate = perfRatingTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfRatingTypeMockMvc.perform(put("/api/perf-rating-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfRatingType)))
            .andExpect(status().isBadRequest());

        // Validate the PerfRatingType in the database
        List<PerfRatingType> perfRatingTypeList = perfRatingTypeRepository.findAll();
        assertThat(perfRatingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfRatingType() throws Exception {
        // Initialize the database
        perfRatingTypeRepository.saveAndFlush(perfRatingType);

        int databaseSizeBeforeDelete = perfRatingTypeRepository.findAll().size();

        // Delete the perfRatingType
        restPerfRatingTypeMockMvc.perform(delete("/api/perf-rating-types/{id}", perfRatingType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfRatingType> perfRatingTypeList = perfRatingTypeRepository.findAll();
        assertThat(perfRatingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
