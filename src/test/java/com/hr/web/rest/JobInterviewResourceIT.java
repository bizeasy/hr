package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.JobInterview;
import com.hr.repository.JobInterviewRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobInterviewResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobInterviewResourceIT {

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_INTERVIEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INTERVIEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private JobInterviewRepository jobInterviewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobInterviewMockMvc;

    private JobInterview jobInterview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInterview createEntity(EntityManager em) {
        JobInterview jobInterview = new JobInterview()
            .remarks(DEFAULT_REMARKS)
            .interviewDate(DEFAULT_INTERVIEW_DATE);
        return jobInterview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInterview createUpdatedEntity(EntityManager em) {
        JobInterview jobInterview = new JobInterview()
            .remarks(UPDATED_REMARKS)
            .interviewDate(UPDATED_INTERVIEW_DATE);
        return jobInterview;
    }

    @BeforeEach
    public void initTest() {
        jobInterview = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobInterview() throws Exception {
        int databaseSizeBeforeCreate = jobInterviewRepository.findAll().size();
        // Create the JobInterview
        restJobInterviewMockMvc.perform(post("/api/job-interviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterview)))
            .andExpect(status().isCreated());

        // Validate the JobInterview in the database
        List<JobInterview> jobInterviewList = jobInterviewRepository.findAll();
        assertThat(jobInterviewList).hasSize(databaseSizeBeforeCreate + 1);
        JobInterview testJobInterview = jobInterviewList.get(jobInterviewList.size() - 1);
        assertThat(testJobInterview.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testJobInterview.getInterviewDate()).isEqualTo(DEFAULT_INTERVIEW_DATE);
    }

    @Test
    @Transactional
    public void createJobInterviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobInterviewRepository.findAll().size();

        // Create the JobInterview with an existing ID
        jobInterview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobInterviewMockMvc.perform(post("/api/job-interviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterview)))
            .andExpect(status().isBadRequest());

        // Validate the JobInterview in the database
        List<JobInterview> jobInterviewList = jobInterviewRepository.findAll();
        assertThat(jobInterviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobInterviews() throws Exception {
        // Initialize the database
        jobInterviewRepository.saveAndFlush(jobInterview);

        // Get all the jobInterviewList
        restJobInterviewMockMvc.perform(get("/api/job-interviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobInterview.getId().intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].interviewDate").value(hasItem(DEFAULT_INTERVIEW_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getJobInterview() throws Exception {
        // Initialize the database
        jobInterviewRepository.saveAndFlush(jobInterview);

        // Get the jobInterview
        restJobInterviewMockMvc.perform(get("/api/job-interviews/{id}", jobInterview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobInterview.getId().intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.interviewDate").value(DEFAULT_INTERVIEW_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJobInterview() throws Exception {
        // Get the jobInterview
        restJobInterviewMockMvc.perform(get("/api/job-interviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobInterview() throws Exception {
        // Initialize the database
        jobInterviewRepository.saveAndFlush(jobInterview);

        int databaseSizeBeforeUpdate = jobInterviewRepository.findAll().size();

        // Update the jobInterview
        JobInterview updatedJobInterview = jobInterviewRepository.findById(jobInterview.getId()).get();
        // Disconnect from session so that the updates on updatedJobInterview are not directly saved in db
        em.detach(updatedJobInterview);
        updatedJobInterview
            .remarks(UPDATED_REMARKS)
            .interviewDate(UPDATED_INTERVIEW_DATE);

        restJobInterviewMockMvc.perform(put("/api/job-interviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobInterview)))
            .andExpect(status().isOk());

        // Validate the JobInterview in the database
        List<JobInterview> jobInterviewList = jobInterviewRepository.findAll();
        assertThat(jobInterviewList).hasSize(databaseSizeBeforeUpdate);
        JobInterview testJobInterview = jobInterviewList.get(jobInterviewList.size() - 1);
        assertThat(testJobInterview.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testJobInterview.getInterviewDate()).isEqualTo(UPDATED_INTERVIEW_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobInterview() throws Exception {
        int databaseSizeBeforeUpdate = jobInterviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobInterviewMockMvc.perform(put("/api/job-interviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobInterview)))
            .andExpect(status().isBadRequest());

        // Validate the JobInterview in the database
        List<JobInterview> jobInterviewList = jobInterviewRepository.findAll();
        assertThat(jobInterviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobInterview() throws Exception {
        // Initialize the database
        jobInterviewRepository.saveAndFlush(jobInterview);

        int databaseSizeBeforeDelete = jobInterviewRepository.findAll().size();

        // Delete the jobInterview
        restJobInterviewMockMvc.perform(delete("/api/job-interviews/{id}", jobInterview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobInterview> jobInterviewList = jobInterviewRepository.findAll();
        assertThat(jobInterviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
