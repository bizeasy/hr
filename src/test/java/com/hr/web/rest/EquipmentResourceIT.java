package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Equipment;
import com.hr.repository.EquipmentRepository;

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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hr.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EquipmentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquipmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EQUIPMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_CAPACITY_RANGE = 1D;
    private static final Double UPDATED_MIN_CAPACITY_RANGE = 2D;

    private static final Double DEFAULT_MAX_CAPACITY_RANGE = 1D;
    private static final Double UPDATED_MAX_CAPACITY_RANGE = 2D;

    private static final Double DEFAULT_MIN_OPERATIONAL_RANGE = 1D;
    private static final Double UPDATED_MIN_OPERATIONAL_RANGE = 2D;

    private static final Double DEFAULT_MAX_OPERATIONAL_RANGE = 1D;
    private static final Double UPDATED_MAX_OPERATIONAL_RANGE = 2D;

    private static final ZonedDateTime DEFAULT_LAST_CALIBRATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_CALIBRATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CALIBRATION_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CALIBRATION_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CHANGE_CONTROL_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_CONTROL_NO = "BBBBBBBBBB";

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentMockMvc;

    private Equipment equipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .equipmentNumber(DEFAULT_EQUIPMENT_NUMBER)
            .minCapacityRange(DEFAULT_MIN_CAPACITY_RANGE)
            .maxCapacityRange(DEFAULT_MAX_CAPACITY_RANGE)
            .minOperationalRange(DEFAULT_MIN_OPERATIONAL_RANGE)
            .maxOperationalRange(DEFAULT_MAX_OPERATIONAL_RANGE)
            .lastCalibratedDate(DEFAULT_LAST_CALIBRATED_DATE)
            .calibrationDueDate(DEFAULT_CALIBRATION_DUE_DATE)
            .changeControlNo(DEFAULT_CHANGE_CONTROL_NO);
        return equipment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createUpdatedEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .equipmentNumber(UPDATED_EQUIPMENT_NUMBER)
            .minCapacityRange(UPDATED_MIN_CAPACITY_RANGE)
            .maxCapacityRange(UPDATED_MAX_CAPACITY_RANGE)
            .minOperationalRange(UPDATED_MIN_OPERATIONAL_RANGE)
            .maxOperationalRange(UPDATED_MAX_OPERATIONAL_RANGE)
            .lastCalibratedDate(UPDATED_LAST_CALIBRATED_DATE)
            .calibrationDueDate(UPDATED_CALIBRATION_DUE_DATE)
            .changeControlNo(UPDATED_CHANGE_CONTROL_NO);
        return equipment;
    }

    @BeforeEach
    public void initTest() {
        equipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipment() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();
        // Create the Equipment
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isCreated());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEquipment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEquipment.getEquipmentNumber()).isEqualTo(DEFAULT_EQUIPMENT_NUMBER);
        assertThat(testEquipment.getMinCapacityRange()).isEqualTo(DEFAULT_MIN_CAPACITY_RANGE);
        assertThat(testEquipment.getMaxCapacityRange()).isEqualTo(DEFAULT_MAX_CAPACITY_RANGE);
        assertThat(testEquipment.getMinOperationalRange()).isEqualTo(DEFAULT_MIN_OPERATIONAL_RANGE);
        assertThat(testEquipment.getMaxOperationalRange()).isEqualTo(DEFAULT_MAX_OPERATIONAL_RANGE);
        assertThat(testEquipment.getLastCalibratedDate()).isEqualTo(DEFAULT_LAST_CALIBRATED_DATE);
        assertThat(testEquipment.getCalibrationDueDate()).isEqualTo(DEFAULT_CALIBRATION_DUE_DATE);
        assertThat(testEquipment.getChangeControlNo()).isEqualTo(DEFAULT_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void createEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment with an existing ID
        equipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList
        restEquipmentMockMvc.perform(get("/api/equipment?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].equipmentNumber").value(hasItem(DEFAULT_EQUIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].minCapacityRange").value(hasItem(DEFAULT_MIN_CAPACITY_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxCapacityRange").value(hasItem(DEFAULT_MAX_CAPACITY_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].minOperationalRange").value(hasItem(DEFAULT_MIN_OPERATIONAL_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxOperationalRange").value(hasItem(DEFAULT_MAX_OPERATIONAL_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastCalibratedDate").value(hasItem(sameInstant(DEFAULT_LAST_CALIBRATED_DATE))))
            .andExpect(jsonPath("$.[*].calibrationDueDate").value(hasItem(sameInstant(DEFAULT_CALIBRATION_DUE_DATE))))
            .andExpect(jsonPath("$.[*].changeControlNo").value(hasItem(DEFAULT_CHANGE_CONTROL_NO)));
    }
    
    @Test
    @Transactional
    public void getEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.equipmentNumber").value(DEFAULT_EQUIPMENT_NUMBER))
            .andExpect(jsonPath("$.minCapacityRange").value(DEFAULT_MIN_CAPACITY_RANGE.doubleValue()))
            .andExpect(jsonPath("$.maxCapacityRange").value(DEFAULT_MAX_CAPACITY_RANGE.doubleValue()))
            .andExpect(jsonPath("$.minOperationalRange").value(DEFAULT_MIN_OPERATIONAL_RANGE.doubleValue()))
            .andExpect(jsonPath("$.maxOperationalRange").value(DEFAULT_MAX_OPERATIONAL_RANGE.doubleValue()))
            .andExpect(jsonPath("$.lastCalibratedDate").value(sameInstant(DEFAULT_LAST_CALIBRATED_DATE)))
            .andExpect(jsonPath("$.calibrationDueDate").value(sameInstant(DEFAULT_CALIBRATION_DUE_DATE)))
            .andExpect(jsonPath("$.changeControlNo").value(DEFAULT_CHANGE_CONTROL_NO));
    }
    @Test
    @Transactional
    public void getNonExistingEquipment() throws Exception {
        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment
        Equipment updatedEquipment = equipmentRepository.findById(equipment.getId()).get();
        // Disconnect from session so that the updates on updatedEquipment are not directly saved in db
        em.detach(updatedEquipment);
        updatedEquipment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .equipmentNumber(UPDATED_EQUIPMENT_NUMBER)
            .minCapacityRange(UPDATED_MIN_CAPACITY_RANGE)
            .maxCapacityRange(UPDATED_MAX_CAPACITY_RANGE)
            .minOperationalRange(UPDATED_MIN_OPERATIONAL_RANGE)
            .maxOperationalRange(UPDATED_MAX_OPERATIONAL_RANGE)
            .lastCalibratedDate(UPDATED_LAST_CALIBRATED_DATE)
            .calibrationDueDate(UPDATED_CALIBRATION_DUE_DATE)
            .changeControlNo(UPDATED_CHANGE_CONTROL_NO);

        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipment)))
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEquipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEquipment.getEquipmentNumber()).isEqualTo(UPDATED_EQUIPMENT_NUMBER);
        assertThat(testEquipment.getMinCapacityRange()).isEqualTo(UPDATED_MIN_CAPACITY_RANGE);
        assertThat(testEquipment.getMaxCapacityRange()).isEqualTo(UPDATED_MAX_CAPACITY_RANGE);
        assertThat(testEquipment.getMinOperationalRange()).isEqualTo(UPDATED_MIN_OPERATIONAL_RANGE);
        assertThat(testEquipment.getMaxOperationalRange()).isEqualTo(UPDATED_MAX_OPERATIONAL_RANGE);
        assertThat(testEquipment.getLastCalibratedDate()).isEqualTo(UPDATED_LAST_CALIBRATED_DATE);
        assertThat(testEquipment.getCalibrationDueDate()).isEqualTo(UPDATED_CALIBRATION_DUE_DATE);
        assertThat(testEquipment.getChangeControlNo()).isEqualTo(UPDATED_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        int databaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Delete the equipment
        restEquipmentMockMvc.perform(delete("/api/equipment/{id}", equipment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
