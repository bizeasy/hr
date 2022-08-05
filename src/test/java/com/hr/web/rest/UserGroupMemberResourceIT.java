package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.UserGroupMember;
import com.hr.domain.UserGroup;
import com.hr.domain.User;
import com.hr.repository.UserGroupMemberRepository;
import com.hr.service.UserGroupMemberService;
import com.hr.service.dto.UserGroupMemberCriteria;
import com.hr.service.UserGroupMemberQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserGroupMemberResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGroupMemberResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserGroupMemberRepository userGroupMemberRepository;

    @Autowired
    private UserGroupMemberService userGroupMemberService;

    @Autowired
    private UserGroupMemberQueryService userGroupMemberQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGroupMemberMockMvc;

    private UserGroupMember userGroupMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupMember createEntity(EntityManager em) {
        UserGroupMember userGroupMember = new UserGroupMember()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        // Add required entity
        UserGroup userGroup;
        if (TestUtil.findAll(em, UserGroup.class).isEmpty()) {
            userGroup = UserGroupResourceIT.createEntity(em);
            em.persist(userGroup);
            em.flush();
        } else {
            userGroup = TestUtil.findAll(em, UserGroup.class).get(0);
        }
        userGroupMember.setUserGroup(userGroup);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userGroupMember.setUser(user);
        return userGroupMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupMember createUpdatedEntity(EntityManager em) {
        UserGroupMember userGroupMember = new UserGroupMember()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        // Add required entity
        UserGroup userGroup;
        if (TestUtil.findAll(em, UserGroup.class).isEmpty()) {
            userGroup = UserGroupResourceIT.createUpdatedEntity(em);
            em.persist(userGroup);
            em.flush();
        } else {
            userGroup = TestUtil.findAll(em, UserGroup.class).get(0);
        }
        userGroupMember.setUserGroup(userGroup);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userGroupMember.setUser(user);
        return userGroupMember;
    }

    @BeforeEach
    public void initTest() {
        userGroupMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroupMember() throws Exception {
        int databaseSizeBeforeCreate = userGroupMemberRepository.findAll().size();
        // Create the UserGroupMember
        restUserGroupMemberMockMvc.perform(post("/api/user-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupMember)))
            .andExpect(status().isCreated());

        // Validate the UserGroupMember in the database
        List<UserGroupMember> userGroupMemberList = userGroupMemberRepository.findAll();
        assertThat(userGroupMemberList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroupMember testUserGroupMember = userGroupMemberList.get(userGroupMemberList.size() - 1);
        assertThat(testUserGroupMember.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testUserGroupMember.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createUserGroupMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupMemberRepository.findAll().size();

        // Create the UserGroupMember with an existing ID
        userGroupMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupMemberMockMvc.perform(post("/api/user-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupMember)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupMember in the database
        List<UserGroupMember> userGroupMemberList = userGroupMemberRepository.findAll();
        assertThat(userGroupMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGroupMembers() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserGroupMember() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get the userGroupMember
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members/{id}", userGroupMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroupMember.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }


    @Test
    @Transactional
    public void getUserGroupMembersByIdFiltering() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        Long id = userGroupMember.getId();

        defaultUserGroupMemberShouldBeFound("id.equals=" + id);
        defaultUserGroupMemberShouldNotBeFound("id.notEquals=" + id);

        defaultUserGroupMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserGroupMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultUserGroupMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserGroupMemberShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserGroupMembersByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where fromDate equals to DEFAULT_FROM_DATE
        defaultUserGroupMemberShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the userGroupMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultUserGroupMemberShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where fromDate not equals to DEFAULT_FROM_DATE
        defaultUserGroupMemberShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the userGroupMemberList where fromDate not equals to UPDATED_FROM_DATE
        defaultUserGroupMemberShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultUserGroupMemberShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the userGroupMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultUserGroupMemberShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where fromDate is not null
        defaultUserGroupMemberShouldBeFound("fromDate.specified=true");

        // Get all the userGroupMemberList where fromDate is null
        defaultUserGroupMemberShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where thruDate equals to DEFAULT_THRU_DATE
        defaultUserGroupMemberShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the userGroupMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultUserGroupMemberShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where thruDate not equals to DEFAULT_THRU_DATE
        defaultUserGroupMemberShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the userGroupMemberList where thruDate not equals to UPDATED_THRU_DATE
        defaultUserGroupMemberShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultUserGroupMemberShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the userGroupMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultUserGroupMemberShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupMemberRepository.saveAndFlush(userGroupMember);

        // Get all the userGroupMemberList where thruDate is not null
        defaultUserGroupMemberShouldBeFound("thruDate.specified=true");

        // Get all the userGroupMemberList where thruDate is null
        defaultUserGroupMemberShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserGroupMembersByUserGroupIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserGroup userGroup = userGroupMember.getUserGroup();
        userGroupMemberRepository.saveAndFlush(userGroupMember);
        Long userGroupId = userGroup.getId();

        // Get all the userGroupMemberList where userGroup equals to userGroupId
        defaultUserGroupMemberShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the userGroupMemberList where userGroup equals to userGroupId + 1
        defaultUserGroupMemberShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllUserGroupMembersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = userGroupMember.getUser();
        userGroupMemberRepository.saveAndFlush(userGroupMember);
        Long userId = user.getId();

        // Get all the userGroupMemberList where user equals to userId
        defaultUserGroupMemberShouldBeFound("userId.equals=" + userId);

        // Get all the userGroupMemberList where user equals to userId + 1
        defaultUserGroupMemberShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGroupMemberShouldBeFound(String filter) throws Exception {
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));

        // Check, that the count call also returns 1
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserGroupMemberShouldNotBeFound(String filter) throws Exception {
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupMember() throws Exception {
        // Get the userGroupMember
        restUserGroupMemberMockMvc.perform(get("/api/user-group-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupMember() throws Exception {
        // Initialize the database
        userGroupMemberService.save(userGroupMember);

        int databaseSizeBeforeUpdate = userGroupMemberRepository.findAll().size();

        // Update the userGroupMember
        UserGroupMember updatedUserGroupMember = userGroupMemberRepository.findById(userGroupMember.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroupMember are not directly saved in db
        em.detach(updatedUserGroupMember);
        updatedUserGroupMember
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restUserGroupMemberMockMvc.perform(put("/api/user-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserGroupMember)))
            .andExpect(status().isOk());

        // Validate the UserGroupMember in the database
        List<UserGroupMember> userGroupMemberList = userGroupMemberRepository.findAll();
        assertThat(userGroupMemberList).hasSize(databaseSizeBeforeUpdate);
        UserGroupMember testUserGroupMember = userGroupMemberList.get(userGroupMemberList.size() - 1);
        assertThat(testUserGroupMember.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testUserGroupMember.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroupMember() throws Exception {
        int databaseSizeBeforeUpdate = userGroupMemberRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMemberMockMvc.perform(put("/api/user-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupMember)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupMember in the database
        List<UserGroupMember> userGroupMemberList = userGroupMemberRepository.findAll();
        assertThat(userGroupMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroupMember() throws Exception {
        // Initialize the database
        userGroupMemberService.save(userGroupMember);

        int databaseSizeBeforeDelete = userGroupMemberRepository.findAll().size();

        // Delete the userGroupMember
        restUserGroupMemberMockMvc.perform(delete("/api/user-group-members/{id}", userGroupMember.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroupMember> userGroupMemberList = userGroupMemberRepository.findAll();
        assertThat(userGroupMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
