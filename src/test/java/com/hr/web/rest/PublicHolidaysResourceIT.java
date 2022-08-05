package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PublicHolidays;
import com.hr.repository.PublicHolidaysRepository;

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
 * Integration tests for the {@link PublicHolidaysResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PublicHolidaysResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NO_OF_HOLIDAYS = 1;
    private static final Integer UPDATED_NO_OF_HOLIDAYS = 2;

    @Autowired
    private PublicHolidaysRepository publicHolidaysRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicHolidaysMockMvc;

    private PublicHolidays publicHolidays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHolidays createEntity(EntityManager em) {
        PublicHolidays publicHolidays = new PublicHolidays()
            .name(DEFAULT_NAME)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .noOfHolidays(DEFAULT_NO_OF_HOLIDAYS);
        return publicHolidays;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHolidays createUpdatedEntity(EntityManager em) {
        PublicHolidays publicHolidays = new PublicHolidays()
            .name(UPDATED_NAME)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .noOfHolidays(UPDATED_NO_OF_HOLIDAYS);
        return publicHolidays;
    }

    @BeforeEach
    public void initTest() {
        publicHolidays = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicHolidays() throws Exception {
        int databaseSizeBeforeCreate = publicHolidaysRepository.findAll().size();
        // Create the PublicHolidays
        restPublicHolidaysMockMvc.perform(post("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidays)))
            .andExpect(status().isCreated());

        // Validate the PublicHolidays in the database
        List<PublicHolidays> publicHolidaysList = publicHolidaysRepository.findAll();
        assertThat(publicHolidaysList).hasSize(databaseSizeBeforeCreate + 1);
        PublicHolidays testPublicHolidays = publicHolidaysList.get(publicHolidaysList.size() - 1);
        assertThat(testPublicHolidays.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublicHolidays.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPublicHolidays.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPublicHolidays.getNoOfHolidays()).isEqualTo(DEFAULT_NO_OF_HOLIDAYS);
    }

    @Test
    @Transactional
    public void createPublicHolidaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicHolidaysRepository.findAll().size();

        // Create the PublicHolidays with an existing ID
        publicHolidays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicHolidaysMockMvc.perform(post("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidays)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHolidays in the database
        List<PublicHolidays> publicHolidaysList = publicHolidaysRepository.findAll();
        assertThat(publicHolidaysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPublicHolidays() throws Exception {
        // Initialize the database
        publicHolidaysRepository.saveAndFlush(publicHolidays);

        // Get all the publicHolidaysList
        restPublicHolidaysMockMvc.perform(get("/api/public-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHolidays.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].noOfHolidays").value(hasItem(DEFAULT_NO_OF_HOLIDAYS)));
    }
    
    @Test
    @Transactional
    public void getPublicHolidays() throws Exception {
        // Initialize the database
        publicHolidaysRepository.saveAndFlush(publicHolidays);

        // Get the publicHolidays
        restPublicHolidaysMockMvc.perform(get("/api/public-holidays/{id}", publicHolidays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicHolidays.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.noOfHolidays").value(DEFAULT_NO_OF_HOLIDAYS));
    }
    @Test
    @Transactional
    public void getNonExistingPublicHolidays() throws Exception {
        // Get the publicHolidays
        restPublicHolidaysMockMvc.perform(get("/api/public-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicHolidays() throws Exception {
        // Initialize the database
        publicHolidaysRepository.saveAndFlush(publicHolidays);

        int databaseSizeBeforeUpdate = publicHolidaysRepository.findAll().size();

        // Update the publicHolidays
        PublicHolidays updatedPublicHolidays = publicHolidaysRepository.findById(publicHolidays.getId()).get();
        // Disconnect from session so that the updates on updatedPublicHolidays are not directly saved in db
        em.detach(updatedPublicHolidays);
        updatedPublicHolidays
            .name(UPDATED_NAME)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .noOfHolidays(UPDATED_NO_OF_HOLIDAYS);

        restPublicHolidaysMockMvc.perform(put("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicHolidays)))
            .andExpect(status().isOk());

        // Validate the PublicHolidays in the database
        List<PublicHolidays> publicHolidaysList = publicHolidaysRepository.findAll();
        assertThat(publicHolidaysList).hasSize(databaseSizeBeforeUpdate);
        PublicHolidays testPublicHolidays = publicHolidaysList.get(publicHolidaysList.size() - 1);
        assertThat(testPublicHolidays.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicHolidays.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPublicHolidays.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPublicHolidays.getNoOfHolidays()).isEqualTo(UPDATED_NO_OF_HOLIDAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicHolidays() throws Exception {
        int databaseSizeBeforeUpdate = publicHolidaysRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicHolidaysMockMvc.perform(put("/api/public-holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidays)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHolidays in the database
        List<PublicHolidays> publicHolidaysList = publicHolidaysRepository.findAll();
        assertThat(publicHolidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicHolidays() throws Exception {
        // Initialize the database
        publicHolidaysRepository.saveAndFlush(publicHolidays);

        int databaseSizeBeforeDelete = publicHolidaysRepository.findAll().size();

        // Delete the publicHolidays
        restPublicHolidaysMockMvc.perform(delete("/api/public-holidays/{id}", publicHolidays.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicHolidays> publicHolidaysList = publicHolidaysRepository.findAll();
        assertThat(publicHolidaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
