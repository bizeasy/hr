package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortAssocType;
import com.hr.repository.WorkEffortAssocTypeRepository;

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
 * Integration tests for the {@link WorkEffortAssocTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortAssocTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkEffortAssocTypeRepository workEffortAssocTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortAssocTypeMockMvc;

    private WorkEffortAssocType workEffortAssocType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortAssocType createEntity(EntityManager em) {
        WorkEffortAssocType workEffortAssocType = new WorkEffortAssocType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return workEffortAssocType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortAssocType createUpdatedEntity(EntityManager em) {
        WorkEffortAssocType workEffortAssocType = new WorkEffortAssocType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return workEffortAssocType;
    }

    @BeforeEach
    public void initTest() {
        workEffortAssocType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortAssocType() throws Exception {
        int databaseSizeBeforeCreate = workEffortAssocTypeRepository.findAll().size();
        // Create the WorkEffortAssocType
        restWorkEffortAssocTypeMockMvc.perform(post("/api/work-effort-assoc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssocType)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortAssocType in the database
        List<WorkEffortAssocType> workEffortAssocTypeList = workEffortAssocTypeRepository.findAll();
        assertThat(workEffortAssocTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortAssocType testWorkEffortAssocType = workEffortAssocTypeList.get(workEffortAssocTypeList.size() - 1);
        assertThat(testWorkEffortAssocType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEffortAssocType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkEffortAssocTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortAssocTypeRepository.findAll().size();

        // Create the WorkEffortAssocType with an existing ID
        workEffortAssocType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortAssocTypeMockMvc.perform(post("/api/work-effort-assoc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssocType)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortAssocType in the database
        List<WorkEffortAssocType> workEffortAssocTypeList = workEffortAssocTypeRepository.findAll();
        assertThat(workEffortAssocTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocTypes() throws Exception {
        // Initialize the database
        workEffortAssocTypeRepository.saveAndFlush(workEffortAssocType);

        // Get all the workEffortAssocTypeList
        restWorkEffortAssocTypeMockMvc.perform(get("/api/work-effort-assoc-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortAssocType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkEffortAssocType() throws Exception {
        // Initialize the database
        workEffortAssocTypeRepository.saveAndFlush(workEffortAssocType);

        // Get the workEffortAssocType
        restWorkEffortAssocTypeMockMvc.perform(get("/api/work-effort-assoc-types/{id}", workEffortAssocType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortAssocType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWorkEffortAssocType() throws Exception {
        // Get the workEffortAssocType
        restWorkEffortAssocTypeMockMvc.perform(get("/api/work-effort-assoc-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortAssocType() throws Exception {
        // Initialize the database
        workEffortAssocTypeRepository.saveAndFlush(workEffortAssocType);

        int databaseSizeBeforeUpdate = workEffortAssocTypeRepository.findAll().size();

        // Update the workEffortAssocType
        WorkEffortAssocType updatedWorkEffortAssocType = workEffortAssocTypeRepository.findById(workEffortAssocType.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortAssocType are not directly saved in db
        em.detach(updatedWorkEffortAssocType);
        updatedWorkEffortAssocType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restWorkEffortAssocTypeMockMvc.perform(put("/api/work-effort-assoc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortAssocType)))
            .andExpect(status().isOk());

        // Validate the WorkEffortAssocType in the database
        List<WorkEffortAssocType> workEffortAssocTypeList = workEffortAssocTypeRepository.findAll();
        assertThat(workEffortAssocTypeList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortAssocType testWorkEffortAssocType = workEffortAssocTypeList.get(workEffortAssocTypeList.size() - 1);
        assertThat(testWorkEffortAssocType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEffortAssocType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortAssocType() throws Exception {
        int databaseSizeBeforeUpdate = workEffortAssocTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortAssocTypeMockMvc.perform(put("/api/work-effort-assoc-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssocType)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortAssocType in the database
        List<WorkEffortAssocType> workEffortAssocTypeList = workEffortAssocTypeRepository.findAll();
        assertThat(workEffortAssocTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortAssocType() throws Exception {
        // Initialize the database
        workEffortAssocTypeRepository.saveAndFlush(workEffortAssocType);

        int databaseSizeBeforeDelete = workEffortAssocTypeRepository.findAll().size();

        // Delete the workEffortAssocType
        restWorkEffortAssocTypeMockMvc.perform(delete("/api/work-effort-assoc-types/{id}", workEffortAssocType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortAssocType> workEffortAssocTypeList = workEffortAssocTypeRepository.findAll();
        assertThat(workEffortAssocTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
