package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PerfReviewItemType;
import com.hr.repository.PerfReviewItemTypeRepository;

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
 * Integration tests for the {@link PerfReviewItemTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PerfReviewItemTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PerfReviewItemTypeRepository perfReviewItemTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfReviewItemTypeMockMvc;

    private PerfReviewItemType perfReviewItemType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReviewItemType createEntity(EntityManager em) {
        PerfReviewItemType perfReviewItemType = new PerfReviewItemType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return perfReviewItemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReviewItemType createUpdatedEntity(EntityManager em) {
        PerfReviewItemType perfReviewItemType = new PerfReviewItemType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return perfReviewItemType;
    }

    @BeforeEach
    public void initTest() {
        perfReviewItemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfReviewItemType() throws Exception {
        int databaseSizeBeforeCreate = perfReviewItemTypeRepository.findAll().size();
        // Create the PerfReviewItemType
        restPerfReviewItemTypeMockMvc.perform(post("/api/perf-review-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItemType)))
            .andExpect(status().isCreated());

        // Validate the PerfReviewItemType in the database
        List<PerfReviewItemType> perfReviewItemTypeList = perfReviewItemTypeRepository.findAll();
        assertThat(perfReviewItemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PerfReviewItemType testPerfReviewItemType = perfReviewItemTypeList.get(perfReviewItemTypeList.size() - 1);
        assertThat(testPerfReviewItemType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerfReviewItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPerfReviewItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfReviewItemTypeRepository.findAll().size();

        // Create the PerfReviewItemType with an existing ID
        perfReviewItemType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfReviewItemTypeMockMvc.perform(post("/api/perf-review-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItemType)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReviewItemType in the database
        List<PerfReviewItemType> perfReviewItemTypeList = perfReviewItemTypeRepository.findAll();
        assertThat(perfReviewItemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfReviewItemTypes() throws Exception {
        // Initialize the database
        perfReviewItemTypeRepository.saveAndFlush(perfReviewItemType);

        // Get all the perfReviewItemTypeList
        restPerfReviewItemTypeMockMvc.perform(get("/api/perf-review-item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfReviewItemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPerfReviewItemType() throws Exception {
        // Initialize the database
        perfReviewItemTypeRepository.saveAndFlush(perfReviewItemType);

        // Get the perfReviewItemType
        restPerfReviewItemTypeMockMvc.perform(get("/api/perf-review-item-types/{id}", perfReviewItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfReviewItemType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPerfReviewItemType() throws Exception {
        // Get the perfReviewItemType
        restPerfReviewItemTypeMockMvc.perform(get("/api/perf-review-item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfReviewItemType() throws Exception {
        // Initialize the database
        perfReviewItemTypeRepository.saveAndFlush(perfReviewItemType);

        int databaseSizeBeforeUpdate = perfReviewItemTypeRepository.findAll().size();

        // Update the perfReviewItemType
        PerfReviewItemType updatedPerfReviewItemType = perfReviewItemTypeRepository.findById(perfReviewItemType.getId()).get();
        // Disconnect from session so that the updates on updatedPerfReviewItemType are not directly saved in db
        em.detach(updatedPerfReviewItemType);
        updatedPerfReviewItemType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPerfReviewItemTypeMockMvc.perform(put("/api/perf-review-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfReviewItemType)))
            .andExpect(status().isOk());

        // Validate the PerfReviewItemType in the database
        List<PerfReviewItemType> perfReviewItemTypeList = perfReviewItemTypeRepository.findAll();
        assertThat(perfReviewItemTypeList).hasSize(databaseSizeBeforeUpdate);
        PerfReviewItemType testPerfReviewItemType = perfReviewItemTypeList.get(perfReviewItemTypeList.size() - 1);
        assertThat(testPerfReviewItemType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerfReviewItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfReviewItemType() throws Exception {
        int databaseSizeBeforeUpdate = perfReviewItemTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfReviewItemTypeMockMvc.perform(put("/api/perf-review-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItemType)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReviewItemType in the database
        List<PerfReviewItemType> perfReviewItemTypeList = perfReviewItemTypeRepository.findAll();
        assertThat(perfReviewItemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfReviewItemType() throws Exception {
        // Initialize the database
        perfReviewItemTypeRepository.saveAndFlush(perfReviewItemType);

        int databaseSizeBeforeDelete = perfReviewItemTypeRepository.findAll().size();

        // Delete the perfReviewItemType
        restPerfReviewItemTypeMockMvc.perform(delete("/api/perf-review-item-types/{id}", perfReviewItemType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfReviewItemType> perfReviewItemTypeList = perfReviewItemTypeRepository.findAll();
        assertThat(perfReviewItemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
