package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.DeductionType;
import com.hr.repository.DeductionTypeRepository;

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
 * Integration tests for the {@link DeductionTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeductionTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeductionTypeMockMvc;

    private DeductionType deductionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeductionType createEntity(EntityManager em) {
        DeductionType deductionType = new DeductionType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return deductionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeductionType createUpdatedEntity(EntityManager em) {
        DeductionType deductionType = new DeductionType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return deductionType;
    }

    @BeforeEach
    public void initTest() {
        deductionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeductionType() throws Exception {
        int databaseSizeBeforeCreate = deductionTypeRepository.findAll().size();
        // Create the DeductionType
        restDeductionTypeMockMvc.perform(post("/api/deduction-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isCreated());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeductionType testDeductionType = deductionTypeList.get(deductionTypeList.size() - 1);
        assertThat(testDeductionType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeductionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDeductionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deductionTypeRepository.findAll().size();

        // Create the DeductionType with an existing ID
        deductionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeductionTypeMockMvc.perform(post("/api/deduction-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeductionTypes() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        // Get all the deductionTypeList
        restDeductionTypeMockMvc.perform(get("/api/deduction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deductionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        // Get the deductionType
        restDeductionTypeMockMvc.perform(get("/api/deduction-types/{id}", deductionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deductionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingDeductionType() throws Exception {
        // Get the deductionType
        restDeductionTypeMockMvc.perform(get("/api/deduction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        int databaseSizeBeforeUpdate = deductionTypeRepository.findAll().size();

        // Update the deductionType
        DeductionType updatedDeductionType = deductionTypeRepository.findById(deductionType.getId()).get();
        // Disconnect from session so that the updates on updatedDeductionType are not directly saved in db
        em.detach(updatedDeductionType);
        updatedDeductionType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restDeductionTypeMockMvc.perform(put("/api/deduction-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeductionType)))
            .andExpect(status().isOk());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeUpdate);
        DeductionType testDeductionType = deductionTypeList.get(deductionTypeList.size() - 1);
        assertThat(testDeductionType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeductionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDeductionType() throws Exception {
        int databaseSizeBeforeUpdate = deductionTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeductionTypeMockMvc.perform(put("/api/deduction-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        int databaseSizeBeforeDelete = deductionTypeRepository.findAll().size();

        // Delete the deductionType
        restDeductionTypeMockMvc.perform(delete("/api/deduction-types/{id}", deductionType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
