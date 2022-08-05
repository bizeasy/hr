package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmploymentAppSourceType;
import com.hr.repository.EmploymentAppSourceTypeRepository;

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
 * Integration tests for the {@link EmploymentAppSourceTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmploymentAppSourceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EmploymentAppSourceTypeRepository employmentAppSourceTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentAppSourceTypeMockMvc;

    private EmploymentAppSourceType employmentAppSourceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentAppSourceType createEntity(EntityManager em) {
        EmploymentAppSourceType employmentAppSourceType = new EmploymentAppSourceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return employmentAppSourceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentAppSourceType createUpdatedEntity(EntityManager em) {
        EmploymentAppSourceType employmentAppSourceType = new EmploymentAppSourceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return employmentAppSourceType;
    }

    @BeforeEach
    public void initTest() {
        employmentAppSourceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploymentAppSourceType() throws Exception {
        int databaseSizeBeforeCreate = employmentAppSourceTypeRepository.findAll().size();
        // Create the EmploymentAppSourceType
        restEmploymentAppSourceTypeMockMvc.perform(post("/api/employment-app-source-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentAppSourceType)))
            .andExpect(status().isCreated());

        // Validate the EmploymentAppSourceType in the database
        List<EmploymentAppSourceType> employmentAppSourceTypeList = employmentAppSourceTypeRepository.findAll();
        assertThat(employmentAppSourceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentAppSourceType testEmploymentAppSourceType = employmentAppSourceTypeList.get(employmentAppSourceTypeList.size() - 1);
        assertThat(testEmploymentAppSourceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentAppSourceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEmploymentAppSourceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentAppSourceTypeRepository.findAll().size();

        // Create the EmploymentAppSourceType with an existing ID
        employmentAppSourceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentAppSourceTypeMockMvc.perform(post("/api/employment-app-source-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentAppSourceType)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentAppSourceType in the database
        List<EmploymentAppSourceType> employmentAppSourceTypeList = employmentAppSourceTypeRepository.findAll();
        assertThat(employmentAppSourceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmploymentAppSourceTypes() throws Exception {
        // Initialize the database
        employmentAppSourceTypeRepository.saveAndFlush(employmentAppSourceType);

        // Get all the employmentAppSourceTypeList
        restEmploymentAppSourceTypeMockMvc.perform(get("/api/employment-app-source-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentAppSourceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEmploymentAppSourceType() throws Exception {
        // Initialize the database
        employmentAppSourceTypeRepository.saveAndFlush(employmentAppSourceType);

        // Get the employmentAppSourceType
        restEmploymentAppSourceTypeMockMvc.perform(get("/api/employment-app-source-types/{id}", employmentAppSourceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentAppSourceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingEmploymentAppSourceType() throws Exception {
        // Get the employmentAppSourceType
        restEmploymentAppSourceTypeMockMvc.perform(get("/api/employment-app-source-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploymentAppSourceType() throws Exception {
        // Initialize the database
        employmentAppSourceTypeRepository.saveAndFlush(employmentAppSourceType);

        int databaseSizeBeforeUpdate = employmentAppSourceTypeRepository.findAll().size();

        // Update the employmentAppSourceType
        EmploymentAppSourceType updatedEmploymentAppSourceType = employmentAppSourceTypeRepository.findById(employmentAppSourceType.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentAppSourceType are not directly saved in db
        em.detach(updatedEmploymentAppSourceType);
        updatedEmploymentAppSourceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEmploymentAppSourceTypeMockMvc.perform(put("/api/employment-app-source-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentAppSourceType)))
            .andExpect(status().isOk());

        // Validate the EmploymentAppSourceType in the database
        List<EmploymentAppSourceType> employmentAppSourceTypeList = employmentAppSourceTypeRepository.findAll();
        assertThat(employmentAppSourceTypeList).hasSize(databaseSizeBeforeUpdate);
        EmploymentAppSourceType testEmploymentAppSourceType = employmentAppSourceTypeList.get(employmentAppSourceTypeList.size() - 1);
        assertThat(testEmploymentAppSourceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentAppSourceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploymentAppSourceType() throws Exception {
        int databaseSizeBeforeUpdate = employmentAppSourceTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentAppSourceTypeMockMvc.perform(put("/api/employment-app-source-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentAppSourceType)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentAppSourceType in the database
        List<EmploymentAppSourceType> employmentAppSourceTypeList = employmentAppSourceTypeRepository.findAll();
        assertThat(employmentAppSourceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploymentAppSourceType() throws Exception {
        // Initialize the database
        employmentAppSourceTypeRepository.saveAndFlush(employmentAppSourceType);

        int databaseSizeBeforeDelete = employmentAppSourceTypeRepository.findAll().size();

        // Delete the employmentAppSourceType
        restEmploymentAppSourceTypeMockMvc.perform(delete("/api/employment-app-source-types/{id}", employmentAppSourceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentAppSourceType> employmentAppSourceTypeList = employmentAppSourceTypeRepository.findAll();
        assertThat(employmentAppSourceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
