package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TimeSheet;
import com.hr.repository.TimeSheetRepository;

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
 * Integration tests for the {@link TimeSheetResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimeSheetResourceIT {

    private static final Integer DEFAULT_OVER_TIME_DAYS = 1;
    private static final Integer UPDATED_OVER_TIME_DAYS = 2;

    private static final Integer DEFAULT_LEAVES = 1;
    private static final Integer UPDATED_LEAVES = 2;

    private static final Integer DEFAULT_UNAPPLIED_LEAVES = 1;
    private static final Integer UPDATED_UNAPPLIED_LEAVES = 2;

    private static final Integer DEFAULT_HALF_DAYS = 1;
    private static final Integer UPDATED_HALF_DAYS = 2;

    private static final Double DEFAULT_TOTAL_WORKING_HOURS = 1D;
    private static final Double UPDATED_TOTAL_WORKING_HOURS = 2D;

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeSheetMockMvc;

    private TimeSheet timeSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .overTimeDays(DEFAULT_OVER_TIME_DAYS)
            .leaves(DEFAULT_LEAVES)
            .unappliedLeaves(DEFAULT_UNAPPLIED_LEAVES)
            .halfDays(DEFAULT_HALF_DAYS)
            .totalWorkingHours(DEFAULT_TOTAL_WORKING_HOURS);
        return timeSheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createUpdatedEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .overTimeDays(UPDATED_OVER_TIME_DAYS)
            .leaves(UPDATED_LEAVES)
            .unappliedLeaves(UPDATED_UNAPPLIED_LEAVES)
            .halfDays(UPDATED_HALF_DAYS)
            .totalWorkingHours(UPDATED_TOTAL_WORKING_HOURS);
        return timeSheet;
    }

    @BeforeEach
    public void initTest() {
        timeSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();
        // Create the TimeSheet
        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getOverTimeDays()).isEqualTo(DEFAULT_OVER_TIME_DAYS);
        assertThat(testTimeSheet.getLeaves()).isEqualTo(DEFAULT_LEAVES);
        assertThat(testTimeSheet.getUnappliedLeaves()).isEqualTo(DEFAULT_UNAPPLIED_LEAVES);
        assertThat(testTimeSheet.getHalfDays()).isEqualTo(DEFAULT_HALF_DAYS);
        assertThat(testTimeSheet.getTotalWorkingHours()).isEqualTo(DEFAULT_TOTAL_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void createTimeSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // Create the TimeSheet with an existing ID
        timeSheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimeSheets() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList
        restTimeSheetMockMvc.perform(get("/api/time-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].overTimeDays").value(hasItem(DEFAULT_OVER_TIME_DAYS)))
            .andExpect(jsonPath("$.[*].leaves").value(hasItem(DEFAULT_LEAVES)))
            .andExpect(jsonPath("$.[*].unappliedLeaves").value(hasItem(DEFAULT_UNAPPLIED_LEAVES)))
            .andExpect(jsonPath("$.[*].halfDays").value(hasItem(DEFAULT_HALF_DAYS)))
            .andExpect(jsonPath("$.[*].totalWorkingHours").value(hasItem(DEFAULT_TOTAL_WORKING_HOURS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get the timeSheet
        restTimeSheetMockMvc.perform(get("/api/time-sheets/{id}", timeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeSheet.getId().intValue()))
            .andExpect(jsonPath("$.overTimeDays").value(DEFAULT_OVER_TIME_DAYS))
            .andExpect(jsonPath("$.leaves").value(DEFAULT_LEAVES))
            .andExpect(jsonPath("$.unappliedLeaves").value(DEFAULT_UNAPPLIED_LEAVES))
            .andExpect(jsonPath("$.halfDays").value(DEFAULT_HALF_DAYS))
            .andExpect(jsonPath("$.totalWorkingHours").value(DEFAULT_TOTAL_WORKING_HOURS.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTimeSheet() throws Exception {
        // Get the timeSheet
        restTimeSheetMockMvc.perform(get("/api/time-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet
        TimeSheet updatedTimeSheet = timeSheetRepository.findById(timeSheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSheet are not directly saved in db
        em.detach(updatedTimeSheet);
        updatedTimeSheet
            .overTimeDays(UPDATED_OVER_TIME_DAYS)
            .leaves(UPDATED_LEAVES)
            .unappliedLeaves(UPDATED_UNAPPLIED_LEAVES)
            .halfDays(UPDATED_HALF_DAYS)
            .totalWorkingHours(UPDATED_TOTAL_WORKING_HOURS);

        restTimeSheetMockMvc.perform(put("/api/time-sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeSheet)))
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getOverTimeDays()).isEqualTo(UPDATED_OVER_TIME_DAYS);
        assertThat(testTimeSheet.getLeaves()).isEqualTo(UPDATED_LEAVES);
        assertThat(testTimeSheet.getUnappliedLeaves()).isEqualTo(UPDATED_UNAPPLIED_LEAVES);
        assertThat(testTimeSheet.getHalfDays()).isEqualTo(UPDATED_HALF_DAYS);
        assertThat(testTimeSheet.getTotalWorkingHours()).isEqualTo(UPDATED_TOTAL_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc.perform(put("/api/time-sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeDelete = timeSheetRepository.findAll().size();

        // Delete the timeSheet
        restTimeSheetMockMvc.perform(delete("/api/time-sheets/{id}", timeSheet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
