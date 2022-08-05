package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmploymentApp;
import com.hr.repository.EmploymentAppRepository;

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
 * Integration tests for the {@link EmploymentAppResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmploymentAppResourceIT {

    private static final Instant DEFAULT_APPLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmploymentAppRepository employmentAppRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentAppMockMvc;

    private EmploymentApp employmentApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentApp createEntity(EntityManager em) {
        EmploymentApp employmentApp = new EmploymentApp()
            .applicationDate(DEFAULT_APPLICATION_DATE);
        return employmentApp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentApp createUpdatedEntity(EntityManager em) {
        EmploymentApp employmentApp = new EmploymentApp()
            .applicationDate(UPDATED_APPLICATION_DATE);
        return employmentApp;
    }

    @BeforeEach
    public void initTest() {
        employmentApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploymentApp() throws Exception {
        int databaseSizeBeforeCreate = employmentAppRepository.findAll().size();
        // Create the EmploymentApp
        restEmploymentAppMockMvc.perform(post("/api/employment-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentApp)))
            .andExpect(status().isCreated());

        // Validate the EmploymentApp in the database
        List<EmploymentApp> employmentAppList = employmentAppRepository.findAll();
        assertThat(employmentAppList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentApp testEmploymentApp = employmentAppList.get(employmentAppList.size() - 1);
        assertThat(testEmploymentApp.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
    }

    @Test
    @Transactional
    public void createEmploymentAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentAppRepository.findAll().size();

        // Create the EmploymentApp with an existing ID
        employmentApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentAppMockMvc.perform(post("/api/employment-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentApp)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentApp in the database
        List<EmploymentApp> employmentAppList = employmentAppRepository.findAll();
        assertThat(employmentAppList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmploymentApps() throws Exception {
        // Initialize the database
        employmentAppRepository.saveAndFlush(employmentApp);

        // Get all the employmentAppList
        restEmploymentAppMockMvc.perform(get("/api/employment-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmploymentApp() throws Exception {
        // Initialize the database
        employmentAppRepository.saveAndFlush(employmentApp);

        // Get the employmentApp
        restEmploymentAppMockMvc.perform(get("/api/employment-apps/{id}", employmentApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentApp.getId().intValue()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmploymentApp() throws Exception {
        // Get the employmentApp
        restEmploymentAppMockMvc.perform(get("/api/employment-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploymentApp() throws Exception {
        // Initialize the database
        employmentAppRepository.saveAndFlush(employmentApp);

        int databaseSizeBeforeUpdate = employmentAppRepository.findAll().size();

        // Update the employmentApp
        EmploymentApp updatedEmploymentApp = employmentAppRepository.findById(employmentApp.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentApp are not directly saved in db
        em.detach(updatedEmploymentApp);
        updatedEmploymentApp
            .applicationDate(UPDATED_APPLICATION_DATE);

        restEmploymentAppMockMvc.perform(put("/api/employment-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentApp)))
            .andExpect(status().isOk());

        // Validate the EmploymentApp in the database
        List<EmploymentApp> employmentAppList = employmentAppRepository.findAll();
        assertThat(employmentAppList).hasSize(databaseSizeBeforeUpdate);
        EmploymentApp testEmploymentApp = employmentAppList.get(employmentAppList.size() - 1);
        assertThat(testEmploymentApp.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploymentApp() throws Exception {
        int databaseSizeBeforeUpdate = employmentAppRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentAppMockMvc.perform(put("/api/employment-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentApp)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentApp in the database
        List<EmploymentApp> employmentAppList = employmentAppRepository.findAll();
        assertThat(employmentAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploymentApp() throws Exception {
        // Initialize the database
        employmentAppRepository.saveAndFlush(employmentApp);

        int databaseSizeBeforeDelete = employmentAppRepository.findAll().size();

        // Delete the employmentApp
        restEmploymentAppMockMvc.perform(delete("/api/employment-apps/{id}", employmentApp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentApp> employmentAppList = employmentAppRepository.findAll();
        assertThat(employmentAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
