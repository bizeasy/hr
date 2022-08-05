package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortPurpose;
import com.hr.repository.WorkEffortPurposeRepository;

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
 * Integration tests for the {@link WorkEffortPurposeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortPurposeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkEffortPurposeRepository workEffortPurposeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortPurposeMockMvc;

    private WorkEffortPurpose workEffortPurpose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortPurpose createEntity(EntityManager em) {
        WorkEffortPurpose workEffortPurpose = new WorkEffortPurpose()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return workEffortPurpose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortPurpose createUpdatedEntity(EntityManager em) {
        WorkEffortPurpose workEffortPurpose = new WorkEffortPurpose()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return workEffortPurpose;
    }

    @BeforeEach
    public void initTest() {
        workEffortPurpose = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortPurpose() throws Exception {
        int databaseSizeBeforeCreate = workEffortPurposeRepository.findAll().size();
        // Create the WorkEffortPurpose
        restWorkEffortPurposeMockMvc.perform(post("/api/work-effort-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortPurpose)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortPurpose in the database
        List<WorkEffortPurpose> workEffortPurposeList = workEffortPurposeRepository.findAll();
        assertThat(workEffortPurposeList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortPurpose testWorkEffortPurpose = workEffortPurposeList.get(workEffortPurposeList.size() - 1);
        assertThat(testWorkEffortPurpose.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEffortPurpose.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkEffortPurposeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortPurposeRepository.findAll().size();

        // Create the WorkEffortPurpose with an existing ID
        workEffortPurpose.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortPurposeMockMvc.perform(post("/api/work-effort-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortPurpose in the database
        List<WorkEffortPurpose> workEffortPurposeList = workEffortPurposeRepository.findAll();
        assertThat(workEffortPurposeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortPurposes() throws Exception {
        // Initialize the database
        workEffortPurposeRepository.saveAndFlush(workEffortPurpose);

        // Get all the workEffortPurposeList
        restWorkEffortPurposeMockMvc.perform(get("/api/work-effort-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortPurpose.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkEffortPurpose() throws Exception {
        // Initialize the database
        workEffortPurposeRepository.saveAndFlush(workEffortPurpose);

        // Get the workEffortPurpose
        restWorkEffortPurposeMockMvc.perform(get("/api/work-effort-purposes/{id}", workEffortPurpose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortPurpose.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWorkEffortPurpose() throws Exception {
        // Get the workEffortPurpose
        restWorkEffortPurposeMockMvc.perform(get("/api/work-effort-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortPurpose() throws Exception {
        // Initialize the database
        workEffortPurposeRepository.saveAndFlush(workEffortPurpose);

        int databaseSizeBeforeUpdate = workEffortPurposeRepository.findAll().size();

        // Update the workEffortPurpose
        WorkEffortPurpose updatedWorkEffortPurpose = workEffortPurposeRepository.findById(workEffortPurpose.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortPurpose are not directly saved in db
        em.detach(updatedWorkEffortPurpose);
        updatedWorkEffortPurpose
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restWorkEffortPurposeMockMvc.perform(put("/api/work-effort-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortPurpose)))
            .andExpect(status().isOk());

        // Validate the WorkEffortPurpose in the database
        List<WorkEffortPurpose> workEffortPurposeList = workEffortPurposeRepository.findAll();
        assertThat(workEffortPurposeList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortPurpose testWorkEffortPurpose = workEffortPurposeList.get(workEffortPurposeList.size() - 1);
        assertThat(testWorkEffortPurpose.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEffortPurpose.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortPurpose() throws Exception {
        int databaseSizeBeforeUpdate = workEffortPurposeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortPurposeMockMvc.perform(put("/api/work-effort-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortPurpose in the database
        List<WorkEffortPurpose> workEffortPurposeList = workEffortPurposeRepository.findAll();
        assertThat(workEffortPurposeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortPurpose() throws Exception {
        // Initialize the database
        workEffortPurposeRepository.saveAndFlush(workEffortPurpose);

        int databaseSizeBeforeDelete = workEffortPurposeRepository.findAll().size();

        // Delete the workEffortPurpose
        restWorkEffortPurposeMockMvc.perform(delete("/api/work-effort-purposes/{id}", workEffortPurpose.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortPurpose> workEffortPurposeList = workEffortPurposeRepository.findAll();
        assertThat(workEffortPurposeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
