package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PaymentGatewayConfig;
import com.hr.repository.PaymentGatewayConfigRepository;

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
 * Integration tests for the {@link PaymentGatewayConfigResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentGatewayConfigResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_URL = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REFUND_URL = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_URL = "BBBBBBBBBB";

    @Autowired
    private PaymentGatewayConfigRepository paymentGatewayConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentGatewayConfigMockMvc;

    private PaymentGatewayConfig paymentGatewayConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGatewayConfig createEntity(EntityManager em) {
        PaymentGatewayConfig paymentGatewayConfig = new PaymentGatewayConfig()
            .name(DEFAULT_NAME)
            .authUrl(DEFAULT_AUTH_URL)
            .releaseUrl(DEFAULT_RELEASE_URL)
            .refundUrl(DEFAULT_REFUND_URL);
        return paymentGatewayConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGatewayConfig createUpdatedEntity(EntityManager em) {
        PaymentGatewayConfig paymentGatewayConfig = new PaymentGatewayConfig()
            .name(UPDATED_NAME)
            .authUrl(UPDATED_AUTH_URL)
            .releaseUrl(UPDATED_RELEASE_URL)
            .refundUrl(UPDATED_REFUND_URL);
        return paymentGatewayConfig;
    }

    @BeforeEach
    public void initTest() {
        paymentGatewayConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentGatewayConfig() throws Exception {
        int databaseSizeBeforeCreate = paymentGatewayConfigRepository.findAll().size();
        // Create the PaymentGatewayConfig
        restPaymentGatewayConfigMockMvc.perform(post("/api/payment-gateway-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayConfig)))
            .andExpect(status().isCreated());

        // Validate the PaymentGatewayConfig in the database
        List<PaymentGatewayConfig> paymentGatewayConfigList = paymentGatewayConfigRepository.findAll();
        assertThat(paymentGatewayConfigList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentGatewayConfig testPaymentGatewayConfig = paymentGatewayConfigList.get(paymentGatewayConfigList.size() - 1);
        assertThat(testPaymentGatewayConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentGatewayConfig.getAuthUrl()).isEqualTo(DEFAULT_AUTH_URL);
        assertThat(testPaymentGatewayConfig.getReleaseUrl()).isEqualTo(DEFAULT_RELEASE_URL);
        assertThat(testPaymentGatewayConfig.getRefundUrl()).isEqualTo(DEFAULT_REFUND_URL);
    }

    @Test
    @Transactional
    public void createPaymentGatewayConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentGatewayConfigRepository.findAll().size();

        // Create the PaymentGatewayConfig with an existing ID
        paymentGatewayConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGatewayConfigMockMvc.perform(post("/api/payment-gateway-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayConfig)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGatewayConfig in the database
        List<PaymentGatewayConfig> paymentGatewayConfigList = paymentGatewayConfigRepository.findAll();
        assertThat(paymentGatewayConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentGatewayConfigs() throws Exception {
        // Initialize the database
        paymentGatewayConfigRepository.saveAndFlush(paymentGatewayConfig);

        // Get all the paymentGatewayConfigList
        restPaymentGatewayConfigMockMvc.perform(get("/api/payment-gateway-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGatewayConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].authUrl").value(hasItem(DEFAULT_AUTH_URL)))
            .andExpect(jsonPath("$.[*].releaseUrl").value(hasItem(DEFAULT_RELEASE_URL)))
            .andExpect(jsonPath("$.[*].refundUrl").value(hasItem(DEFAULT_REFUND_URL)));
    }
    
    @Test
    @Transactional
    public void getPaymentGatewayConfig() throws Exception {
        // Initialize the database
        paymentGatewayConfigRepository.saveAndFlush(paymentGatewayConfig);

        // Get the paymentGatewayConfig
        restPaymentGatewayConfigMockMvc.perform(get("/api/payment-gateway-configs/{id}", paymentGatewayConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGatewayConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.authUrl").value(DEFAULT_AUTH_URL))
            .andExpect(jsonPath("$.releaseUrl").value(DEFAULT_RELEASE_URL))
            .andExpect(jsonPath("$.refundUrl").value(DEFAULT_REFUND_URL));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentGatewayConfig() throws Exception {
        // Get the paymentGatewayConfig
        restPaymentGatewayConfigMockMvc.perform(get("/api/payment-gateway-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentGatewayConfig() throws Exception {
        // Initialize the database
        paymentGatewayConfigRepository.saveAndFlush(paymentGatewayConfig);

        int databaseSizeBeforeUpdate = paymentGatewayConfigRepository.findAll().size();

        // Update the paymentGatewayConfig
        PaymentGatewayConfig updatedPaymentGatewayConfig = paymentGatewayConfigRepository.findById(paymentGatewayConfig.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentGatewayConfig are not directly saved in db
        em.detach(updatedPaymentGatewayConfig);
        updatedPaymentGatewayConfig
            .name(UPDATED_NAME)
            .authUrl(UPDATED_AUTH_URL)
            .releaseUrl(UPDATED_RELEASE_URL)
            .refundUrl(UPDATED_REFUND_URL);

        restPaymentGatewayConfigMockMvc.perform(put("/api/payment-gateway-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentGatewayConfig)))
            .andExpect(status().isOk());

        // Validate the PaymentGatewayConfig in the database
        List<PaymentGatewayConfig> paymentGatewayConfigList = paymentGatewayConfigRepository.findAll();
        assertThat(paymentGatewayConfigList).hasSize(databaseSizeBeforeUpdate);
        PaymentGatewayConfig testPaymentGatewayConfig = paymentGatewayConfigList.get(paymentGatewayConfigList.size() - 1);
        assertThat(testPaymentGatewayConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentGatewayConfig.getAuthUrl()).isEqualTo(UPDATED_AUTH_URL);
        assertThat(testPaymentGatewayConfig.getReleaseUrl()).isEqualTo(UPDATED_RELEASE_URL);
        assertThat(testPaymentGatewayConfig.getRefundUrl()).isEqualTo(UPDATED_REFUND_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentGatewayConfig() throws Exception {
        int databaseSizeBeforeUpdate = paymentGatewayConfigRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGatewayConfigMockMvc.perform(put("/api/payment-gateway-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayConfig)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGatewayConfig in the database
        List<PaymentGatewayConfig> paymentGatewayConfigList = paymentGatewayConfigRepository.findAll();
        assertThat(paymentGatewayConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentGatewayConfig() throws Exception {
        // Initialize the database
        paymentGatewayConfigRepository.saveAndFlush(paymentGatewayConfig);

        int databaseSizeBeforeDelete = paymentGatewayConfigRepository.findAll().size();

        // Delete the paymentGatewayConfig
        restPaymentGatewayConfigMockMvc.perform(delete("/api/payment-gateway-configs/{id}", paymentGatewayConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentGatewayConfig> paymentGatewayConfigList = paymentGatewayConfigRepository.findAll();
        assertThat(paymentGatewayConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
