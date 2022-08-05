package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplLeaveType;
import com.hr.repository.EmplLeaveTypeRepository;

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
 * Integration tests for the {@link EmplLeaveTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplLeaveTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EmplLeaveTypeRepository emplLeaveTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplLeaveTypeMockMvc;

    private EmplLeaveType emplLeaveType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeaveType createEntity(EntityManager em) {
        EmplLeaveType emplLeaveType = new EmplLeaveType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return emplLeaveType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeaveType createUpdatedEntity(EntityManager em) {
        EmplLeaveType emplLeaveType = new EmplLeaveType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return emplLeaveType;
    }

    @BeforeEach
    public void initTest() {
        emplLeaveType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplLeaveType() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveTypeRepository.findAll().size();
        // Create the EmplLeaveType
        restEmplLeaveTypeMockMvc.perform(post("/api/empl-leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveType)))
            .andExpect(status().isCreated());

        // Validate the EmplLeaveType in the database
        List<EmplLeaveType> emplLeaveTypeList = emplLeaveTypeRepository.findAll();
        assertThat(emplLeaveTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmplLeaveType testEmplLeaveType = emplLeaveTypeList.get(emplLeaveTypeList.size() - 1);
        assertThat(testEmplLeaveType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmplLeaveType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEmplLeaveTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveTypeRepository.findAll().size();

        // Create the EmplLeaveType with an existing ID
        emplLeaveType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplLeaveTypeMockMvc.perform(post("/api/empl-leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeaveType in the database
        List<EmplLeaveType> emplLeaveTypeList = emplLeaveTypeRepository.findAll();
        assertThat(emplLeaveTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplLeaveTypes() throws Exception {
        // Initialize the database
        emplLeaveTypeRepository.saveAndFlush(emplLeaveType);

        // Get all the emplLeaveTypeList
        restEmplLeaveTypeMockMvc.perform(get("/api/empl-leave-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplLeaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEmplLeaveType() throws Exception {
        // Initialize the database
        emplLeaveTypeRepository.saveAndFlush(emplLeaveType);

        // Get the emplLeaveType
        restEmplLeaveTypeMockMvc.perform(get("/api/empl-leave-types/{id}", emplLeaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplLeaveType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingEmplLeaveType() throws Exception {
        // Get the emplLeaveType
        restEmplLeaveTypeMockMvc.perform(get("/api/empl-leave-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplLeaveType() throws Exception {
        // Initialize the database
        emplLeaveTypeRepository.saveAndFlush(emplLeaveType);

        int databaseSizeBeforeUpdate = emplLeaveTypeRepository.findAll().size();

        // Update the emplLeaveType
        EmplLeaveType updatedEmplLeaveType = emplLeaveTypeRepository.findById(emplLeaveType.getId()).get();
        // Disconnect from session so that the updates on updatedEmplLeaveType are not directly saved in db
        em.detach(updatedEmplLeaveType);
        updatedEmplLeaveType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEmplLeaveTypeMockMvc.perform(put("/api/empl-leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplLeaveType)))
            .andExpect(status().isOk());

        // Validate the EmplLeaveType in the database
        List<EmplLeaveType> emplLeaveTypeList = emplLeaveTypeRepository.findAll();
        assertThat(emplLeaveTypeList).hasSize(databaseSizeBeforeUpdate);
        EmplLeaveType testEmplLeaveType = emplLeaveTypeList.get(emplLeaveTypeList.size() - 1);
        assertThat(testEmplLeaveType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmplLeaveType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = emplLeaveTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplLeaveTypeMockMvc.perform(put("/api/empl-leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeaveType in the database
        List<EmplLeaveType> emplLeaveTypeList = emplLeaveTypeRepository.findAll();
        assertThat(emplLeaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplLeaveType() throws Exception {
        // Initialize the database
        emplLeaveTypeRepository.saveAndFlush(emplLeaveType);

        int databaseSizeBeforeDelete = emplLeaveTypeRepository.findAll().size();

        // Delete the emplLeaveType
        restEmplLeaveTypeMockMvc.perform(delete("/api/empl-leave-types/{id}", emplLeaveType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplLeaveType> emplLeaveTypeList = emplLeaveTypeRepository.findAll();
        assertThat(emplLeaveTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
