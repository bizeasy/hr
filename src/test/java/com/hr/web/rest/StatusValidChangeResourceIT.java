package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.StatusValidChange;
import com.hr.domain.Status;
import com.hr.repository.StatusValidChangeRepository;
import com.hr.service.StatusValidChangeService;
import com.hr.service.dto.StatusValidChangeCriteria;
import com.hr.service.StatusValidChangeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StatusValidChangeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatusValidChangeResourceIT {

    private static final String DEFAULT_TRANSITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRANSITION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RULES = "AAAAAAAAAA";
    private static final String UPDATED_RULES = "BBBBBBBBBB";

    @Autowired
    private StatusValidChangeRepository statusValidChangeRepository;

    @Autowired
    private StatusValidChangeService statusValidChangeService;

    @Autowired
    private StatusValidChangeQueryService statusValidChangeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusValidChangeMockMvc;

    private StatusValidChange statusValidChange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusValidChange createEntity(EntityManager em) {
        StatusValidChange statusValidChange = new StatusValidChange()
            .transitionName(DEFAULT_TRANSITION_NAME)
            .rules(DEFAULT_RULES);
        return statusValidChange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusValidChange createUpdatedEntity(EntityManager em) {
        StatusValidChange statusValidChange = new StatusValidChange()
            .transitionName(UPDATED_TRANSITION_NAME)
            .rules(UPDATED_RULES);
        return statusValidChange;
    }

    @BeforeEach
    public void initTest() {
        statusValidChange = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusValidChange() throws Exception {
        int databaseSizeBeforeCreate = statusValidChangeRepository.findAll().size();
        // Create the StatusValidChange
        restStatusValidChangeMockMvc.perform(post("/api/status-valid-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusValidChange)))
            .andExpect(status().isCreated());

        // Validate the StatusValidChange in the database
        List<StatusValidChange> statusValidChangeList = statusValidChangeRepository.findAll();
        assertThat(statusValidChangeList).hasSize(databaseSizeBeforeCreate + 1);
        StatusValidChange testStatusValidChange = statusValidChangeList.get(statusValidChangeList.size() - 1);
        assertThat(testStatusValidChange.getTransitionName()).isEqualTo(DEFAULT_TRANSITION_NAME);
        assertThat(testStatusValidChange.getRules()).isEqualTo(DEFAULT_RULES);
    }

    @Test
    @Transactional
    public void createStatusValidChangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusValidChangeRepository.findAll().size();

        // Create the StatusValidChange with an existing ID
        statusValidChange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusValidChangeMockMvc.perform(post("/api/status-valid-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusValidChange)))
            .andExpect(status().isBadRequest());

        // Validate the StatusValidChange in the database
        List<StatusValidChange> statusValidChangeList = statusValidChangeRepository.findAll();
        assertThat(statusValidChangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatusValidChanges() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusValidChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].transitionName").value(hasItem(DEFAULT_TRANSITION_NAME)))
            .andExpect(jsonPath("$.[*].rules").value(hasItem(DEFAULT_RULES.toString())));
    }
    
    @Test
    @Transactional
    public void getStatusValidChange() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get the statusValidChange
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes/{id}", statusValidChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusValidChange.getId().intValue()))
            .andExpect(jsonPath("$.transitionName").value(DEFAULT_TRANSITION_NAME))
            .andExpect(jsonPath("$.rules").value(DEFAULT_RULES.toString()));
    }


    @Test
    @Transactional
    public void getStatusValidChangesByIdFiltering() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        Long id = statusValidChange.getId();

        defaultStatusValidChangeShouldBeFound("id.equals=" + id);
        defaultStatusValidChangeShouldNotBeFound("id.notEquals=" + id);

        defaultStatusValidChangeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusValidChangeShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusValidChangeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusValidChangeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName equals to DEFAULT_TRANSITION_NAME
        defaultStatusValidChangeShouldBeFound("transitionName.equals=" + DEFAULT_TRANSITION_NAME);

        // Get all the statusValidChangeList where transitionName equals to UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldNotBeFound("transitionName.equals=" + UPDATED_TRANSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName not equals to DEFAULT_TRANSITION_NAME
        defaultStatusValidChangeShouldNotBeFound("transitionName.notEquals=" + DEFAULT_TRANSITION_NAME);

        // Get all the statusValidChangeList where transitionName not equals to UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldBeFound("transitionName.notEquals=" + UPDATED_TRANSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName in DEFAULT_TRANSITION_NAME or UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldBeFound("transitionName.in=" + DEFAULT_TRANSITION_NAME + "," + UPDATED_TRANSITION_NAME);

        // Get all the statusValidChangeList where transitionName equals to UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldNotBeFound("transitionName.in=" + UPDATED_TRANSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName is not null
        defaultStatusValidChangeShouldBeFound("transitionName.specified=true");

        // Get all the statusValidChangeList where transitionName is null
        defaultStatusValidChangeShouldNotBeFound("transitionName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameContainsSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName contains DEFAULT_TRANSITION_NAME
        defaultStatusValidChangeShouldBeFound("transitionName.contains=" + DEFAULT_TRANSITION_NAME);

        // Get all the statusValidChangeList where transitionName contains UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldNotBeFound("transitionName.contains=" + UPDATED_TRANSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusValidChangesByTransitionNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);

        // Get all the statusValidChangeList where transitionName does not contain DEFAULT_TRANSITION_NAME
        defaultStatusValidChangeShouldNotBeFound("transitionName.doesNotContain=" + DEFAULT_TRANSITION_NAME);

        // Get all the statusValidChangeList where transitionName does not contain UPDATED_TRANSITION_NAME
        defaultStatusValidChangeShouldBeFound("transitionName.doesNotContain=" + UPDATED_TRANSITION_NAME);
    }


    @Test
    @Transactional
    public void getAllStatusValidChangesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        statusValidChange.setStatus(status);
        statusValidChangeRepository.saveAndFlush(statusValidChange);
        Long statusId = status.getId();

        // Get all the statusValidChangeList where status equals to statusId
        defaultStatusValidChangeShouldBeFound("statusId.equals=" + statusId);

        // Get all the statusValidChangeList where status equals to statusId + 1
        defaultStatusValidChangeShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllStatusValidChangesByStatusToIsEqualToSomething() throws Exception {
        // Initialize the database
        statusValidChangeRepository.saveAndFlush(statusValidChange);
        Status statusTo = StatusResourceIT.createEntity(em);
        em.persist(statusTo);
        em.flush();
        statusValidChange.setStatusTo(statusTo);
        statusValidChangeRepository.saveAndFlush(statusValidChange);
        Long statusToId = statusTo.getId();

        // Get all the statusValidChangeList where statusTo equals to statusToId
        defaultStatusValidChangeShouldBeFound("statusToId.equals=" + statusToId);

        // Get all the statusValidChangeList where statusTo equals to statusToId + 1
        defaultStatusValidChangeShouldNotBeFound("statusToId.equals=" + (statusToId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusValidChangeShouldBeFound(String filter) throws Exception {
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusValidChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].transitionName").value(hasItem(DEFAULT_TRANSITION_NAME)))
            .andExpect(jsonPath("$.[*].rules").value(hasItem(DEFAULT_RULES.toString())));

        // Check, that the count call also returns 1
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusValidChangeShouldNotBeFound(String filter) throws Exception {
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatusValidChange() throws Exception {
        // Get the statusValidChange
        restStatusValidChangeMockMvc.perform(get("/api/status-valid-changes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusValidChange() throws Exception {
        // Initialize the database
        statusValidChangeService.save(statusValidChange);

        int databaseSizeBeforeUpdate = statusValidChangeRepository.findAll().size();

        // Update the statusValidChange
        StatusValidChange updatedStatusValidChange = statusValidChangeRepository.findById(statusValidChange.getId()).get();
        // Disconnect from session so that the updates on updatedStatusValidChange are not directly saved in db
        em.detach(updatedStatusValidChange);
        updatedStatusValidChange
            .transitionName(UPDATED_TRANSITION_NAME)
            .rules(UPDATED_RULES);

        restStatusValidChangeMockMvc.perform(put("/api/status-valid-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatusValidChange)))
            .andExpect(status().isOk());

        // Validate the StatusValidChange in the database
        List<StatusValidChange> statusValidChangeList = statusValidChangeRepository.findAll();
        assertThat(statusValidChangeList).hasSize(databaseSizeBeforeUpdate);
        StatusValidChange testStatusValidChange = statusValidChangeList.get(statusValidChangeList.size() - 1);
        assertThat(testStatusValidChange.getTransitionName()).isEqualTo(UPDATED_TRANSITION_NAME);
        assertThat(testStatusValidChange.getRules()).isEqualTo(UPDATED_RULES);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusValidChange() throws Exception {
        int databaseSizeBeforeUpdate = statusValidChangeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusValidChangeMockMvc.perform(put("/api/status-valid-changes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusValidChange)))
            .andExpect(status().isBadRequest());

        // Validate the StatusValidChange in the database
        List<StatusValidChange> statusValidChangeList = statusValidChangeRepository.findAll();
        assertThat(statusValidChangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatusValidChange() throws Exception {
        // Initialize the database
        statusValidChangeService.save(statusValidChange);

        int databaseSizeBeforeDelete = statusValidChangeRepository.findAll().size();

        // Delete the statusValidChange
        restStatusValidChangeMockMvc.perform(delete("/api/status-valid-changes/{id}", statusValidChange.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusValidChange> statusValidChangeList = statusValidChangeRepository.findAll();
        assertThat(statusValidChangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
