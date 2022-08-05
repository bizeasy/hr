package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ReasonType;
import com.hr.repository.ReasonTypeRepository;

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
 * Integration tests for the {@link ReasonTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReasonTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReasonTypeRepository reasonTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReasonTypeMockMvc;

    private ReasonType reasonType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReasonType createEntity(EntityManager em) {
        ReasonType reasonType = new ReasonType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return reasonType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReasonType createUpdatedEntity(EntityManager em) {
        ReasonType reasonType = new ReasonType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return reasonType;
    }

    @BeforeEach
    public void initTest() {
        reasonType = createEntity(em);
    }

    @Test
    @Transactional
    public void createReasonType() throws Exception {
        int databaseSizeBeforeCreate = reasonTypeRepository.findAll().size();
        // Create the ReasonType
        restReasonTypeMockMvc.perform(post("/api/reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reasonType)))
            .andExpect(status().isCreated());

        // Validate the ReasonType in the database
        List<ReasonType> reasonTypeList = reasonTypeRepository.findAll();
        assertThat(reasonTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ReasonType testReasonType = reasonTypeList.get(reasonTypeList.size() - 1);
        assertThat(testReasonType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReasonType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReasonTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonTypeRepository.findAll().size();

        // Create the ReasonType with an existing ID
        reasonType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonTypeMockMvc.perform(post("/api/reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reasonType)))
            .andExpect(status().isBadRequest());

        // Validate the ReasonType in the database
        List<ReasonType> reasonTypeList = reasonTypeRepository.findAll();
        assertThat(reasonTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReasonTypes() throws Exception {
        // Initialize the database
        reasonTypeRepository.saveAndFlush(reasonType);

        // Get all the reasonTypeList
        restReasonTypeMockMvc.perform(get("/api/reason-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasonType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getReasonType() throws Exception {
        // Initialize the database
        reasonTypeRepository.saveAndFlush(reasonType);

        // Get the reasonType
        restReasonTypeMockMvc.perform(get("/api/reason-types/{id}", reasonType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reasonType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingReasonType() throws Exception {
        // Get the reasonType
        restReasonTypeMockMvc.perform(get("/api/reason-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReasonType() throws Exception {
        // Initialize the database
        reasonTypeRepository.saveAndFlush(reasonType);

        int databaseSizeBeforeUpdate = reasonTypeRepository.findAll().size();

        // Update the reasonType
        ReasonType updatedReasonType = reasonTypeRepository.findById(reasonType.getId()).get();
        // Disconnect from session so that the updates on updatedReasonType are not directly saved in db
        em.detach(updatedReasonType);
        updatedReasonType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restReasonTypeMockMvc.perform(put("/api/reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReasonType)))
            .andExpect(status().isOk());

        // Validate the ReasonType in the database
        List<ReasonType> reasonTypeList = reasonTypeRepository.findAll();
        assertThat(reasonTypeList).hasSize(databaseSizeBeforeUpdate);
        ReasonType testReasonType = reasonTypeList.get(reasonTypeList.size() - 1);
        assertThat(testReasonType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReasonType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReasonType() throws Exception {
        int databaseSizeBeforeUpdate = reasonTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonTypeMockMvc.perform(put("/api/reason-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reasonType)))
            .andExpect(status().isBadRequest());

        // Validate the ReasonType in the database
        List<ReasonType> reasonTypeList = reasonTypeRepository.findAll();
        assertThat(reasonTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReasonType() throws Exception {
        // Initialize the database
        reasonTypeRepository.saveAndFlush(reasonType);

        int databaseSizeBeforeDelete = reasonTypeRepository.findAll().size();

        // Delete the reasonType
        restReasonTypeMockMvc.perform(delete("/api/reason-types/{id}", reasonType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReasonType> reasonTypeList = reasonTypeRepository.findAll();
        assertThat(reasonTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
