package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.FacilityUsageLog;
import com.hr.repository.FacilityUsageLogRepository;

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
 * Integration tests for the {@link FacilityUsageLogResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityUsageLogResourceIT {

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private FacilityUsageLogRepository facilityUsageLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityUsageLogMockMvc;

    private FacilityUsageLog facilityUsageLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityUsageLog createEntity(EntityManager em) {
        FacilityUsageLog facilityUsageLog = new FacilityUsageLog()
            .fromTime(DEFAULT_FROM_TIME)
            .toTime(DEFAULT_TO_TIME)
            .remarks(DEFAULT_REMARKS);
        return facilityUsageLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityUsageLog createUpdatedEntity(EntityManager em) {
        FacilityUsageLog facilityUsageLog = new FacilityUsageLog()
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .remarks(UPDATED_REMARKS);
        return facilityUsageLog;
    }

    @BeforeEach
    public void initTest() {
        facilityUsageLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityUsageLog() throws Exception {
        int databaseSizeBeforeCreate = facilityUsageLogRepository.findAll().size();
        // Create the FacilityUsageLog
        restFacilityUsageLogMockMvc.perform(post("/api/facility-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityUsageLog)))
            .andExpect(status().isCreated());

        // Validate the FacilityUsageLog in the database
        List<FacilityUsageLog> facilityUsageLogList = facilityUsageLogRepository.findAll();
        assertThat(facilityUsageLogList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityUsageLog testFacilityUsageLog = facilityUsageLogList.get(facilityUsageLogList.size() - 1);
        assertThat(testFacilityUsageLog.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testFacilityUsageLog.getToTime()).isEqualTo(DEFAULT_TO_TIME);
        assertThat(testFacilityUsageLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createFacilityUsageLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityUsageLogRepository.findAll().size();

        // Create the FacilityUsageLog with an existing ID
        facilityUsageLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityUsageLogMockMvc.perform(post("/api/facility-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityUsageLog)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityUsageLog in the database
        List<FacilityUsageLog> facilityUsageLogList = facilityUsageLogRepository.findAll();
        assertThat(facilityUsageLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityUsageLogs() throws Exception {
        // Initialize the database
        facilityUsageLogRepository.saveAndFlush(facilityUsageLog);

        // Get all the facilityUsageLogList
        restFacilityUsageLogMockMvc.perform(get("/api/facility-usage-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityUsageLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getFacilityUsageLog() throws Exception {
        // Initialize the database
        facilityUsageLogRepository.saveAndFlush(facilityUsageLog);

        // Get the facilityUsageLog
        restFacilityUsageLogMockMvc.perform(get("/api/facility-usage-logs/{id}", facilityUsageLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityUsageLog.getId().intValue()))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.toTime").value(DEFAULT_TO_TIME.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityUsageLog() throws Exception {
        // Get the facilityUsageLog
        restFacilityUsageLogMockMvc.perform(get("/api/facility-usage-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityUsageLog() throws Exception {
        // Initialize the database
        facilityUsageLogRepository.saveAndFlush(facilityUsageLog);

        int databaseSizeBeforeUpdate = facilityUsageLogRepository.findAll().size();

        // Update the facilityUsageLog
        FacilityUsageLog updatedFacilityUsageLog = facilityUsageLogRepository.findById(facilityUsageLog.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityUsageLog are not directly saved in db
        em.detach(updatedFacilityUsageLog);
        updatedFacilityUsageLog
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .remarks(UPDATED_REMARKS);

        restFacilityUsageLogMockMvc.perform(put("/api/facility-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityUsageLog)))
            .andExpect(status().isOk());

        // Validate the FacilityUsageLog in the database
        List<FacilityUsageLog> facilityUsageLogList = facilityUsageLogRepository.findAll();
        assertThat(facilityUsageLogList).hasSize(databaseSizeBeforeUpdate);
        FacilityUsageLog testFacilityUsageLog = facilityUsageLogList.get(facilityUsageLogList.size() - 1);
        assertThat(testFacilityUsageLog.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testFacilityUsageLog.getToTime()).isEqualTo(UPDATED_TO_TIME);
        assertThat(testFacilityUsageLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityUsageLog() throws Exception {
        int databaseSizeBeforeUpdate = facilityUsageLogRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityUsageLogMockMvc.perform(put("/api/facility-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityUsageLog)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityUsageLog in the database
        List<FacilityUsageLog> facilityUsageLogList = facilityUsageLogRepository.findAll();
        assertThat(facilityUsageLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityUsageLog() throws Exception {
        // Initialize the database
        facilityUsageLogRepository.saveAndFlush(facilityUsageLog);

        int databaseSizeBeforeDelete = facilityUsageLogRepository.findAll().size();

        // Delete the facilityUsageLog
        restFacilityUsageLogMockMvc.perform(delete("/api/facility-usage-logs/{id}", facilityUsageLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityUsageLog> facilityUsageLogList = facilityUsageLogRepository.findAll();
        assertThat(facilityUsageLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
