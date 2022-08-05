package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PaymentGatewayResponse;
import com.hr.repository.PaymentGatewayResponseRepository;

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
 * Integration tests for the {@link PaymentGatewayResponseResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentGatewayResponseResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GATEWAY_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_GATEWAY_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private PaymentGatewayResponseRepository paymentGatewayResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentGatewayResponseMockMvc;

    private PaymentGatewayResponse paymentGatewayResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGatewayResponse createEntity(EntityManager em) {
        PaymentGatewayResponse paymentGatewayResponse = new PaymentGatewayResponse()
            .amount(DEFAULT_AMOUNT)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .gatewayMessage(DEFAULT_GATEWAY_MESSAGE);
        return paymentGatewayResponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGatewayResponse createUpdatedEntity(EntityManager em) {
        PaymentGatewayResponse paymentGatewayResponse = new PaymentGatewayResponse()
            .amount(UPDATED_AMOUNT)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .gatewayMessage(UPDATED_GATEWAY_MESSAGE);
        return paymentGatewayResponse;
    }

    @BeforeEach
    public void initTest() {
        paymentGatewayResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentGatewayResponse() throws Exception {
        int databaseSizeBeforeCreate = paymentGatewayResponseRepository.findAll().size();
        // Create the PaymentGatewayResponse
        restPaymentGatewayResponseMockMvc.perform(post("/api/payment-gateway-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayResponse)))
            .andExpect(status().isCreated());

        // Validate the PaymentGatewayResponse in the database
        List<PaymentGatewayResponse> paymentGatewayResponseList = paymentGatewayResponseRepository.findAll();
        assertThat(paymentGatewayResponseList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentGatewayResponse testPaymentGatewayResponse = paymentGatewayResponseList.get(paymentGatewayResponseList.size() - 1);
        assertThat(testPaymentGatewayResponse.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPaymentGatewayResponse.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testPaymentGatewayResponse.getGatewayMessage()).isEqualTo(DEFAULT_GATEWAY_MESSAGE);
    }

    @Test
    @Transactional
    public void createPaymentGatewayResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentGatewayResponseRepository.findAll().size();

        // Create the PaymentGatewayResponse with an existing ID
        paymentGatewayResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGatewayResponseMockMvc.perform(post("/api/payment-gateway-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayResponse)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGatewayResponse in the database
        List<PaymentGatewayResponse> paymentGatewayResponseList = paymentGatewayResponseRepository.findAll();
        assertThat(paymentGatewayResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentGatewayResponses() throws Exception {
        // Initialize the database
        paymentGatewayResponseRepository.saveAndFlush(paymentGatewayResponse);

        // Get all the paymentGatewayResponseList
        restPaymentGatewayResponseMockMvc.perform(get("/api/payment-gateway-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGatewayResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].gatewayMessage").value(hasItem(DEFAULT_GATEWAY_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void getPaymentGatewayResponse() throws Exception {
        // Initialize the database
        paymentGatewayResponseRepository.saveAndFlush(paymentGatewayResponse);

        // Get the paymentGatewayResponse
        restPaymentGatewayResponseMockMvc.perform(get("/api/payment-gateway-responses/{id}", paymentGatewayResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGatewayResponse.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.gatewayMessage").value(DEFAULT_GATEWAY_MESSAGE));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentGatewayResponse() throws Exception {
        // Get the paymentGatewayResponse
        restPaymentGatewayResponseMockMvc.perform(get("/api/payment-gateway-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentGatewayResponse() throws Exception {
        // Initialize the database
        paymentGatewayResponseRepository.saveAndFlush(paymentGatewayResponse);

        int databaseSizeBeforeUpdate = paymentGatewayResponseRepository.findAll().size();

        // Update the paymentGatewayResponse
        PaymentGatewayResponse updatedPaymentGatewayResponse = paymentGatewayResponseRepository.findById(paymentGatewayResponse.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentGatewayResponse are not directly saved in db
        em.detach(updatedPaymentGatewayResponse);
        updatedPaymentGatewayResponse
            .amount(UPDATED_AMOUNT)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .gatewayMessage(UPDATED_GATEWAY_MESSAGE);

        restPaymentGatewayResponseMockMvc.perform(put("/api/payment-gateway-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentGatewayResponse)))
            .andExpect(status().isOk());

        // Validate the PaymentGatewayResponse in the database
        List<PaymentGatewayResponse> paymentGatewayResponseList = paymentGatewayResponseRepository.findAll();
        assertThat(paymentGatewayResponseList).hasSize(databaseSizeBeforeUpdate);
        PaymentGatewayResponse testPaymentGatewayResponse = paymentGatewayResponseList.get(paymentGatewayResponseList.size() - 1);
        assertThat(testPaymentGatewayResponse.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentGatewayResponse.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testPaymentGatewayResponse.getGatewayMessage()).isEqualTo(UPDATED_GATEWAY_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentGatewayResponse() throws Exception {
        int databaseSizeBeforeUpdate = paymentGatewayResponseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGatewayResponseMockMvc.perform(put("/api/payment-gateway-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentGatewayResponse)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGatewayResponse in the database
        List<PaymentGatewayResponse> paymentGatewayResponseList = paymentGatewayResponseRepository.findAll();
        assertThat(paymentGatewayResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentGatewayResponse() throws Exception {
        // Initialize the database
        paymentGatewayResponseRepository.saveAndFlush(paymentGatewayResponse);

        int databaseSizeBeforeDelete = paymentGatewayResponseRepository.findAll().size();

        // Delete the paymentGatewayResponse
        restPaymentGatewayResponseMockMvc.perform(delete("/api/payment-gateway-responses/{id}", paymentGatewayResponse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentGatewayResponse> paymentGatewayResponseList = paymentGatewayResponseRepository.findAll();
        assertThat(paymentGatewayResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
