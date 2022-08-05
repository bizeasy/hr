package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PayrollPreference;
import com.hr.repository.PayrollPreferenceRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PayrollPreferenceResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PayrollPreferenceResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FLAT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FLAT_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    @Autowired
    private PayrollPreferenceRepository payrollPreferenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollPreferenceMockMvc;

    private PayrollPreference payrollPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollPreference createEntity(EntityManager em) {
        PayrollPreference payrollPreference = new PayrollPreference()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .percentage(DEFAULT_PERCENTAGE)
            .flatAmount(DEFAULT_FLAT_AMOUNT)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE)
            .branch(DEFAULT_BRANCH);
        return payrollPreference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollPreference createUpdatedEntity(EntityManager em) {
        PayrollPreference payrollPreference = new PayrollPreference()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .percentage(UPDATED_PERCENTAGE)
            .flatAmount(UPDATED_FLAT_AMOUNT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branch(UPDATED_BRANCH);
        return payrollPreference;
    }

    @BeforeEach
    public void initTest() {
        payrollPreference = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollPreference() throws Exception {
        int databaseSizeBeforeCreate = payrollPreferenceRepository.findAll().size();
        // Create the PayrollPreference
        restPayrollPreferenceMockMvc.perform(post("/api/payroll-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollPreference)))
            .andExpect(status().isCreated());

        // Validate the PayrollPreference in the database
        List<PayrollPreference> payrollPreferenceList = payrollPreferenceRepository.findAll();
        assertThat(payrollPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollPreference testPayrollPreference = payrollPreferenceList.get(payrollPreferenceList.size() - 1);
        assertThat(testPayrollPreference.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPayrollPreference.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPayrollPreference.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testPayrollPreference.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testPayrollPreference.getFlatAmount()).isEqualTo(DEFAULT_FLAT_AMOUNT);
        assertThat(testPayrollPreference.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPayrollPreference.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPayrollPreference.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testPayrollPreference.getBranch()).isEqualTo(DEFAULT_BRANCH);
    }

    @Test
    @Transactional
    public void createPayrollPreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollPreferenceRepository.findAll().size();

        // Create the PayrollPreference with an existing ID
        payrollPreference.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollPreferenceMockMvc.perform(post("/api/payroll-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollPreference)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollPreference in the database
        List<PayrollPreference> payrollPreferenceList = payrollPreferenceRepository.findAll();
        assertThat(payrollPreferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayrollPreferences() throws Exception {
        // Initialize the database
        payrollPreferenceRepository.saveAndFlush(payrollPreference);

        // Get all the payrollPreferenceList
        restPayrollPreferenceMockMvc.perform(get("/api/payroll-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].flatAmount").value(hasItem(DEFAULT_FLAT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)));
    }
    
    @Test
    @Transactional
    public void getPayrollPreference() throws Exception {
        // Initialize the database
        payrollPreferenceRepository.saveAndFlush(payrollPreference);

        // Get the payrollPreference
        restPayrollPreferenceMockMvc.perform(get("/api/payroll-preferences/{id}", payrollPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payrollPreference.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.flatAmount").value(DEFAULT_FLAT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH));
    }
    @Test
    @Transactional
    public void getNonExistingPayrollPreference() throws Exception {
        // Get the payrollPreference
        restPayrollPreferenceMockMvc.perform(get("/api/payroll-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollPreference() throws Exception {
        // Initialize the database
        payrollPreferenceRepository.saveAndFlush(payrollPreference);

        int databaseSizeBeforeUpdate = payrollPreferenceRepository.findAll().size();

        // Update the payrollPreference
        PayrollPreference updatedPayrollPreference = payrollPreferenceRepository.findById(payrollPreference.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollPreference are not directly saved in db
        em.detach(updatedPayrollPreference);
        updatedPayrollPreference
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .percentage(UPDATED_PERCENTAGE)
            .flatAmount(UPDATED_FLAT_AMOUNT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branch(UPDATED_BRANCH);

        restPayrollPreferenceMockMvc.perform(put("/api/payroll-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollPreference)))
            .andExpect(status().isOk());

        // Validate the PayrollPreference in the database
        List<PayrollPreference> payrollPreferenceList = payrollPreferenceRepository.findAll();
        assertThat(payrollPreferenceList).hasSize(databaseSizeBeforeUpdate);
        PayrollPreference testPayrollPreference = payrollPreferenceList.get(payrollPreferenceList.size() - 1);
        assertThat(testPayrollPreference.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPayrollPreference.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPayrollPreference.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testPayrollPreference.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testPayrollPreference.getFlatAmount()).isEqualTo(UPDATED_FLAT_AMOUNT);
        assertThat(testPayrollPreference.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPayrollPreference.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPayrollPreference.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testPayrollPreference.getBranch()).isEqualTo(UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollPreference() throws Exception {
        int databaseSizeBeforeUpdate = payrollPreferenceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollPreferenceMockMvc.perform(put("/api/payroll-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payrollPreference)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollPreference in the database
        List<PayrollPreference> payrollPreferenceList = payrollPreferenceRepository.findAll();
        assertThat(payrollPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayrollPreference() throws Exception {
        // Initialize the database
        payrollPreferenceRepository.saveAndFlush(payrollPreference);

        int databaseSizeBeforeDelete = payrollPreferenceRepository.findAll().size();

        // Delete the payrollPreference
        restPayrollPreferenceMockMvc.perform(delete("/api/payroll-preferences/{id}", payrollPreference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayrollPreference> payrollPreferenceList = payrollPreferenceRepository.findAll();
        assertThat(payrollPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
