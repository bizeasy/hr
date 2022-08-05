package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PermissionAuthority;
import com.hr.repository.PermissionAuthorityRepository;
import com.hr.service.PermissionAuthorityService;
import com.hr.service.dto.PermissionAuthorityCriteria;
import com.hr.service.PermissionAuthorityQueryService;

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
 * Integration tests for the {@link PermissionAuthorityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PermissionAuthorityResourceIT {

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    @Autowired
    private PermissionAuthorityRepository permissionAuthorityRepository;

    @Autowired
    private PermissionAuthorityService permissionAuthorityService;

    @Autowired
    private PermissionAuthorityQueryService permissionAuthorityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermissionAuthorityMockMvc;

    private PermissionAuthority permissionAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermissionAuthority createEntity(EntityManager em) {
        PermissionAuthority permissionAuthority = new PermissionAuthority()
            .authority(DEFAULT_AUTHORITY);
        return permissionAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermissionAuthority createUpdatedEntity(EntityManager em) {
        PermissionAuthority permissionAuthority = new PermissionAuthority()
            .authority(UPDATED_AUTHORITY);
        return permissionAuthority;
    }

    @BeforeEach
    public void initTest() {
        permissionAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermissionAuthority() throws Exception {
        int databaseSizeBeforeCreate = permissionAuthorityRepository.findAll().size();
        // Create the PermissionAuthority
        restPermissionAuthorityMockMvc.perform(post("/api/permission-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionAuthority)))
            .andExpect(status().isCreated());

        // Validate the PermissionAuthority in the database
        List<PermissionAuthority> permissionAuthorityList = permissionAuthorityRepository.findAll();
        assertThat(permissionAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        PermissionAuthority testPermissionAuthority = permissionAuthorityList.get(permissionAuthorityList.size() - 1);
        assertThat(testPermissionAuthority.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
    }

    @Test
    @Transactional
    public void createPermissionAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permissionAuthorityRepository.findAll().size();

        // Create the PermissionAuthority with an existing ID
        permissionAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionAuthorityMockMvc.perform(post("/api/permission-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the PermissionAuthority in the database
        List<PermissionAuthority> permissionAuthorityList = permissionAuthorityRepository.findAll();
        assertThat(permissionAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPermissionAuthorities() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)));
    }
    
    @Test
    @Transactional
    public void getPermissionAuthority() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get the permissionAuthority
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities/{id}", permissionAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permissionAuthority.getId().intValue()))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY));
    }


    @Test
    @Transactional
    public void getPermissionAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        Long id = permissionAuthority.getId();

        defaultPermissionAuthorityShouldBeFound("id.equals=" + id);
        defaultPermissionAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultPermissionAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPermissionAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultPermissionAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPermissionAuthorityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityIsEqualToSomething() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority equals to DEFAULT_AUTHORITY
        defaultPermissionAuthorityShouldBeFound("authority.equals=" + DEFAULT_AUTHORITY);

        // Get all the permissionAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldNotBeFound("authority.equals=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority not equals to DEFAULT_AUTHORITY
        defaultPermissionAuthorityShouldNotBeFound("authority.notEquals=" + DEFAULT_AUTHORITY);

        // Get all the permissionAuthorityList where authority not equals to UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldBeFound("authority.notEquals=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityIsInShouldWork() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority in DEFAULT_AUTHORITY or UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldBeFound("authority.in=" + DEFAULT_AUTHORITY + "," + UPDATED_AUTHORITY);

        // Get all the permissionAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldNotBeFound("authority.in=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority is not null
        defaultPermissionAuthorityShouldBeFound("authority.specified=true");

        // Get all the permissionAuthorityList where authority is null
        defaultPermissionAuthorityShouldNotBeFound("authority.specified=false");
    }
                @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityContainsSomething() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority contains DEFAULT_AUTHORITY
        defaultPermissionAuthorityShouldBeFound("authority.contains=" + DEFAULT_AUTHORITY);

        // Get all the permissionAuthorityList where authority contains UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldNotBeFound("authority.contains=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermissionAuthoritiesByAuthorityNotContainsSomething() throws Exception {
        // Initialize the database
        permissionAuthorityRepository.saveAndFlush(permissionAuthority);

        // Get all the permissionAuthorityList where authority does not contain DEFAULT_AUTHORITY
        defaultPermissionAuthorityShouldNotBeFound("authority.doesNotContain=" + DEFAULT_AUTHORITY);

        // Get all the permissionAuthorityList where authority does not contain UPDATED_AUTHORITY
        defaultPermissionAuthorityShouldBeFound("authority.doesNotContain=" + UPDATED_AUTHORITY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermissionAuthorityShouldBeFound(String filter) throws Exception {
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)));

        // Check, that the count call also returns 1
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermissionAuthorityShouldNotBeFound(String filter) throws Exception {
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPermissionAuthority() throws Exception {
        // Get the permissionAuthority
        restPermissionAuthorityMockMvc.perform(get("/api/permission-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermissionAuthority() throws Exception {
        // Initialize the database
        permissionAuthorityService.save(permissionAuthority);

        int databaseSizeBeforeUpdate = permissionAuthorityRepository.findAll().size();

        // Update the permissionAuthority
        PermissionAuthority updatedPermissionAuthority = permissionAuthorityRepository.findById(permissionAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedPermissionAuthority are not directly saved in db
        em.detach(updatedPermissionAuthority);
        updatedPermissionAuthority
            .authority(UPDATED_AUTHORITY);

        restPermissionAuthorityMockMvc.perform(put("/api/permission-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPermissionAuthority)))
            .andExpect(status().isOk());

        // Validate the PermissionAuthority in the database
        List<PermissionAuthority> permissionAuthorityList = permissionAuthorityRepository.findAll();
        assertThat(permissionAuthorityList).hasSize(databaseSizeBeforeUpdate);
        PermissionAuthority testPermissionAuthority = permissionAuthorityList.get(permissionAuthorityList.size() - 1);
        assertThat(testPermissionAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPermissionAuthority() throws Exception {
        int databaseSizeBeforeUpdate = permissionAuthorityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionAuthorityMockMvc.perform(put("/api/permission-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissionAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the PermissionAuthority in the database
        List<PermissionAuthority> permissionAuthorityList = permissionAuthorityRepository.findAll();
        assertThat(permissionAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermissionAuthority() throws Exception {
        // Initialize the database
        permissionAuthorityService.save(permissionAuthority);

        int databaseSizeBeforeDelete = permissionAuthorityRepository.findAll().size();

        // Delete the permissionAuthority
        restPermissionAuthorityMockMvc.perform(delete("/api/permission-authorities/{id}", permissionAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PermissionAuthority> permissionAuthorityList = permissionAuthorityRepository.findAll();
        assertThat(permissionAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
