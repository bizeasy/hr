package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.FacilityEquipment;
import com.hr.repository.FacilityEquipmentRepository;

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
 * Integration tests for the {@link FacilityEquipmentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityEquipmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FacilityEquipmentRepository facilityEquipmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityEquipmentMockMvc;

    private FacilityEquipment facilityEquipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityEquipment createEntity(EntityManager em) {
        FacilityEquipment facilityEquipment = new FacilityEquipment()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return facilityEquipment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityEquipment createUpdatedEntity(EntityManager em) {
        FacilityEquipment facilityEquipment = new FacilityEquipment()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return facilityEquipment;
    }

    @BeforeEach
    public void initTest() {
        facilityEquipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityEquipment() throws Exception {
        int databaseSizeBeforeCreate = facilityEquipmentRepository.findAll().size();
        // Create the FacilityEquipment
        restFacilityEquipmentMockMvc.perform(post("/api/facility-equipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityEquipment)))
            .andExpect(status().isCreated());

        // Validate the FacilityEquipment in the database
        List<FacilityEquipment> facilityEquipmentList = facilityEquipmentRepository.findAll();
        assertThat(facilityEquipmentList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEquipment testFacilityEquipment = facilityEquipmentList.get(facilityEquipmentList.size() - 1);
        assertThat(testFacilityEquipment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacilityEquipment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFacilityEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityEquipmentRepository.findAll().size();

        // Create the FacilityEquipment with an existing ID
        facilityEquipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityEquipmentMockMvc.perform(post("/api/facility-equipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityEquipment)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityEquipment in the database
        List<FacilityEquipment> facilityEquipmentList = facilityEquipmentRepository.findAll();
        assertThat(facilityEquipmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityEquipments() throws Exception {
        // Initialize the database
        facilityEquipmentRepository.saveAndFlush(facilityEquipment);

        // Get all the facilityEquipmentList
        restFacilityEquipmentMockMvc.perform(get("/api/facility-equipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEquipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFacilityEquipment() throws Exception {
        // Initialize the database
        facilityEquipmentRepository.saveAndFlush(facilityEquipment);

        // Get the facilityEquipment
        restFacilityEquipmentMockMvc.perform(get("/api/facility-equipments/{id}", facilityEquipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityEquipment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityEquipment() throws Exception {
        // Get the facilityEquipment
        restFacilityEquipmentMockMvc.perform(get("/api/facility-equipments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEquipment() throws Exception {
        // Initialize the database
        facilityEquipmentRepository.saveAndFlush(facilityEquipment);

        int databaseSizeBeforeUpdate = facilityEquipmentRepository.findAll().size();

        // Update the facilityEquipment
        FacilityEquipment updatedFacilityEquipment = facilityEquipmentRepository.findById(facilityEquipment.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityEquipment are not directly saved in db
        em.detach(updatedFacilityEquipment);
        updatedFacilityEquipment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFacilityEquipmentMockMvc.perform(put("/api/facility-equipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEquipment)))
            .andExpect(status().isOk());

        // Validate the FacilityEquipment in the database
        List<FacilityEquipment> facilityEquipmentList = facilityEquipmentRepository.findAll();
        assertThat(facilityEquipmentList).hasSize(databaseSizeBeforeUpdate);
        FacilityEquipment testFacilityEquipment = facilityEquipmentList.get(facilityEquipmentList.size() - 1);
        assertThat(testFacilityEquipment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacilityEquipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityEquipment() throws Exception {
        int databaseSizeBeforeUpdate = facilityEquipmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityEquipmentMockMvc.perform(put("/api/facility-equipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityEquipment)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityEquipment in the database
        List<FacilityEquipment> facilityEquipmentList = facilityEquipmentRepository.findAll();
        assertThat(facilityEquipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityEquipment() throws Exception {
        // Initialize the database
        facilityEquipmentRepository.saveAndFlush(facilityEquipment);

        int databaseSizeBeforeDelete = facilityEquipmentRepository.findAll().size();

        // Delete the facilityEquipment
        restFacilityEquipmentMockMvc.perform(delete("/api/facility-equipments/{id}", facilityEquipment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityEquipment> facilityEquipmentList = facilityEquipmentRepository.findAll();
        assertThat(facilityEquipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
