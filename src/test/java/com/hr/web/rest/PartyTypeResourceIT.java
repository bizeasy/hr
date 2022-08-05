package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyType;
import com.hr.repository.PartyTypeRepository;

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
 * Integration tests for the {@link PartyTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyTypeRepository partyTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyTypeMockMvc;

    private PartyType partyType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return partyType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createUpdatedEntity(EntityManager em) {
        PartyType partyType = new PartyType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return partyType;
    }

    @BeforeEach
    public void initTest() {
        partyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyType() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();
        // Create the PartyType
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isCreated());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // Create the PartyType with an existing ID
        partyType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyTypes() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get all the partyTypeList
        restPartyTypeMockMvc.perform(get("/api/party-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", partyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPartyType() throws Exception {
        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType
        PartyType updatedPartyType = partyTypeRepository.findById(partyType.getId()).get();
        // Disconnect from session so that the updates on updatedPartyType are not directly saved in db
        em.detach(updatedPartyType);
        updatedPartyType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyType)))
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
        assertThat(testPartyType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        int databaseSizeBeforeDelete = partyTypeRepository.findAll().size();

        // Delete the partyType
        restPartyTypeMockMvc.perform(delete("/api/party-types/{id}", partyType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
