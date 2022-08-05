package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Reason;
import com.hr.domain.ReasonType;
import com.hr.repository.ReasonRepository;
import com.hr.service.ReasonService;
import com.hr.service.dto.ReasonCriteria;
import com.hr.service.ReasonQueryService;

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
 * Integration tests for the {@link ReasonResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReasonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReasonRepository reasonRepository;

    @Autowired
    private ReasonService reasonService;

    @Autowired
    private ReasonQueryService reasonQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReasonMockMvc;

    private Reason reason;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reason createEntity(EntityManager em) {
        Reason reason = new Reason()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return reason;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reason createUpdatedEntity(EntityManager em) {
        Reason reason = new Reason()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return reason;
    }

    @BeforeEach
    public void initTest() {
        reason = createEntity(em);
    }

    @Test
    @Transactional
    public void createReason() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();
        // Create the Reason
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reason)))
            .andExpect(status().isCreated());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate + 1);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason with an existing ID
        reason.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reason)))
            .andExpect(status().isBadRequest());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReasons() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList
        restReasonMockMvc.perform(get("/api/reasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reason.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getReasonsByIdFiltering() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        Long id = reason.getId();

        defaultReasonShouldBeFound("id.equals=" + id);
        defaultReasonShouldNotBeFound("id.notEquals=" + id);

        defaultReasonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReasonShouldNotBeFound("id.greaterThan=" + id);

        defaultReasonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReasonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReasonsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name equals to DEFAULT_NAME
        defaultReasonShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reasonList where name equals to UPDATED_NAME
        defaultReasonShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReasonsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name not equals to DEFAULT_NAME
        defaultReasonShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the reasonList where name not equals to UPDATED_NAME
        defaultReasonShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReasonsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReasonShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reasonList where name equals to UPDATED_NAME
        defaultReasonShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReasonsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name is not null
        defaultReasonShouldBeFound("name.specified=true");

        // Get all the reasonList where name is null
        defaultReasonShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReasonsByNameContainsSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name contains DEFAULT_NAME
        defaultReasonShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the reasonList where name contains UPDATED_NAME
        defaultReasonShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReasonsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where name does not contain DEFAULT_NAME
        defaultReasonShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the reasonList where name does not contain UPDATED_NAME
        defaultReasonShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReasonsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description equals to DEFAULT_DESCRIPTION
        defaultReasonShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reasonList where description equals to UPDATED_DESCRIPTION
        defaultReasonShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReasonsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description not equals to DEFAULT_DESCRIPTION
        defaultReasonShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the reasonList where description not equals to UPDATED_DESCRIPTION
        defaultReasonShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReasonsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReasonShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reasonList where description equals to UPDATED_DESCRIPTION
        defaultReasonShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReasonsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description is not null
        defaultReasonShouldBeFound("description.specified=true");

        // Get all the reasonList where description is null
        defaultReasonShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllReasonsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description contains DEFAULT_DESCRIPTION
        defaultReasonShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the reasonList where description contains UPDATED_DESCRIPTION
        defaultReasonShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReasonsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList where description does not contain DEFAULT_DESCRIPTION
        defaultReasonShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the reasonList where description does not contain UPDATED_DESCRIPTION
        defaultReasonShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllReasonsByReasonTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);
        ReasonType reasonType = ReasonTypeResourceIT.createEntity(em);
        em.persist(reasonType);
        em.flush();
        reason.setReasonType(reasonType);
        reasonRepository.saveAndFlush(reason);
        Long reasonTypeId = reasonType.getId();

        // Get all the reasonList where reasonType equals to reasonTypeId
        defaultReasonShouldBeFound("reasonTypeId.equals=" + reasonTypeId);

        // Get all the reasonList where reasonType equals to reasonTypeId + 1
        defaultReasonShouldNotBeFound("reasonTypeId.equals=" + (reasonTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReasonShouldBeFound(String filter) throws Exception {
        restReasonMockMvc.perform(get("/api/reasons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restReasonMockMvc.perform(get("/api/reasons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReasonShouldNotBeFound(String filter) throws Exception {
        restReasonMockMvc.perform(get("/api/reasons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReasonMockMvc.perform(get("/api/reasons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReason() throws Exception {
        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReason() throws Exception {
        // Initialize the database
        reasonService.save(reason);

        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Update the reason
        Reason updatedReason = reasonRepository.findById(reason.getId()).get();
        // Disconnect from session so that the updates on updatedReason are not directly saved in db
        em.detach(updatedReason);
        updatedReason
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReason)))
            .andExpect(status().isOk());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReason() throws Exception {
        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reason)))
            .andExpect(status().isBadRequest());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReason() throws Exception {
        // Initialize the database
        reasonService.save(reason);

        int databaseSizeBeforeDelete = reasonRepository.findAll().size();

        // Delete the reason
        restReasonMockMvc.perform(delete("/api/reasons/{id}", reason.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
