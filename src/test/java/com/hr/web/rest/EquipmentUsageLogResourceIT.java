package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EquipmentUsageLog;
import com.hr.repository.EquipmentUsageLogRepository;

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
 * Integration tests for the {@link EquipmentUsageLogResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquipmentUsageLogResourceIT {

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private EquipmentUsageLogRepository equipmentUsageLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentUsageLogMockMvc;

    private EquipmentUsageLog equipmentUsageLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentUsageLog createEntity(EntityManager em) {
        EquipmentUsageLog equipmentUsageLog = new EquipmentUsageLog()
            .fromTime(DEFAULT_FROM_TIME)
            .toTime(DEFAULT_TO_TIME)
            .remarks(DEFAULT_REMARKS);
        return equipmentUsageLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentUsageLog createUpdatedEntity(EntityManager em) {
        EquipmentUsageLog equipmentUsageLog = new EquipmentUsageLog()
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .remarks(UPDATED_REMARKS);
        return equipmentUsageLog;
    }

    @BeforeEach
    public void initTest() {
        equipmentUsageLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipmentUsageLog() throws Exception {
        int databaseSizeBeforeCreate = equipmentUsageLogRepository.findAll().size();
        // Create the EquipmentUsageLog
        restEquipmentUsageLogMockMvc.perform(post("/api/equipment-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipmentUsageLog)))
            .andExpect(status().isCreated());

        // Validate the EquipmentUsageLog in the database
        List<EquipmentUsageLog> equipmentUsageLogList = equipmentUsageLogRepository.findAll();
        assertThat(equipmentUsageLogList).hasSize(databaseSizeBeforeCreate + 1);
        EquipmentUsageLog testEquipmentUsageLog = equipmentUsageLogList.get(equipmentUsageLogList.size() - 1);
        assertThat(testEquipmentUsageLog.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testEquipmentUsageLog.getToTime()).isEqualTo(DEFAULT_TO_TIME);
        assertThat(testEquipmentUsageLog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createEquipmentUsageLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentUsageLogRepository.findAll().size();

        // Create the EquipmentUsageLog with an existing ID
        equipmentUsageLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentUsageLogMockMvc.perform(post("/api/equipment-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipmentUsageLog)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentUsageLog in the database
        List<EquipmentUsageLog> equipmentUsageLogList = equipmentUsageLogRepository.findAll();
        assertThat(equipmentUsageLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEquipmentUsageLogs() throws Exception {
        // Initialize the database
        equipmentUsageLogRepository.saveAndFlush(equipmentUsageLog);

        // Get all the equipmentUsageLogList
        restEquipmentUsageLogMockMvc.perform(get("/api/equipment-usage-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentUsageLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getEquipmentUsageLog() throws Exception {
        // Initialize the database
        equipmentUsageLogRepository.saveAndFlush(equipmentUsageLog);

        // Get the equipmentUsageLog
        restEquipmentUsageLogMockMvc.perform(get("/api/equipment-usage-logs/{id}", equipmentUsageLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentUsageLog.getId().intValue()))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.toTime").value(DEFAULT_TO_TIME.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }
    @Test
    @Transactional
    public void getNonExistingEquipmentUsageLog() throws Exception {
        // Get the equipmentUsageLog
        restEquipmentUsageLogMockMvc.perform(get("/api/equipment-usage-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipmentUsageLog() throws Exception {
        // Initialize the database
        equipmentUsageLogRepository.saveAndFlush(equipmentUsageLog);

        int databaseSizeBeforeUpdate = equipmentUsageLogRepository.findAll().size();

        // Update the equipmentUsageLog
        EquipmentUsageLog updatedEquipmentUsageLog = equipmentUsageLogRepository.findById(equipmentUsageLog.getId()).get();
        // Disconnect from session so that the updates on updatedEquipmentUsageLog are not directly saved in db
        em.detach(updatedEquipmentUsageLog);
        updatedEquipmentUsageLog
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .remarks(UPDATED_REMARKS);

        restEquipmentUsageLogMockMvc.perform(put("/api/equipment-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipmentUsageLog)))
            .andExpect(status().isOk());

        // Validate the EquipmentUsageLog in the database
        List<EquipmentUsageLog> equipmentUsageLogList = equipmentUsageLogRepository.findAll();
        assertThat(equipmentUsageLogList).hasSize(databaseSizeBeforeUpdate);
        EquipmentUsageLog testEquipmentUsageLog = equipmentUsageLogList.get(equipmentUsageLogList.size() - 1);
        assertThat(testEquipmentUsageLog.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testEquipmentUsageLog.getToTime()).isEqualTo(UPDATED_TO_TIME);
        assertThat(testEquipmentUsageLog.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipmentUsageLog() throws Exception {
        int databaseSizeBeforeUpdate = equipmentUsageLogRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentUsageLogMockMvc.perform(put("/api/equipment-usage-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipmentUsageLog)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentUsageLog in the database
        List<EquipmentUsageLog> equipmentUsageLogList = equipmentUsageLogRepository.findAll();
        assertThat(equipmentUsageLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipmentUsageLog() throws Exception {
        // Initialize the database
        equipmentUsageLogRepository.saveAndFlush(equipmentUsageLog);

        int databaseSizeBeforeDelete = equipmentUsageLogRepository.findAll().size();

        // Delete the equipmentUsageLog
        restEquipmentUsageLogMockMvc.perform(delete("/api/equipment-usage-logs/{id}", equipmentUsageLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EquipmentUsageLog> equipmentUsageLogList = equipmentUsageLogRepository.findAll();
        assertThat(equipmentUsageLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
