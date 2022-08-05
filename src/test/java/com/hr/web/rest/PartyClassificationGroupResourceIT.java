package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyClassificationGroup;
import com.hr.repository.PartyClassificationGroupRepository;

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
 * Integration tests for the {@link PartyClassificationGroupResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyClassificationGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PartyClassificationGroupRepository partyClassificationGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyClassificationGroupMockMvc;

    private PartyClassificationGroup partyClassificationGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassificationGroup createEntity(EntityManager em) {
        PartyClassificationGroup partyClassificationGroup = new PartyClassificationGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return partyClassificationGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassificationGroup createUpdatedEntity(EntityManager em) {
        PartyClassificationGroup partyClassificationGroup = new PartyClassificationGroup()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return partyClassificationGroup;
    }

    @BeforeEach
    public void initTest() {
        partyClassificationGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyClassificationGroup() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationGroupRepository.findAll().size();
        // Create the PartyClassificationGroup
        restPartyClassificationGroupMockMvc.perform(post("/api/party-classification-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationGroup)))
            .andExpect(status().isCreated());

        // Validate the PartyClassificationGroup in the database
        List<PartyClassificationGroup> partyClassificationGroupList = partyClassificationGroupRepository.findAll();
        assertThat(partyClassificationGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PartyClassificationGroup testPartyClassificationGroup = partyClassificationGroupList.get(partyClassificationGroupList.size() - 1);
        assertThat(testPartyClassificationGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartyClassificationGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartyClassificationGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationGroupRepository.findAll().size();

        // Create the PartyClassificationGroup with an existing ID
        partyClassificationGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyClassificationGroupMockMvc.perform(post("/api/party-classification-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassificationGroup in the database
        List<PartyClassificationGroup> partyClassificationGroupList = partyClassificationGroupRepository.findAll();
        assertThat(partyClassificationGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyClassificationGroups() throws Exception {
        // Initialize the database
        partyClassificationGroupRepository.saveAndFlush(partyClassificationGroup);

        // Get all the partyClassificationGroupList
        restPartyClassificationGroupMockMvc.perform(get("/api/party-classification-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassificationGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPartyClassificationGroup() throws Exception {
        // Initialize the database
        partyClassificationGroupRepository.saveAndFlush(partyClassificationGroup);

        // Get the partyClassificationGroup
        restPartyClassificationGroupMockMvc.perform(get("/api/party-classification-groups/{id}", partyClassificationGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyClassificationGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPartyClassificationGroup() throws Exception {
        // Get the partyClassificationGroup
        restPartyClassificationGroupMockMvc.perform(get("/api/party-classification-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyClassificationGroup() throws Exception {
        // Initialize the database
        partyClassificationGroupRepository.saveAndFlush(partyClassificationGroup);

        int databaseSizeBeforeUpdate = partyClassificationGroupRepository.findAll().size();

        // Update the partyClassificationGroup
        PartyClassificationGroup updatedPartyClassificationGroup = partyClassificationGroupRepository.findById(partyClassificationGroup.getId()).get();
        // Disconnect from session so that the updates on updatedPartyClassificationGroup are not directly saved in db
        em.detach(updatedPartyClassificationGroup);
        updatedPartyClassificationGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPartyClassificationGroupMockMvc.perform(put("/api/party-classification-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyClassificationGroup)))
            .andExpect(status().isOk());

        // Validate the PartyClassificationGroup in the database
        List<PartyClassificationGroup> partyClassificationGroupList = partyClassificationGroupRepository.findAll();
        assertThat(partyClassificationGroupList).hasSize(databaseSizeBeforeUpdate);
        PartyClassificationGroup testPartyClassificationGroup = partyClassificationGroupList.get(partyClassificationGroupList.size() - 1);
        assertThat(testPartyClassificationGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartyClassificationGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyClassificationGroup() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationGroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationGroupMockMvc.perform(put("/api/party-classification-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassificationGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassificationGroup in the database
        List<PartyClassificationGroup> partyClassificationGroupList = partyClassificationGroupRepository.findAll();
        assertThat(partyClassificationGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyClassificationGroup() throws Exception {
        // Initialize the database
        partyClassificationGroupRepository.saveAndFlush(partyClassificationGroup);

        int databaseSizeBeforeDelete = partyClassificationGroupRepository.findAll().size();

        // Delete the partyClassificationGroup
        restPartyClassificationGroupMockMvc.perform(delete("/api/party-classification-groups/{id}", partyClassificationGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyClassificationGroup> partyClassificationGroupList = partyClassificationGroupRepository.findAll();
        assertThat(partyClassificationGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
