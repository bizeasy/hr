package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ShiftWeekends;
import com.hr.repository.ShiftWeekendsRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShiftWeekendsResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShiftWeekendsResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ShiftWeekendsRepository shiftWeekendsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShiftWeekendsMockMvc;

    private ShiftWeekends shiftWeekends;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShiftWeekends createEntity(EntityManager em) {
        ShiftWeekends shiftWeekends = new ShiftWeekends()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return shiftWeekends;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShiftWeekends createUpdatedEntity(EntityManager em) {
        ShiftWeekends shiftWeekends = new ShiftWeekends()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return shiftWeekends;
    }

    @BeforeEach
    public void initTest() {
        shiftWeekends = createEntity(em);
    }

    @Test
    @Transactional
    public void createShiftWeekends() throws Exception {
        int databaseSizeBeforeCreate = shiftWeekendsRepository.findAll().size();
        // Create the ShiftWeekends
        restShiftWeekendsMockMvc.perform(post("/api/shift-weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftWeekends)))
            .andExpect(status().isCreated());

        // Validate the ShiftWeekends in the database
        List<ShiftWeekends> shiftWeekendsList = shiftWeekendsRepository.findAll();
        assertThat(shiftWeekendsList).hasSize(databaseSizeBeforeCreate + 1);
        ShiftWeekends testShiftWeekends = shiftWeekendsList.get(shiftWeekendsList.size() - 1);
        assertThat(testShiftWeekends.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testShiftWeekends.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createShiftWeekendsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shiftWeekendsRepository.findAll().size();

        // Create the ShiftWeekends with an existing ID
        shiftWeekends.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShiftWeekendsMockMvc.perform(post("/api/shift-weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftWeekends)))
            .andExpect(status().isBadRequest());

        // Validate the ShiftWeekends in the database
        List<ShiftWeekends> shiftWeekendsList = shiftWeekendsRepository.findAll();
        assertThat(shiftWeekendsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShiftWeekends() throws Exception {
        // Initialize the database
        shiftWeekendsRepository.saveAndFlush(shiftWeekends);

        // Get all the shiftWeekendsList
        restShiftWeekendsMockMvc.perform(get("/api/shift-weekends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shiftWeekends.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getShiftWeekends() throws Exception {
        // Initialize the database
        shiftWeekendsRepository.saveAndFlush(shiftWeekends);

        // Get the shiftWeekends
        restShiftWeekendsMockMvc.perform(get("/api/shift-weekends/{id}", shiftWeekends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shiftWeekends.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingShiftWeekends() throws Exception {
        // Get the shiftWeekends
        restShiftWeekendsMockMvc.perform(get("/api/shift-weekends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShiftWeekends() throws Exception {
        // Initialize the database
        shiftWeekendsRepository.saveAndFlush(shiftWeekends);

        int databaseSizeBeforeUpdate = shiftWeekendsRepository.findAll().size();

        // Update the shiftWeekends
        ShiftWeekends updatedShiftWeekends = shiftWeekendsRepository.findById(shiftWeekends.getId()).get();
        // Disconnect from session so that the updates on updatedShiftWeekends are not directly saved in db
        em.detach(updatedShiftWeekends);
        updatedShiftWeekends
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restShiftWeekendsMockMvc.perform(put("/api/shift-weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShiftWeekends)))
            .andExpect(status().isOk());

        // Validate the ShiftWeekends in the database
        List<ShiftWeekends> shiftWeekendsList = shiftWeekendsRepository.findAll();
        assertThat(shiftWeekendsList).hasSize(databaseSizeBeforeUpdate);
        ShiftWeekends testShiftWeekends = shiftWeekendsList.get(shiftWeekendsList.size() - 1);
        assertThat(testShiftWeekends.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testShiftWeekends.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingShiftWeekends() throws Exception {
        int databaseSizeBeforeUpdate = shiftWeekendsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShiftWeekendsMockMvc.perform(put("/api/shift-weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftWeekends)))
            .andExpect(status().isBadRequest());

        // Validate the ShiftWeekends in the database
        List<ShiftWeekends> shiftWeekendsList = shiftWeekendsRepository.findAll();
        assertThat(shiftWeekendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShiftWeekends() throws Exception {
        // Initialize the database
        shiftWeekendsRepository.saveAndFlush(shiftWeekends);

        int databaseSizeBeforeDelete = shiftWeekendsRepository.findAll().size();

        // Delete the shiftWeekends
        restShiftWeekendsMockMvc.perform(delete("/api/shift-weekends/{id}", shiftWeekends.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShiftWeekends> shiftWeekendsList = shiftWeekendsRepository.findAll();
        assertThat(shiftWeekendsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
