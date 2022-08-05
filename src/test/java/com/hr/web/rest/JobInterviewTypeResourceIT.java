package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.JobInterviewType;
import com.hr.repository.JobInterviewTypeRepository;

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
 * Integration tests for the {@link JobInterviewTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobInterviewTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JobInterviewTypeRepository jobInterviewTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobInterviewTypeMockMvc;

    private JobInterviewType jobInterviewType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInterviewType createEntity(EntityManager em) {
        JobInterviewType jobInterviewType = new JobInterviewType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return jobInterviewType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInterviewType createUpdatedEntity(EntityManager em) {
        JobInterviewType jobInterviewType = new JobInterviewType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return jobInterviewType;
    }

    @BeforeEach
    public void initTest() {
        jobInterviewType = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobInterviewType() throws Exception {
        int databaseSizeBeforeCreate = jobInterviewTypeRepository.findAll().size();
        // Create the JobInterviewType
        restJobInterviewTypeMockMvc.perform(post("/api/job-interview-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterviewType)))
            .andExpect(status().isCreated());

        // Validate the JobInterviewType in the database
        List<JobInterviewType> jobInterviewTypeList = jobInterviewTypeRepository.findAll();
        assertThat(jobInterviewTypeList).hasSize(databaseSizeBeforeCreate + 1);
        JobInterviewType testJobInterviewType = jobInterviewTypeList.get(jobInterviewTypeList.size() - 1);
        assertThat(testJobInterviewType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobInterviewType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJobInterviewTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobInterviewTypeRepository.findAll().size();

        // Create the JobInterviewType with an existing ID
        jobInterviewType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobInterviewTypeMockMvc.perform(post("/api/job-interview-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterviewType)))
            .andExpect(status().isBadRequest());

        // Validate the JobInterviewType in the database
        List<JobInterviewType> jobInterviewTypeList = jobInterviewTypeRepository.findAll();
        assertThat(jobInterviewTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobInterviewTypes() throws Exception {
        // Initialize the database
        jobInterviewTypeRepository.saveAndFlush(jobInterviewType);

        // Get all the jobInterviewTypeList
        restJobInterviewTypeMockMvc.perform(get("/api/job-interview-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobInterviewType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getJobInterviewType() throws Exception {
        // Initialize the database
        jobInterviewTypeRepository.saveAndFlush(jobInterviewType);

        // Get the jobInterviewType
        restJobInterviewTypeMockMvc.perform(get("/api/job-interview-types/{id}", jobInterviewType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobInterviewType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingJobInterviewType() throws Exception {
        // Get the jobInterviewType
        restJobInterviewTypeMockMvc.perform(get("/api/job-interview-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobInterviewType() throws Exception {
        // Initialize the database
        jobInterviewTypeRepository.saveAndFlush(jobInterviewType);

        int databaseSizeBeforeUpdate = jobInterviewTypeRepository.findAll().size();

        // Update the jobInterviewType
        JobInterviewType updatedJobInterviewType = jobInterviewTypeRepository.findById(jobInterviewType.getId()).get();
        // Disconnect from session so that the updates on updatedJobInterviewType are not directly saved in db
        em.detach(updatedJobInterviewType);
        updatedJobInterviewType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restJobInterviewTypeMockMvc.perform(put("/api/job-interview-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobInterviewType)))
            .andExpect(status().isOk());

        // Validate the JobInterviewType in the database
        List<JobInterviewType> jobInterviewTypeList = jobInterviewTypeRepository.findAll();
        assertThat(jobInterviewTypeList).hasSize(databaseSizeBeforeUpdate);
        JobInterviewType testJobInterviewType = jobInterviewTypeList.get(jobInterviewTypeList.size() - 1);
        assertThat(testJobInterviewType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobInterviewType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingJobInterviewType() throws Exception {
        int databaseSizeBeforeUpdate = jobInterviewTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobInterviewTypeMockMvc.perform(put("/api/job-interview-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterviewType)))
            .andExpect(status().isBadRequest());

        // Validate the JobInterviewType in the database
        List<JobInterviewType> jobInterviewTypeList = jobInterviewTypeRepository.findAll();
        assertThat(jobInterviewTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobInterviewType() throws Exception {
        // Initialize the database
        jobInterviewTypeRepository.saveAndFlush(jobInterviewType);

        int databaseSizeBeforeDelete = jobInterviewTypeRepository.findAll().size();

        // Delete the jobInterviewType
        restJobInterviewTypeMockMvc.perform(delete("/api/job-interview-types/{id}", jobInterviewType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobInterviewType> jobInterviewTypeList = jobInterviewTypeRepository.findAll();
        assertThat(jobInterviewTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
