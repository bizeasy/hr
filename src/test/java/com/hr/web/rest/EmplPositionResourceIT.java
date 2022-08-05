package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPosition;
import com.hr.repository.EmplPositionRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmplPositionResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionResourceIT {

    private static final BigDecimal DEFAULT_MAX_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAX_BUDGET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MIN_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_BUDGET = new BigDecimal(2);

    private static final LocalDate DEFAULT_ESTIMATED_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ESTIMATED_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PAID_JOB = false;
    private static final Boolean UPDATED_PAID_JOB = true;

    private static final Boolean DEFAULT_IS_FULLTIME = false;
    private static final Boolean UPDATED_IS_FULLTIME = true;

    private static final Boolean DEFAULT_IS_TEMPORARY = false;
    private static final Boolean UPDATED_IS_TEMPORARY = true;

    private static final LocalDate DEFAULT_ACTUAL_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmplPositionRepository emplPositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionMockMvc;

    private EmplPosition emplPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPosition createEntity(EntityManager em) {
        EmplPosition emplPosition = new EmplPosition()
            .maxBudget(DEFAULT_MAX_BUDGET)
            .minBudget(DEFAULT_MIN_BUDGET)
            .estimatedFromDate(DEFAULT_ESTIMATED_FROM_DATE)
            .estimatedThruDate(DEFAULT_ESTIMATED_THRU_DATE)
            .paidJob(DEFAULT_PAID_JOB)
            .isFulltime(DEFAULT_IS_FULLTIME)
            .isTemporary(DEFAULT_IS_TEMPORARY)
            .actualFromDate(DEFAULT_ACTUAL_FROM_DATE)
            .actualThruDate(DEFAULT_ACTUAL_THRU_DATE);
        return emplPosition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPosition createUpdatedEntity(EntityManager em) {
        EmplPosition emplPosition = new EmplPosition()
            .maxBudget(UPDATED_MAX_BUDGET)
            .minBudget(UPDATED_MIN_BUDGET)
            .estimatedFromDate(UPDATED_ESTIMATED_FROM_DATE)
            .estimatedThruDate(UPDATED_ESTIMATED_THRU_DATE)
            .paidJob(UPDATED_PAID_JOB)
            .isFulltime(UPDATED_IS_FULLTIME)
            .isTemporary(UPDATED_IS_TEMPORARY)
            .actualFromDate(UPDATED_ACTUAL_FROM_DATE)
            .actualThruDate(UPDATED_ACTUAL_THRU_DATE);
        return emplPosition;
    }

    @BeforeEach
    public void initTest() {
        emplPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPosition() throws Exception {
        int databaseSizeBeforeCreate = emplPositionRepository.findAll().size();
        // Create the EmplPosition
        restEmplPositionMockMvc.perform(post("/api/empl-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPosition)))
            .andExpect(status().isCreated());

        // Validate the EmplPosition in the database
        List<EmplPosition> emplPositionList = emplPositionRepository.findAll();
        assertThat(emplPositionList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPosition testEmplPosition = emplPositionList.get(emplPositionList.size() - 1);
        assertThat(testEmplPosition.getMaxBudget()).isEqualTo(DEFAULT_MAX_BUDGET);
        assertThat(testEmplPosition.getMinBudget()).isEqualTo(DEFAULT_MIN_BUDGET);
        assertThat(testEmplPosition.getEstimatedFromDate()).isEqualTo(DEFAULT_ESTIMATED_FROM_DATE);
        assertThat(testEmplPosition.getEstimatedThruDate()).isEqualTo(DEFAULT_ESTIMATED_THRU_DATE);
        assertThat(testEmplPosition.isPaidJob()).isEqualTo(DEFAULT_PAID_JOB);
        assertThat(testEmplPosition.isIsFulltime()).isEqualTo(DEFAULT_IS_FULLTIME);
        assertThat(testEmplPosition.isIsTemporary()).isEqualTo(DEFAULT_IS_TEMPORARY);
        assertThat(testEmplPosition.getActualFromDate()).isEqualTo(DEFAULT_ACTUAL_FROM_DATE);
        assertThat(testEmplPosition.getActualThruDate()).isEqualTo(DEFAULT_ACTUAL_THRU_DATE);
    }

    @Test
    @Transactional
    public void createEmplPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionRepository.findAll().size();

        // Create the EmplPosition with an existing ID
        emplPosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionMockMvc.perform(post("/api/empl-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPosition)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPosition in the database
        List<EmplPosition> emplPositionList = emplPositionRepository.findAll();
        assertThat(emplPositionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositions() throws Exception {
        // Initialize the database
        emplPositionRepository.saveAndFlush(emplPosition);

        // Get all the emplPositionList
        restEmplPositionMockMvc.perform(get("/api/empl-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].maxBudget").value(hasItem(DEFAULT_MAX_BUDGET.intValue())))
            .andExpect(jsonPath("$.[*].minBudget").value(hasItem(DEFAULT_MIN_BUDGET.intValue())))
            .andExpect(jsonPath("$.[*].estimatedFromDate").value(hasItem(DEFAULT_ESTIMATED_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedThruDate").value(hasItem(DEFAULT_ESTIMATED_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].paidJob").value(hasItem(DEFAULT_PAID_JOB.booleanValue())))
            .andExpect(jsonPath("$.[*].isFulltime").value(hasItem(DEFAULT_IS_FULLTIME.booleanValue())))
            .andExpect(jsonPath("$.[*].isTemporary").value(hasItem(DEFAULT_IS_TEMPORARY.booleanValue())))
            .andExpect(jsonPath("$.[*].actualFromDate").value(hasItem(DEFAULT_ACTUAL_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualThruDate").value(hasItem(DEFAULT_ACTUAL_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmplPosition() throws Exception {
        // Initialize the database
        emplPositionRepository.saveAndFlush(emplPosition);

        // Get the emplPosition
        restEmplPositionMockMvc.perform(get("/api/empl-positions/{id}", emplPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPosition.getId().intValue()))
            .andExpect(jsonPath("$.maxBudget").value(DEFAULT_MAX_BUDGET.intValue()))
            .andExpect(jsonPath("$.minBudget").value(DEFAULT_MIN_BUDGET.intValue()))
            .andExpect(jsonPath("$.estimatedFromDate").value(DEFAULT_ESTIMATED_FROM_DATE.toString()))
            .andExpect(jsonPath("$.estimatedThruDate").value(DEFAULT_ESTIMATED_THRU_DATE.toString()))
            .andExpect(jsonPath("$.paidJob").value(DEFAULT_PAID_JOB.booleanValue()))
            .andExpect(jsonPath("$.isFulltime").value(DEFAULT_IS_FULLTIME.booleanValue()))
            .andExpect(jsonPath("$.isTemporary").value(DEFAULT_IS_TEMPORARY.booleanValue()))
            .andExpect(jsonPath("$.actualFromDate").value(DEFAULT_ACTUAL_FROM_DATE.toString()))
            .andExpect(jsonPath("$.actualThruDate").value(DEFAULT_ACTUAL_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPosition() throws Exception {
        // Get the emplPosition
        restEmplPositionMockMvc.perform(get("/api/empl-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPosition() throws Exception {
        // Initialize the database
        emplPositionRepository.saveAndFlush(emplPosition);

        int databaseSizeBeforeUpdate = emplPositionRepository.findAll().size();

        // Update the emplPosition
        EmplPosition updatedEmplPosition = emplPositionRepository.findById(emplPosition.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPosition are not directly saved in db
        em.detach(updatedEmplPosition);
        updatedEmplPosition
            .maxBudget(UPDATED_MAX_BUDGET)
            .minBudget(UPDATED_MIN_BUDGET)
            .estimatedFromDate(UPDATED_ESTIMATED_FROM_DATE)
            .estimatedThruDate(UPDATED_ESTIMATED_THRU_DATE)
            .paidJob(UPDATED_PAID_JOB)
            .isFulltime(UPDATED_IS_FULLTIME)
            .isTemporary(UPDATED_IS_TEMPORARY)
            .actualFromDate(UPDATED_ACTUAL_FROM_DATE)
            .actualThruDate(UPDATED_ACTUAL_THRU_DATE);

        restEmplPositionMockMvc.perform(put("/api/empl-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPosition)))
            .andExpect(status().isOk());

        // Validate the EmplPosition in the database
        List<EmplPosition> emplPositionList = emplPositionRepository.findAll();
        assertThat(emplPositionList).hasSize(databaseSizeBeforeUpdate);
        EmplPosition testEmplPosition = emplPositionList.get(emplPositionList.size() - 1);
        assertThat(testEmplPosition.getMaxBudget()).isEqualTo(UPDATED_MAX_BUDGET);
        assertThat(testEmplPosition.getMinBudget()).isEqualTo(UPDATED_MIN_BUDGET);
        assertThat(testEmplPosition.getEstimatedFromDate()).isEqualTo(UPDATED_ESTIMATED_FROM_DATE);
        assertThat(testEmplPosition.getEstimatedThruDate()).isEqualTo(UPDATED_ESTIMATED_THRU_DATE);
        assertThat(testEmplPosition.isPaidJob()).isEqualTo(UPDATED_PAID_JOB);
        assertThat(testEmplPosition.isIsFulltime()).isEqualTo(UPDATED_IS_FULLTIME);
        assertThat(testEmplPosition.isIsTemporary()).isEqualTo(UPDATED_IS_TEMPORARY);
        assertThat(testEmplPosition.getActualFromDate()).isEqualTo(UPDATED_ACTUAL_FROM_DATE);
        assertThat(testEmplPosition.getActualThruDate()).isEqualTo(UPDATED_ACTUAL_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPosition() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionMockMvc.perform(put("/api/empl-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPosition)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPosition in the database
        List<EmplPosition> emplPositionList = emplPositionRepository.findAll();
        assertThat(emplPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPosition() throws Exception {
        // Initialize the database
        emplPositionRepository.saveAndFlush(emplPosition);

        int databaseSizeBeforeDelete = emplPositionRepository.findAll().size();

        // Delete the emplPosition
        restEmplPositionMockMvc.perform(delete("/api/empl-positions/{id}", emplPosition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPosition> emplPositionList = emplPositionRepository.findAll();
        assertThat(emplPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
