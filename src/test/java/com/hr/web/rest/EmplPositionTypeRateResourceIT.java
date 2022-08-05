package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPositionTypeRate;
import com.hr.repository.EmplPositionTypeRateRepository;

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
 * Integration tests for the {@link EmplPositionTypeRateResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionTypeRateResourceIT {

    private static final BigDecimal DEFAULT_RATE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmplPositionTypeRateRepository emplPositionTypeRateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionTypeRateMockMvc;

    private EmplPositionTypeRate emplPositionTypeRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionTypeRate createEntity(EntityManager em) {
        EmplPositionTypeRate emplPositionTypeRate = new EmplPositionTypeRate()
            .rateAmount(DEFAULT_RATE_AMOUNT)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return emplPositionTypeRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionTypeRate createUpdatedEntity(EntityManager em) {
        EmplPositionTypeRate emplPositionTypeRate = new EmplPositionTypeRate()
            .rateAmount(UPDATED_RATE_AMOUNT)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return emplPositionTypeRate;
    }

    @BeforeEach
    public void initTest() {
        emplPositionTypeRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPositionTypeRate() throws Exception {
        int databaseSizeBeforeCreate = emplPositionTypeRateRepository.findAll().size();
        // Create the EmplPositionTypeRate
        restEmplPositionTypeRateMockMvc.perform(post("/api/empl-position-type-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionTypeRate)))
            .andExpect(status().isCreated());

        // Validate the EmplPositionTypeRate in the database
        List<EmplPositionTypeRate> emplPositionTypeRateList = emplPositionTypeRateRepository.findAll();
        assertThat(emplPositionTypeRateList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPositionTypeRate testEmplPositionTypeRate = emplPositionTypeRateList.get(emplPositionTypeRateList.size() - 1);
        assertThat(testEmplPositionTypeRate.getRateAmount()).isEqualTo(DEFAULT_RATE_AMOUNT);
        assertThat(testEmplPositionTypeRate.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmplPositionTypeRate.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createEmplPositionTypeRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionTypeRateRepository.findAll().size();

        // Create the EmplPositionTypeRate with an existing ID
        emplPositionTypeRate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionTypeRateMockMvc.perform(post("/api/empl-position-type-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionTypeRate)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionTypeRate in the database
        List<EmplPositionTypeRate> emplPositionTypeRateList = emplPositionTypeRateRepository.findAll();
        assertThat(emplPositionTypeRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositionTypeRates() throws Exception {
        // Initialize the database
        emplPositionTypeRateRepository.saveAndFlush(emplPositionTypeRate);

        // Get all the emplPositionTypeRateList
        restEmplPositionTypeRateMockMvc.perform(get("/api/empl-position-type-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPositionTypeRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateAmount").value(hasItem(DEFAULT_RATE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmplPositionTypeRate() throws Exception {
        // Initialize the database
        emplPositionTypeRateRepository.saveAndFlush(emplPositionTypeRate);

        // Get the emplPositionTypeRate
        restEmplPositionTypeRateMockMvc.perform(get("/api/empl-position-type-rates/{id}", emplPositionTypeRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPositionTypeRate.getId().intValue()))
            .andExpect(jsonPath("$.rateAmount").value(DEFAULT_RATE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPositionTypeRate() throws Exception {
        // Get the emplPositionTypeRate
        restEmplPositionTypeRateMockMvc.perform(get("/api/empl-position-type-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPositionTypeRate() throws Exception {
        // Initialize the database
        emplPositionTypeRateRepository.saveAndFlush(emplPositionTypeRate);

        int databaseSizeBeforeUpdate = emplPositionTypeRateRepository.findAll().size();

        // Update the emplPositionTypeRate
        EmplPositionTypeRate updatedEmplPositionTypeRate = emplPositionTypeRateRepository.findById(emplPositionTypeRate.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPositionTypeRate are not directly saved in db
        em.detach(updatedEmplPositionTypeRate);
        updatedEmplPositionTypeRate
            .rateAmount(UPDATED_RATE_AMOUNT)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restEmplPositionTypeRateMockMvc.perform(put("/api/empl-position-type-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPositionTypeRate)))
            .andExpect(status().isOk());

        // Validate the EmplPositionTypeRate in the database
        List<EmplPositionTypeRate> emplPositionTypeRateList = emplPositionTypeRateRepository.findAll();
        assertThat(emplPositionTypeRateList).hasSize(databaseSizeBeforeUpdate);
        EmplPositionTypeRate testEmplPositionTypeRate = emplPositionTypeRateList.get(emplPositionTypeRateList.size() - 1);
        assertThat(testEmplPositionTypeRate.getRateAmount()).isEqualTo(UPDATED_RATE_AMOUNT);
        assertThat(testEmplPositionTypeRate.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmplPositionTypeRate.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPositionTypeRate() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionTypeRateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionTypeRateMockMvc.perform(put("/api/empl-position-type-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionTypeRate)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionTypeRate in the database
        List<EmplPositionTypeRate> emplPositionTypeRateList = emplPositionTypeRateRepository.findAll();
        assertThat(emplPositionTypeRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPositionTypeRate() throws Exception {
        // Initialize the database
        emplPositionTypeRateRepository.saveAndFlush(emplPositionTypeRate);

        int databaseSizeBeforeDelete = emplPositionTypeRateRepository.findAll().size();

        // Delete the emplPositionTypeRate
        restEmplPositionTypeRateMockMvc.perform(delete("/api/empl-position-type-rates/{id}", emplPositionTypeRate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPositionTypeRate> emplPositionTypeRateList = emplPositionTypeRateRepository.findAll();
        assertThat(emplPositionTypeRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
