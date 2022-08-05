package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InterviewGrade;
import com.hr.repository.InterviewGradeRepository;

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
 * Integration tests for the {@link InterviewGradeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InterviewGradeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InterviewGradeRepository interviewGradeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewGradeMockMvc;

    private InterviewGrade interviewGrade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewGrade createEntity(EntityManager em) {
        InterviewGrade interviewGrade = new InterviewGrade()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return interviewGrade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewGrade createUpdatedEntity(EntityManager em) {
        InterviewGrade interviewGrade = new InterviewGrade()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return interviewGrade;
    }

    @BeforeEach
    public void initTest() {
        interviewGrade = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewGrade() throws Exception {
        int databaseSizeBeforeCreate = interviewGradeRepository.findAll().size();
        // Create the InterviewGrade
        restInterviewGradeMockMvc.perform(post("/api/interview-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewGrade)))
            .andExpect(status().isCreated());

        // Validate the InterviewGrade in the database
        List<InterviewGrade> interviewGradeList = interviewGradeRepository.findAll();
        assertThat(interviewGradeList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewGrade testInterviewGrade = interviewGradeList.get(interviewGradeList.size() - 1);
        assertThat(testInterviewGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInterviewGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInterviewGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewGradeRepository.findAll().size();

        // Create the InterviewGrade with an existing ID
        interviewGrade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewGradeMockMvc.perform(post("/api/interview-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewGrade)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewGrade in the database
        List<InterviewGrade> interviewGradeList = interviewGradeRepository.findAll();
        assertThat(interviewGradeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterviewGrades() throws Exception {
        // Initialize the database
        interviewGradeRepository.saveAndFlush(interviewGrade);

        // Get all the interviewGradeList
        restInterviewGradeMockMvc.perform(get("/api/interview-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewGrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInterviewGrade() throws Exception {
        // Initialize the database
        interviewGradeRepository.saveAndFlush(interviewGrade);

        // Get the interviewGrade
        restInterviewGradeMockMvc.perform(get("/api/interview-grades/{id}", interviewGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewGrade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingInterviewGrade() throws Exception {
        // Get the interviewGrade
        restInterviewGradeMockMvc.perform(get("/api/interview-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewGrade() throws Exception {
        // Initialize the database
        interviewGradeRepository.saveAndFlush(interviewGrade);

        int databaseSizeBeforeUpdate = interviewGradeRepository.findAll().size();

        // Update the interviewGrade
        InterviewGrade updatedInterviewGrade = interviewGradeRepository.findById(interviewGrade.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewGrade are not directly saved in db
        em.detach(updatedInterviewGrade);
        updatedInterviewGrade
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restInterviewGradeMockMvc.perform(put("/api/interview-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterviewGrade)))
            .andExpect(status().isOk());

        // Validate the InterviewGrade in the database
        List<InterviewGrade> interviewGradeList = interviewGradeRepository.findAll();
        assertThat(interviewGradeList).hasSize(databaseSizeBeforeUpdate);
        InterviewGrade testInterviewGrade = interviewGradeList.get(interviewGradeList.size() - 1);
        assertThat(testInterviewGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInterviewGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewGrade() throws Exception {
        int databaseSizeBeforeUpdate = interviewGradeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewGradeMockMvc.perform(put("/api/interview-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewGrade)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewGrade in the database
        List<InterviewGrade> interviewGradeList = interviewGradeRepository.findAll();
        assertThat(interviewGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterviewGrade() throws Exception {
        // Initialize the database
        interviewGradeRepository.saveAndFlush(interviewGrade);

        int databaseSizeBeforeDelete = interviewGradeRepository.findAll().size();

        // Delete the interviewGrade
        restInterviewGradeMockMvc.perform(delete("/api/interview-grades/{id}", interviewGrade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterviewGrade> interviewGradeList = interviewGradeRepository.findAll();
        assertThat(interviewGradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
