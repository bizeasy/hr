package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyClassificationType;
import com.hr.repository.PartyClassificationTypeRepository;

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
 * Integration tests for the {@link PartyClassificationTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyClassificationTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyClassificationTypeRepository partyClassificationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyClassificationTypeMockMvc;

    private PartyClassificationType partyClassificationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassificationType createEntity(EntityManager em) {
        PartyClassificationType partyClassificationType = new PartyClassificationType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return partyClassificationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassificationType createUpdatedEntity(EntityManager em) {
        PartyClassificationType partyClassificationType = new PartyClassificationType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return partyClassificationType;
    }

    @BeforeEach
    public void initTest() {
        partyClassificationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyClassificationType() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationTypeRepository.findAll().size();
        // Create the PartyClassificationType
        restPartyClassificationTypeMockMvc.perform(post("/api/party-classification-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationType)))
            .andExpect(status().isCreated());

        // Validate the PartyClassificationType in the database
        List<PartyClassificationType> partyClassificationTypeList = partyClassificationTypeRepository.findAll();
        assertThat(partyClassificationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyClassificationType testPartyClassificationType = partyClassificationTypeList.get(partyClassificationTypeList.size() - 1);
        assertThat(testPartyClassificationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartyClassificationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyClassificationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationTypeRepository.findAll().size();

        // Create the PartyClassificationType with an existing ID
        partyClassificationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyClassificationTypeMockMvc.perform(post("/api/party-classification-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassificationType in the database
        List<PartyClassificationType> partyClassificationTypeList = partyClassificationTypeRepository.findAll();
        assertThat(partyClassificationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyClassificationTypes() throws Exception {
        // Initialize the database
        partyClassificationTypeRepository.saveAndFlush(partyClassificationType);

        // Get all the partyClassificationTypeList
        restPartyClassificationTypeMockMvc.perform(get("/api/party-classification-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassificationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPartyClassificationType() throws Exception {
        // Initialize the database
        partyClassificationTypeRepository.saveAndFlush(partyClassificationType);

        // Get the partyClassificationType
        restPartyClassificationTypeMockMvc.perform(get("/api/party-classification-types/{id}", partyClassificationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyClassificationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPartyClassificationType() throws Exception {
        // Get the partyClassificationType
        restPartyClassificationTypeMockMvc.perform(get("/api/party-classification-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyClassificationType() throws Exception {
        // Initialize the database
        partyClassificationTypeRepository.saveAndFlush(partyClassificationType);

        int databaseSizeBeforeUpdate = partyClassificationTypeRepository.findAll().size();

        // Update the partyClassificationType
        PartyClassificationType updatedPartyClassificationType = partyClassificationTypeRepository.findById(partyClassificationType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyClassificationType are not directly saved in db
        em.detach(updatedPartyClassificationType);
        updatedPartyClassificationType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPartyClassificationTypeMockMvc.perform(put("/api/party-classification-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyClassificationType)))
            .andExpect(status().isOk());

        // Validate the PartyClassificationType in the database
        List<PartyClassificationType> partyClassificationTypeList = partyClassificationTypeRepository.findAll();
        assertThat(partyClassificationTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyClassificationType testPartyClassificationType = partyClassificationTypeList.get(partyClassificationTypeList.size() - 1);
        assertThat(testPartyClassificationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartyClassificationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyClassificationType() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationTypeMockMvc.perform(put("/api/party-classification-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassificationType in the database
        List<PartyClassificationType> partyClassificationTypeList = partyClassificationTypeRepository.findAll();
        assertThat(partyClassificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyClassificationType() throws Exception {
        // Initialize the database
        partyClassificationTypeRepository.saveAndFlush(partyClassificationType);

        int databaseSizeBeforeDelete = partyClassificationTypeRepository.findAll().size();

        // Delete the partyClassificationType
        restPartyClassificationTypeMockMvc.perform(delete("/api/party-classification-types/{id}", partyClassificationType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyClassificationType> partyClassificationTypeList = partyClassificationTypeRepository.findAll();
        assertThat(partyClassificationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
