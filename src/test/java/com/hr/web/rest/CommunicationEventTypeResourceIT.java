package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.CommunicationEventType;
import com.hr.repository.CommunicationEventTypeRepository;

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
 * Integration tests for the {@link CommunicationEventTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommunicationEventTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CommunicationEventTypeRepository communicationEventTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunicationEventTypeMockMvc;

    private CommunicationEventType communicationEventType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationEventType createEntity(EntityManager em) {
        CommunicationEventType communicationEventType = new CommunicationEventType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return communicationEventType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationEventType createUpdatedEntity(EntityManager em) {
        CommunicationEventType communicationEventType = new CommunicationEventType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return communicationEventType;
    }

    @BeforeEach
    public void initTest() {
        communicationEventType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunicationEventType() throws Exception {
        int databaseSizeBeforeCreate = communicationEventTypeRepository.findAll().size();
        // Create the CommunicationEventType
        restCommunicationEventTypeMockMvc.perform(post("/api/communication-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEventType)))
            .andExpect(status().isCreated());

        // Validate the CommunicationEventType in the database
        List<CommunicationEventType> communicationEventTypeList = communicationEventTypeRepository.findAll();
        assertThat(communicationEventTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CommunicationEventType testCommunicationEventType = communicationEventTypeList.get(communicationEventTypeList.size() - 1);
        assertThat(testCommunicationEventType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommunicationEventType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommunicationEventTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communicationEventTypeRepository.findAll().size();

        // Create the CommunicationEventType with an existing ID
        communicationEventType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationEventTypeMockMvc.perform(post("/api/communication-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEventType)))
            .andExpect(status().isBadRequest());

        // Validate the CommunicationEventType in the database
        List<CommunicationEventType> communicationEventTypeList = communicationEventTypeRepository.findAll();
        assertThat(communicationEventTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommunicationEventTypes() throws Exception {
        // Initialize the database
        communicationEventTypeRepository.saveAndFlush(communicationEventType);

        // Get all the communicationEventTypeList
        restCommunicationEventTypeMockMvc.perform(get("/api/communication-event-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communicationEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCommunicationEventType() throws Exception {
        // Initialize the database
        communicationEventTypeRepository.saveAndFlush(communicationEventType);

        // Get the communicationEventType
        restCommunicationEventTypeMockMvc.perform(get("/api/communication-event-types/{id}", communicationEventType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communicationEventType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCommunicationEventType() throws Exception {
        // Get the communicationEventType
        restCommunicationEventTypeMockMvc.perform(get("/api/communication-event-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunicationEventType() throws Exception {
        // Initialize the database
        communicationEventTypeRepository.saveAndFlush(communicationEventType);

        int databaseSizeBeforeUpdate = communicationEventTypeRepository.findAll().size();

        // Update the communicationEventType
        CommunicationEventType updatedCommunicationEventType = communicationEventTypeRepository.findById(communicationEventType.getId()).get();
        // Disconnect from session so that the updates on updatedCommunicationEventType are not directly saved in db
        em.detach(updatedCommunicationEventType);
        updatedCommunicationEventType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCommunicationEventTypeMockMvc.perform(put("/api/communication-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunicationEventType)))
            .andExpect(status().isOk());

        // Validate the CommunicationEventType in the database
        List<CommunicationEventType> communicationEventTypeList = communicationEventTypeRepository.findAll();
        assertThat(communicationEventTypeList).hasSize(databaseSizeBeforeUpdate);
        CommunicationEventType testCommunicationEventType = communicationEventTypeList.get(communicationEventTypeList.size() - 1);
        assertThat(testCommunicationEventType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommunicationEventType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunicationEventType() throws Exception {
        int databaseSizeBeforeUpdate = communicationEventTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationEventTypeMockMvc.perform(put("/api/communication-event-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEventType)))
            .andExpect(status().isBadRequest());

        // Validate the CommunicationEventType in the database
        List<CommunicationEventType> communicationEventTypeList = communicationEventTypeRepository.findAll();
        assertThat(communicationEventTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommunicationEventType() throws Exception {
        // Initialize the database
        communicationEventTypeRepository.saveAndFlush(communicationEventType);

        int databaseSizeBeforeDelete = communicationEventTypeRepository.findAll().size();

        // Delete the communicationEventType
        restCommunicationEventTypeMockMvc.perform(delete("/api/communication-event-types/{id}", communicationEventType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommunicationEventType> communicationEventTypeList = communicationEventTypeRepository.findAll();
        assertThat(communicationEventTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
