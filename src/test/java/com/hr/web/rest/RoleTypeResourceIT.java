package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.RoleType;
import com.hr.repository.RoleTypeRepository;

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
 * Integration tests for the {@link RoleTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RoleTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RoleTypeRepository roleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleTypeMockMvc;

    private RoleType roleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleType createEntity(EntityManager em) {
        RoleType roleType = new RoleType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return roleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleType createUpdatedEntity(EntityManager em) {
        RoleType roleType = new RoleType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return roleType;
    }

    @BeforeEach
    public void initTest() {
        roleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleType() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();
        // Create the RoleType
        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isCreated());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRoleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();

        // Create the RoleType with an existing ID
        roleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRoleTypes() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get all the roleTypeList
        restRoleTypeMockMvc.perform(get("/api/role-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get the roleType
        restRoleTypeMockMvc.perform(get("/api/role-types/{id}", roleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingRoleType() throws Exception {
        // Get the roleType
        restRoleTypeMockMvc.perform(get("/api/role-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Update the roleType
        RoleType updatedRoleType = roleTypeRepository.findById(roleType.getId()).get();
        // Disconnect from session so that the updates on updatedRoleType are not directly saved in db
        em.detach(updatedRoleType);
        updatedRoleType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRoleTypeMockMvc.perform(put("/api/role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleType)))
            .andExpect(status().isOk());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc.perform(put("/api/role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        int databaseSizeBeforeDelete = roleTypeRepository.findAll().size();

        // Delete the roleType
        restRoleTypeMockMvc.perform(delete("/api/role-types/{id}", roleType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
