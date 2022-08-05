package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPositionType;
import com.hr.repository.EmplPositionTypeRepository;

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
 * Integration tests for the {@link EmplPositionTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EmplPositionTypeRepository emplPositionTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionTypeMockMvc;

    private EmplPositionType emplPositionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionType createEntity(EntityManager em) {
        EmplPositionType emplPositionType = new EmplPositionType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return emplPositionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionType createUpdatedEntity(EntityManager em) {
        EmplPositionType emplPositionType = new EmplPositionType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return emplPositionType;
    }

    @BeforeEach
    public void initTest() {
        emplPositionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPositionType() throws Exception {
        int databaseSizeBeforeCreate = emplPositionTypeRepository.findAll().size();
        // Create the EmplPositionType
        restEmplPositionTypeMockMvc.perform(post("/api/empl-position-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionType)))
            .andExpect(status().isCreated());

        // Validate the EmplPositionType in the database
        List<EmplPositionType> emplPositionTypeList = emplPositionTypeRepository.findAll();
        assertThat(emplPositionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPositionType testEmplPositionType = emplPositionTypeList.get(emplPositionTypeList.size() - 1);
        assertThat(testEmplPositionType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmplPositionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEmplPositionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionTypeRepository.findAll().size();

        // Create the EmplPositionType with an existing ID
        emplPositionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionTypeMockMvc.perform(post("/api/empl-position-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionType in the database
        List<EmplPositionType> emplPositionTypeList = emplPositionTypeRepository.findAll();
        assertThat(emplPositionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositionTypes() throws Exception {
        // Initialize the database
        emplPositionTypeRepository.saveAndFlush(emplPositionType);

        // Get all the emplPositionTypeList
        restEmplPositionTypeMockMvc.perform(get("/api/empl-position-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPositionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEmplPositionType() throws Exception {
        // Initialize the database
        emplPositionTypeRepository.saveAndFlush(emplPositionType);

        // Get the emplPositionType
        restEmplPositionTypeMockMvc.perform(get("/api/empl-position-types/{id}", emplPositionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPositionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPositionType() throws Exception {
        // Get the emplPositionType
        restEmplPositionTypeMockMvc.perform(get("/api/empl-position-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPositionType() throws Exception {
        // Initialize the database
        emplPositionTypeRepository.saveAndFlush(emplPositionType);

        int databaseSizeBeforeUpdate = emplPositionTypeRepository.findAll().size();

        // Update the emplPositionType
        EmplPositionType updatedEmplPositionType = emplPositionTypeRepository.findById(emplPositionType.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPositionType are not directly saved in db
        em.detach(updatedEmplPositionType);
        updatedEmplPositionType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEmplPositionTypeMockMvc.perform(put("/api/empl-position-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPositionType)))
            .andExpect(status().isOk());

        // Validate the EmplPositionType in the database
        List<EmplPositionType> emplPositionTypeList = emplPositionTypeRepository.findAll();
        assertThat(emplPositionTypeList).hasSize(databaseSizeBeforeUpdate);
        EmplPositionType testEmplPositionType = emplPositionTypeList.get(emplPositionTypeList.size() - 1);
        assertThat(testEmplPositionType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmplPositionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPositionType() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionTypeMockMvc.perform(put("/api/empl-position-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionType)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionType in the database
        List<EmplPositionType> emplPositionTypeList = emplPositionTypeRepository.findAll();
        assertThat(emplPositionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPositionType() throws Exception {
        // Initialize the database
        emplPositionTypeRepository.saveAndFlush(emplPositionType);

        int databaseSizeBeforeDelete = emplPositionTypeRepository.findAll().size();

        // Delete the emplPositionType
        restEmplPositionTypeMockMvc.perform(delete("/api/empl-position-types/{id}", emplPositionType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPositionType> emplPositionTypeList = emplPositionTypeRepository.findAll();
        assertThat(emplPositionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
