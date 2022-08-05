package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortStatus;
import com.hr.domain.WorkEffort;
import com.hr.domain.Status;
import com.hr.repository.WorkEffortStatusRepository;
import com.hr.service.WorkEffortStatusService;
import com.hr.service.dto.WorkEffortStatusCriteria;
import com.hr.service.WorkEffortStatusQueryService;

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
 * Integration tests for the {@link WorkEffortStatusResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortStatusResourceIT {

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private WorkEffortStatusRepository workEffortStatusRepository;

    @Autowired
    private WorkEffortStatusService workEffortStatusService;

    @Autowired
    private WorkEffortStatusQueryService workEffortStatusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortStatusMockMvc;

    private WorkEffortStatus workEffortStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortStatus createEntity(EntityManager em) {
        WorkEffortStatus workEffortStatus = new WorkEffortStatus()
            .reason(DEFAULT_REASON);
        return workEffortStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortStatus createUpdatedEntity(EntityManager em) {
        WorkEffortStatus workEffortStatus = new WorkEffortStatus()
            .reason(UPDATED_REASON);
        return workEffortStatus;
    }

    @BeforeEach
    public void initTest() {
        workEffortStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortStatus() throws Exception {
        int databaseSizeBeforeCreate = workEffortStatusRepository.findAll().size();
        // Create the WorkEffortStatus
        restWorkEffortStatusMockMvc.perform(post("/api/work-effort-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortStatus)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortStatus in the database
        List<WorkEffortStatus> workEffortStatusList = workEffortStatusRepository.findAll();
        assertThat(workEffortStatusList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortStatus testWorkEffortStatus = workEffortStatusList.get(workEffortStatusList.size() - 1);
        assertThat(testWorkEffortStatus.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createWorkEffortStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortStatusRepository.findAll().size();

        // Create the WorkEffortStatus with an existing ID
        workEffortStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortStatusMockMvc.perform(post("/api/work-effort-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortStatus)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortStatus in the database
        List<WorkEffortStatus> workEffortStatusList = workEffortStatusRepository.findAll();
        assertThat(workEffortStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortStatuses() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }
    
    @Test
    @Transactional
    public void getWorkEffortStatus() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get the workEffortStatus
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses/{id}", workEffortStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortStatus.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }


    @Test
    @Transactional
    public void getWorkEffortStatusesByIdFiltering() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        Long id = workEffortStatus.getId();

        defaultWorkEffortStatusShouldBeFound("id.equals=" + id);
        defaultWorkEffortStatusShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason equals to DEFAULT_REASON
        defaultWorkEffortStatusShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the workEffortStatusList where reason equals to UPDATED_REASON
        defaultWorkEffortStatusShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason not equals to DEFAULT_REASON
        defaultWorkEffortStatusShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the workEffortStatusList where reason not equals to UPDATED_REASON
        defaultWorkEffortStatusShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultWorkEffortStatusShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the workEffortStatusList where reason equals to UPDATED_REASON
        defaultWorkEffortStatusShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason is not null
        defaultWorkEffortStatusShouldBeFound("reason.specified=true");

        // Get all the workEffortStatusList where reason is null
        defaultWorkEffortStatusShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonContainsSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason contains DEFAULT_REASON
        defaultWorkEffortStatusShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the workEffortStatusList where reason contains UPDATED_REASON
        defaultWorkEffortStatusShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllWorkEffortStatusesByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);

        // Get all the workEffortStatusList where reason does not contain DEFAULT_REASON
        defaultWorkEffortStatusShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the workEffortStatusList where reason does not contain UPDATED_REASON
        defaultWorkEffortStatusShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllWorkEffortStatusesByWorkEffortIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);
        WorkEffort workEffort = WorkEffortResourceIT.createEntity(em);
        em.persist(workEffort);
        em.flush();
        workEffortStatus.setWorkEffort(workEffort);
        workEffortStatusRepository.saveAndFlush(workEffortStatus);
        Long workEffortId = workEffort.getId();

        // Get all the workEffortStatusList where workEffort equals to workEffortId
        defaultWorkEffortStatusShouldBeFound("workEffortId.equals=" + workEffortId);

        // Get all the workEffortStatusList where workEffort equals to workEffortId + 1
        defaultWorkEffortStatusShouldNotBeFound("workEffortId.equals=" + (workEffortId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortStatusRepository.saveAndFlush(workEffortStatus);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        workEffortStatus.setStatus(status);
        workEffortStatusRepository.saveAndFlush(workEffortStatus);
        Long statusId = status.getId();

        // Get all the workEffortStatusList where status equals to statusId
        defaultWorkEffortStatusShouldBeFound("statusId.equals=" + statusId);

        // Get all the workEffortStatusList where status equals to statusId + 1
        defaultWorkEffortStatusShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortStatusShouldBeFound(String filter) throws Exception {
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));

        // Check, that the count call also returns 1
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortStatusShouldNotBeFound(String filter) throws Exception {
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffortStatus() throws Exception {
        // Get the workEffortStatus
        restWorkEffortStatusMockMvc.perform(get("/api/work-effort-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortStatus() throws Exception {
        // Initialize the database
        workEffortStatusService.save(workEffortStatus);

        int databaseSizeBeforeUpdate = workEffortStatusRepository.findAll().size();

        // Update the workEffortStatus
        WorkEffortStatus updatedWorkEffortStatus = workEffortStatusRepository.findById(workEffortStatus.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortStatus are not directly saved in db
        em.detach(updatedWorkEffortStatus);
        updatedWorkEffortStatus
            .reason(UPDATED_REASON);

        restWorkEffortStatusMockMvc.perform(put("/api/work-effort-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortStatus)))
            .andExpect(status().isOk());

        // Validate the WorkEffortStatus in the database
        List<WorkEffortStatus> workEffortStatusList = workEffortStatusRepository.findAll();
        assertThat(workEffortStatusList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortStatus testWorkEffortStatus = workEffortStatusList.get(workEffortStatusList.size() - 1);
        assertThat(testWorkEffortStatus.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortStatus() throws Exception {
        int databaseSizeBeforeUpdate = workEffortStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortStatusMockMvc.perform(put("/api/work-effort-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortStatus)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortStatus in the database
        List<WorkEffortStatus> workEffortStatusList = workEffortStatusRepository.findAll();
        assertThat(workEffortStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortStatus() throws Exception {
        // Initialize the database
        workEffortStatusService.save(workEffortStatus);

        int databaseSizeBeforeDelete = workEffortStatusRepository.findAll().size();

        // Delete the workEffortStatus
        restWorkEffortStatusMockMvc.perform(delete("/api/work-effort-statuses/{id}", workEffortStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortStatus> workEffortStatusList = workEffortStatusRepository.findAll();
        assertThat(workEffortStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
