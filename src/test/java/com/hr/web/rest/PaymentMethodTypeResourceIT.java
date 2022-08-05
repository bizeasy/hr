package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PaymentMethodType;
import com.hr.repository.PaymentMethodTypeRepository;

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
 * Integration tests for the {@link PaymentMethodTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentMethodTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PaymentMethodTypeRepository paymentMethodTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMethodTypeMockMvc;

    private PaymentMethodType paymentMethodType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethodType createEntity(EntityManager em) {
        PaymentMethodType paymentMethodType = new PaymentMethodType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return paymentMethodType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethodType createUpdatedEntity(EntityManager em) {
        PaymentMethodType paymentMethodType = new PaymentMethodType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return paymentMethodType;
    }

    @BeforeEach
    public void initTest() {
        paymentMethodType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentMethodType() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodTypeRepository.findAll().size();
        // Create the PaymentMethodType
        restPaymentMethodTypeMockMvc.perform(post("/api/payment-method-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodType)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethodType in the database
        List<PaymentMethodType> paymentMethodTypeList = paymentMethodTypeRepository.findAll();
        assertThat(paymentMethodTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethodType testPaymentMethodType = paymentMethodTypeList.get(paymentMethodTypeList.size() - 1);
        assertThat(testPaymentMethodType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentMethodType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPaymentMethodTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodTypeRepository.findAll().size();

        // Create the PaymentMethodType with an existing ID
        paymentMethodType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodTypeMockMvc.perform(post("/api/payment-method-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodType)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodType in the database
        List<PaymentMethodType> paymentMethodTypeList = paymentMethodTypeRepository.findAll();
        assertThat(paymentMethodTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodTypes() throws Exception {
        // Initialize the database
        paymentMethodTypeRepository.saveAndFlush(paymentMethodType);

        // Get all the paymentMethodTypeList
        restPaymentMethodTypeMockMvc.perform(get("/api/payment-method-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethodType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPaymentMethodType() throws Exception {
        // Initialize the database
        paymentMethodTypeRepository.saveAndFlush(paymentMethodType);

        // Get the paymentMethodType
        restPaymentMethodTypeMockMvc.perform(get("/api/payment-method-types/{id}", paymentMethodType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethodType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentMethodType() throws Exception {
        // Get the paymentMethodType
        restPaymentMethodTypeMockMvc.perform(get("/api/payment-method-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentMethodType() throws Exception {
        // Initialize the database
        paymentMethodTypeRepository.saveAndFlush(paymentMethodType);

        int databaseSizeBeforeUpdate = paymentMethodTypeRepository.findAll().size();

        // Update the paymentMethodType
        PaymentMethodType updatedPaymentMethodType = paymentMethodTypeRepository.findById(paymentMethodType.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethodType are not directly saved in db
        em.detach(updatedPaymentMethodType);
        updatedPaymentMethodType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPaymentMethodTypeMockMvc.perform(put("/api/payment-method-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentMethodType)))
            .andExpect(status().isOk());

        // Validate the PaymentMethodType in the database
        List<PaymentMethodType> paymentMethodTypeList = paymentMethodTypeRepository.findAll();
        assertThat(paymentMethodTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethodType testPaymentMethodType = paymentMethodTypeList.get(paymentMethodTypeList.size() - 1);
        assertThat(testPaymentMethodType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentMethodType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentMethodType() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodTypeMockMvc.perform(put("/api/payment-method-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodType)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodType in the database
        List<PaymentMethodType> paymentMethodTypeList = paymentMethodTypeRepository.findAll();
        assertThat(paymentMethodTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentMethodType() throws Exception {
        // Initialize the database
        paymentMethodTypeRepository.saveAndFlush(paymentMethodType);

        int databaseSizeBeforeDelete = paymentMethodTypeRepository.findAll().size();

        // Delete the paymentMethodType
        restPaymentMethodTypeMockMvc.perform(delete("/api/payment-method-types/{id}", paymentMethodType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethodType> paymentMethodTypeList = paymentMethodTypeRepository.findAll();
        assertThat(paymentMethodTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
