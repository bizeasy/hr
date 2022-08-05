package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TerminationType;
import com.hr.repository.TerminationTypeRepository;

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
 * Integration tests for the {@link TerminationTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TerminationTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TerminationTypeRepository terminationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerminationTypeMockMvc;

    private TerminationType terminationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminationType createEntity(EntityManager em) {
        TerminationType terminationType = new TerminationType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return terminationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminationType createUpdatedEntity(EntityManager em) {
        TerminationType terminationType = new TerminationType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return terminationType;
    }

    @BeforeEach
    public void initTest() {
        terminationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerminationType() throws Exception {
        int databaseSizeBeforeCreate = terminationTypeRepository.findAll().size();
        // Create the TerminationType
        restTerminationTypeMockMvc.perform(post("/api/termination-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminationType)))
            .andExpect(status().isCreated());

        // Validate the TerminationType in the database
        List<TerminationType> terminationTypeList = terminationTypeRepository.findAll();
        assertThat(terminationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TerminationType testTerminationType = terminationTypeList.get(terminationTypeList.size() - 1);
        assertThat(testTerminationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTerminationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTerminationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terminationTypeRepository.findAll().size();

        // Create the TerminationType with an existing ID
        terminationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminationTypeMockMvc.perform(post("/api/termination-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminationType)))
            .andExpect(status().isBadRequest());

        // Validate the TerminationType in the database
        List<TerminationType> terminationTypeList = terminationTypeRepository.findAll();
        assertThat(terminationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTerminationTypes() throws Exception {
        // Initialize the database
        terminationTypeRepository.saveAndFlush(terminationType);

        // Get all the terminationTypeList
        restTerminationTypeMockMvc.perform(get("/api/termination-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTerminationType() throws Exception {
        // Initialize the database
        terminationTypeRepository.saveAndFlush(terminationType);

        // Get the terminationType
        restTerminationTypeMockMvc.perform(get("/api/termination-types/{id}", terminationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terminationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTerminationType() throws Exception {
        // Get the terminationType
        restTerminationTypeMockMvc.perform(get("/api/termination-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerminationType() throws Exception {
        // Initialize the database
        terminationTypeRepository.saveAndFlush(terminationType);

        int databaseSizeBeforeUpdate = terminationTypeRepository.findAll().size();

        // Update the terminationType
        TerminationType updatedTerminationType = terminationTypeRepository.findById(terminationType.getId()).get();
        // Disconnect from session so that the updates on updatedTerminationType are not directly saved in db
        em.detach(updatedTerminationType);
        updatedTerminationType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTerminationTypeMockMvc.perform(put("/api/termination-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerminationType)))
            .andExpect(status().isOk());

        // Validate the TerminationType in the database
        List<TerminationType> terminationTypeList = terminationTypeRepository.findAll();
        assertThat(terminationTypeList).hasSize(databaseSizeBeforeUpdate);
        TerminationType testTerminationType = terminationTypeList.get(terminationTypeList.size() - 1);
        assertThat(testTerminationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerminationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTerminationType() throws Exception {
        int databaseSizeBeforeUpdate = terminationTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminationTypeMockMvc.perform(put("/api/termination-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terminationType)))
            .andExpect(status().isBadRequest());

        // Validate the TerminationType in the database
        List<TerminationType> terminationTypeList = terminationTypeRepository.findAll();
        assertThat(terminationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerminationType() throws Exception {
        // Initialize the database
        terminationTypeRepository.saveAndFlush(terminationType);

        int databaseSizeBeforeDelete = terminationTypeRepository.findAll().size();

        // Delete the terminationType
        restTerminationTypeMockMvc.perform(delete("/api/termination-types/{id}", terminationType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerminationType> terminationTypeList = terminationTypeRepository.findAll();
        assertThat(terminationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
