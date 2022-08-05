package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PayGrade;
import com.hr.repository.PayGradeRepository;

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
 * Integration tests for the {@link PayGradeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PayGradeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PayGradeRepository payGradeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayGradeMockMvc;

    private PayGrade payGrade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayGrade createEntity(EntityManager em) {
        PayGrade payGrade = new PayGrade()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return payGrade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayGrade createUpdatedEntity(EntityManager em) {
        PayGrade payGrade = new PayGrade()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return payGrade;
    }

    @BeforeEach
    public void initTest() {
        payGrade = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayGrade() throws Exception {
        int databaseSizeBeforeCreate = payGradeRepository.findAll().size();
        // Create the PayGrade
        restPayGradeMockMvc.perform(post("/api/pay-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payGrade)))
            .andExpect(status().isCreated());

        // Validate the PayGrade in the database
        List<PayGrade> payGradeList = payGradeRepository.findAll();
        assertThat(payGradeList).hasSize(databaseSizeBeforeCreate + 1);
        PayGrade testPayGrade = payGradeList.get(payGradeList.size() - 1);
        assertThat(testPayGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPayGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payGradeRepository.findAll().size();

        // Create the PayGrade with an existing ID
        payGrade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayGradeMockMvc.perform(post("/api/pay-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payGrade)))
            .andExpect(status().isBadRequest());

        // Validate the PayGrade in the database
        List<PayGrade> payGradeList = payGradeRepository.findAll();
        assertThat(payGradeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayGrades() throws Exception {
        // Initialize the database
        payGradeRepository.saveAndFlush(payGrade);

        // Get all the payGradeList
        restPayGradeMockMvc.perform(get("/api/pay-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payGrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPayGrade() throws Exception {
        // Initialize the database
        payGradeRepository.saveAndFlush(payGrade);

        // Get the payGrade
        restPayGradeMockMvc.perform(get("/api/pay-grades/{id}", payGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payGrade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPayGrade() throws Exception {
        // Get the payGrade
        restPayGradeMockMvc.perform(get("/api/pay-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayGrade() throws Exception {
        // Initialize the database
        payGradeRepository.saveAndFlush(payGrade);

        int databaseSizeBeforeUpdate = payGradeRepository.findAll().size();

        // Update the payGrade
        PayGrade updatedPayGrade = payGradeRepository.findById(payGrade.getId()).get();
        // Disconnect from session so that the updates on updatedPayGrade are not directly saved in db
        em.detach(updatedPayGrade);
        updatedPayGrade
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPayGradeMockMvc.perform(put("/api/pay-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayGrade)))
            .andExpect(status().isOk());

        // Validate the PayGrade in the database
        List<PayGrade> payGradeList = payGradeRepository.findAll();
        assertThat(payGradeList).hasSize(databaseSizeBeforeUpdate);
        PayGrade testPayGrade = payGradeList.get(payGradeList.size() - 1);
        assertThat(testPayGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPayGrade() throws Exception {
        int databaseSizeBeforeUpdate = payGradeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayGradeMockMvc.perform(put("/api/pay-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payGrade)))
            .andExpect(status().isBadRequest());

        // Validate the PayGrade in the database
        List<PayGrade> payGradeList = payGradeRepository.findAll();
        assertThat(payGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayGrade() throws Exception {
        // Initialize the database
        payGradeRepository.saveAndFlush(payGrade);

        int databaseSizeBeforeDelete = payGradeRepository.findAll().size();

        // Delete the payGrade
        restPayGradeMockMvc.perform(delete("/api/pay-grades/{id}", payGrade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayGrade> payGradeList = payGradeRepository.findAll();
        assertThat(payGradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
