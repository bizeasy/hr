package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyRole;
import com.hr.domain.Party;
import com.hr.domain.RoleType;
import com.hr.repository.PartyRoleRepository;
import com.hr.service.PartyRoleService;
import com.hr.service.dto.PartyRoleCriteria;
import com.hr.service.PartyRoleQueryService;

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
 * Integration tests for the {@link PartyRoleResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyRoleResourceIT {

    @Autowired
    private PartyRoleRepository partyRoleRepository;

    @Autowired
    private PartyRoleService partyRoleService;

    @Autowired
    private PartyRoleQueryService partyRoleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRoleMockMvc;

    private PartyRole partyRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole();
        return partyRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createUpdatedEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole();
        return partyRole;
    }

    @BeforeEach
    public void initTest() {
        partyRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyRole() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();
        // Create the PartyRole
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isCreated());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void createPartyRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();

        // Create the PartyRole with an existing ID
        partyRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyRoles() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get all the partyRoleList
        restPartyRoleMockMvc.perform(get("/api/party-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRole.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", partyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRole.getId().intValue()));
    }


    @Test
    @Transactional
    public void getPartyRolesByIdFiltering() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        Long id = partyRole.getId();

        defaultPartyRoleShouldBeFound("id.equals=" + id);
        defaultPartyRoleShouldNotBeFound("id.notEquals=" + id);

        defaultPartyRoleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartyRoleShouldNotBeFound("id.greaterThan=" + id);

        defaultPartyRoleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartyRoleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartyRolesByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        partyRole.setParty(party);
        partyRoleRepository.saveAndFlush(partyRole);
        Long partyId = party.getId();

        // Get all the partyRoleList where party equals to partyId
        defaultPartyRoleShouldBeFound("partyId.equals=" + partyId);

        // Get all the partyRoleList where party equals to partyId + 1
        defaultPartyRoleShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllPartyRolesByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);
        RoleType roleType = RoleTypeResourceIT.createEntity(em);
        em.persist(roleType);
        em.flush();
        partyRole.setRoleType(roleType);
        partyRoleRepository.saveAndFlush(partyRole);
        Long roleTypeId = roleType.getId();

        // Get all the partyRoleList where roleType equals to roleTypeId
        defaultPartyRoleShouldBeFound("roleTypeId.equals=" + roleTypeId);

        // Get all the partyRoleList where roleType equals to roleTypeId + 1
        defaultPartyRoleShouldNotBeFound("roleTypeId.equals=" + (roleTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartyRoleShouldBeFound(String filter) throws Exception {
        restPartyRoleMockMvc.perform(get("/api/party-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRole.getId().intValue())));

        // Check, that the count call also returns 1
        restPartyRoleMockMvc.perform(get("/api/party-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartyRoleShouldNotBeFound(String filter) throws Exception {
        restPartyRoleMockMvc.perform(get("/api/party-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartyRoleMockMvc.perform(get("/api/party-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPartyRole() throws Exception {
        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyRole() throws Exception {
        // Initialize the database
        partyRoleService.save(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole
        PartyRole updatedPartyRole = partyRoleRepository.findById(partyRole.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRole are not directly saved in db
        em.detach(updatedPartyRole);

        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyRole)))
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyRole() throws Exception {
        // Initialize the database
        partyRoleService.save(partyRole);

        int databaseSizeBeforeDelete = partyRoleRepository.findAll().size();

        // Delete the partyRole
        restPartyRoleMockMvc.perform(delete("/api/party-roles/{id}", partyRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
