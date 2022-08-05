package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ExamType;
import com.hr.repository.ExamTypeRepository;

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
 * Integration tests for the {@link ExamTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamTypeMockMvc;

    private ExamType examType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamType createEntity(EntityManager em) {
        ExamType examType = new ExamType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return examType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamType createUpdatedEntity(EntityManager em) {
        ExamType examType = new ExamType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return examType;
    }

    @BeforeEach
    public void initTest() {
        examType = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamType() throws Exception {
        int databaseSizeBeforeCreate = examTypeRepository.findAll().size();
        // Create the ExamType
        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isCreated());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExamType testExamType = examTypeList.get(examTypeList.size() - 1);
        assertThat(testExamType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExamTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examTypeRepository.findAll().size();

        // Create the ExamType with an existing ID
        examType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExamTypes() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList
        restExamTypeMockMvc.perform(get("/api/exam-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getExamType() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get the examType
        restExamTypeMockMvc.perform(get("/api/exam-types/{id}", examType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingExamType() throws Exception {
        // Get the examType
        restExamTypeMockMvc.perform(get("/api/exam-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamType() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        int databaseSizeBeforeUpdate = examTypeRepository.findAll().size();

        // Update the examType
        ExamType updatedExamType = examTypeRepository.findById(examType.getId()).get();
        // Disconnect from session so that the updates on updatedExamType are not directly saved in db
        em.detach(updatedExamType);
        updatedExamType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restExamTypeMockMvc.perform(put("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamType)))
            .andExpect(status().isOk());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeUpdate);
        ExamType testExamType = examTypeList.get(examTypeList.size() - 1);
        assertThat(testExamType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExamType() throws Exception {
        int databaseSizeBeforeUpdate = examTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamTypeMockMvc.perform(put("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamType() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        int databaseSizeBeforeDelete = examTypeRepository.findAll().size();

        // Delete the examType
        restExamTypeMockMvc.perform(delete("/api/exam-types/{id}", examType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
