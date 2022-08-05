package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.JobRequisition;
import com.hr.repository.JobRequisitionRepository;

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
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hr.domain.enumeration.Gender;
/**
 * Integration tests for the {@link JobRequisitionResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobRequisitionResourceIT {

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_EXPERIENCE_MONTHS = 1;
    private static final Integer UPDATED_EXPERIENCE_MONTHS = 2;

    private static final Integer DEFAULT_EXPERIENCE_YEARS = 1;
    private static final Integer UPDATED_EXPERIENCE_YEARS = 2;

    private static final String DEFAULT_QUALIFICATION_STR = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION_STR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_RESOURCE = 1;
    private static final Integer UPDATED_NO_OF_RESOURCE = 2;

    private static final LocalDate DEFAULT_REQUIRED_ON_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUIRED_ON_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private JobRequisitionRepository jobRequisitionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobRequisitionMockMvc;

    private JobRequisition jobRequisition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobRequisition createEntity(EntityManager em) {
        JobRequisition jobRequisition = new JobRequisition()
            .duration(DEFAULT_DURATION)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .experienceMonths(DEFAULT_EXPERIENCE_MONTHS)
            .experienceYears(DEFAULT_EXPERIENCE_YEARS)
            .qualificationStr(DEFAULT_QUALIFICATION_STR)
            .noOfResource(DEFAULT_NO_OF_RESOURCE)
            .requiredOnDate(DEFAULT_REQUIRED_ON_DATE);
        return jobRequisition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobRequisition createUpdatedEntity(EntityManager em) {
        JobRequisition jobRequisition = new JobRequisition()
            .duration(UPDATED_DURATION)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .experienceMonths(UPDATED_EXPERIENCE_MONTHS)
            .experienceYears(UPDATED_EXPERIENCE_YEARS)
            .qualificationStr(UPDATED_QUALIFICATION_STR)
            .noOfResource(UPDATED_NO_OF_RESOURCE)
            .requiredOnDate(UPDATED_REQUIRED_ON_DATE);
        return jobRequisition;
    }

    @BeforeEach
    public void initTest() {
        jobRequisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobRequisition() throws Exception {
        int databaseSizeBeforeCreate = jobRequisitionRepository.findAll().size();
        // Create the JobRequisition
        restJobRequisitionMockMvc.perform(post("/api/job-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequisition)))
            .andExpect(status().isCreated());

        // Validate the JobRequisition in the database
        List<JobRequisition> jobRequisitionList = jobRequisitionRepository.findAll();
        assertThat(jobRequisitionList).hasSize(databaseSizeBeforeCreate + 1);
        JobRequisition testJobRequisition = jobRequisitionList.get(jobRequisitionList.size() - 1);
        assertThat(testJobRequisition.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJobRequisition.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testJobRequisition.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testJobRequisition.getExperienceMonths()).isEqualTo(DEFAULT_EXPERIENCE_MONTHS);
        assertThat(testJobRequisition.getExperienceYears()).isEqualTo(DEFAULT_EXPERIENCE_YEARS);
        assertThat(testJobRequisition.getQualificationStr()).isEqualTo(DEFAULT_QUALIFICATION_STR);
        assertThat(testJobRequisition.getNoOfResource()).isEqualTo(DEFAULT_NO_OF_RESOURCE);
        assertThat(testJobRequisition.getRequiredOnDate()).isEqualTo(DEFAULT_REQUIRED_ON_DATE);
    }

    @Test
    @Transactional
    public void createJobRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRequisitionRepository.findAll().size();

        // Create the JobRequisition with an existing ID
        jobRequisition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobRequisitionMockMvc.perform(post("/api/job-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequisition)))
            .andExpect(status().isBadRequest());

        // Validate the JobRequisition in the database
        List<JobRequisition> jobRequisitionList = jobRequisitionRepository.findAll();
        assertThat(jobRequisitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobRequisitions() throws Exception {
        // Initialize the database
        jobRequisitionRepository.saveAndFlush(jobRequisition);

        // Get all the jobRequisitionList
        restJobRequisitionMockMvc.perform(get("/api/job-requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobRequisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].experienceMonths").value(hasItem(DEFAULT_EXPERIENCE_MONTHS)))
            .andExpect(jsonPath("$.[*].experienceYears").value(hasItem(DEFAULT_EXPERIENCE_YEARS)))
            .andExpect(jsonPath("$.[*].qualificationStr").value(hasItem(DEFAULT_QUALIFICATION_STR)))
            .andExpect(jsonPath("$.[*].noOfResource").value(hasItem(DEFAULT_NO_OF_RESOURCE)))
            .andExpect(jsonPath("$.[*].requiredOnDate").value(hasItem(DEFAULT_REQUIRED_ON_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getJobRequisition() throws Exception {
        // Initialize the database
        jobRequisitionRepository.saveAndFlush(jobRequisition);

        // Get the jobRequisition
        restJobRequisitionMockMvc.perform(get("/api/job-requisitions/{id}", jobRequisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobRequisition.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.experienceMonths").value(DEFAULT_EXPERIENCE_MONTHS))
            .andExpect(jsonPath("$.experienceYears").value(DEFAULT_EXPERIENCE_YEARS))
            .andExpect(jsonPath("$.qualificationStr").value(DEFAULT_QUALIFICATION_STR))
            .andExpect(jsonPath("$.noOfResource").value(DEFAULT_NO_OF_RESOURCE))
            .andExpect(jsonPath("$.requiredOnDate").value(DEFAULT_REQUIRED_ON_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJobRequisition() throws Exception {
        // Get the jobRequisition
        restJobRequisitionMockMvc.perform(get("/api/job-requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobRequisition() throws Exception {
        // Initialize the database
        jobRequisitionRepository.saveAndFlush(jobRequisition);

        int databaseSizeBeforeUpdate = jobRequisitionRepository.findAll().size();

        // Update the jobRequisition
        JobRequisition updatedJobRequisition = jobRequisitionRepository.findById(jobRequisition.getId()).get();
        // Disconnect from session so that the updates on updatedJobRequisition are not directly saved in db
        em.detach(updatedJobRequisition);
        updatedJobRequisition
            .duration(UPDATED_DURATION)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .experienceMonths(UPDATED_EXPERIENCE_MONTHS)
            .experienceYears(UPDATED_EXPERIENCE_YEARS)
            .qualificationStr(UPDATED_QUALIFICATION_STR)
            .noOfResource(UPDATED_NO_OF_RESOURCE)
            .requiredOnDate(UPDATED_REQUIRED_ON_DATE);

        restJobRequisitionMockMvc.perform(put("/api/job-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobRequisition)))
            .andExpect(status().isOk());

        // Validate the JobRequisition in the database
        List<JobRequisition> jobRequisitionList = jobRequisitionRepository.findAll();
        assertThat(jobRequisitionList).hasSize(databaseSizeBeforeUpdate);
        JobRequisition testJobRequisition = jobRequisitionList.get(jobRequisitionList.size() - 1);
        assertThat(testJobRequisition.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJobRequisition.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testJobRequisition.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJobRequisition.getExperienceMonths()).isEqualTo(UPDATED_EXPERIENCE_MONTHS);
        assertThat(testJobRequisition.getExperienceYears()).isEqualTo(UPDATED_EXPERIENCE_YEARS);
        assertThat(testJobRequisition.getQualificationStr()).isEqualTo(UPDATED_QUALIFICATION_STR);
        assertThat(testJobRequisition.getNoOfResource()).isEqualTo(UPDATED_NO_OF_RESOURCE);
        assertThat(testJobRequisition.getRequiredOnDate()).isEqualTo(UPDATED_REQUIRED_ON_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobRequisition() throws Exception {
        int databaseSizeBeforeUpdate = jobRequisitionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobRequisitionMockMvc.perform(put("/api/job-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobRequisition)))
            .andExpect(status().isBadRequest());

        // Validate the JobRequisition in the database
        List<JobRequisition> jobRequisitionList = jobRequisitionRepository.findAll();
        assertThat(jobRequisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobRequisition() throws Exception {
        // Initialize the database
        jobRequisitionRepository.saveAndFlush(jobRequisition);

        int databaseSizeBeforeDelete = jobRequisitionRepository.findAll().size();

        // Delete the jobRequisition
        restJobRequisitionMockMvc.perform(delete("/api/job-requisitions/{id}", jobRequisition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobRequisition> jobRequisitionList = jobRequisitionRepository.findAll();
        assertThat(jobRequisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
