package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ShiftHolidays;
import com.hr.repository.ShiftHolidaysRepository;

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

import com.hr.domain.enumeration.DayOfheWeek;
/**
 * Integration tests for the {@link ShiftHolidaysResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShiftHolidaysResourceIT {

    private static final DayOfheWeek DEFAULT_DAY_OFHE_WEEK = DayOfheWeek.SATURDAY;
    private static final DayOfheWeek UPDATED_DAY_OFHE_WEEK = DayOfheWeek.SUNDAY;

    private static final Integer DEFAULT_MONTHLY_PAID_LEAVES = 1;
    private static final Integer UPDATED_MONTHLY_PAID_LEAVES = 2;

    private static final Integer DEFAULT_YEARLY_PAID_LEAVES = 1;
    private static final Integer UPDATED_YEARLY_PAID_LEAVES = 2;

    @Autowired
    private ShiftHolidaysRepository shiftHolidaysRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShiftHolidaysMockMvc;

    private ShiftHolidays shiftHolidays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShiftHolidays createEntity(EntityManager em) {
        ShiftHolidays shiftHolidays = new ShiftHolidays()
            .dayOfheWeek(DEFAULT_DAY_OFHE_WEEK)
            .monthlyPaidLeaves(DEFAULT_MONTHLY_PAID_LEAVES)
            .yearlyPaidLeaves(DEFAULT_YEARLY_PAID_LEAVES);
        return shiftHolidays;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShiftHolidays createUpdatedEntity(EntityManager em) {
        ShiftHolidays shiftHolidays = new ShiftHolidays()
            .dayOfheWeek(UPDATED_DAY_OFHE_WEEK)
            .monthlyPaidLeaves(UPDATED_MONTHLY_PAID_LEAVES)
            .yearlyPaidLeaves(UPDATED_YEARLY_PAID_LEAVES);
        return shiftHolidays;
    }

    @BeforeEach
    public void initTest() {
        shiftHolidays = createEntity(em);
    }

    @Test
    @Transactional
    public void createShiftHolidays() throws Exception {
        int databaseSizeBeforeCreate = shiftHolidaysRepository.findAll().size();
        // Create the ShiftHolidays
        restShiftHolidaysMockMvc.perform(post("/api/shift-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftHolidays)))
            .andExpect(status().isCreated());

        // Validate the ShiftHolidays in the database
        List<ShiftHolidays> shiftHolidaysList = shiftHolidaysRepository.findAll();
        assertThat(shiftHolidaysList).hasSize(databaseSizeBeforeCreate + 1);
        ShiftHolidays testShiftHolidays = shiftHolidaysList.get(shiftHolidaysList.size() - 1);
        assertThat(testShiftHolidays.getDayOfheWeek()).isEqualTo(DEFAULT_DAY_OFHE_WEEK);
        assertThat(testShiftHolidays.getMonthlyPaidLeaves()).isEqualTo(DEFAULT_MONTHLY_PAID_LEAVES);
        assertThat(testShiftHolidays.getYearlyPaidLeaves()).isEqualTo(DEFAULT_YEARLY_PAID_LEAVES);
    }

    @Test
    @Transactional
    public void createShiftHolidaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shiftHolidaysRepository.findAll().size();

        // Create the ShiftHolidays with an existing ID
        shiftHolidays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShiftHolidaysMockMvc.perform(post("/api/shift-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftHolidays)))
            .andExpect(status().isBadRequest());

        // Validate the ShiftHolidays in the database
        List<ShiftHolidays> shiftHolidaysList = shiftHolidaysRepository.findAll();
        assertThat(shiftHolidaysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShiftHolidays() throws Exception {
        // Initialize the database
        shiftHolidaysRepository.saveAndFlush(shiftHolidays);

        // Get all the shiftHolidaysList
        restShiftHolidaysMockMvc.perform(get("/api/shift-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shiftHolidays.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfheWeek").value(hasItem(DEFAULT_DAY_OFHE_WEEK.toString())))
            .andExpect(jsonPath("$.[*].monthlyPaidLeaves").value(hasItem(DEFAULT_MONTHLY_PAID_LEAVES)))
            .andExpect(jsonPath("$.[*].yearlyPaidLeaves").value(hasItem(DEFAULT_YEARLY_PAID_LEAVES)));
    }
    
    @Test
    @Transactional
    public void getShiftHolidays() throws Exception {
        // Initialize the database
        shiftHolidaysRepository.saveAndFlush(shiftHolidays);

        // Get the shiftHolidays
        restShiftHolidaysMockMvc.perform(get("/api/shift-holidays/{id}", shiftHolidays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shiftHolidays.getId().intValue()))
            .andExpect(jsonPath("$.dayOfheWeek").value(DEFAULT_DAY_OFHE_WEEK.toString()))
            .andExpect(jsonPath("$.monthlyPaidLeaves").value(DEFAULT_MONTHLY_PAID_LEAVES))
            .andExpect(jsonPath("$.yearlyPaidLeaves").value(DEFAULT_YEARLY_PAID_LEAVES));
    }
    @Test
    @Transactional
    public void getNonExistingShiftHolidays() throws Exception {
        // Get the shiftHolidays
        restShiftHolidaysMockMvc.perform(get("/api/shift-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShiftHolidays() throws Exception {
        // Initialize the database
        shiftHolidaysRepository.saveAndFlush(shiftHolidays);

        int databaseSizeBeforeUpdate = shiftHolidaysRepository.findAll().size();

        // Update the shiftHolidays
        ShiftHolidays updatedShiftHolidays = shiftHolidaysRepository.findById(shiftHolidays.getId()).get();
        // Disconnect from session so that the updates on updatedShiftHolidays are not directly saved in db
        em.detach(updatedShiftHolidays);
        updatedShiftHolidays
            .dayOfheWeek(UPDATED_DAY_OFHE_WEEK)
            .monthlyPaidLeaves(UPDATED_MONTHLY_PAID_LEAVES)
            .yearlyPaidLeaves(UPDATED_YEARLY_PAID_LEAVES);

        restShiftHolidaysMockMvc.perform(put("/api/shift-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShiftHolidays)))
            .andExpect(status().isOk());

        // Validate the ShiftHolidays in the database
        List<ShiftHolidays> shiftHolidaysList = shiftHolidaysRepository.findAll();
        assertThat(shiftHolidaysList).hasSize(databaseSizeBeforeUpdate);
        ShiftHolidays testShiftHolidays = shiftHolidaysList.get(shiftHolidaysList.size() - 1);
        assertThat(testShiftHolidays.getDayOfheWeek()).isEqualTo(UPDATED_DAY_OFHE_WEEK);
        assertThat(testShiftHolidays.getMonthlyPaidLeaves()).isEqualTo(UPDATED_MONTHLY_PAID_LEAVES);
        assertThat(testShiftHolidays.getYearlyPaidLeaves()).isEqualTo(UPDATED_YEARLY_PAID_LEAVES);
    }

    @Test
    @Transactional
    public void updateNonExistingShiftHolidays() throws Exception {
        int databaseSizeBeforeUpdate = shiftHolidaysRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShiftHolidaysMockMvc.perform(put("/api/shift-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shiftHolidays)))
            .andExpect(status().isBadRequest());

        // Validate the ShiftHolidays in the database
        List<ShiftHolidays> shiftHolidaysList = shiftHolidaysRepository.findAll();
        assertThat(shiftHolidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShiftHolidays() throws Exception {
        // Initialize the database
        shiftHolidaysRepository.saveAndFlush(shiftHolidays);

        int databaseSizeBeforeDelete = shiftHolidaysRepository.findAll().size();

        // Delete the shiftHolidays
        restShiftHolidaysMockMvc.perform(delete("/api/shift-holidays/{id}", shiftHolidays.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShiftHolidays> shiftHolidaysList = shiftHolidaysRepository.findAll();
        assertThat(shiftHolidaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
