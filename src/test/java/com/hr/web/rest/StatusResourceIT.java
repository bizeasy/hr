package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Status;
import com.hr.domain.StatusCategory;
import com.hr.repository.StatusRepository;
import com.hr.service.StatusService;
import com.hr.service.dto.StatusCriteria;
import com.hr.service.StatusQueryService;

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
 * Integration tests for the {@link StatusResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusQueryService statusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusMockMvc;

    private Status status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity(EntityManager em) {
        Status status = new Status()
            .name(DEFAULT_NAME)
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .description(DEFAULT_DESCRIPTION)
            .action(DEFAULT_ACTION);
        return status;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity(EntityManager em) {
        Status status = new Status()
            .name(UPDATED_NAME)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .description(UPDATED_DESCRIPTION)
            .action(UPDATED_ACTION);
        return status;
    }

    @BeforeEach
    public void initTest() {
        status = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();
        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatus.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)));
    }
    
    @Test
    @Transactional
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION));
    }


    @Test
    @Transactional
    public void getStatusesByIdFiltering() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        Long id = status.getId();

        defaultStatusShouldBeFound("id.equals=" + id);
        defaultStatusShouldNotBeFound("id.notEquals=" + id);

        defaultStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name equals to DEFAULT_NAME
        defaultStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the statusList where name equals to UPDATED_NAME
        defaultStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name not equals to DEFAULT_NAME
        defaultStatusShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the statusList where name not equals to UPDATED_NAME
        defaultStatusShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the statusList where name equals to UPDATED_NAME
        defaultStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name is not null
        defaultStatusShouldBeFound("name.specified=true");

        // Get all the statusList where name is null
        defaultStatusShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name contains DEFAULT_NAME
        defaultStatusShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the statusList where name contains UPDATED_NAME
        defaultStatusShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where name does not contain DEFAULT_NAME
        defaultStatusShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the statusList where name does not contain UPDATED_NAME
        defaultStatusShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the statusList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo is not null
        defaultStatusShouldBeFound("sequenceNo.specified=true");

        // Get all the statusList where sequenceNo is null
        defaultStatusShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStatusesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultStatusShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the statusList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultStatusShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllStatusesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description equals to DEFAULT_DESCRIPTION
        defaultStatusShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the statusList where description equals to UPDATED_DESCRIPTION
        defaultStatusShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description not equals to DEFAULT_DESCRIPTION
        defaultStatusShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the statusList where description not equals to UPDATED_DESCRIPTION
        defaultStatusShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStatusShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the statusList where description equals to UPDATED_DESCRIPTION
        defaultStatusShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description is not null
        defaultStatusShouldBeFound("description.specified=true");

        // Get all the statusList where description is null
        defaultStatusShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description contains DEFAULT_DESCRIPTION
        defaultStatusShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the statusList where description contains UPDATED_DESCRIPTION
        defaultStatusShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where description does not contain DEFAULT_DESCRIPTION
        defaultStatusShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the statusList where description does not contain UPDATED_DESCRIPTION
        defaultStatusShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllStatusesByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action equals to DEFAULT_ACTION
        defaultStatusShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the statusList where action equals to UPDATED_ACTION
        defaultStatusShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action not equals to DEFAULT_ACTION
        defaultStatusShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the statusList where action not equals to UPDATED_ACTION
        defaultStatusShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByActionIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultStatusShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the statusList where action equals to UPDATED_ACTION
        defaultStatusShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action is not null
        defaultStatusShouldBeFound("action.specified=true");

        // Get all the statusList where action is null
        defaultStatusShouldNotBeFound("action.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusesByActionContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action contains DEFAULT_ACTION
        defaultStatusShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the statusList where action contains UPDATED_ACTION
        defaultStatusShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllStatusesByActionNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where action does not contain DEFAULT_ACTION
        defaultStatusShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the statusList where action does not contain UPDATED_ACTION
        defaultStatusShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }


    @Test
    @Transactional
    public void getAllStatusesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);
        StatusCategory category = StatusCategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        status.setCategory(category);
        statusRepository.saveAndFlush(status);
        Long categoryId = category.getId();

        // Get all the statusList where category equals to categoryId
        defaultStatusShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the statusList where category equals to categoryId + 1
        defaultStatusShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusShouldBeFound(String filter) throws Exception {
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)));

        // Check, that the count call also returns 1
        restStatusMockMvc.perform(get("/api/statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusShouldNotBeFound(String filter) throws Exception {
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusMockMvc.perform(get("/api/statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        // Disconnect from session so that the updates on updatedStatus are not directly saved in db
        em.detach(updatedStatus);
        updatedStatus
            .name(UPDATED_NAME)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .description(UPDATED_DESCRIPTION)
            .action(UPDATED_ACTION);

        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatus.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
