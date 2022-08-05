package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.CustomTimePeriod;
import com.hr.repository.CustomTimePeriodRepository;

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
 * Integration tests for the {@link CustomTimePeriodResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomTimePeriodResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_CLOSED = false;
    private static final Boolean UPDATED_IS_CLOSED = true;

    private static final String DEFAULT_PERIOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIOD_NUM = 1;
    private static final Integer UPDATED_PERIOD_NUM = 2;

    @Autowired
    private CustomTimePeriodRepository customTimePeriodRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomTimePeriodMockMvc;

    private CustomTimePeriod customTimePeriod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomTimePeriod createEntity(EntityManager em) {
        CustomTimePeriod customTimePeriod = new CustomTimePeriod()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .isClosed(DEFAULT_IS_CLOSED)
            .periodName(DEFAULT_PERIOD_NAME)
            .periodNum(DEFAULT_PERIOD_NUM);
        return customTimePeriod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomTimePeriod createUpdatedEntity(EntityManager em) {
        CustomTimePeriod customTimePeriod = new CustomTimePeriod()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .isClosed(UPDATED_IS_CLOSED)
            .periodName(UPDATED_PERIOD_NAME)
            .periodNum(UPDATED_PERIOD_NUM);
        return customTimePeriod;
    }

    @BeforeEach
    public void initTest() {
        customTimePeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomTimePeriod() throws Exception {
        int databaseSizeBeforeCreate = customTimePeriodRepository.findAll().size();
        // Create the CustomTimePeriod
        restCustomTimePeriodMockMvc.perform(post("/api/custom-time-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customTimePeriod)))
            .andExpect(status().isCreated());

        // Validate the CustomTimePeriod in the database
        List<CustomTimePeriod> customTimePeriodList = customTimePeriodRepository.findAll();
        assertThat(customTimePeriodList).hasSize(databaseSizeBeforeCreate + 1);
        CustomTimePeriod testCustomTimePeriod = customTimePeriodList.get(customTimePeriodList.size() - 1);
        assertThat(testCustomTimePeriod.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testCustomTimePeriod.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testCustomTimePeriod.isIsClosed()).isEqualTo(DEFAULT_IS_CLOSED);
        assertThat(testCustomTimePeriod.getPeriodName()).isEqualTo(DEFAULT_PERIOD_NAME);
        assertThat(testCustomTimePeriod.getPeriodNum()).isEqualTo(DEFAULT_PERIOD_NUM);
    }

    @Test
    @Transactional
    public void createCustomTimePeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customTimePeriodRepository.findAll().size();

        // Create the CustomTimePeriod with an existing ID
        customTimePeriod.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomTimePeriodMockMvc.perform(post("/api/custom-time-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customTimePeriod)))
            .andExpect(status().isBadRequest());

        // Validate the CustomTimePeriod in the database
        List<CustomTimePeriod> customTimePeriodList = customTimePeriodRepository.findAll();
        assertThat(customTimePeriodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomTimePeriods() throws Exception {
        // Initialize the database
        customTimePeriodRepository.saveAndFlush(customTimePeriod);

        // Get all the customTimePeriodList
        restCustomTimePeriodMockMvc.perform(get("/api/custom-time-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customTimePeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].isClosed").value(hasItem(DEFAULT_IS_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].periodName").value(hasItem(DEFAULT_PERIOD_NAME)))
            .andExpect(jsonPath("$.[*].periodNum").value(hasItem(DEFAULT_PERIOD_NUM)));
    }
    
    @Test
    @Transactional
    public void getCustomTimePeriod() throws Exception {
        // Initialize the database
        customTimePeriodRepository.saveAndFlush(customTimePeriod);

        // Get the customTimePeriod
        restCustomTimePeriodMockMvc.perform(get("/api/custom-time-periods/{id}", customTimePeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customTimePeriod.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.isClosed").value(DEFAULT_IS_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.periodName").value(DEFAULT_PERIOD_NAME))
            .andExpect(jsonPath("$.periodNum").value(DEFAULT_PERIOD_NUM));
    }
    @Test
    @Transactional
    public void getNonExistingCustomTimePeriod() throws Exception {
        // Get the customTimePeriod
        restCustomTimePeriodMockMvc.perform(get("/api/custom-time-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomTimePeriod() throws Exception {
        // Initialize the database
        customTimePeriodRepository.saveAndFlush(customTimePeriod);

        int databaseSizeBeforeUpdate = customTimePeriodRepository.findAll().size();

        // Update the customTimePeriod
        CustomTimePeriod updatedCustomTimePeriod = customTimePeriodRepository.findById(customTimePeriod.getId()).get();
        // Disconnect from session so that the updates on updatedCustomTimePeriod are not directly saved in db
        em.detach(updatedCustomTimePeriod);
        updatedCustomTimePeriod
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .isClosed(UPDATED_IS_CLOSED)
            .periodName(UPDATED_PERIOD_NAME)
            .periodNum(UPDATED_PERIOD_NUM);

        restCustomTimePeriodMockMvc.perform(put("/api/custom-time-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomTimePeriod)))
            .andExpect(status().isOk());

        // Validate the CustomTimePeriod in the database
        List<CustomTimePeriod> customTimePeriodList = customTimePeriodRepository.findAll();
        assertThat(customTimePeriodList).hasSize(databaseSizeBeforeUpdate);
        CustomTimePeriod testCustomTimePeriod = customTimePeriodList.get(customTimePeriodList.size() - 1);
        assertThat(testCustomTimePeriod.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testCustomTimePeriod.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testCustomTimePeriod.isIsClosed()).isEqualTo(UPDATED_IS_CLOSED);
        assertThat(testCustomTimePeriod.getPeriodName()).isEqualTo(UPDATED_PERIOD_NAME);
        assertThat(testCustomTimePeriod.getPeriodNum()).isEqualTo(UPDATED_PERIOD_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomTimePeriod() throws Exception {
        int databaseSizeBeforeUpdate = customTimePeriodRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomTimePeriodMockMvc.perform(put("/api/custom-time-periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customTimePeriod)))
            .andExpect(status().isBadRequest());

        // Validate the CustomTimePeriod in the database
        List<CustomTimePeriod> customTimePeriodList = customTimePeriodRepository.findAll();
        assertThat(customTimePeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomTimePeriod() throws Exception {
        // Initialize the database
        customTimePeriodRepository.saveAndFlush(customTimePeriod);

        int databaseSizeBeforeDelete = customTimePeriodRepository.findAll().size();

        // Delete the customTimePeriod
        restCustomTimePeriodMockMvc.perform(delete("/api/custom-time-periods/{id}", customTimePeriod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomTimePeriod> customTimePeriodList = customTimePeriodRepository.findAll();
        assertThat(customTimePeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
