package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortType;
import com.hr.repository.WorkEffortTypeRepository;

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
 * Integration tests for the {@link WorkEffortTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkEffortTypeRepository workEffortTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortTypeMockMvc;

    private WorkEffortType workEffortType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortType createEntity(EntityManager em) {
        WorkEffortType workEffortType = new WorkEffortType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return workEffortType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortType createUpdatedEntity(EntityManager em) {
        WorkEffortType workEffortType = new WorkEffortType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return workEffortType;
    }

    @BeforeEach
    public void initTest() {
        workEffortType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortType() throws Exception {
        int databaseSizeBeforeCreate = workEffortTypeRepository.findAll().size();
        // Create the WorkEffortType
        restWorkEffortTypeMockMvc.perform(post("/api/work-effort-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortType)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortType in the database
        List<WorkEffortType> workEffortTypeList = workEffortTypeRepository.findAll();
        assertThat(workEffortTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortType testWorkEffortType = workEffortTypeList.get(workEffortTypeList.size() - 1);
        assertThat(testWorkEffortType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEffortType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkEffortTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortTypeRepository.findAll().size();

        // Create the WorkEffortType with an existing ID
        workEffortType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortTypeMockMvc.perform(post("/api/work-effort-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortType)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortType in the database
        List<WorkEffortType> workEffortTypeList = workEffortTypeRepository.findAll();
        assertThat(workEffortTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortTypes() throws Exception {
        // Initialize the database
        workEffortTypeRepository.saveAndFlush(workEffortType);

        // Get all the workEffortTypeList
        restWorkEffortTypeMockMvc.perform(get("/api/work-effort-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkEffortType() throws Exception {
        // Initialize the database
        workEffortTypeRepository.saveAndFlush(workEffortType);

        // Get the workEffortType
        restWorkEffortTypeMockMvc.perform(get("/api/work-effort-types/{id}", workEffortType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWorkEffortType() throws Exception {
        // Get the workEffortType
        restWorkEffortTypeMockMvc.perform(get("/api/work-effort-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortType() throws Exception {
        // Initialize the database
        workEffortTypeRepository.saveAndFlush(workEffortType);

        int databaseSizeBeforeUpdate = workEffortTypeRepository.findAll().size();

        // Update the workEffortType
        WorkEffortType updatedWorkEffortType = workEffortTypeRepository.findById(workEffortType.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortType are not directly saved in db
        em.detach(updatedWorkEffortType);
        updatedWorkEffortType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restWorkEffortTypeMockMvc.perform(put("/api/work-effort-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortType)))
            .andExpect(status().isOk());

        // Validate the WorkEffortType in the database
        List<WorkEffortType> workEffortTypeList = workEffortTypeRepository.findAll();
        assertThat(workEffortTypeList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortType testWorkEffortType = workEffortTypeList.get(workEffortTypeList.size() - 1);
        assertThat(testWorkEffortType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEffortType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortType() throws Exception {
        int databaseSizeBeforeUpdate = workEffortTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortTypeMockMvc.perform(put("/api/work-effort-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortType)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortType in the database
        List<WorkEffortType> workEffortTypeList = workEffortTypeRepository.findAll();
        assertThat(workEffortTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortType() throws Exception {
        // Initialize the database
        workEffortTypeRepository.saveAndFlush(workEffortType);

        int databaseSizeBeforeDelete = workEffortTypeRepository.findAll().size();

        // Delete the workEffortType
        restWorkEffortTypeMockMvc.perform(delete("/api/work-effort-types/{id}", workEffortType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortType> workEffortTypeList = workEffortTypeRepository.findAll();
        assertThat(workEffortTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
