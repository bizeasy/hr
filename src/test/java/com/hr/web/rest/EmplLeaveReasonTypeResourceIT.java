package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplLeaveReasonType;
import com.hr.repository.EmplLeaveReasonTypeRepository;

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
 * Integration tests for the {@link EmplLeaveReasonTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplLeaveReasonTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EmplLeaveReasonTypeRepository emplLeaveReasonTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplLeaveReasonTypeMockMvc;

    private EmplLeaveReasonType emplLeaveReasonType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeaveReasonType createEntity(EntityManager em) {
        EmplLeaveReasonType emplLeaveReasonType = new EmplLeaveReasonType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return emplLeaveReasonType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeaveReasonType createUpdatedEntity(EntityManager em) {
        EmplLeaveReasonType emplLeaveReasonType = new EmplLeaveReasonType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return emplLeaveReasonType;
    }

    @BeforeEach
    public void initTest() {
        emplLeaveReasonType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplLeaveReasonType() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveReasonTypeRepository.findAll().size();
        // Create the EmplLeaveReasonType
        restEmplLeaveReasonTypeMockMvc.perform(post("/api/empl-leave-reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveReasonType)))
            .andExpect(status().isCreated());

        // Validate the EmplLeaveReasonType in the database
        List<EmplLeaveReasonType> emplLeaveReasonTypeList = emplLeaveReasonTypeRepository.findAll();
        assertThat(emplLeaveReasonTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmplLeaveReasonType testEmplLeaveReasonType = emplLeaveReasonTypeList.get(emplLeaveReasonTypeList.size() - 1);
        assertThat(testEmplLeaveReasonType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmplLeaveReasonType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEmplLeaveReasonTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveReasonTypeRepository.findAll().size();

        // Create the EmplLeaveReasonType with an existing ID
        emplLeaveReasonType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplLeaveReasonTypeMockMvc.perform(post("/api/empl-leave-reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveReasonType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeaveReasonType in the database
        List<EmplLeaveReasonType> emplLeaveReasonTypeList = emplLeaveReasonTypeRepository.findAll();
        assertThat(emplLeaveReasonTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplLeaveReasonTypes() throws Exception {
        // Initialize the database
        emplLeaveReasonTypeRepository.saveAndFlush(emplLeaveReasonType);

        // Get all the emplLeaveReasonTypeList
        restEmplLeaveReasonTypeMockMvc.perform(get("/api/empl-leave-reason-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplLeaveReasonType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEmplLeaveReasonType() throws Exception {
        // Initialize the database
        emplLeaveReasonTypeRepository.saveAndFlush(emplLeaveReasonType);

        // Get the emplLeaveReasonType
        restEmplLeaveReasonTypeMockMvc.perform(get("/api/empl-leave-reason-types/{id}", emplLeaveReasonType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplLeaveReasonType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingEmplLeaveReasonType() throws Exception {
        // Get the emplLeaveReasonType
        restEmplLeaveReasonTypeMockMvc.perform(get("/api/empl-leave-reason-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplLeaveReasonType() throws Exception {
        // Initialize the database
        emplLeaveReasonTypeRepository.saveAndFlush(emplLeaveReasonType);

        int databaseSizeBeforeUpdate = emplLeaveReasonTypeRepository.findAll().size();

        // Update the emplLeaveReasonType
        EmplLeaveReasonType updatedEmplLeaveReasonType = emplLeaveReasonTypeRepository.findById(emplLeaveReasonType.getId()).get();
        // Disconnect from session so that the updates on updatedEmplLeaveReasonType are not directly saved in db
        em.detach(updatedEmplLeaveReasonType);
        updatedEmplLeaveReasonType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEmplLeaveReasonTypeMockMvc.perform(put("/api/empl-leave-reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplLeaveReasonType)))
            .andExpect(status().isOk());

        // Validate the EmplLeaveReasonType in the database
        List<EmplLeaveReasonType> emplLeaveReasonTypeList = emplLeaveReasonTypeRepository.findAll();
        assertThat(emplLeaveReasonTypeList).hasSize(databaseSizeBeforeUpdate);
        EmplLeaveReasonType testEmplLeaveReasonType = emplLeaveReasonTypeList.get(emplLeaveReasonTypeList.size() - 1);
        assertThat(testEmplLeaveReasonType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmplLeaveReasonType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplLeaveReasonType() throws Exception {
        int databaseSizeBeforeUpdate = emplLeaveReasonTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplLeaveReasonTypeMockMvc.perform(put("/api/empl-leave-reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeaveReasonType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeaveReasonType in the database
        List<EmplLeaveReasonType> emplLeaveReasonTypeList = emplLeaveReasonTypeRepository.findAll();
        assertThat(emplLeaveReasonTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplLeaveReasonType() throws Exception {
        // Initialize the database
        emplLeaveReasonTypeRepository.saveAndFlush(emplLeaveReasonType);

        int databaseSizeBeforeDelete = emplLeaveReasonTypeRepository.findAll().size();

        // Delete the emplLeaveReasonType
        restEmplLeaveReasonTypeMockMvc.perform(delete("/api/empl-leave-reason-types/{id}", emplLeaveReasonType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplLeaveReasonType> emplLeaveReasonTypeList = emplLeaveReasonTypeRepository.findAll();
        assertThat(emplLeaveReasonTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
