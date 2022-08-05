package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Employment;
import com.hr.repository.EmploymentRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmploymentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmploymentResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmploymentRepository employmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentMockMvc;

    private Employment employment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employment createEntity(EntityManager em) {
        Employment employment = new Employment()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return employment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employment createUpdatedEntity(EntityManager em) {
        Employment employment = new Employment()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return employment;
    }

    @BeforeEach
    public void initTest() {
        employment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployment() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();
        // Create the Employment
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate + 1);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmployment.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createEmploymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment with an existing ID
        employment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentMockMvc.perform(post("/api/employments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployments() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get all the employmentList
        restEmploymentMockMvc.perform(get("/api/employments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", employment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employment.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployment() throws Exception {
        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment
        Employment updatedEmployment = employmentRepository.findById(employment.getId()).get();
        // Disconnect from session so that the updates on updatedEmployment are not directly saved in db
        em.detach(updatedEmployment);
        updatedEmployment
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployment)))
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmployment.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentMockMvc.perform(put("/api/employments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employment)))
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployment() throws Exception {
        // Initialize the database
        employmentRepository.saveAndFlush(employment);

        int databaseSizeBeforeDelete = employmentRepository.findAll().size();

        // Delete the employment
        restEmploymentMockMvc.perform(delete("/api/employments/{id}", employment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
