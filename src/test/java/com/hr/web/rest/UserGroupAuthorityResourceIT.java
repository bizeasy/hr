package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.UserGroupAuthority;
import com.hr.domain.UserGroup;
import com.hr.repository.UserGroupAuthorityRepository;
import com.hr.service.UserGroupAuthorityService;
import com.hr.service.dto.UserGroupAuthorityCriteria;
import com.hr.service.UserGroupAuthorityQueryService;

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
 * Integration tests for the {@link UserGroupAuthorityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGroupAuthorityResourceIT {

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    @Autowired
    private UserGroupAuthorityRepository userGroupAuthorityRepository;

    @Autowired
    private UserGroupAuthorityService userGroupAuthorityService;

    @Autowired
    private UserGroupAuthorityQueryService userGroupAuthorityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGroupAuthorityMockMvc;

    private UserGroupAuthority userGroupAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupAuthority createEntity(EntityManager em) {
        UserGroupAuthority userGroupAuthority = new UserGroupAuthority()
            .authority(DEFAULT_AUTHORITY);
        // Add required entity
        UserGroup userGroup;
        if (TestUtil.findAll(em, UserGroup.class).isEmpty()) {
            userGroup = UserGroupResourceIT.createEntity(em);
            em.persist(userGroup);
            em.flush();
        } else {
            userGroup = TestUtil.findAll(em, UserGroup.class).get(0);
        }
        userGroupAuthority.setUserGroup(userGroup);
        return userGroupAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupAuthority createUpdatedEntity(EntityManager em) {
        UserGroupAuthority userGroupAuthority = new UserGroupAuthority()
            .authority(UPDATED_AUTHORITY);
        // Add required entity
        UserGroup userGroup;
        if (TestUtil.findAll(em, UserGroup.class).isEmpty()) {
            userGroup = UserGroupResourceIT.createUpdatedEntity(em);
            em.persist(userGroup);
            em.flush();
        } else {
            userGroup = TestUtil.findAll(em, UserGroup.class).get(0);
        }
        userGroupAuthority.setUserGroup(userGroup);
        return userGroupAuthority;
    }

    @BeforeEach
    public void initTest() {
        userGroupAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroupAuthority() throws Exception {
        int databaseSizeBeforeCreate = userGroupAuthorityRepository.findAll().size();
        // Create the UserGroupAuthority
        restUserGroupAuthorityMockMvc.perform(post("/api/user-group-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupAuthority)))
            .andExpect(status().isCreated());

        // Validate the UserGroupAuthority in the database
        List<UserGroupAuthority> userGroupAuthorityList = userGroupAuthorityRepository.findAll();
        assertThat(userGroupAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroupAuthority testUserGroupAuthority = userGroupAuthorityList.get(userGroupAuthorityList.size() - 1);
        assertThat(testUserGroupAuthority.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
    }

    @Test
    @Transactional
    public void createUserGroupAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupAuthorityRepository.findAll().size();

        // Create the UserGroupAuthority with an existing ID
        userGroupAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupAuthorityMockMvc.perform(post("/api/user-group-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupAuthority in the database
        List<UserGroupAuthority> userGroupAuthorityList = userGroupAuthorityRepository.findAll();
        assertThat(userGroupAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGroupAuthorities() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)));
    }
    
    @Test
    @Transactional
    public void getUserGroupAuthority() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get the userGroupAuthority
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities/{id}", userGroupAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroupAuthority.getId().intValue()))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY));
    }


    @Test
    @Transactional
    public void getUserGroupAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        Long id = userGroupAuthority.getId();

        defaultUserGroupAuthorityShouldBeFound("id.equals=" + id);
        defaultUserGroupAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultUserGroupAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserGroupAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultUserGroupAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserGroupAuthorityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority equals to DEFAULT_AUTHORITY
        defaultUserGroupAuthorityShouldBeFound("authority.equals=" + DEFAULT_AUTHORITY);

        // Get all the userGroupAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldNotBeFound("authority.equals=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority not equals to DEFAULT_AUTHORITY
        defaultUserGroupAuthorityShouldNotBeFound("authority.notEquals=" + DEFAULT_AUTHORITY);

        // Get all the userGroupAuthorityList where authority not equals to UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldBeFound("authority.notEquals=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority in DEFAULT_AUTHORITY or UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldBeFound("authority.in=" + DEFAULT_AUTHORITY + "," + UPDATED_AUTHORITY);

        // Get all the userGroupAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldNotBeFound("authority.in=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority is not null
        defaultUserGroupAuthorityShouldBeFound("authority.specified=true");

        // Get all the userGroupAuthorityList where authority is null
        defaultUserGroupAuthorityShouldNotBeFound("authority.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityContainsSomething() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority contains DEFAULT_AUTHORITY
        defaultUserGroupAuthorityShouldBeFound("authority.contains=" + DEFAULT_AUTHORITY);

        // Get all the userGroupAuthorityList where authority contains UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldNotBeFound("authority.contains=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByAuthorityNotContainsSomething() throws Exception {
        // Initialize the database
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);

        // Get all the userGroupAuthorityList where authority does not contain DEFAULT_AUTHORITY
        defaultUserGroupAuthorityShouldNotBeFound("authority.doesNotContain=" + DEFAULT_AUTHORITY);

        // Get all the userGroupAuthorityList where authority does not contain UPDATED_AUTHORITY
        defaultUserGroupAuthorityShouldBeFound("authority.doesNotContain=" + UPDATED_AUTHORITY);
    }


    @Test
    @Transactional
    public void getAllUserGroupAuthoritiesByUserGroupIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserGroup userGroup = userGroupAuthority.getUserGroup();
        userGroupAuthorityRepository.saveAndFlush(userGroupAuthority);
        Long userGroupId = userGroup.getId();

        // Get all the userGroupAuthorityList where userGroup equals to userGroupId
        defaultUserGroupAuthorityShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the userGroupAuthorityList where userGroup equals to userGroupId + 1
        defaultUserGroupAuthorityShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGroupAuthorityShouldBeFound(String filter) throws Exception {
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)));

        // Check, that the count call also returns 1
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserGroupAuthorityShouldNotBeFound(String filter) throws Exception {
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupAuthority() throws Exception {
        // Get the userGroupAuthority
        restUserGroupAuthorityMockMvc.perform(get("/api/user-group-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupAuthority() throws Exception {
        // Initialize the database
        userGroupAuthorityService.save(userGroupAuthority);

        int databaseSizeBeforeUpdate = userGroupAuthorityRepository.findAll().size();

        // Update the userGroupAuthority
        UserGroupAuthority updatedUserGroupAuthority = userGroupAuthorityRepository.findById(userGroupAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroupAuthority are not directly saved in db
        em.detach(updatedUserGroupAuthority);
        updatedUserGroupAuthority
            .authority(UPDATED_AUTHORITY);

        restUserGroupAuthorityMockMvc.perform(put("/api/user-group-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserGroupAuthority)))
            .andExpect(status().isOk());

        // Validate the UserGroupAuthority in the database
        List<UserGroupAuthority> userGroupAuthorityList = userGroupAuthorityRepository.findAll();
        assertThat(userGroupAuthorityList).hasSize(databaseSizeBeforeUpdate);
        UserGroupAuthority testUserGroupAuthority = userGroupAuthorityList.get(userGroupAuthorityList.size() - 1);
        assertThat(testUserGroupAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroupAuthority() throws Exception {
        int databaseSizeBeforeUpdate = userGroupAuthorityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupAuthorityMockMvc.perform(put("/api/user-group-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupAuthority in the database
        List<UserGroupAuthority> userGroupAuthorityList = userGroupAuthorityRepository.findAll();
        assertThat(userGroupAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroupAuthority() throws Exception {
        // Initialize the database
        userGroupAuthorityService.save(userGroupAuthority);

        int databaseSizeBeforeDelete = userGroupAuthorityRepository.findAll().size();

        // Delete the userGroupAuthority
        restUserGroupAuthorityMockMvc.perform(delete("/api/user-group-authorities/{id}", userGroupAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroupAuthority> userGroupAuthorityList = userGroupAuthorityRepository.findAll();
        assertThat(userGroupAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
